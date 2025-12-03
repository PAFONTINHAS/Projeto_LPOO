package br.ufpr.sistemaavaliacao.controller;

import java.io.IOException;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.TurmaDAO;
import br.ufpr.sistemaavaliacao.model.Turma;
import br.ufpr.sistemaavaliacao.model.UnidadeCurricular;

@WebServlet("/FiltrarTurmasServlet")
public class FiltrarTurmasServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        request.setCharacterEncoding("UTF-8");
        String cursosRaw = request.getParameter("nomesCursos");
        String[] nomesCursos = cursosRaw.split(";");

        try(Connection connection = ConnectionFactory.getConnection()){
            TurmaDAO turmaDAO = new TurmaDAO();

            List<Turma> turmasFiltradas = turmaDAO.listarPorNomesDeCursos(nomesCursos);
            List<UnidadeCurricular> disciplinasFiltradas = turmaDAO.listarDisciplinasPorCursos(nomesCursos);

            request.setAttribute("turmas", turmasFiltradas);
            request.setAttribute("disciplinas", disciplinasFiltradas);
            request.setAttribute("cursosSelecionados", nomesCursos);

            request.getRequestDispatcher("/jsp/coordenador/novo_processo3.jsp").forward(request, response);
        } catch(SQLException e){

            e.printStackTrace();
            String msg = java.net.URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("jsp/coordenador/novo_processo2.jsp?erro=" + msg);
        }
    }
}
