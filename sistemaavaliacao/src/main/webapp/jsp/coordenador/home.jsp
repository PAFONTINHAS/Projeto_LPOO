<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>

<% 
    // Validação de Sessão (Scriptlet)
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    String perfil = (usuario != null) ? usuario.getPerfil().toLowerCase() : "";

    // Verifica se está logado E se o perfil é Coordenador ou Administrador (RF02)
    if (usuario == null || (!"coordenador".equals(perfil) && !"administrador".equals(perfil))) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp?msg=Acesso negado.");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - ${perfil}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/home.css">
</head>
<body>
    <header>
        <nav>
            <h1>Bem-vindo, ${usuario.nome} (${usuario.perfil})</h1>
            <a href="${pageContext.request.contextPath}/jsp/coordenador/criar-formulario.jsp">Criar Formulário</a>
            <a href="${pageContext.request.contextPath}/LogoutServlet">Sair</a>
        </nav>
    </header>
    
    <main>
        <h2>Dashboard de ${perfil}</h2>
        <p>Use o menu para gerenciar processos e formulários de avaliação.</p>
    </main>

    <footer>
        <p>&copy; AvaliaUFPR</p>
    </footer>
</body>
</html>