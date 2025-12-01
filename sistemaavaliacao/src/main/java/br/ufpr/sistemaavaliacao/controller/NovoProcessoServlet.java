package br.ufpr.sistemaavaliacao.controller;

import java.io.IOException;
import java.rmi.ServerException;
import java.sql.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufpr.sistemaavaliacao.model.ProcessoAvaliativo;

@WebServlet("/SalvarProcessoInicial")
public class NovoProcessoServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        Logger logger = Logger.getLogger(getClass().getName());
        
        String titulo = request.getParameter("titulo");
        String inicioString = request.getParameter("inicio");
        String fimString = request.getParameter("fim");
        String observacoes = request.getParameter("observacoes");

        ProcessoAvaliativo processo = new ProcessoAvaliativo();
        processo.setNome(titulo);
        processo.setObservacoes(observacoes);

        try{
            processo.setDataInicio(Date.valueOf(inicioString));
            processo.setDataFim(Date.valueOf(fimString));
        } catch(IllegalArgumentException e){

            response.sendRedirect(("novo-processo1.jsp?erro=DatasInvalidas"));
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("processoEmAndamento", processo);

        response.sendRedirect("novo-processo2.jsp");
    }
    
    
}
