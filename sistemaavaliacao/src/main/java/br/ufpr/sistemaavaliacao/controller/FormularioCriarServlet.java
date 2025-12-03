package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.builder.QuestaoBuilder;
import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.FormularioDAO;
import br.ufpr.sistemaavaliacao.model.Formulario;
import br.ufpr.sistemaavaliacao.model.Questao;

import java.io.IOException; // Necessário para throws IOException
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException; // Necessário para throws ServletException
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/FormularioCriarServlet")
public class FormularioCriarServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                request.setCharacterEncoding("UTF-8");
                
                String titulo = request.getParameter("titulo");
                Formulario formulario = new Formulario(titulo, false);
                
                int totalQuestoes = Integer.parseInt(request.getParameter("totalQuestoes"));
                
            for (int i = 1; i <= totalQuestoes; i++) {
                String tipo = request.getParameter("tipo_q" + i);
                String enunciado = request.getParameter("enunciado_q" + i);
                boolean obrigatoria = request.getParameter("obrigatoria_q" + i) != null;

                QuestaoBuilder builder = new QuestaoBuilder()
                        .doTipo(tipo)
                        .comEnunciado(enunciado)
                        .isObrigatoria(obrigatoria);
                        
                        if ("FECHADA".equals(tipo)) {
                    boolean multiSelecao = request.getParameter("multi_q" + i) != null;
                    builder.permiteMultiplaSelecao(multiSelecao);
                    
                    String[] textosAlt = request.getParameterValues("texto_alt_q" + i);
                    String[] pesosAlt = request.getParameterValues("peso_alt_q" + i);
                    
                    if (textosAlt != null) {
                        for (int j = 0; j < textosAlt.length; j++) {
                            int peso = Integer.parseInt(pesosAlt[j]);
                            builder.comAlternativa(textosAlt[j], peso);
                        }
                    }
                }
                
                Questao novQuestao = builder.build();
                
                formulario.adicionarQuestao(novQuestao);
                
            }
            
        try (Connection conexao = ConnectionFactory.getConnection()) {
            FormularioDAO dao = new FormularioDAO(conexao);
            dao.salvar(formulario);

            response.sendRedirect("jsp/aluno/home.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("erro.jsp?msg=" + e.getMessage());
        }

    }
}