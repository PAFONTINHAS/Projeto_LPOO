<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Curso" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>

<% 
    // Captura o objeto Curso do Request para preenchimento do formulário (se for edição)
    Curso curso = (Curso) request.getAttribute("curso");
%>

<jsp:include page="cabecalho.jsp" />

<style>
    /* CSS adaptado de cursos.css/curriculos.css para o JSP */
    .container-form {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        padding: 40px;
        background-color: #0351A6;
        min-height: calc(100vh - 130px);
    }
    .form-card {
        background: white;
        padding: 30px;
        border-radius: 15px;
        width: 100%;
        max-width: 900px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.3);
    }
    .form-card h1 {
        color: #0351A6;
        margin-bottom: 20px;
        text-align: center;
    }
    form {
        display: flex;
        flex-direction: column;
        gap: 30px;
    }
    .form-section {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
    }
    .input_group, .select_group {
        display: flex;
        flex-direction: column;
        gap: 8px;
        flex: 1 1 200px; /* Flexível para responsividade */
    }
    .input_style, select {
        padding: 10px;
        border: 2px solid #3CA1E9;
        border-radius: 8px;
        font-size: 1rem;
        width: 100%;
    }
    label {
        font-weight: bold;
        color: #333;
    }
    .checkbox-group {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-top: 25px;
    }
    .checkbox-group input[type="checkbox"] {
        width: 20px;
        height: 20px;
    }
    .buttons {
        display: flex;
        justify-content: flex-end;
        gap: 15px;
        margin-top: 30px;
    }
    .buttons button, .buttons a {
        padding: 12px 25px;
        border: none;
        border-radius: 8px;
        font-size: 1.1rem;
        cursor: pointer;
        text-decoration: none;
        color: white;
        font-weight: 600;
        transition: opacity 0.3s;
    }
    .btn-salvar { background-color: #0351A6; }
    .btn-cancelar { background-color: #941111; }
    .btn-salvar:hover, .btn-cancelar:hover { opacity: 0.8; }
</style>

<div class="container-form">
    <div class="form-card">
        <h1>${curso != null ? 'Editar' : 'Novo'} Curso</h1>
        
        <c:if test="${not empty requestScope.erro}">
            <p style="color: red; font-weight: bold; margin-bottom: 15px;">Erro: ${requestScope.erro}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/coordenador/curso" method="post">
            <!-- Campo oculto para ID (apenas em edição) -->
            <c:if test="${curso != null}">
                <input type="hidden" name="id" value="${curso.id}">
            </c:if>

            <div class="form-section">
                <div class="input_group">
                    <label for="codigo">Código do Curso:</label>
                    <input class="input_style" type="text" name="codigo" id="codigo" placeholder="EX: TADS001" required 
                        value="${curso != null ? curso.codigo : ''}">
                </div>

                <div class="input_group" style="flex: 2 1 350px;">
                    <label for="nome">Nome do Curso:</label>
                    <input class="input_style" type="text" name="nome" id="nome" placeholder="Ex: Análise e Desenvolvimento de Sistemas" required
                        value="${curso != null ? curso.nome : ''}">
                </div>
                
                <div class="select_group" style="flex: 1 1 200px;">
                    <label for="coordenadorId">Coordenador:</label>
                    <select name="coordenadorId" id="coordenadorId" required>
                        <option value="" disabled ${curso == null ? 'selected' : ''}>Selecione</option>
                        <c:forEach var="coordenador" items="${requestScope.coordenadores}">
                            <option value="${coordenador.id}" 
                                ${curso != null && curso.coordenadorUsuarioId == coordenador.id ? 'selected' : ''}>
                                ${coordenador.nome} (${coordenador.perfil})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="input_group checkbox-group" style="flex: 1 1 100px;">
                    <label for="isAtivo">Curso Ativo?</label>
                    <input type="checkbox" name="isAtivo" id="isAtivo" 
                        ${curso == null || curso.isAtivo ? 'checked' : ''}>
                </div>
            </div>

            <div class="form-section">
                <div class="select_group">
                    <label for="campus">Campus:</label>
                    <select name="campus" id="campus" required>
                        <option value="" disabled ${curso == null || curso.campus == null ? 'selected' : ''}>Selecione</option>
                        <option value="Agrária" ${curso != null && curso.campus == 'Agrária' ? 'selected' : ''}>Agrária</option>
                        <option value="Botânico" ${curso != null && curso.campus == 'Botânico' ? 'selected' : ''}>Botânico</option>
                        <option value="Politécnico" ${curso != null && curso.campus == 'Politécnico' ? 'selected' : ''}>Politécnico</option>
                        <option value="Reitoria" ${curso != null && curso.campus == 'Reitoria' ? 'selected' : ''}>Reitoria</option>
                    </select>
                </div>

                <div class="select_group">
                    <label for="modalidade">Modalidade:</label>
                    <select name="modalidade" id="modalidade" required>
                        <option value="" disabled ${curso == null || curso.modalidade == null ? 'selected' : ''}>Selecione</option>
                        <option value="EAD" ${curso != null && curso.modalidade == 'EAD' ? 'selected' : ''}>EAD</option>
                        <option value="Presencial" ${curso != null && curso.modalidade == 'Presencial' ? 'selected' : ''}>Presencial</option>
                        <option value="Híbrido" ${curso != null && curso.modalidade == 'Híbrido' ? 'selected' : ''}>Híbrido</option>
                    </select>
                </div>
                
                <div class="select_group">
                    <label for="turno">Turno:</label>
                    <select name="turno" id="turno" required>
                        <option value="" disabled ${curso == null || curso.turno == null ? 'selected' : ''}>Selecione</option>
                        <option value="Matutino" ${curso != null && curso.turno == 'Matutino' ? 'selected' : ''}>Matutino</option>
                        <option value="Vespertino" ${curso != null && curso.turno == 'Vespertino' ? 'selected' : ''}>Vespertino</option>
                        <option value="Noturno" ${curso != null && curso.turno == 'Noturno' ? 'selected' : ''}>Noturno</option>
                        <option value="Integral" ${curso != null && curso.turno == 'Integral' ? 'selected' : ''}>Integral</option>
                    </select>
                </div>
                
                <div class="select_group">
                    <label for="setor">Setor:</label>
                    <select name="setor" id="setor" required>
                        <option value="" disabled ${curso == null || curso.setor == null ? 'selected' : ''}>Selecione</option>
                        <option value="Tecnológico" ${curso != null && curso.setor == 'Tecnológico' ? 'selected' : ''}>Tecnológico</option>
                        <option value="Humanas" ${curso != null && curso.setor == 'Humanas' ? 'selected' : ''}>Humanas</option>
                        <option value="Exatas" ${curso != null && curso.setor == 'Exatas' ? 'selected' : ''}>Exatas</option>
                        <option value="HC" ${curso != null && curso.setor == 'HC' ? 'selected' : ''}>HC</option>
                    </select>
                </div>
            </div>

            <div class="buttons">
                <a href="${pageContext.request.contextPath}/coordenador/curso?acao=listar" class="btn-cancelar">Cancelar</a>
                <button type="submit" class="btn-salvar">Salvar Curso</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="rodape.jsp" />