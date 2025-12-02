package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.dao.AlunoDAO;
import br.ufpr.sistemaavaliacao.model.Aluno;
import br.ufpr.sistemaavaliacao.model.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {
    private AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        
        try {
            if (acao == null || acao.equals("listar")) {
                listarAlunos(request, response);
            } else if (acao.equals("editar")) {
                mostrarFormularioEdicao(request, response);
            } else if (acao.equals("excluir")) {
                excluirAluno(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro no banco de dados", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");
        
        try {
            if (acao.equals("criar")) {
                criarAluno(request, response);
            } else if (acao.equals("atualizar")) {
                atualizarAluno(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro no banco de dados", e);
        }
    }

   
    private void listarAlunos(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<Aluno> alunos = alunoDAO.listarTodos();
        request.setAttribute("alunos", alunos);
        request.getRequestDispatcher("/views/gerenciar-aluno.jsp").forward(request, response);
    }


    private void criarAluno(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        
        Usuario usuario = new Usuario();
        usuario.setNome(request.getParameter("nome"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setLogin(request.getParameter("login"));
        usuario.setSenha(request.getParameter("senha"));
        usuario.setPerfil("ALUNO");
        
        
        Aluno aluno = new Aluno();
        aluno.setMatricula(request.getParameter("matricula"));
        aluno.setUsuario(usuario);
        
      
        boolean sucesso = alunoDAO.criar(aluno);
        
        if (sucesso) {
            response.sendRedirect("aluno?acao=listar&msg=criado");
        } else {
            response.sendRedirect("aluno?acao=listar&erro=criar");
        }
    }


    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Aluno aluno = alunoDAO.buscarPorId(id);
        
        List<Aluno> alunos = alunoDAO.listarTodos();
        request.setAttribute("alunos", alunos);
        request.setAttribute("alunoEdicao", aluno);
       request.getRequestDispatcher("/views/gerenciar-aluno.jsp").forward(request, response);
    }

    
    private void atualizarAluno(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
       
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(request.getParameter("nome"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setLogin(request.getParameter("login"));
        usuario.setPerfil("ALUNO");
        
       
        Aluno aluno = new Aluno();
        aluno.setUsuarioId(id);
        aluno.setMatricula(request.getParameter("matricula"));
        aluno.setUsuario(usuario);
        
      
        boolean sucesso = alunoDAO.atualizar(aluno);
        
        if (sucesso) {
            response.sendRedirect("aluno?acao=listar&msg=atualizado");
        } else {
            response.sendRedirect("aluno?acao=listar&erro=atualizar");
        }
    }

    private void excluirAluno(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        boolean sucesso = alunoDAO.excluir(id);
        
        if (sucesso) {
            response.sendRedirect("aluno?acao=listar&msg=excluido");
        } else {
            response.sendRedirect("aluno?acao=listar&erro=excluir");
        }
    }
} 
