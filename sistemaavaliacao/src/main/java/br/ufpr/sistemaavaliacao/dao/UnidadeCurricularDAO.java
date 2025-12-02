package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.model.UnidadeCurricular;
import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadeCurricularDAO {

    public void inserir(UnidadeCurricular uc) throws Exception {
        String sql = "INSERT INTO unidades_curriculares " +
                "(codigo, nome, periodo, carga_horaria, tipo, observacoes, curso_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uc.getCodigo());
            ps.setString(2, uc.getNome());
            ps.setString(3, uc.getPeriodo());
            ps.setInt(4, uc.getCargaHoraria());
            ps.setString(5, uc.getTipo());
            ps.setString(6, uc.getObservacoes());
            ps.setInt(7, uc.getCursoId()); // por enquanto pode ser 1 fixo no servlet
            ps.executeUpdate();
        }
    }

    public List<UnidadeCurricular> listar() throws Exception {
        List<UnidadeCurricular> lista = new ArrayList<>();
        String sql = "SELECT * FROM unidades_curriculares";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UnidadeCurricular uc = new UnidadeCurricular(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getString("periodo"),
                        rs.getInt("carga_horaria"),
                        rs.getString("tipo"),
                        rs.getString("observacoes"),
                        rs.getInt("curso_id")
                );
                lista.add(uc);
            }
        }
        return lista;
    }

    public UnidadeCurricular buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM unidades_curriculares WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new UnidadeCurricular(
                            rs.getInt("id"),
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getString("periodo"),
                            rs.getInt("carga_horaria"),
                            rs.getString("tipo"),
                            rs.getString("observacoes"),
                            rs.getInt("curso_id")
                    );
                }
            }
        }
        return null;
    }

    public void atualizar(UnidadeCurricular uc) throws Exception {
        String sql = "UPDATE unidades_curriculares SET " +
                "codigo = ?, nome = ?, periodo = ?, carga_horaria = ?, " +
                "tipo = ?, observacoes = ?, curso_id = ? " +
                "WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uc.getCodigo());
            ps.setString(2, uc.getNome());
            ps.setString(3, uc.getPeriodo());
            ps.setInt(4, uc.getCargaHoraria());
            ps.setString(5, uc.getTipo());
            ps.setString(6, uc.getObservacoes());
            ps.setInt(7, uc.getCursoId());
            ps.setInt(8, uc.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM unidades_curriculares WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
