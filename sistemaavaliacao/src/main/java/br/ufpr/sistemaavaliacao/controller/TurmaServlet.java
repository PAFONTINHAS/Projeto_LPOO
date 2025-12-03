package br.ufpr.sistemaavaliacao.controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import br.ufpr.sistemaavaliacao.dao.TurmaDAO;
import br.ufpr.sistemaavaliacao.dao.UnidadeCurricularDAO;
import br.ufpr.sistemaavaliacao.model.Turma;
import br.ufpr.sistemaavaliacao.model.UnidadeCurricular;


@WebServlet("/turma")
public class TurmaServlet extends HttpServlet {

    private TurmaDAO turmaDAO = new TurmaDAO();
    private UnidadeCurricularDAO ucDAO = new UnidadeCurricularDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");

        try {
            switch (acao) {

                case "listar":
                    req.setAttribute("turmas", turmaDAO.listar());
                    req.getRequestDispatcher("turma-lista.jsp").forward(req, resp);
                    break;

                case "nova":
                    req.setAttribute("ucs", ucDAO.listar());
                    req.getRequestDispatcher("turma-form.jsp").forward(req, resp);
                    break;

                default:
                    resp.sendRedirect("turma?acao=listar");
            }

        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    // @Override
    // protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    //         throws ServletException, IOException {

    //     Turma turma = new Turma();
    //     turma.setNome(req.getParameter("nome"));
    //     turma.setAno(Integer.parseInt(req.getParameter("ano")));
    //     turma.setSemestre(Integer.parseInt(req.getParameter("semestre")));

    //     UnidadeCurricular uc = new UnidadeCurricular();
    //     uc.setId(Integer.parseInt(req.getParameter("id_uc")));
    //     turma.setUnidadeCurricular(uc);

    //     try {
    //         turmaDAO.inserir(turma);
    //         resp.sendRedirect("turma?acao=listar");

    //     } catch (Exception e) {
    //         throw new ServletException(e);
    //     }
    // }
}
