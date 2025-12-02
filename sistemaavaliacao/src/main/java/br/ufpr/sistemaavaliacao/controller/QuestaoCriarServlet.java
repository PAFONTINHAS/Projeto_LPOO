/*package br.ufpr.sistemaavaliacao.controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpr.sistemaavaliacao.model.Questao;
import br.ufpr.sistemaavaliacao.dao.QuestaoAbDAO;

import java.io.IOException;



@WebServlet("/questao/criar-aberta")
public class QuestaoCriarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String descricao = req.getParameter("descricao");
        int idFormulario = Integer.parseInt(req.getParameter("idFormulario"));

        Questao q = new Questao();
        q.setDescricao(descricao);
        q.setIdFormulario(idFormulario);

        new QuestaoAbDAO().inserirQuestaoAberta(q);

        resp.sendRedirect("../formularios/visualizar?id=" + idFormulario);
    }
}
*/