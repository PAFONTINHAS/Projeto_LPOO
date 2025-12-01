package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.dao.UsuarioDAO;
import br.ufpr.sistemaavaliacao.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//@WebServlet("/CadastroServlet")
public class CadastroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String perfil = request.getParameter("perfil");
        String infoEspecifica = request.getParameter("info_especifica"); // Matrícula ou Registro
        String infoExtra = request.getParameter("departamento"); // Departamento (para Professor)

        if (nome == null || nome.isEmpty() || login == null || login.isEmpty() || senha == null || senha.isEmpty() || perfil == null || perfil.isEmpty()) {
            request.setAttribute("mensagemErro", "Todos os campos marcados são obrigatórios.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }

        // Validação extra para campos específicos
        if (("aluno".equalsIgnoreCase(perfil) || "professor".equalsIgnoreCase(perfil)) && (infoEspecifica == null || infoEspecifica.isEmpty())) {
             request.setAttribute("mensagemErro", perfil.equals("aluno") ? "A Matrícula é obrigatória." : "O Registro é obrigatório.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            return;
        }


        Usuario novoUsuario = new Usuario(nome, email, login, senha, perfil);

        try {
            int idGerado = new UsuarioDAO().inserir(novoUsuario, infoEspecifica, infoExtra);
            
            if (idGerado > 0) {
                request.setAttribute("mensagemSucesso", "Usuário cadastrado com sucesso! ID: " + idGerado + ". Faça login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("mensagemErro", "Erro desconhecido ao cadastrar usuário.");
                request.getRequestDispatcher("cadastro.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            request.setAttribute("mensagemErro", "Erro ao cadastrar usuário: " + e.getMessage());
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        }
    }
}