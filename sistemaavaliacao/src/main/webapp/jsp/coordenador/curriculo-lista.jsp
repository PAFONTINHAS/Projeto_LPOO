<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Curriculo" %>
<%@ page import="java.util.List" %>

<jsp:include page="cabecalho.jsp" />

<style>
/* CSS BÁSICO PARA LISTAGEM, MELHORIAS PODEM SER FEITAS NO CSS EXTERNO */
.container-list {
    padding: 30px;
    background-color: #f1f1f1;
    min-height: 80vh;
    border-radius: 10px;
}
.header-list {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    border-bottom: 2px solid #ccc;
    padding-bottom: 10px;
}
.header-list h1 {
    color: #0351A6;
    font-size: 2em;
}
.list-group {
    list-style: none;
    padding: 0;
}
.list-item {
    background-color: white;
    margin-bottom: 10px;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.list-item-info {
    flex-grow: 1;
}
.list-item-info h3 {
    margin: 0 0 5px 0;
    color: #333;
}
.list-item-info p {
    margin: 0;
    font-size: 0.9em;
    color: #666;
}
.list-item-actions button, .list-item-actions a {
    padding: 8px 12px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 0.9em;
    margin-left: 10px;
    text-decoration: none;
    color: white;
}
.btn-editar { background-color: orange; }
.btn-excluir { background-color: #941111; }
.btn-novo { background-color: #0351A6; }
.btn-link { background-color: #28a745; }
.btn-link:hover { opacity: 0.9; }

.total-ch {
    display: inline-block;
    background-color: #e0f7fa;
    color: #00796b;
    padding: 5px 10px;
    border-radius: 5px;
    font-weight: bold;
    margin-top: 5px;
}
</style>

<div class="container-list">
    <div class="header-list">
        <h1>Gerenciar Currículos (RF04)</h1>
        <div style="display: flex; gap: 10px;">
            <a href="${pageContext.request.contextPath}/coordenador/curso?acao=listar" class="btn-link" style="background-color: #007bff;">
                ← Gerenciar Cursos
            </a>
            <a href="curriculo?acao=novo" class="btn-link"> + Novo Currículo</a>
        </div>
    </div>

    <c:if test="${not empty param.msg}">
        <p style="color: green; font-weight: bold; margin-bottom: 15px;">${param.msg}</p>
    </c:if>
    <c:if test="${not empty requestScope.erro}">
        <p style="color: red; font-weight: bold; margin-bottom: 15px;">Erro: ${requestScope.erro}</p>
    </c:if>

    <h2 style="font-size: 1.5em; color: #555; margin-bottom: 15px;">Currículos Cadastrados</h2>
    
    <c:choose>
        <c:when test="${empty requestScope.curriculos}">
            <p>Nenhum currículo cadastrado ainda.</p>
        </c:when>
        <c:otherwise>
            <ul class="list-group">
                <c:forEach var="curriculo" items="${requestScope.curriculos}">
                    <li class="list-item">
                        <div class="list-item-info">
                            <h3>${curriculo.cursoNome} - Versão ${curriculo.versao}</h3>
                            <p>
                                Implantação: ${curriculo.anoImplantacao} | 
                                Portaria: ${curriculo.portariaAprovacao} | 
                                TCC: ${curriculo.tccObrigatorio ? 'Obrigatório' : 'Opcional'}
                            </p>
                            <span class="total-ch">Carga Horária Total: ${curriculo.chTotal}h</span>
                        </div>
                        <div class="list-item-actions">
                            <a href="curriculo?acao=editar&id=${curriculo.id}" class="btn-editar">Editar</a>
                            <a href="curriculo?acao=excluir&id=${curriculo.id}" class="btn-excluir" onclick="return confirm('Tem certeza que deseja excluir o currículo ${curriculo.versao} do curso ${curriculo.cursoNome}?');">Excluir</a>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="rodape.jsp" />