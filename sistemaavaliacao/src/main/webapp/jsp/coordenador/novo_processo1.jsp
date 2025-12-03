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
    <title>Novo Processo Avaliativo</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/novo-processo1.css">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">

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


<div class="container">
    <div class="cards">

        <p class="titulo-card">Novo Processo Avaliativo</p>

        <form action="${pageContext.request.contextPath}/SalvarProcessoInicial" method="post">

            <div class="campo">
                <label for="titulo">Título</label>
                <input type="text" name="titulo" required>
            </div>
    
            <div class="campo">
                <label for="periodo">Período:</label>
            </div>
    
            <div class="input-data">
                <div class="campo">
                    <label for="inicio">Início:</label>
                    <input type="date" name="inicio" required>
                </div>
    
                <div class="campo">
                    <label for="fim">Fim:</label>
                    <input type="date" name="fim" required>
                </div>
            </div>
    
            <div class="campo">
                <label for="observacoes">Observações (opcional):</label>
                <textarea name="observacoes"></textarea>
            </div>
    
            <div class="botoes">
                <button type="button" class="cancelar"> Cancelar</button>
                <button type="submit" class="prosseguir">Prosseguir</button>
            </div>
        </form>

    </div>
</div>

    
</body>
</html>