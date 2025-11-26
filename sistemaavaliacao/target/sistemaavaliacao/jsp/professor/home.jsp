<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>

<% 
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null || !"professor".equalsIgnoreCase(usuario.getPerfil())) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp?msg=Acesso negado.");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Professor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/professor/home.css">
</head>
<body>
    <header>
        <nav>
            <h1>Painel do Professor: ${usuario.nome}</h1>
            <a href="${pageContext.request.contextPath}/LogoutServlet">Sair</a>
        </nav>
    </header>
    
    <main>
        <h2>Minhas Turmas e Relatórios</h2>
        <p>Visualize as disciplinas e acesse os relatórios consolidados (RF16, RF19).</p>
    </main>
</body>
</html>