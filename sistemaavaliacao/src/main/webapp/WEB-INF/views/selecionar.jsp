<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Selecionar Relatório - AvaliaUFPR</title>
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
            max-width: 1000px;
            margin: 0 auto;
        }
        .header {
            background: white;
            padding: 20px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            margin-bottom: 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            color: #333;
            font-size: 24px;
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
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        .formularios-list {
            display: grid;
            gap: 20px;
        }
        .formulario-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            transition: transform 0.3s;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .formulario-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 12px rgba(0,0,0,0.15);
        }
        .formulario-info {
            flex: 1;
        }
        .formulario-title {
            font-size: 18px;
            color: #333;
            font-weight: 600;
            margin-bottom: 8px;
        }
        .formulario-meta {
            color: #666;
            font-size: 14px;
            margin-bottom: 5px;
        }
        .respostas-badge {
            display: inline-block;
            background: #e7f3ff;
            color: #0c5460;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: 600;
            margin-top: 10px;
        }
        .empty-state {
            background: white;
            padding: 60px 40px;
            border-radius: 10px;
            text-align: center;
            color: #666;
        }
        .empty-state h2 {
            color: #333;
            margin-bottom: 10px;
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
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📊 Selecionar Relatório</h1>
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-secondary">← Voltar</a>
        </div>

        <% if (request.getAttribute("erro") != null) { %>
            <div class="alert alert-error">
                ⚠ <%= request.getAttribute("erro") %>
            </div>
        <% } %>

        <div class="formularios-list">
            <%
                List<Map<String, Object>> formularios = (List<Map<String, Object>>) request.getAttribute("formularios");
                
                if (formularios == null || formularios.isEmpty()) {
            %>
                <div class="empty-state">
                    <h2>Nenhum formulário disponível</h2>
                    <p>Não há formulários cadastrados no sistema.</p>
                </div>
            <%
                } else {
                    for (Map<String, Object> form : formularios) {
                        int totalRespostas = (Integer) form.get("total_respostas");
            %>
                <div class="formulario-card">
                    <div class="formulario-info">
                        <div class="formulario-title"><%= form.get("titulo") %></div>
                        <div class="formulario-meta">
                            <strong>Processo:</strong> <%= form.get("processo_nome") %>
                        </div>
                        <span class="respostas-badge">
                            📝 <%= totalRespostas %> resposta(s)
                        </span>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}/relatorio/basico?id=<%= form.get("id") %>" 
                           class="btn btn-primary">Ver Relatório →</a>
                    </div>
                </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</body>
</html>