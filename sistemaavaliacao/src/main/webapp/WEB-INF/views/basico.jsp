<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatório de Avaliação - AvaliaUFPR</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .header {
            background: white;
            padding: 25px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            margin-bottom: 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header-info h1 {
            color: #333;
            font-size: 26px;
            margin-bottom: 5px;
        }
        .header-info .subtitle {
            color: #666;
            font-size: 14px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
            font-weight: 500;
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background: #5a6268;
        }
        .btn-primary {
            background: #667eea;
            color: white;
        }
        .btn-primary:hover {
            background: #5568d3;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            text-align: center;
        }
        .stat-value {
            font-size: 36px;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 5px;
        }
        .stat-label {
            color: #666;
            font-size: 14px;
        }
        .section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            margin-bottom: 25px;
        }
        .section-title {
            font-size: 20px;
            color: #333;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #667eea;
        }
        .questao-resultado {
            margin-bottom: 30px;
            padding-bottom: 30px;
            border-bottom: 1px solid #eee;
        }
        .questao-resultado:last-child {
            border-bottom: none;
        }
        .questao-enunciado {
            font-size: 16px;
            font-weight: 600;
            color: #333;
            margin-bottom: 15px;
        }
        .alternativa-resultado {
            margin-bottom: 10px;
        }
        .alternativa-texto {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 5px;
            font-size: 14px;
        }
        .progress-bar {
            background: #e9ecef;
            height: 25px;
            border-radius: 5px;
            overflow: hidden;
            position: relative;
        }
        .progress-fill {
            background: linear-gradient(90deg, #667eea, #764ba2);
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 12px;
            font-weight: 600;
            transition: width 0.5s ease;
        }
        .peso-badge {
            background: #f8f9fa;
            padding: 2px 8px;
            border-radius: 12px;
            font-size: 12px;
            color: #666;
        }
        .resposta-aberta-item {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 15px;
            border-left: 3px solid #667eea;
        }
        .resposta-aberta-texto {
            color: #333;
            line-height: 1.6;
            margin-bottom: 8px;
        }
        .resposta-aberta-meta {
            color: #999;
            font-size: 12px;
        }
        .export-section {
            margin-top: 20px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }
        .alert {
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .no-data {
            text-align: center;
            color: #999;
            padding: 40px;
            font-style: italic;
        }
    </style>
</head>
<body>
    <div class="container">
        <%
            Map<String, Object> formulario = (Map<String, Object>) request.getAttribute("formulario");
            Integer totalSubmissoes = (Integer) request.getAttribute("totalSubmissoes");
            Map<Integer, Map<String, Object>> questoesOrganizadas = (Map<Integer, Map<String, Object>>) request.getAttribute("questoesOrganizadas");
            List<Map<String, Object>> respostasAbertas = (List<Map<String, Object>>) request.getAttribute("respostasAbertas");
            Double scoreMedio = (Double) request.getAttribute("scoreMedio");
            String perfil = (String) request.getAttribute("perfil");
            DecimalFormat df = new DecimalFormat("#.##");
            
            if (formulario == null) {
        %>
            <div class="alert alert-error">
                ⚠ Relatório não encontrado.
            </div>
            <a href="${pageContext.request.contextPath}/relatorio/basico" class="btn btn-secondary">← Voltar</a>
        <%
                return;
            }
        %>

        <div class="header">
            <div class="header-info">
                <h1>📊 <%= formulario.get("titulo") %></h1>
                <div class="subtitle">
                    <strong>Processo:</strong> <%= formulario.get("processo_nome") %> | 
                    <strong>Período:</strong> <%= formulario.get("data_inicio") %> a <%= formulario.get("data_fim") %>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/relatorio/basico" class="btn btn-secondary">← Voltar</a>
        </div>

        <% if (request.getAttribute("erro") != null) { %>
            <div class="alert alert-error">
                ⚠ <%= request.getAttribute("erro") %>
            </div>
        <% } %>

        <!-- Estatísticas Gerais -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-value"><%= totalSubmissoes %></div>
                <div class="stat-label">Total de Respostas</div>
            </div>
            <div class="stat-card">
                <div class="stat-value"><%= df.format(scoreMedio) %></div>
                <div class="stat-label">Score Médio Ponderado</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">
                    <%= (Boolean) formulario.get("is_anonimo") ? "🔒 Sim" : "👤 Não" %>
                </div>
                <div class="stat-label">Avaliação Anônima</div>
            </div>
        </div>

        <!-- Questões de Múltipla Escolha -->
        <% if (questoesOrganizadas != null && !questoesOrganizadas.isEmpty()) { %>
            <div class="section">
                <h2 class="section-title">📈 Questões de Múltipla Escolha</h2>
                
                <%
                    for (Map.Entry<Integer, Map<String, Object>> entry : questoesOrganizadas.entrySet()) {
                        Map<String, Object> questao = entry.getValue();
                        List<Map<String, Object>> alternativas = (List<Map<String, Object>>) questao.get("alternativas");
                %>
                    <div class="questao-resultado">
                        <div class="questao-enunciado"><%= questao.get("enunciado") %></div>
                        
                        <%
                            for (Map<String, Object> alt : alternativas) {
                                int contagem = (Integer) alt.get("contagem");
                                double percentual = (Double) alt.get("percentual");
                                int peso = (Integer) alt.get("peso");
                                double safePercentual = (Double.isNaN(percentual) || Double.isInfinite(percentual)) ? 0.0 : percentual;
                        %>
                            <div class="alternativa-resultado">
                                <div class="alternativa-texto">
                                    <span><%= alt.get("texto") %></span>
                                    <div>
                                        <span class="peso-badge">Peso: <%= peso %></span>
                                        <span style="margin-left: 10px; font-weight: 600;"><%= contagem %> votos</span>
                                    </div>
                                </div>
                                <div class="progress-bar">
                                    <div class="progress-fill" style="width: <%= String.format(java.util.Locale.US, "%.2f%%", safePercentual) %>;">
                                        <%= df.format(safePercentual) %>%
                                    </div>
                                </div>
                            </div>
                        <%
                            }
                        %>
                    </div>
                <%
                    }
                %>
            </div>
        <% } %>

        <!-- Respostas Abertas -->
        <% if (respostasAbertas != null && !respostasAbertas.isEmpty()) { %>
            <div class="section">
                <h2 class="section-title">💬 Respostas Abertas</h2>
                
                <% 
                    // Agrupar por questão
                    Map<Integer, List<Map<String, Object>>> respostasAgrupadasMap = new LinkedHashMap<>();
                    for (Map<String, Object> resp : respostasAbertas) {
                        int qId = (Integer) resp.get("questao_id");
                        if (!respostasAgrupadasMap.containsKey(qId)) {
                            respostasAgrupadasMap.put(qId, new ArrayList<>());
                        }
                        respostasAgrupadasMap.get(qId).add(resp);
                    }
                    
                    for (Map.Entry<Integer, List<Map<String, Object>>> entryResp : respostasAgrupadasMap.entrySet()) {
                        List<Map<String, Object>> respostas = entryResp.getValue();
                        if (!respostas.isEmpty()) {
                            String enunciado = (String) respostas.get(0).get("enunciado");
                %>
                    <div class="questao-resultado">
                        <div class="questao-enunciado"><%= enunciado %></div>
                        <div style="color: #666; font-size: 13px; margin-bottom: 15px;">
                            <%= respostas.size() %> resposta(s)
                        </div>
                        
                        <%
                            for (Map<String, Object> resp : respostas) {
                        %>
                            <div class="resposta-aberta-item">
                                <div class="resposta-aberta-texto">
                                    "<%= resp.get("texto_resposta") %>"
                                </div>
                                <div class="resposta-aberta-meta">
                                    Respondido em: <%= resp.get("data_submissao") %>
                                </div>
                            </div>
                        <%
                            }
                        %>
                    </div>
                <%
                        }
                    }
                %>
            </div>
        <% } %>

        <!-- Exportar Dados (apenas para administradores) -->
        <% if ("administrador".equals(perfil)) { %>
            <div class="section">
                <h2 class="section-title">📥 Exportar Dados</h2>
                <p style="color: #666; margin-bottom: 15px;">
                    Baixe os dados brutos em formato CSV para análise externa.
                </p>
                
                <form method="post" action="${pageContext.request.contextPath}/relatorio/basico" style="display: inline-block; margin-right: 15px;">
                    <input type="hidden" name="formularioId" value="<%= formulario.get("id") %>">
                    <input type="hidden" name="incluirIdentificacao" value="false">
                    <button type="submit" class="btn btn-primary">📄 Exportar (Anônimo)</button>
                </form>
                
                <% if (!(Boolean) formulario.get("is_anonimo")) { %>
                    <form method="post" action="${pageContext.request.contextPath}/relatorio/basico" style="display: inline-block;">
                        <input type="hidden" name="formularioId" value="<%= formulario.get("id") %>">
                        <input type="hidden" name="incluirIdentificacao" value="true">
                        <button type="submit" class="btn btn-secondary">👤 Exportar (com Identificação)</button>
                    </form>
                <% } %>
            </div>
        <% } %>

        <% if (totalSubmissoes == 0) { %>
            <div class="section">
                <div class="no-data">
                    Ainda não há respostas para este formulário.
                </div>
            </div>
        <% } %>
    </div>
</body>
</html>