package br.ufpr.sistemaavaliacao.controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpr.sistemaavaliacao.dao.TurmaProfessorDAO;

import java.io.IOException;



@WebServlet("/turma/professor")
public class VincularProfessorServlet extends HttpServlet {

    private TurmaProfessorDAO dao = new TurmaProfessorDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int idProfessor = Integer.parseInt(req.getParameter("professor"));
        int idTurma = Integer.parseInt(req.getParameter("turma"));

        try {
            if (dao.professorJaNaTurma(idProfessor, idTurma)) {
                req.setAttribute("erro", "Professor já está vinculado!");
            } else {
                dao.vincular(idProfessor, idTurma);
            }

            resp.sendRedirect("../turma?acao=listar");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
