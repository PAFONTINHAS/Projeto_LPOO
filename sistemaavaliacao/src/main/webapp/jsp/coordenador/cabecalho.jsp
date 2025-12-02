<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>

<% 
    // Validação de Sessão (Redundante, mas garante que o cabecalho só carrega para coordenador/adm)
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    String perfil = (usuario != null) ? usuario.getPerfil().toLowerCase() : "";

    if (usuario == null || (!"coordenador".equals(perfil) && !"administrador".equals(perfil))) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp?msg=Acesso negado.");
        return;
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Reutilizando CSS existente para a navegação visual -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/cursos.css"> 
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">

    <title>Gerenciar Cursos e Currículos</title>
</head>
<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="Logo UFPR" class="logo">
        </div>
        
        <div class="header-centro">
            <div class="cards-header">
                <div class="card-header textCenter translate120 azul">
                    <a href="${pageContext.request.contextPath}/coordenador/curso?acao=listar"><p>1. Gerenciar Cursos</p></a>
                </div>
                <div class="card-header textCenter translate90 cinza-escuro">
                    <a href="${pageContext.request.contextPath}/coordenador/curriculo?acao=listar"><p>2. Gerenciar Currículos</p></a>
                </div>
                <div class="card-header textCenter translate60 cinza-claro">
                    <a style="color: black;" href="unidades_curriculares.jsp"><p>3. Gerenciar UCs</p></a>
                </div>
                <div class="card-header textCenter translate30 branco">
                    <a style="color: black;" href="alunos.jsp">
                        <p>4. Gerenciar Alunos</p>
                    </a>
                </div>
            </div>
        </div>

        <div class="header-direito">
            <button><a href="${pageContext.request.contextPath}/LogoutServlet" style="color: black;">Sair</a></button>
            <button>Perfil</button>
        </div>
    </header>

    <div class="titulo">
        <h1>Gerenciamento de Cursos e Currículos</h1>
    </div>

    <!-- O conteúdo de cada página JSP (listagem ou form) virá depois deste ponto -->