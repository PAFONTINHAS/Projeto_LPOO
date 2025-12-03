<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>

<% 
    // Validação de Sessão
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    String perfil = (usuario != null) ? usuario.getPerfil().toLowerCase() : "";
    
    if (usuario == null || (!"coordenador".equals(perfil) && !"administrador".equals(perfil))) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp?msg=Acesso negado.");
        return;
    }
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/detalhes-processo.css">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">
    <title>Detalhes do Processo</title>
</head>

<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="Logo UFPR">
        </div>
        
        <div class="header-centro">
            <div class="cards-header">
                <div class="card-header textCenter translate120 azul">
                    <a href="./cursos.html"><p>1. Gerenciar Cursos</p></a>
                </div>
                <div class="card-header textEnd translate90 cinza-escuro">
                    <a href="${pageContext.request.contextPath}/ListarProcessosServlet"><p>2. Processos Avaliativos</p></a>
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

    <div class="titulo-pagina">
        <h2>${processo.nome}</h2>
        <div class="info-datas">
            <span><strong>Início:</strong> ${processo.dataInicio}</span>
            <span><strong>Fim:</strong> ${processo.dataFim}</span>
        </div>
    </div>

    <div class="container-detalhes">
        
        <div class="barra-acoes">
            <button class="btn-voltar" onclick="history.back()">← Voltar</button>
            <p class="subtitulo">Visualização dos Formulários Vinculados</p>
        </div>

        <c:forEach var="form" items="${formularios}">
            <div class="card-formulario">
                
                <div class="cabecalho-form">
                    <h3>${form.titulo}</h3>
                    <span class="badge-anonimo">
                        ${form.isAnonimo ? 'Anônimo' : 'Identificado'}
                    </span>
                </div>
                
                <p class="instrucoes"><em>"${form.instrucoes}"</em></p>
                <hr>

                <div class="lista-questoes">
                    <c:forEach var="q" items="${form.questoes}" varStatus="status">
                        <div class="item-questao">
                            <div class="enunciado">
                                <strong>${status.count}. ${q.enunciado}</strong>
                                <c:if test="${q.isObrigatoria}">
                                    <span style="color: red; font-size: 0.8em;">*</span>
                                </c:if>
                            </div>

                            <div class="area-resposta">
                                <c:if test="${q.class.simpleName == 'QuestaoAberta'}">
                                    <textarea disabled class="input-preview">Espaço para resposta dissertativa do aluno...</textarea>
                                </c:if>

                                <c:if test="${q.class.simpleName == 'QuestaoMultiplaEscolha'}">
                                    <ul class="lista-alternativas">
                                        <c:forEach var="alt" items="${q.alternativas}">
                                            <li>
                                                <input type="${q.permiteMultiplaSelecao ? 'checkbox' : 'radio'}" disabled>
                                                <label>${alt.texto} <span class="peso-badge">(Peso: ${alt.peso})</span></label>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                    
                    <c:if test="${empty form.questoes}">
                        <p style="color: #666; padding: 20px; text-align: center;">Este formulário ainda não possui questões cadastradas.</p>
                    </c:if>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty formularios}">
            <div class="card-formulario" style="text-align: center; padding: 50px;">
                <h3>Nenhum formulário vinculado.</h3>
                <p>Vincule um formulário para ver o preview aqui.</p>
            </div>
        </c:if>

    </div>

</body>
</html>