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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/vincular-formulario.css">
    <link
        href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap"
        rel="stylesheet">

    <title>Gerenciamento de Processos Avaliativos</title>
</head>

<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="" class="logo">
        </div>

        <div class="header-centro">
            <div class="cards-header">
                <div class="card-header textCenter translate120 azul">
                    <a href="./cursos.html">
                        <p>1. Gerenciar Cursos</p>
                    </a>
                </div>
                <div class="card-header textEnd translate90 cinza-escuro">
                    <a href="./curriculos.html">
                        <p>2. Gerenciar Currículos</p>
                    </a>
                </div>
                <div class="card-header textCenter translate60 cinza-claro">
                    <p>3. Gerenciar UCs</p>
                </div>
                <div class="card-header textCenter translate30 branco">
                    <p>4. Gerenciar Alunos</p>
                </div>
            </div>
        </div>

        <div class="header-direito">
            <button>Sair</button>
            <button>Perfil</button>
        </div>
    </header>

    <div class="titulo">
        <h2>Vincular formulario</h2>
        <p>4/4 - Processo: ${processoEmAndamento.nome}</p>
    </div>

    <div class="secao2">
        <p class="subtitulo">Formulários existentes:</p>

        <a href="${pageContext.request.contextPath}/jsp/coordenador/criar-formulario.jsp">
            <button type="button"> + Criar novo formulário </button>
        </a>
    </div>

    <div class="container">

        <div class="cards">
            <c:forEach var="form" items="${listaFormularios}">
                <div class="card">

                    <div class="itens-avaliacao">
                        <strong>${form.titulo}</strong>
                        <p class="professor">Questões: ${form.questoes.size()}</p> <span
                            style="font-size: 0.8rem; color: #666; display: block; margin-top: 5px;">ID:
                            ${form.id}</span>
                    </div>

                    <div style="display: flex; gap: 10px; margin-top: 15px;">

                        <form action="${pageContext.request.contextPath}/FinalizarProcessoServlet" method="post"
                            style="flex: 1;">
                            <input type="hidden" name="formularioId" value="${form.id}">
                            <button type="submit" class="vincular"
                                style="background-color: #FFA500; color: white; border: none; padding: 10px; border-radius: 5px; width: 100%; cursor: pointer; font-weight: bold;">
                                Vincular
                            </button>
                        </form>

                        <form action="${pageContext.request.contextPath}/ExcluirFormularioServlet" method="post"
                            style="flex: 1;"
                            onsubmit="return confirm('Tem certeza que deseja excluir este formulário?');">
                            <input type="hidden" name="formularioId" value="${form.id}">
                            <button type="submit" class="excluir"
                                style="background-color: #FF0000; color: white; border: none; padding: 10px; border-radius: 5px; width: 100%; cursor: pointer; font-weight: bold;">
                                Excluir
                            </button>
                        </form>

                    </div>

                    <button class="ver"
                        style="background-color: #555; color: white; border: none; padding: 8px; border-radius: 5px; width: 100%; margin-top: 10px; cursor: pointer;">
                        Ver Detalhes
                    </button>

                </div>
            </c:forEach>
        </div>
    </div>

</body>

</html>