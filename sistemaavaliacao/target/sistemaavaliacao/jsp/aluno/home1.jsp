<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>

<% 
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null || !"aluno".equalsIgnoreCase(usuario.getPerfil())) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp?msg=Acesso negado.");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno/home.css">
</head>
<body>
    <header>
        <nav>
            <h1>Área do Aluno: ${usuario.nome}</h1>
            <a href="#">Minhas Avaliações (RF12)</a>
            <a href="${pageContext.request.contextPath}/LogoutServlet">Sair</a>
        </nav>
    </header>
    
    <main>
        <h2>Avaliações Disponíveis</h2>
        <p>Acesse aqui os formulários de avaliação das turmas em que você está matriculado (RF12).</p>
    </main>
</body>
</html>