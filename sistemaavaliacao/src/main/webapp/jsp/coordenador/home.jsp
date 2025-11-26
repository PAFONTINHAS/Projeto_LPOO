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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/home.css">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">

    <title>Página Inicial</title>
</head>
<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="../../images/logo_ufpr.png" alt="" class="logo">
        </div>

        <div class="header-direito">
            <button>Sair</button>
            <button>Perfil</button>
        </div>
    </header>

    <div class="titulo">
        <h1>Página Inicial</h1>
    </div>

    <div class="container">


        <div class="cards">
            <div class="card">
                <a href="cursos_curriculos.jsp">Gerenciar Cursos e Currículos</a>
            </div>

            <div class="card">
                <a href="processos-avaliativos.jsp">Gerenciar Processos Avaliativos</a>
            </div>
            <div class="card">
                <a href="criar-formulario">Gerenciar Formulários</a>
            </div>
            <div class="card">
                <a href="">Relatórios e Dados</a>
            </div>
        </div>

    </div>
    
</body>
</html>