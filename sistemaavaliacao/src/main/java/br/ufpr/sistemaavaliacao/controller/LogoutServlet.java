package br.ufpr.sistemaavaliacao.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); 

        if (session != null) {
            session.invalidate(); // Invalida a sessão
        }
        
        // Redireciona para a página de login
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}