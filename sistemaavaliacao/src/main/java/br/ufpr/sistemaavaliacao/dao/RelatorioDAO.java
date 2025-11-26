package br.ufpr.sistemaavaliacao.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioDAO {
    private Connection connection;

    public RelatorioDAO(Connection connection) {
        this.connection = connection;
    }

   
    public List<Map<String, Object>> buscarEstatisticasMultiplaEscolha(int formularioId) throws SQLException {
        List<Map<String, Object>> estatisticas = new ArrayList<>();
        
        String sql = "SELECT " +
                     "q.id as questao_id, " +
                     "q.enunciado, " +
                     "a.id as alternativa_id, " +
                     "a.texto as alternativa_texto, " +
                     "a.peso, " +
                     "COUNT(DISTINCT av.id) as total_respostas, " +
                     "COUNT(rmea.alternativa_id) as contagem_alternativa " +
                     "FROM questoes q " +
                     "INNER JOIN questoes_multipla_escolha qme ON q.id = qme.questao_id " +
                     "INNER JOIN alternativas a ON qme.questao_id = a.questao_multipla_escolha_id " +
                     "LEFT JOIN respostas r ON q.id = r.questao_id " +
                     "LEFT JOIN avaliacoes av ON r.avaliacao_id = av.id AND av.formulario_id = ? " +
                     "LEFT JOIN respostas_multipla_escolha_alternativas rmea ON r.id = rmea.resposta_id AND a.id = rmea.alternativa_id " +
                     "WHERE q.formulario_id = ? " +
                     "GROUP BY q.id, q.enunciado, a.id, a.texto, a.peso " +
                     "ORDER BY q.id, a.id";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, formularioId);
            stmt.setInt(2, formularioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> linha = new HashMap<>();
                linha.put("questao_id", rs.getInt("questao_id"));
                linha.put("enunciado", rs.getString("enunciado"));
                linha.put("alternativa_id", rs.getInt("alternativa_id"));
                linha.put("alternativa_texto", rs.getString("alternativa_texto"));
                linha.put("peso", rs.getInt("peso"));
                linha.put("total_respostas", rs.getInt("total_respostas"));
                linha.put("contagem_alternativa", rs.getInt("contagem_alternativa"));
                
                estatisticas.add(linha);
            }
        }
        
        return estatisticas;
    }

    
    public List<Map<String, Object>> buscarRespostasAbertas(int formularioId) throws SQLException {
        List<Map<String, Object>> respostas = new ArrayList<>();
        
        String sql = "SELECT " +
                     "q.id as questao_id, " +
                     "q.enunciado, " +
                     "ra.texto_resposta, " +
                     "av.data_submissao " +
                     "FROM questoes q " +
                     "INNER JOIN respostas r ON q.id = r.questao_id " +
                     "INNER JOIN respostas_abertas ra ON r.id = ra.resposta_id " +
                     "INNER JOIN avaliacoes av ON r.avaliacao_id = av.id " +
                     "WHERE q.formulario_id = ? AND q.tipo = 'aberta' " +
                     "ORDER BY q.id, av.data_submissao DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, formularioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> linha = new HashMap<>();
                linha.put("questao_id", rs.getInt("questao_id"));
                linha.put("enunciado", rs.getString("enunciado"));
                linha.put("texto_resposta", rs.getString("texto_resposta"));
                linha.put("data_submissao", rs.getTimestamp("data_submissao"));
                
                respostas.add(linha);
            }
        }
        
        return respostas;
    }


    public int contarSubmissoes(int formularioId) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM avaliacoes WHERE formulario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, formularioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

   
    public Map<String, Object> buscarInfoFormulario(int formularioId) throws SQLException {
        String sql = "SELECT f.id, f.titulo, f.is_anonimo, " +
                     "pa.nome as processo_nome, pa.data_inicio, pa.data_fim " +
                     "FROM formularios f " +
                     "INNER JOIN processos_avaliativos pa ON f.processo_avaliativo_id = pa.id " +
                     "WHERE f.id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, formularioId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Map<String, Object> info = new HashMap<>();
                info.put("id", rs.getInt("id"));
                info.put("titulo", rs.getString("titulo"));
                info.put("is_anonimo", rs.getBoolean("is_anonimo"));
                info.put("processo_nome", rs.getString("processo_nome"));
                info.put("data_inicio", rs.getDate("data_inicio"));
                info.put("data_fim", rs.getDate("data_fim"));
                
                return info;
            }
        }
        
        return null;
    }

   
    public List<Map<String, Object>> exportarDadosBrutos(int formularioId, boolean incluirIdentificacao) throws SQLException {
        List<Map<String, Object>> dados = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT av.id as avaliacao_id, ");
        
        if (incluirIdentificacao) {
            sql.append("u.nome as aluno_nome, al.matricula, ");
        }
        
        sql.append("av.data_submissao, ")
           .append("q.id as questao_id, q.enunciado, q.tipo, ")
           .append("ra.texto_resposta, ")
           .append("GROUP_CONCAT(a.texto SEPARATOR '; ') as alternativas_selecionadas ")
           .append("FROM avaliacoes av ");
        
        if (incluirIdentificacao) {
            sql.append("INNER JOIN alunos al ON av.aluno_usuario_id = al.usuario_id ")
               .append("INNER JOIN usuarios u ON al.usuario_id = u.id ");
        }
        
        sql.append("INNER JOIN respostas r ON av.id = r.avaliacao_id ")
           .append("INNER JOIN questoes q ON r.questao_id = q.id ")
           .append("LEFT JOIN respostas_abertas ra ON r.id = ra.resposta_id ")
           .append("LEFT JOIN respostas_multipla_escolha_alternativas rmea ON r.id = rmea.resposta_id ")
           .append("LEFT JOIN alternativas a ON rmea.alternativa_id = a.id ")
           .append("WHERE av.formulario_id = ? ")
           .append("GROUP BY av.id, av.data_submissao, q.id, q.enunciado, q.tipo, ra.texto_resposta");
        
        if (incluirIdentificacao) {
            sql.append(", u.nome, al.matricula");
        }
        
        sql.append(" ORDER BY av.id, q.id");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            stmt.setInt(1, formularioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> linha = new HashMap<>();
                linha.put("avaliacao_id", rs.getInt("avaliacao_id"));
                
                if (incluirIdentificacao) {
                    linha.put("aluno_nome", rs.getString("aluno_nome"));
                    linha.put("matricula", rs.getString("matricula"));
                }
                
                linha.put("data_submissao", rs.getTimestamp("data_submissao"));
                linha.put("questao_id", rs.getInt("questao_id"));
                linha.put("enunciado", rs.getString("enunciado"));
                linha.put("tipo", rs.getString("tipo"));
                linha.put("texto_resposta", rs.getString("texto_resposta"));
                linha.put("alternativas_selecionadas", rs.getString("alternativas_selecionadas"));
                
                dados.add(linha);
            }
        }
        
        return dados;
    }

    
    public double calcularScoreMedio(int formularioId) throws SQLException {
        String sql = "SELECT " +
                     "SUM(a.peso * rmea_count.contagem) / SUM(rmea_count.contagem) as score_medio " +
                     "FROM alternativas a " +
                     "INNER JOIN questoes_multipla_escolha qme ON a.questao_multipla_escolha_id = qme.questao_id " +
                     "INNER JOIN questoes q ON qme.questao_id = q.id " +
                     "INNER JOIN (" +
                     "    SELECT rmea.alternativa_id, COUNT(*) as contagem " +
                     "    FROM respostas_multipla_escolha_alternativas rmea " +
                     "    INNER JOIN respostas r ON rmea.resposta_id = r.id " +
                     "    INNER JOIN avaliacoes av ON r.avaliacao_id = av.id " +
                     "    WHERE av.formulario_id = ? " +
                     "    GROUP BY rmea.alternativa_id" +
                     ") rmea_count ON a.id = rmea_count.alternativa_id " +
                     "WHERE q.formulario_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, formularioId);
            stmt.setInt(2, formularioId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("score_medio");
            }
        }
        
        return 0.0;
    }
}