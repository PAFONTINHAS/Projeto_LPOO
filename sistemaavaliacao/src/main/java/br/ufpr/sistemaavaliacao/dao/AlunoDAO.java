package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.model.Aluno;
import br.ufpr.sistemaavaliacao.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    // CRIAR 
    public boolean criar(Aluno aluno) throws SQLException {
        String sqlUsuario = "INSERT INTO usuarios (nome, email, login, senha, perfil) VALUES (?, ?, ?, ?, 'ALUNO')";
        String sqlAluno = "INSERT INTO alunos (usuario_id, matricula) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, aluno.getUsuario().getNome());
                psUsuario.setString(2, aluno.getUsuario().getEmail());
                psUsuario.setString(3, aluno.getUsuario().getLogin());
                psUsuario.setString(4, aluno.getUsuario().getSenha());
                psUsuario.executeUpdate();

                try (ResultSet rs = psUsuario.getGeneratedKeys()) {
                    if (rs.next()) {
                        int usuarioId = rs.getInt(1);
                        aluno.setUsuarioId(usuarioId);

                        try (PreparedStatement psAluno = conn.prepareStatement(sqlAluno)) {
                            psAluno.setInt(1, usuarioId);
                            psAluno.setString(2, aluno.getMatricula());
                            psAluno.executeUpdate();
                        }
                    } else {
                        conn.rollback();
                        throw new SQLException("Falha ao criar usuário, nenhum ID obtido.");
                    }
                }
            }

            conn.commit();
            return true;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao criar aluno.", e);
        }
    }

    // LISTAR
    public List<Aluno> listarTodos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT u.id, u.nome, u.email, u.login, a.matricula " +
                     "FROM alunos a " +
                     "INNER JOIN usuarios u ON a.usuario_id = u.id " +
                     "ORDER BY u.nome";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setLogin(rs.getString("login"));
                usuario.setPerfil("ALUNO");
                
                Aluno aluno = new Aluno();
                aluno.setUsuarioId(rs.getInt("id"));
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setUsuario(usuario);
                
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    // BUSCAR 
    public Aluno buscarPorId(int usuarioId) throws SQLException {
        String sql = "SELECT u.id, u.nome, u.email, u.login, u.senha, a.matricula " +
                     "FROM alunos a " +
                     "INNER JOIN usuarios u ON a.usuario_id = u.id " +
                     "WHERE u.id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil("ALUNO");
                
                Aluno aluno = new Aluno();
                aluno.setUsuarioId(rs.getInt("id"));
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setUsuario(usuario);
                
                return aluno;
            }
        }
        return null;
    }

    // ATUALIZAR
    public boolean atualizar(Aluno aluno) throws SQLException {
        String sqlUsuario = "UPDATE usuarios SET nome = ?, email = ?, login = ? WHERE id = ?";
        String sqlAluno = "UPDATE alunos SET matricula = ? WHERE usuario_id = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario)) {
                psUsuario.setString(1, aluno.getUsuario().getNome());
                psUsuario.setString(2, aluno.getUsuario().getEmail());
                psUsuario.setString(3, aluno.getUsuario().getLogin());
                psUsuario.setInt(4, aluno.getUsuarioId());
                psUsuario.executeUpdate();
            }

            try (PreparedStatement psAluno = conn.prepareStatement(sqlAluno)) {
                psAluno.setString(1, aluno.getMatricula());
                psAluno.setInt(2, aluno.getUsuarioId());
                psAluno.executeUpdate();
            }

            conn.commit();
            return true;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar aluno.", e);
        }
    }

    // EXCLUIR 
    public boolean excluir(int usuarioId) throws SQLException {
        String sqlAluno = "DELETE FROM alunos WHERE usuario_id = ?";
        String sqlUsuario = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psAluno = conn.prepareStatement(sqlAluno)) {
                psAluno.setInt(1, usuarioId);
                psAluno.executeUpdate();
            }

            try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario)) {
                psUsuario.setInt(1, usuarioId);
                psUsuario.executeUpdate();
            }

            conn.commit();
            return true;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao excluir aluno.", e);
        }
    }

    
    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }
}