package br.ufpr.sistemaavaliacao.controller;

import java.io.IOException;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.ProcessoAvaliativoDAO;
import br.ufpr.sistemaavaliacao.model.ProcessoAvaliativo;

@WebServlet("/SalvarProcessoInicial")
public class NovoProcessoServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

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

            try(Connection connection = ConnectionFactory.getConnection()){
                ProcessoAvaliativoDAO dao = new ProcessoAvaliativoDAO(connection);

                dao.salvar(processo);

                HttpSession session = request.getSession();
                session.setAttribute("processoEmAndamento", processo);

                response.sendRedirect("jsp/coordenador/novo-processo2.jsp");
            } catch(SQLException e){
                e.printStackTrace();
                response.sendRedirect("jsp/coordenador/novo-processo1.jsp?erro=ErroBanco");
            }
        } catch(IllegalArgumentException e){

            response.sendRedirect(("jsp/coordenador/novo-processo1.jsp?erro=DatasInvalidas"));
        }
    }
    
    
}
