<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Minhas Avaliações - AvaliaUFPR</title>
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
        .btn-primary {
            background: #667eea;
            color: white;
        }
        .btn-primary:hover {
            background: #5568d3;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background: #5a6268;
        }
        .alert {
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
        }
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .avaliacoes-list {
            display: grid;
            gap: 20px;
        }
        .avaliacao-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            transition: transform 0.3s;
        }
        .avaliacao-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 12px rgba(0,0,0,0.15);
        }
        .avaliacao-title {
            font-size: 20px;
            color: #333;
            margin-bottom: 10px;
            font-weight: 600;
        }
        .avaliacao-info {
            color: #666;
            font-size: 14px;
            margin-bottom: 5px;
        }
        .avaliacao-status {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            margin-top: 10px;
            margin-right: 10px;
        }
        .status-pendente {
            background: #fff3cd;
            color: #856404;
        }
        .status-respondido {
            background: #d4edda;
            color: #155724;
        }
        .status-anonimo {
            background: #d1ecf1;
            color: #0c5460;
        }
        .avaliacao-actions {
            margin-top: 15px;
            display: flex;
            gap: 10px;
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
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📋 Minhas Avaliações</h1>
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-secondary">← Voltar</a>
        </div>

        <% if (request.getParameter("sucesso") != null) { %>
            <div class="alert alert-success">
                ✓ Avaliação enviada com sucesso!
            </div>
        <% } %>

        <% if (request.getAttribute("erro") != null) { %>
            <div class="alert alert-error">
                ⚠ <%= request.getAttribute("erro") %>
            </div>
        <% } %>

        <div class="avaliacoes-list">
            <%
                List<Map<String, Object>> formularios = (List<Map<String, Object>>) request.getAttribute("formularios");
                
                if (formularios == null || formularios.isEmpty()) {
            %>
                <div class="empty-state">
                    <h2>Nenhuma avaliação disponível</h2>
                    <p>Não há avaliações ativas no momento para as suas turmas.</p>
                </div>
            <%
                } else {
                    for (Map<String, Object> form : formularios) {
                        boolean jaRespondido = (Boolean) form.get("ja_respondido");
                        boolean isAnonimo = (Boolean) form.get("is_anonimo");
            %>
                <div class="avaliacao-card">
                    <div class="avaliacao-title"><%= form.get("titulo") %></div>
                    <div class="avaliacao-info">
                        <strong>Processo:</strong> <%= form.get("processo_nome") %>
                    </div>
                    <div class="avaliacao-info">
                        <strong>Período:</strong> <%= form.get("data_inicio") %> até <%= form.get("data_fim") %>
                    </div>
                    
                    <% if (form.get("instrucoes") != null && !form.get("instrucoes").toString().isEmpty()) { %>
                        <div class="avaliacao-info">
                            <strong>Instruções:</strong> <%= form.get("instrucoes") %>
                        </div>
                    <% } %>
                    
                    <div>
                        <% if (jaRespondido) { %>
                            <span class="avaliacao-status status-respondido">✓ Respondido</span>
                        <% } else { %>
                            <span class="avaliacao-status status-pendente">⏳ Pendente</span>
                        <% } %>
                        
                        <% if (isAnonimo) { %>
                            <span class="avaliacao-status status-anonimo">🔒 Anônimo</span>
                        <% } %>
                    </div>
                    
                    <div class="avaliacao-actions">
                        <% if (jaRespondido) { %>
                            <a href="${pageContext.request.contextPath}/avaliacao/responder?id=<%= form.get("id") %>" 
                               class="btn btn-secondary">✏️ Editar Resposta</a>
                        <% } else { %>
                            <a href="${pageContext.request.contextPath}/avaliacao/responder?id=<%= form.get("id") %>" 
                               class="btn btn-primary">▶️ Responder</a>
                        <% } %>
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