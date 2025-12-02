package br.ufpr.sistemaavaliacao.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespostaDAO {
    private Connection connection;

    public RespostaDAO(Connection connection) {
        this.connection = connection;
    }

  
     
    public boolean alunoJaRespondeu(int alunoUsuarioId, int formularioId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM avaliacoes WHERE aluno_usuario_id = ? AND formulario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, alunoUsuarioId);
            stmt.setInt(2, formularioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    
    public int criarAvaliacao(int alunoUsuarioId, int formularioId) throws SQLException {
        String sql = "INSERT INTO avaliacoes (aluno_usuario_id, formulario_id, data_submissao) VALUES (?, ?, NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, alunoUsuarioId);
            stmt.setInt(2, formularioId);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public int buscarAvaliacaoId(int alunoUsuarioId, int formularioId) throws SQLException {
        String sql = "SELECT id FROM avaliacoes WHERE aluno_usuario_id = ? AND formulario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, alunoUsuarioId);
            stmt.setInt(2, formularioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }

    
    public void salvarRespostaAberta(int avaliacaoId, int questaoId, String textoResposta) throws SQLException {
        // Primeiro cria o registro na tabela respostas
        String sqlResposta = "INSERT INTO respostas (avaliacao_id, questao_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sqlResposta, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, avaliacaoId);
            stmt.setInt(2, questaoId);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int respostaId = rs.getInt(1);
                
                // Depois insere o texto na tabela respostas_abertas
                String sqlAberta = "INSERT INTO respostas_abertas (resposta_id, texto_resposta) VALUES (?, ?)";
                try (PreparedStatement stmtAberta = connection.prepareStatement(sqlAberta)) {
                    stmtAberta.setInt(1, respostaId);
                    stmtAberta.setString(2, textoResposta);
                    stmtAberta.executeUpdate();
                }
            }
        }
    }

   
    public void salvarRespostaMultiplaEscolha(int avaliacaoId, int questaoId, List<Integer> alternativaIds) throws SQLException {
        // Primeiro cria o registro na tabela respostas
        String sqlResposta = "INSERT INTO respostas (avaliacao_id, questao_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sqlResposta, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, avaliacaoId);
            stmt.setInt(2, questaoId);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int respostaId = rs.getInt(1);
                
                // Depois insere as alternativas selecionadas
                String sqlAlternativas = "INSERT INTO respostas_multipla_escolha_alternativas (resposta_id, alternativa_id) VALUES (?, ?)";
                try (PreparedStatement stmtAlt = connection.prepareStatement(sqlAlternativas)) {
                    for (Integer altId : alternativaIds) {
                        stmtAlt.setInt(1, respostaId);
                        stmtAlt.setInt(2, altId);
                        stmtAlt.addBatch();
                    }
                    stmtAlt.executeBatch();
                }
            }
        }
    }

   
    public void removerRespostasAvaliacao(int avaliacaoId) throws SQLException {
        // Buscar IDs das respostas
        String sqlBuscar = "SELECT id FROM respostas WHERE avaliacao_id = ?";
        List<Integer> respostaIds = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sqlBuscar)) {
            stmt.setInt(1, avaliacaoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                respostaIds.add(rs.getInt("id"));
            }
        }

        if (!respostaIds.isEmpty()) {
            
            String sqlRemoverAlt = "DELETE FROM respostas_multipla_escolha_alternativas WHERE resposta_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sqlRemoverAlt)) {
                for (Integer id : respostaIds) {
                    stmt.setInt(1, id);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            
            String sqlRemoverAbertas = "DELETE FROM respostas_abertas WHERE resposta_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sqlRemoverAbertas)) {
                for (Integer id : respostaIds) {
                    stmt.setInt(1, id);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            
            String sqlRemoverRespostas = "DELETE FROM respostas WHERE avaliacao_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sqlRemoverRespostas)) {
                stmt.setInt(1, avaliacaoId);
                stmt.executeUpdate();
            }
        }
    }

   
    public Map<Integer, Object> buscarRespostasAluno(int avaliacaoId) throws SQLException {
        Map<Integer, Object> respostas = new HashMap<>();
        
        String sql = "SELECT r.id, r.questao_id, q.tipo, " +
                     "ra.texto_resposta, " +
                     "GROUP_CONCAT(rmea.alternativa_id) as alternativas " +
                     "FROM respostas r " +
                     "INNER JOIN questoes q ON r.questao_id = q.id " +
                     "LEFT JOIN respostas_abertas ra ON r.id = ra.resposta_id " +
                     "LEFT JOIN respostas_multipla_escolha_alternativas rmea ON r.id = rmea.resposta_id " +
                     "WHERE r.avaliacao_id = ? " +
                     "GROUP BY r.id, r.questao_id, q.tipo, ra.texto_resposta";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, avaliacaoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int questaoId = rs.getInt("questao_id");
                String tipo = rs.getString("tipo");
                
                if ("aberta".equals(tipo)) {
                    respostas.put(questaoId, rs.getString("texto_resposta"));
                } else {
                    String alternativasStr = rs.getString("alternativas");
                    if (alternativasStr != null) {
                        String[] ids = alternativasStr.split(",");
                        List<Integer> alternativaIds = new ArrayList<>();
                        for (String id : ids) {
                            alternativaIds.add(Integer.parseInt(id));
                        }
                        respostas.put(questaoId, alternativaIds);
                    }
                }
            }
        }
        
        return respostas;
    }

   
    public void atualizarDataSubmissao(int avaliacaoId) throws SQLException {
        String sql = "UPDATE avaliacoes SET data_submissao = NOW() WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, avaliacaoId);
            stmt.executeUpdate();
        }
    }
}
