package br.ufpr.sistemaavaliacao.controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpr.sistemaavaliacao.dao.UnidadeCurricularDAO;
import br.ufpr.sistemaavaliacao.model.UnidadeCurricular;

import java.io.IOException;



@WebServlet("/uc")
public class UnidadeCurricularServlet extends HttpServlet {

    private UnidadeCurricularDAO ucDAO = new UnidadeCurricularDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");

        try {
            switch (acao) {
                case "listar":
                    req.setAttribute("ucs", ucDAO.listar());
                    req.getRequestDispatcher("uc-lista.jsp").forward(req, resp);
                    break;

                case "editar":
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("uc", ucDAO.buscarPorId(id));
                    req.getRequestDispatcher("uc-form.jsp").forward(req, resp);
                    break;

                case "excluir":
                    ucDAO.deletar(Integer.parseInt(req.getParameter("id")));
                    resp.sendRedirect("uc?acao=listar");
                    break;

                default:
                    req.getRequestDispatcher("uc-form.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UnidadeCurricular uc = new UnidadeCurricular();

        uc.setId(req.getParameter("id") != null && !req.getParameter("id").isEmpty()
            ? Integer.parseInt(req.getParameter("id"))
            : 0
        );

        uc.setNome(req.getParameter("nome"));
        uc.setTipo(req.getParameter("tipo"));

        try {
            if (uc.getId() == 0)
                ucDAO.inserir(uc);
            else
                ucDAO.atualizar(uc);

            resp.sendRedirect("uc?acao=listar");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
