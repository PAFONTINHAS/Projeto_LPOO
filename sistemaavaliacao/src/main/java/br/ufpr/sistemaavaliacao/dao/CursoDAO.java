package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    /**
     * Insere um novo curso no banco de dados.
     */
    public void inserir(Curso curso) throws SQLException {
        String sql = "INSERT INTO cursos (codigo, nome, coordenador_usuario_id, is_ativo, campus, modalidade, turno, setor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, curso.getCodigo());
            ps.setString(2, curso.getNome());
            ps.setInt(3, curso.getCoordenadorUsuarioId());
            ps.setBoolean(4, curso.isAtivo());
            ps.setString(5, curso.getCampus());
            ps.setString(6, curso.getModalidade());
            ps.setString(7, curso.getTurno());
            ps.setString(8, curso.getSetor());

            ps.executeUpdate();
        }
    }

    /**
     * Lista todos os cursos cadastrados.
     */
    public List<Curso> listar() throws SQLException {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos ORDER BY nome";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Curso curso = new Curso(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nome"),
                    rs.getInt("coordenador_usuario_id"),
                    rs.getBoolean("is_ativo"),
                    rs.getString("campus"),
                    rs.getString("modalidade"),
                    rs.getString("turno"),
                    rs.getString("setor")
                );
                lista.add(curso);
            }
        }
        return lista;
    }

    /**
     * Busca um curso por ID.
     */
    public Curso buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cursos WHERE id = ?";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Curso(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nome"),
                    rs.getInt("coordenador_usuario_id"),
                    rs.getBoolean("is_ativo"),
                    rs.getString("campus"),
                    rs.getString("modalidade"),
                    rs.getString("turno"),
                    rs.getString("setor")
                );
            }
        }
        return null;
    }

    /**
     * Atualiza um curso existente.
     */
    public void atualizar(Curso curso) throws SQLException {
        String sql = "UPDATE cursos SET codigo=?, nome=?, coordenador_usuario_id=?, is_ativo=?, campus=?, modalidade=?, turno=?, setor=? WHERE id=?";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, curso.getCodigo());
            ps.setString(2, curso.getNome());
            ps.setInt(3, curso.getCoordenadorUsuarioId());
            ps.setBoolean(4, curso.isAtivo());
            ps.setString(5, curso.getCampus());
            ps.setString(6, curso.getModalidade());
            ps.setString(7, curso.getTurno());
            ps.setString(8, curso.getSetor());
            ps.setInt(9, curso.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Deleta um curso por ID.
     */
    public void deletar(int id) throws SQLException {
        // Primeiro, precisamos apagar currículos vinculados para evitar erro de FK
        CurriculoDAO curriculoDAO = new CurriculoDAO();
        curriculoDAO.deletarPorCurso(id);
        
        String sql = "DELETE FROM cursos WHERE id = ?";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}