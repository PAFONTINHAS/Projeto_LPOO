package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.dao.CursoDAO;
import br.ufpr.sistemaavaliacao.dao.UsuarioDAO;
import br.ufpr.sistemaavaliacao.model.Curso;
import br.ufpr.sistemaavaliacao.model.Usuario;
import java.io.IOException;
import java.sql.SQLException; // <--- Importação adicionada para resolver o erro
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/coordenador/curso")
public class CursoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CursoDAO cursoDAO = new CursoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: Adicionar validação de acesso para Coordenador/Administrador (RF02)

        String acao = request.getParameter("acao");
        String destino = "/jsp/coordenador/curso/curso-form.jsp"; // Default para novo ou editar

        try {
            switch (acao != null ? acao : "listar") {
                case "listar":
                    List<Curso> cursos = cursoDAO.listar();
                    request.setAttribute("cursos", cursos);
                    // Adiciona o nome do coordenador para exibição na lista
                    cursos.forEach(c -> {
                        Usuario coord = usuarioDAO.buscarPorId(c.getCoordenadorUsuarioId());
                        request.setAttribute("coordenadorNome_" + c.getId(), coord != null ? coord.getNome() : "Não Atribuído");
                    });
                    
                    destino = "/jsp/coordenador/curso/curso-lista.jsp";
                    break;
                case "novo":
                    // Carrega lista de usuários (professores/coordenadores) para seleção
                    request.setAttribute("coordenadores", usuarioDAO.listarCoordenadoresEProfessores());
                    break;
                case "editar":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Curso curso = cursoDAO.buscarPorId(id);
                    request.setAttribute("curso", curso);
                    request.setAttribute("coordenadores", usuarioDAO.listarCoordenadoresEProfessores());
                    break;
                case "excluir":
                    cursoDAO.deletar(Integer.parseInt(request.getParameter("id")));
                    response.sendRedirect("curso?acao=listar&msg=Curso excluído com sucesso!");
                    return;
                default:
                    response.sendRedirect("curso?acao=listar");
                    return;
            }

            request.getRequestDispatcher(destino).forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Note que o .listar() e .buscarPorId() no doGet jogam SQLException.
            // Para simplificar o tratamento, mantive o catch Exception.
            request.setAttribute("erro", "Erro ao processar a requisição: " + e.getMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // TODO: Adicionar validação de acesso para Coordenador/Administrador (RF02)
        
        request.setCharacterEncoding("UTF-8"); // Garante acentuação correta

        int id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id")) : 0;
        
        Curso curso = new Curso();
        curso.setId(id);
        curso.setCodigo(request.getParameter("codigo"));
        curso.setNome(request.getParameter("nome"));
        
        // O coordenador é o ID do usuário (coordenador_usuario_id)
        curso.setCoordenadorUsuarioId(Integer.parseInt(request.getParameter("coordenadorId"))); 
        
        curso.setAtivo(request.getParameter("isAtivo") != null); 
        curso.setCampus(request.getParameter("campus"));
        curso.setModalidade(request.getParameter("modalidade"));
        curso.setTurno(request.getParameter("turno"));
        curso.setSetor(request.getParameter("setor"));

        try {
            if (curso.getId() == 0) {
                cursoDAO.inserir(curso);
            } else {
                cursoDAO.atualizar(curso);
            }

            response.sendRedirect("curso?acao=listar&msg=Curso salvo com sucesso!");
            
        } catch (SQLException e) { // <--- SQLException resolvida pelo import
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao salvar o curso: " + e.getMessage());
            // Se falhar, volta para o formulário para correção
            request.setAttribute("curso", curso);
            try {
                 request.setAttribute("coordenadores", usuarioDAO.listarCoordenadoresEProfessores());
            } catch (SQLException ex) { /* ignorar, apenas exibir o erro principal */ }
            request.getRequestDispatcher("/jsp/coordenador/curso/curso-form.jsp").forward(request, response);
        }
    }
}
