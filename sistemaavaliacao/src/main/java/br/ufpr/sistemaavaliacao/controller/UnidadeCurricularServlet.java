package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.dao.UnidadeCurricularDAO;
import br.ufpr.sistemaavaliacao.model.UnidadeCurricular;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/uc")
public class UnidadeCurricularServlet extends HttpServlet {

    private UnidadeCurricularDAO ucDAO = new UnidadeCurricularDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }

        try {
            switch (acao) {
                case "listar":
                    listar(req, resp);
                    break;

                case "novo":
                    novo(req, resp);
                    break;

                case "editar":
                    editar(req, resp);
                    break;

                case "excluir":
                    excluir(req, resp);
                    break;

                default:
                    listar(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // O form envia para /uc com method POST (criar/atualizar)
        try {
            salvar(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        List<UnidadeCurricular> lista = ucDAO.listar();
        req.setAttribute("ucs", lista);
        req.getRequestDispatcher("jsp/coordenador/uc-lista.jsp").forward(req, resp);
    }

    private void novo(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Só encaminha para o formulário vazio
        req.getRequestDispatcher("jsp/coordenador/uc-form.jsp").forward(req, resp);
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        UnidadeCurricular uc = ucDAO.buscarPorId(id);
        req.setAttribute("uc", uc);
        req.getRequestDispatcher("jsp/coordenador/uc-form.jsp").forward(req, resp);
    }

    private void excluir(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        ucDAO.deletar(id);
        resp.sendRedirect("uc?acao=listar");
    }

    private void salvar(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        String idParam = req.getParameter("id");
        String codigo = req.getParameter("codigo");
        String nome = req.getParameter("nome");
        String periodo = req.getParameter("periodo");
        String cargaParam = req.getParameter("cargaHoraria");
        String tipo = req.getParameter("tipo");
        String observacoes = req.getParameter("observacoes");

        int cargaHoraria = 0;
        if (cargaParam != null && !cargaParam.isEmpty()) {
            cargaHoraria = Integer.parseInt(cargaParam);
        }

        // Por enquanto, vinculando sempre ao curso 1.
        // Depois você pode pegar do select de cursos.
        int cursoId = 1;

        UnidadeCurricular uc = new UnidadeCurricular();
        uc.setCodigo(codigo);
        uc.setNome(nome);
        uc.setPeriodo(periodo);
        uc.setCargaHoraria(cargaHoraria);
        uc.setTipo(tipo);
        uc.setObservacoes(observacoes);
        uc.setCursoId(cursoId);

        if (idParam == null || idParam.isEmpty()) {
            // NOVA UC
            ucDAO.inserir(uc);
        } else {
            // EDIÇÃO
            uc.setId(Integer.parseInt(idParam));
            ucDAO.atualizar(uc);
        }

        resp.sendRedirect("uc?acao=listar");
    }
}
