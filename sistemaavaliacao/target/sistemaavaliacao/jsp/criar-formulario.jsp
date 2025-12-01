<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="cabecalho.jsp" %> <%-- Inclui cabeçalho de coord/adm --%>

<div class="content-wrapper">
    <h1>Criar Novo Formulário de Avaliação</h1>

    <form action="${pageContext.request.contextPath}/FormularioCriarServlet" method="post">
        
        <div class="card">
            <label for="titulo">Título</label><input type="text" name="titulo" required>
            <label>Instruções</label><textarea name="instrucoes"></textarea>
            <label><input type="checkbox" name="anonimo" value="on"> Formulário Anônimo (RF11)</label>
        </div>

        <div id="questoes-container">
            <h3>Questões</h3>

            <%-- Exemplo: Questão de Múltipla Escolha (RF08) --%>
            <div class="questao-card">
                <h4>Questão 1: Múltipla Escolha</h4>
                <label for="q1_enunciado">Enunciado</label><textarea name="q1_enunciado" required></textarea>
                <label><input type="checkbox" name="q1_obrigatoria" value="on"> Obrigatória (RF10)</label>
                <label><input type="checkbox" name="q1_multipla" value="on"> Permite Múltipla Seleção</label>
                
                <h5>Alternativas</h5>
                <input type="text" name="q1_alt1" placeholder="Alternativa 1" required><input type="number" name="q1_peso1" value="1">
                <%-- ... (mais alternativas) --%>
            </div>
            
            <%-- Exemplo: Questão Aberta (RF09) --%>
            <div class="questao-card">
                <h4>Questão 2: Aberta</h4>
                <label for="q2_enunciado">Enunciado</label><textarea name="q2_enunciado" required></textarea>
                <label><input type="checkbox" name="q2_obrigatoria" value="on"> Obrigatória (RF10)</label>
            </div>
        </div>

        <button type="submit" class="btn-salvar">Salvar Formulário</button>
    </form>
</div>

<%@include file="rodape.jsp" %>