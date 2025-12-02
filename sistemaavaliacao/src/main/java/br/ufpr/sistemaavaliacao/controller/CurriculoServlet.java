package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.dao.CurriculoDAO;
import br.ufpr.sistemaavaliacao.dao.CursoDAO;
import br.ufpr.sistemaavaliacao.model.Curriculo;
import java.io.IOException;
import java.sql.SQLException; // <--- Importação adicionada para resolver o erro
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/coordenador/curriculo")
public class CurriculoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CurriculoDAO curriculoDAO = new CurriculoDAO();
    private CursoDAO cursoDAO = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // TODO: Adicionar validação de acesso para Coordenador/Administrador (RF02)

        String acao = request.getParameter("acao");
        String destino = "/jsp/coordenador/curriculo/curriculo-form.jsp"; // Default para novo ou editar

        try {
            switch (acao != null ? acao : "listar") {
                case "listar":
                    request.setAttribute("curriculos", curriculoDAO.listar());
                    destino = "/jsp/coordenador/curriculo/curriculo-lista.jsp";
                    break;
                case "novo":
                    request.setAttribute("cursos", cursoDAO.listar());
                    break;
                case "editar":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Curriculo curriculo = curriculoDAO.buscarPorId(id);
                    request.setAttribute("curriculo", curriculo);
                    request.setAttribute("cursos", cursoDAO.listar());
                    break;
                case "excluir":
                    curriculoDAO.deletar(Integer.parseInt(request.getParameter("id")));
                    response.sendRedirect("curriculo?acao=listar&msg=Currículo excluído com sucesso!");
                    return;
                default:
                    response.sendRedirect("curriculo?acao=listar");
                    return;
            }
            
            request.getRequestDispatcher(destino).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Da mesma forma, capturando exceção genérica para cobrir erros de DAO
            request.setAttribute("erro", "Erro ao processar a requisição: " + e.getMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: Adicionar validação de acesso para Coordenador/Administrador (RF02)
        request.setCharacterEncoding("UTF-8"); 

        int id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id")) : 0;
        
        Curriculo curriculo = new Curriculo();
        curriculo.setId(id);
        
        // Lógica de mapeamento da requisição para o objeto Curriculo
        // Adicionando um tratamento simples para garantir que os campos de CH sejam numéricos
        try {
            curriculo.setCursoId(Integer.parseInt(request.getParameter("cursoId")));
            curriculo.setVersao(request.getParameter("versao"));
            curriculo.setAnoImplantacao(Integer.parseInt(request.getParameter("anoImplantacao")));
            curriculo.setPortariaAprovacao(request.getParameter("portariaAprovacao"));
            curriculo.setChObrigatoria(Integer.parseInt(request.getParameter("chObrigatoria")));
            curriculo.setChOptativa(Integer.parseInt(request.getParameter("chOptativa")));
            curriculo.setChAtividadesFormativas(Integer.parseInt(request.getParameter("chAtividadesFormativas")));
            curriculo.setChExtensao(Integer.parseInt(request.getParameter("chExtensao")));
        } catch (NumberFormatException e) {
             request.setAttribute("erro", "Erro: Os campos de Ano e Carga Horária devem ser números válidos.");
             request.setAttribute("curriculo", curriculo); // Preserva os dados preenchidos
             try {
                request.setAttribute("cursos", cursoDAO.listar());
            } catch (SQLException ex) { /* ignorar */ }
            request.getRequestDispatcher("/jsp/coordenador/curriculo/curriculo-form.jsp").forward(request, response);
            return;
        }

        // O campo vem como "sim" ou "nao" do JSP
        curriculo.setTccObrigatorio("sim".equalsIgnoreCase(request.getParameter("tccObrigatorio")));
        curriculo.setObservacoes(request.getParameter("observacoes"));

        try {
            if (curriculo.getId() == 0) {
                curriculoDAO.inserir(curriculo);
            } else {
                curriculoDAO.atualizar(curriculo);
            }

            response.sendRedirect("curriculo?acao=listar&msg=Currículo salvo com sucesso!");
            
        } catch (SQLException e) { // <--- SQLException resolvida pelo import
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao salvar o currículo: " + e.getMessage());
            // Em caso de falha, retorna para o formulário
            request.setAttribute("curriculo", curriculo);
            try {
                request.setAttribute("cursos", cursoDAO.listar());
            } catch (SQLException ex) { /* ignorar */ }
            request.getRequestDispatcher("/jsp/coordenador/curriculo/curriculo-form.jsp").forward(request, response);
        }
    }
}
