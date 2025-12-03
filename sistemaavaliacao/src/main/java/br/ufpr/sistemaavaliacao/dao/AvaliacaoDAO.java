package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.dto.AvaliacaoPendenteDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {
    private Connection conn;

    public AvaliacaoDAO(Connection conn) {
        this.conn = conn;
    }

    public List<AvaliacaoPendenteDTO> buscarPendentes(int idAluno) throws SQLException {
        List<AvaliacaoPendenteDTO> lista = new ArrayList<>();

        // SQL SIMPLIFICADO:
        // 1. Pega Formulários ligados a Processos
        // 2. Verifica se a data atual está dentro do prazo
        // 3. Remove (NOT IN) os que o aluno já respondeu

        String sql = "SELECT " +
                "   f.id AS id_form, " +
                "   f.titulo AS titulo_form, " +
                "   p.nome AS nome_processo " +
                "FROM formularios f " +
                "JOIN processos_avaliativos p ON f.processo_avaliativo_id = p.id " +
                "WHERE CURDATE() BETWEEN p.data_inicio AND p.data_fim " +
                "  AND f.id NOT IN ( " +
                "      SELECT formulario_id FROM avaliacoes WHERE aluno_usuario_id = ? " +
                "  )";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno); // Único parâmetro agora é o ID do aluno para ver se já respondeu

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Truque para não quebrar seu DTO:
                    // Passamos ID Turma = 0
                    // Passamos Nome Disciplina = "Avaliação Geral" (ou o nome do Processo)
                    lista.add(new AvaliacaoPendenteDTO(
                            rs.getInt("id_form"),
                            0, // ID Turma fictício (Zero)
                            rs.getString("titulo_form"),
                            "Avaliação Institucional", // Nome genérico para aparecer no card
                            rs.getString("nome_processo")));
                }
            }
        }
        return lista;
    }
}