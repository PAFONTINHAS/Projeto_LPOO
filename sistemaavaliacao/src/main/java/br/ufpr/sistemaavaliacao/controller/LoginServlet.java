package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.dao.UsuarioDAO;
import br.ufpr.sistemaavaliacao.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    // ... (Serial Version UID, init)

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        Usuario usuario = new UsuarioDAO().buscarPorLoginESenha(login, senha);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);

            // Redirecionamento baseado no perfil (RF02)
            String perfil = usuario.getPerfil().toLowerCase();
            if ("aluno".equals(perfil)) {
                response.sendRedirect("jsp/aluno/home.jsp"); 
            } else if ("professor".equals(perfil)) {
                response.sendRedirect("jsp/professor/home.jsp"); 
            } else if ("coordenador".equals(perfil) || "administrador".equals(perfil)) {
                response.sendRedirect("jsp/coordenador/home.jsp"); 
            } else {
                request.setAttribute("mensagemErro", "Perfil de usuário não reconhecido.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("mensagemErro", "Login ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}