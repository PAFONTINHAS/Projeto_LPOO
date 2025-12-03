package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.FormularioDAO;
import br.ufpr.sistemaavaliacao.model.Formulario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CarregarFormularioServlet")
public class CarregarFormularioServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Pegar IDs da URL (ex: CarregarFormularioServlet?idForm=1&idTurma=5)
        String idFormStr = request.getParameter("idForm");
        String idTurmaStr = request.getParameter("idTurma");

        if(idFormStr == null || idTurmaStr == null){
            response.sendRedirect("jsp/aluno/home.jsp?msg=Erro:ParametrosInvalidos");
            return;
        }

        try (Connection conexao = ConnectionFactory.getConnection()) {
            FormularioDAO dao = new FormularioDAO(conexao);
            
            // 2. Buscar o formulário completo (Seu DAO já traz as questões!)
            // Precisamos garantir que existe um método buscarPorId no DAO que traga as questões
            // Vou te passar o snippet desse método abaixo caso não tenha
            Formulario formulario = dao.buscarPorIdCompleto(Integer.parseInt(idFormStr));
            
            // 3. Colocar no request para o JSP usar
            request.setAttribute("formulario", formulario);
            request.setAttribute("idTurma", idTurmaStr); // Repassa para o JSP salvar depois
            
            // 4. Mandar para o JSP
            request.getRequestDispatcher("/jsp/aluno/pesquisa.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("jsp/aluno/home.jsp?msg=ErroBanco");
        }
    }
}