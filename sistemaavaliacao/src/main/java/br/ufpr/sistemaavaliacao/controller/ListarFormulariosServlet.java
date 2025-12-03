package br.ufpr.sistemaavaliacao.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpr.sistemaavaliacao.builder.QuestaoBuilder;
import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.FormularioDAO;
import br.ufpr.sistemaavaliacao.dao.ProcessoAvaliativoDAO;
import br.ufpr.sistemaavaliacao.model.Formulario;
import br.ufpr.sistemaavaliacao.model.ProcessoAvaliativo;
import br.ufpr.sistemaavaliacao.model.Questao;

@WebServlet("/ListarFormulariosServlet")
public class ListarFormulariosServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            FormularioDAO dao= new FormularioDAO(conn);

            List<Formulario> lista = dao.listarTodos();

            req.setAttribute("processos", lista);
            req.getRequestDispatcher("/jsp/coordenador/processos-avaliativos.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
