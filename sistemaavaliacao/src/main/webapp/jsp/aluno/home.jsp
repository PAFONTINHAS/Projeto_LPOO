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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno/home.css">
     <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=edit_square" />
    
    <title>Pesquisas</title>
</head>
<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="" class="logo">
        </div>

        <div class="header-direito">
            <button><a style="text-decoration=none" href="${pageContext.request.contextPath}/LogoutServlet">Sair</a></button>
            <button>Perfil</button>
        </div>
    </header>

    <div class="container">

        <div class="card">

            <div class="titulo-card">
                <h1>Pesquisas Disponíveis <i class="fa-solid fa-user"></i></h1>
            </div>


            <div class="pesquisas">

                <ul>
                    <li>

                        <a href="pesquisa.jsp">Avaliação de Ferramentas de IA com Professor X <span class="material-symbols-outlined redirect">edit_square</span></a>
                    </li>

                </ul>
            </div>
            
        </div>
    </div>


    
</body>
</html>