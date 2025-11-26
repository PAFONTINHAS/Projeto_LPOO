package br.ufpr.sistemaavaliacao.controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpr.sistemaavaliacao.dao.TurmaAlunoDAO;

import java.io.IOException;



@WebServlet("/turma/aluno")
public class VincularAlunoServlet extends HttpServlet {

    private TurmaAlunoDAO dao = new TurmaAlunoDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int idAluno = Integer.parseInt(req.getParameter("aluno"));
        int idTurma = Integer.parseInt(req.getParameter("turma"));

        try {
            if (dao.alunoJaNaTurma(idAluno, idTurma)) {
                req.setAttribute("erro", "Aluno já está na turma!");
            } else {
                dao.vincular(idAluno, idTurma);
            }

            resp.sendRedirect("../turma?acao=listar");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
