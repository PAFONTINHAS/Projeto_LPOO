package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.dao.RelatorioDAO;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/relatorio/basico")
public class RelatorioBasicoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String perfil = (String) session.getAttribute("perfil");

        // Apenas administradores, coordenadores e professores podem acessar
        if (!"administrador".equals(perfil) && !"coordenador".equals(perfil) && !"professor".equals(perfil)) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            return;
        }

        String formularioIdStr = request.getParameter("id");
        if (formularioIdStr == null) {
            
            try (Connection conn = ConnectionFactory.getConnection()) {
                List<Map<String, Object>> formularios = listarFormularios(conn);
                request.setAttribute("formularios", formularios);
                request.getRequestDispatcher("/views/relatorios/selecionar.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("erro", "Erro ao buscar formulários: " + e.getMessage());
                request.getRequestDispatcher("/views/relatorios/selecionar.jsp").forward(request, response);
            }
            return;
        }

        try (Connection conn = ConnectionFactory.getConnection()) {
            int formularioId = Integer.parseInt(formularioIdStr);
            RelatorioDAO relatorioDAO = new RelatorioDAO(conn);
            
            //buscar
            Map<String, Object> formulario = relatorioDAO.buscarInfoFormulario(formularioId);
            
            int totalSubmissoes = relatorioDAO.contarSubmissoes(formularioId);
            
         
            List<Map<String, Object>> estatisticasMe = relatorioDAO.buscarEstatisticasMultiplaEscolha(formularioId);
            
            List<Map<String, Object>> respostasAbertas = relatorioDAO.buscarRespostasAbertas(formularioId);
            
            
            double scoreMedio = relatorioDAO.calcularScoreMedio(formularioId);
            
           
            Map<Integer, Map<String, Object>> questoesOrganizadas = organizarEstatisticas(estatisticasMe);
            
            request.setAttribute("formulario", formulario);
            request.setAttribute("totalSubmissoes", totalSubmissoes);
            request.setAttribute("questoesOrganizadas", questoesOrganizadas);
            request.setAttribute("respostasAbertas", respostasAbertas);
            request.setAttribute("scoreMedio", scoreMedio);
            request.setAttribute("perfil", perfil);
            
            request.getRequestDispatcher("/views/relatorios/basico.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao gerar relatório: " + e.getMessage());
            request.getRequestDispatcher("/views/relatorios/basico.jsp").forward(request, response);
        }
    }

    private List<Map<String, Object>> listarFormularios(Connection conn) throws SQLException {
        List<Map<String, Object>> formularios = new ArrayList<>();
        
        String sql = "SELECT f.id, f.titulo, pa.nome as processo_nome, " +
                     "COUNT(DISTINCT av.id) as total_respostas " +
                     "FROM formularios f " +
                     "INNER JOIN processos_avaliativos pa ON f.processo_avaliativo_id = pa.id " +
                     "LEFT JOIN avaliacoes av ON f.id = av.formulario_id " +
                     "GROUP BY f.id, f.titulo, pa.nome " +
                     "ORDER BY pa.data_fim DESC, f.titulo";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> form = new HashMap<>();
                form.put("id", rs.getInt("id"));
                form.put("titulo", rs.getString("titulo"));
                form.put("processo_nome", rs.getString("processo_nome"));
                form.put("total_respostas", rs.getInt("total_respostas"));
                formularios.add(form);
            }
        }
        
        return formularios;
    }

    private Map<Integer, Map<String, Object>> organizarEstatisticas(List<Map<String, Object>> estatisticas) {
        Map<Integer, Map<String, Object>> questoesMap = new LinkedHashMap<>();
        
        for (Map<String, Object> linha : estatisticas) {
            int questaoId = (Integer) linha.get("questao_id");
            
            if (!questoesMap.containsKey(questaoId)) {
                Map<String, Object> questao = new HashMap<>();
                questao.put("id", questaoId);
                questao.put("enunciado", linha.get("enunciado"));
                questao.put("alternativas", new ArrayList<Map<String, Object>>());
                questao.put("total_respostas", linha.get("total_respostas"));
                questoesMap.put(questaoId, questao);
            }
            
            Map<String, Object> questao = questoesMap.get(questaoId);
            List<Map<String, Object>> alternativas = (List<Map<String, Object>>) questao.get("alternativas");
            
            Map<String, Object> alternativa = new HashMap<>();
            alternativa.put("id", linha.get("alternativa_id"));
            alternativa.put("texto", linha.get("alternativa_texto"));
            alternativa.put("peso", linha.get("peso"));
            alternativa.put("contagem", linha.get("contagem_alternativa"));
            
            int totalResp = (Integer) linha.get("total_respostas");
            int contagem = (Integer) linha.get("contagem_alternativa");
            double percentual = totalResp > 0 ? (contagem * 100.0 / totalResp) : 0;
            alternativa.put("percentual", percentual);
            
            alternativas.add(alternativa);
        }
        
        return questoesMap;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Exportar dados 
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String perfil = (String) session.getAttribute("perfil");
        if (!"administrador".equals(perfil)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String formularioIdStr = request.getParameter("formularioId");
        String incluirIdStr = request.getParameter("incluirIdentificacao");
        
        if (formularioIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try (Connection conn = ConnectionFactory.getConnection()) {
            int formularioId = Integer.parseInt(formularioIdStr);
            boolean incluirIdentificacao = "true".equals(incluirIdStr);
            
            RelatorioDAO relatorioDAO = new RelatorioDAO(conn);
            List<Map<String, Object>> dadosBrutos = relatorioDAO.exportarDadosBrutos(formularioId, incluirIdentificacao);
            
            // Configurar resposta como CSV
            response.setContentType("text/csv; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=dados_brutos_" + formularioId + ".csv");
            
            StringBuilder csv = new StringBuilder();
            
            // Cabeçalho
            csv.append("Avaliacao ID,");
            if (incluirIdentificacao) {
                csv.append("Aluno Nome,Matricula,");
            }
            csv.append("Data Submissao,Questao ID,Enunciado,Tipo,Texto Resposta,Alternativas Selecionadas\n");
            
            // Dados
            for (Map<String, Object> linha : dadosBrutos) {
                csv.append(linha.get("avaliacao_id")).append(",");
                if (incluirIdentificacao) {
                    csv.append("\"").append(linha.get("aluno_nome")).append("\",");
                    csv.append(linha.get("matricula")).append(",");
                }
                csv.append(linha.get("data_submissao")).append(",");
                csv.append(linha.get("questao_id")).append(",");
                csv.append("\"").append(linha.get("enunciado")).append("\",");
                csv.append(linha.get("tipo")).append(",");
                csv.append("\"").append(linha.get("texto_resposta") != null ? linha.get("texto_resposta") : "").append("\",");
                csv.append("\"").append(linha.get("alternativas_selecionadas") != null ? linha.get("alternativas_selecionadas") : "").append("\"\n");
            }
            
            response.getWriter().write(csv.toString());
            response.getWriter().flush();
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}