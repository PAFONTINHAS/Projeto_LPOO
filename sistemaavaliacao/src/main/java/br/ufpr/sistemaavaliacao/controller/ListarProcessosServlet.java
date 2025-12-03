package br.ufpr.sistemaavaliacao.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.ProcessoAvaliativoDAO;
import br.ufpr.sistemaavaliacao.model.ProcessoAvaliativo;

@WebServlet("/ListarProcessosServlet")
public class ListarProcessosServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            ProcessoAvaliativoDAO dao = new ProcessoAvaliativoDAO(conn);
            List<ProcessoAvaliativo> lista = dao.listarTodos();

            req.setAttribute("processos", lista);
            req.getRequestDispatcher("/jsp/coordenador/processos-avaliativos.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
