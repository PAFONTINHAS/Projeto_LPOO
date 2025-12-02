package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Listar e exibir formulários disponíveis para ser respondido
@WebServlet("/avaliacao/aplicar")
public class AvaliacaoAplicarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String perfil = (String) session.getAttribute("perfil");

        // Apenas alunos podem acessar esta página
        if (!"aluno".equals(perfil)) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            return;
        }

        try (Connection conn = ConnectionFactory.getConnection()) {
            
            List<Map<String, Object>> formularios = buscarFormulariosDisponiveis(conn, usuarioId);
            
            request.setAttribute("formularios", formularios);
            request.getRequestDispatcher("/views/avaliacao/listar.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao buscar avaliações: " + e.getMessage());
            request.getRequestDispatcher("/views/avaliacao/listar.jsp").forward(request, response);
        }
    }

    private List<Map<String, Object>> buscarFormulariosDisponiveis(Connection conn, int usuarioId) 
            throws SQLException {
        
        List<Map<String, Object>> formularios = new ArrayList<>();
        
        String sql = "SELECT DISTINCT f.id, f.titulo, f.instrucoes, f.is_anonimo, " +
                     "pa.nome as processo_nome, pa.data_inicio, pa.data_fim, " +
                     "CASE WHEN av.id IS NOT NULL THEN 1 ELSE 0 END as ja_respondido " +
                     "FROM formularios f " +
                     "INNER JOIN processos_avaliativos pa ON f.processo_avaliativo_id = pa.id " +
                     "INNER JOIN turmas_alunos ta ON ta.aluno_usuario_id = ? " +
                     "LEFT JOIN avaliacoes av ON f.id = av.formulario_id AND av.aluno_usuario_id = ? " +
                     "WHERE CURDATE() BETWEEN pa.data_inicio AND pa.data_fim " +
                     "ORDER BY pa.data_fim ASC, f.titulo";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> form = new HashMap<>();
                form.put("id", rs.getInt("id"));
                form.put("titulo", rs.getString("titulo"));
                form.put("instrucoes", rs.getString("instrucoes"));
                form.put("is_anonimo", rs.getBoolean("is_anonimo"));
                form.put("processo_nome", rs.getString("processo_nome"));
                form.put("data_inicio", rs.getDate("data_inicio"));
                form.put("data_fim", rs.getDate("data_fim"));
                form.put("ja_respondido", rs.getBoolean("ja_respondido"));
                
                formularios.add(form);
            }
        }
        
        return formularios;
    }
}