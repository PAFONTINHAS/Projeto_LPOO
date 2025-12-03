package br.ufpr.sistemaavaliacao.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.ProcessoAvaliativoDAO;
import br.ufpr.sistemaavaliacao.model.ProcessoAvaliativo;

@WebServlet("/SalvarTurmasProcessoServlet")
public class SalvarTurmasProcessoServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProcessoAvaliativo processo = (ProcessoAvaliativo) session.getAttribute("processoEmAndamento");
        
        // Pega os IDs selecionados no <select multiple>
        String[] idsTurmas = request.getParameterValues("idsTurmasSelecionadas");
        
        if (processo != null && idsTurmas != null) {
            try (Connection conn = ConnectionFactory.getConnection()) {
                ProcessoAvaliativoDAO dao = new ProcessoAvaliativoDAO(conn);
                
                for (String idTurma : idsTurmas) {
                    dao.vincularTurma(processo.getId(), Integer.parseInt(idTurma));
                }
                
                // Sucesso! Vai para a etapa 4 (Vincular Formulário)
                response.sendRedirect("PrepararVinculoFormulario");
                
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("jsp/coordenador/novo-processo3.jsp?erro=ErroAoSalvarTurmas");
            }
        } else {
            response.sendRedirect("jsp/coordenador/novo-processo3.jsp?erro=NenhumaTurmaSelecionada");
        }
    }
    
}
