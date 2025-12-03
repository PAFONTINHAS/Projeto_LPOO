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
import br.ufpr.sistemaavaliacao.dao.FormularioDAO;
import br.ufpr.sistemaavaliacao.dao.ProcessoAvaliativoDAO;
import br.ufpr.sistemaavaliacao.model.Formulario;
import br.ufpr.sistemaavaliacao.model.ProcessoAvaliativo;

@WebServlet("/DetalhesProcessoServlet")
public class DetalhesProcessoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try (Connection conn = ConnectionFactory.getConnection()) {
            ProcessoAvaliativoDAO procDao = new ProcessoAvaliativoDAO(conn);
            FormularioDAO formDao = new FormularioDAO(conn); // Aquele DAO turbinado do passo 2

            ProcessoAvaliativo processo = procDao.buscarPorId(id);
            // Busca os formulários E as questões dentro deles
            List<Formulario> formularios = formDao.listarPorProcesso(id);

            req.setAttribute("processo", processo);
            req.setAttribute("formularios", formularios);

            req.getRequestDispatcher("/jsp/coordenador/detalhes-processo.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
