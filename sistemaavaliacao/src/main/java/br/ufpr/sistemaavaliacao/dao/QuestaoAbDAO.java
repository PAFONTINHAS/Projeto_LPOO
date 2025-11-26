package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.model.Questao;
import br.ufpr.sistemaavaliacao.config.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestaoAbDAO {

    public void inserirQuestaoAberta(Questao q) {
        String sql = "INSERT INTO questao (descricao, tipo, id_formulario) VALUES (?, 'aberta', ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, q.getDescricao());
            stmt.setInt(2, q.getIdFormulario());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Questao> listarAbertasPorFormulario(int idFormulario) {
        List<Questao> lista = new ArrayList<>();

        String sql = "SELECT id, descricao FROM questao WHERE tipo = 'aberta' AND id_formulario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFormulario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Questao q = new Questao();
                q.setId(rs.getInt("id"));
                q.setDescricao(rs.getString("descricao"));
                q.setTipo("aberta");
                q.setIdFormulario(idFormulario);

                lista.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
