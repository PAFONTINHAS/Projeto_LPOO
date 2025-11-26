package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.model.UnidadeCurricular;
import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import java.sql.*;
import java.util.*;

public class UnidadeCurricularDAO {

    public void inserir(UnidadeCurricular uc) throws Exception {
        String sql = "INSERT INTO unidade_curricular (nome, descricao) VALUES (?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uc.getNome());
            ps.setString(2, uc.getDescricao());
            ps.executeUpdate();
        }
    }

    public List<UnidadeCurricular> listar() throws Exception {
        List<UnidadeCurricular> lista = new ArrayList<>();
        String sql = "SELECT * FROM unidade_curricular";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UnidadeCurricular uc = new UnidadeCurricular(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao")
                );
                lista.add(uc);
            }
        }
        return lista;
    }

    public UnidadeCurricular buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM unidade_curricular WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UnidadeCurricular(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao")
                );
            }
        }
        return null;
    }

    public void atualizar(UnidadeCurricular uc) throws Exception {
        String sql = "UPDATE unidade_curricular SET nome=?, descricao=? WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uc.getNome());
            ps.setString(2, uc.getDescricao());
            ps.setInt(3, uc.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM unidade_curricular WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
