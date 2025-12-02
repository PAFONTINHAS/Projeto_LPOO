package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.model.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    /**
     * Autentica um usuário (RF02).
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return O objeto Usuario se a autenticação for bem-sucedida, null caso contrário.
     */
    public Usuario buscarPorLoginESenha(String login, String senha) {
        // ... (Implementação mantida)
        String sql = "SELECT id, nome, email, login, senha, perfil FROM usuarios WHERE login = ? AND senha = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha); 
            rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setPerfil(rs.getString("perfil"));
            }
            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por login e senha.", e);
        } finally {
            ConnectionFactory.closeConnection(conn);
            // Fechar stmt e rs...
        }
    }
    
    /**
     * Busca um usuário por ID. (NOVO MÉTODO)
     */
    public Usuario buscarPorId(int id) {
        String sql = "SELECT id, nome, email, login, senha, perfil FROM usuarios WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setPerfil(rs.getString("perfil"));
                    return usuario;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID.", e);
        }
    }
    
    /**
     * Lista usuários com perfil de Coordenador ou Professor (Para seleção em CRUD de Cursos). (NOVO MÉTODO)
     */
    public List<Usuario> listarCoordenadoresEProfessores() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nome, perfil FROM usuarios WHERE perfil = 'coordenador' OR perfil = 'professor' ORDER BY nome";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setPerfil(rs.getString("perfil"));
                lista.add(usuario);
            }
        }
        return lista;
    }


    /**
     * Insere um novo usuário na tabela geral e na tabela de perfil específica (RF01).
     * @param usuario O objeto Usuario com dados básicos.
     * @param infoEspecifica Informação adicional (Matrícula ou Registro)
     * @param infoExtra Informação extra (Departamento - para Professor)
     * @return O ID do usuário inserido.
     * @throws SQLException Se ocorrer um erro no banco de dados.
     */
    public int inserir(Usuario usuario, String infoEspecifica, String infoExtra) throws SQLException {
        String sqlUsuarios = "INSERT INTO usuarios (nome, email, login, senha, perfil) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int usuarioId = -1;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Inicia transação

            // 1. Inserir em 'usuarios'
            stmt = conn.prepareStatement(sqlUsuarios, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getLogin());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getPerfil());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                usuarioId = rs.getInt(1);
            } else {
                throw new SQLException("Falha ao obter ID do usuário gerado.");
            }
            
            // 2. Inserir na tabela de perfil específica
            String perfil = usuario.getPerfil().toLowerCase();
            String sqlPerfil;
            PreparedStatement stmtPerfil;

            switch (perfil) {
                case "aluno":
                    sqlPerfil = "INSERT INTO alunos (usuario_id, matricula) VALUES (?, ?)";
                    stmtPerfil = conn.prepareStatement(sqlPerfil);
                    stmtPerfil.setInt(1, usuarioId);
                    stmtPerfil.setString(2, infoEspecifica); // Matrícula
                    stmtPerfil.executeUpdate();
                    stmtPerfil.close();
                    break;
                case "professor":
                    sqlPerfil = "INSERT INTO professores (usuario_id, registro, departamento) VALUES (?, ?, ?)";
                    stmtPerfil = conn.prepareStatement(sqlPerfil);
                    stmtPerfil.setInt(1, usuarioId);
                    stmtPerfil.setString(2, infoEspecifica); // Registro
                    stmtPerfil.setString(3, infoExtra);      // Departamento
                    stmtPerfil.executeUpdate();
                    stmtPerfil.close();
                    break;
                case "coordenador":
                    sqlPerfil = "INSERT INTO coordenadores (usuario_id) VALUES (?)";
                    stmtPerfil = conn.prepareStatement(sqlPerfil);
                    stmtPerfil.setInt(1, usuarioId);
                    stmtPerfil.executeUpdate();
                    stmtPerfil.close();
                    break;
                case "administrador":
                    sqlPerfil = "INSERT INTO administradores (usuario_id) VALUES (?)";
                    stmtPerfil = conn.prepareStatement(sqlPerfil);
                    stmtPerfil.setInt(1, usuarioId);
                    stmtPerfil.executeUpdate();
                    stmtPerfil.close();
                    break;
                default:
                    throw new IllegalArgumentException("Perfil de usuário desconhecido: " + perfil);
            }

            conn.commit(); // Confirma transação
            return usuarioId;

        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Desfaz transação
            throw new SQLException("Erro ao cadastrar usuário e perfil específico.", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) { /* Ignorar */ }
            ConnectionFactory.closeConnection(conn);
        }
    }
}