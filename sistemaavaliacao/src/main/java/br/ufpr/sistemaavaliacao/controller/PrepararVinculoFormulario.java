package br.ufpr.sistemaavaliacao.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.FormularioDAO;
import br.ufpr.sistemaavaliacao.model.Formulario;

@WebServlet("/PrepararVinculoFormulario")
public class PrepararVinculoFormulario extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ... (Validação de sessão igual aos outros) ...
        
        try (Connection conn = ConnectionFactory.getConnection()) {
            FormularioDAO dao = new FormularioDAO(conn);
            
            // Lista TODOS os formulários (você pode filtrar apenas os que não têm processo, se quiser)
            List<Formulario> todosFormularios = dao.listarTodosSemProcesso(); // Crie este método no DAO se não houver
            
            request.setAttribute("listaFormularios", todosFormularios);
            
            // Encaminha para o JSP
            request.getRequestDispatcher("/jsp/coordenador/vincular-formulario.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}