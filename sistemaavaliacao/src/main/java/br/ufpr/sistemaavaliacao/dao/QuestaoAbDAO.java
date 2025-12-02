package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.model.Questao;
import br.ufpr.sistemaavaliacao.model.QuestaoAberta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestaoAbDAO {

    // Insere uma questão aberta no modelo/tabela atual (questoes)
    public void inserirQuestaoAberta(QuestaoAberta q, int idFormulario) {
        String sql = "INSERT INTO questoes (enunciado, is_obrigatoria, tipo, formulario_id) VALUES (?,?,?,?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, q.getEnunciado());
            stmt.setBoolean(2, q.getIsObrigatoria());
            stmt.setString(3, "ABERTA");
            stmt.setInt(4, idFormulario);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir questão aberta", e);
        }
    }

    // Lista questões abertas por formulário conforme o novo modelo
    public List<Questao> listarAbertasPorFormulario(int idFormulario) {
        List<Questao> lista = new ArrayList<>();

        String sql = "SELECT enunciado, is_obrigatoria FROM questoes WHERE tipo = 'ABERTA' AND formulario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFormulario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String enunciado = rs.getString("enunciado");
                    boolean obrigatoria = rs.getBoolean("is_obrigatoria");
                    QuestaoAberta q = new QuestaoAberta(enunciado, obrigatoria);
                    lista.add(q);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar questões abertas", e);
        }

        return lista;
    }
}
