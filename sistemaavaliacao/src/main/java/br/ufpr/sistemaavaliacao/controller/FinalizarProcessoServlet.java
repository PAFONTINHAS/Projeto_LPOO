package br.ufpr.sistemaavaliacao.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.FormularioDAO;
import br.ufpr.sistemaavaliacao.model.ProcessoAvaliativo;

@WebServlet("/FinalizarProcessoServlet")
public class FinalizarProcessoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProcessoAvaliativo processo = (ProcessoAvaliativo) session.getAttribute("processoEmAndamento");
        int formId = Integer.parseInt(request.getParameter("formularioId"));

        try (Connection conn = ConnectionFactory.getConnection()) {
            FormularioDAO dao = new FormularioDAO(conn);
            
            // Atualiza o formulário colocando o ID do processo nele
            dao.vincularProcesso(formId, processo.getId()); 
            
            // Limpa a sessão
            session.removeAttribute("processoEmAndamento");
            
            // Manda para a listagem geral
            response.sendRedirect("jsp/coordenador/processos-avaliativos.jsp?msg=ProcessoCriadoComSucesso");
            
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
    
}
