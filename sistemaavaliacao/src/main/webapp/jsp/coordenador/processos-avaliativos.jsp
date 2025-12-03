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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/processos-avaliativos.css">
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
                    <p>1. Cursos e Currículos</p>
                </div>
                <div class="card-header textEnd translate90 cinza-escuro">
                    <p>2. Processos Avaliativos</p>
                </div>
                <div class="card-header textCenter translate60 cinza-claro">
                    <p>3. Formulários</p>
                </div>
                <div class="card-header textCenter translate30 branco">
                    <p>4. Relatórios</p>
                </div>
            </div>
        </div>

        <div class="header-direito">
            <button>Sair</button>
            <button>Perfil</button>
        </div>
    </header>

    <div class="titulo">
        <h2>Gerenciamento de Processos Avaliativos</h2>
    </div>

    <div class="secao2">
        <p class="subtitulo">Processos existentes:</p>
        <button> <a href="${pageContext.request.contextPath}/jsp/coordenador/novo_processo1.jsp">+ Criar novo processo</a>  </button>
    </div>

    <div class="container">

        <div class="cards">

            <c:forEach var="proc" items="${processos}">
                <div class="card">
                    <div class="itens-avaliacao">
                        <a href="${pageContext.request.contextPath}/DetalhesProcessoServlet?id=${proc.id}">
                            <strong>${proc.nome}</strong>
                        </a>
                        <p class="periodo">Início: ${proc.dataInicio} - Fim: ${proc.dataFim}</p>
                        <p class="status">Status: ${proc.status} </p>
                    </div>

                    <div class="botoes">
                        <button class="editar">Editar</button>
                        <button class="excluir">Excluir</button>
                        
                        <a href="${pageContext.request.contextPath}/DetalhesProcessoServlet?id=${proc.id}">
                            <button class="ver-resultados">Ver Detalhes</button>
                        </a>
                    </div>
                </div>
            </c:forEach>

            <%-- <div class="card">
                <div class="itens-avaliacao">
                    <a href="">Avaliação docente 2025</a>
                    <p class="periodo">Período: 20/10/2025 a 23/12/2025</p>
                    <p class="status">Status: Em Andamento</p>
                </div>

                <div class="botoes">
                    <button class="editar">Editar</button>
                    <button class="excluir">Excluir</button>
                    <button class="ver-resultados">Ver resultados</button>
                </div>
            </div>

            <div class="card">
                <div class="itens-avaliacao">
                    <a href="">Avaliação docente 2025</a>
                    <p class="periodo">Período: 20/10/2025 a 23/12/2025</p>
                    <p class="status">Status: Em Andamento</p>
                </div>

                <div class="botoes">
                    <button class="editar">Editar</button>
                    <button class="excluir">Excluir</button>
                    <button class="ver-resultados">Ver resultados</button>
                </div>
            </div> --%>
        </div>
    </div>

</body>

</html>