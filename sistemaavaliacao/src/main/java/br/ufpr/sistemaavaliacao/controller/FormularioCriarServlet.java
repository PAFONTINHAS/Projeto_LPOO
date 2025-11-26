package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.dao.FormularioDAO;
import br.ufpr.sistemaavaliacao.model.Formulario;
import java.io.IOException; // Necessário para throws IOException
import javax.servlet.ServletException; // Necessário para throws ServletException
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//@WebServlet("/FormularioCriarServlet")
public class FormularioCriarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titulo = request.getParameter("titulo");
        String processoIdStr = request.getParameter("processoAvaliativoId"); 
        String instrucoes = request.getParameter("instrucoes");
        boolean isAnonimo = "on".equals(request.getParameter("anonimo")); 

        Formulario formulario = new Formulario();
        formulario.setTitulo(titulo);
        formulario.setInstrucoes(instrucoes);
        formulario.setAnonimo(isAnonimo);
        
        try {
            if (processoIdStr != null && !processoIdStr.isEmpty()) {
                formulario.setProcessoAvaliativoId(Integer.parseInt(processoIdStr));
            } else {
                 throw new IllegalArgumentException("ID do Processo Avaliativo não pode ser nulo.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("mensagemErro", "ID do Processo Avaliativo inválido.");
            request.getRequestDispatcher("/jsp/coordenador/criar-formulario.jsp").forward(request, response);
            return;
        } catch (IllegalArgumentException e) {
            request.setAttribute("mensagemErro", e.getMessage());
            request.getRequestDispatcher("/jsp/coordenador/criar-formulario.jsp").forward(request, response);
            return;
        }

        // Lógica de parsing das questões virá em um passo futuro.
        
        try {
            new FormularioDAO().salvar(formulario); 
            response.sendRedirect(request.getContextPath() + "/jsp/coordenador/processos-avaliativos.jsp");
        } catch (Exception e) {
            e.printStackTrace(); 
            request.setAttribute("mensagemErro", "Erro ao salvar formulário: " + e.getMessage());
            request.getRequestDispatcher("/jsp/coordenador/criar-formulario.jsp").forward(request, response);
        }
    }
}