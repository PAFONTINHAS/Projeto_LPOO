<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Curriculo" %>

<% 
    Curriculo curriculo = (Curriculo) request.getAttribute("curriculo");
%>

<jsp:include page="cabecalho.jsp" />

<style>
    /* CSS adaptado de curriculos.css para o JSP */
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
        max-width: 1000px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.3);
    }
    .form-card h1 {
        color: #0351A6;
        margin-bottom: 10px;
        text-align: center;
    }
    .form-card p {
        text-align: center;
        color: #666;
        margin-bottom: 20px;
    }
    form {
        display: flex;
        flex-direction: column;
        gap: 20px;
    }
    .form-section {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
    }
    .ch-title {
        color: #333;
        font-size: 1.5rem;
        margin-top: 15px;
        width: 100%;
        border-bottom: 1px solid #ccc;
        padding-bottom: 5px;
    }
    .input_group {
        display: flex;
        flex-direction: column;
        gap: 8px;
        flex: 1 1 150px;
    }
    .input_style, select, textarea {
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
    .input-radio-group {
        display: flex;
        gap: 15px;
        margin-top: 10px;
    }
    .input-radio-group label {
        font-weight: normal;
        margin-left: 5px;
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
    
    /* Adição para alinhar o campo TCC e Observações */
    .third-section {
        align-items: flex-start;
    }
    .input_group.tcc-group {
        flex: 0 0 200px;
        margin-right: 30px;
    }
    .input_group.obs-group {
        flex: 2 1 400px;
    }
</style>

<div class="container-form">
    <div class="form-card">
        <h1>${curriculo != null ? 'Editar' : 'Novo'} Currículo</h1>
        <p>Preencha os dados do currículo. A carga horária total é calculada automaticamente.</p>
        
        <c:if test="${not empty requestScope.erro}">
            <p style="color: red; font-weight: bold; margin-bottom: 15px;">Erro: ${requestScope.erro}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/coordenador/curriculo" method="post">
            <!-- Campo oculto para ID (apenas em edição) -->
            <c:if test="${curriculo != null}">
                <input type="hidden" name="id" value="${curriculo.id}">
            </c:if>

            <div class="form-section">
                <div class="input_group" style="flex: 2 1 250px;">
                    <label for="cursoId">Curso Vinculado:</label>
                    <select name="cursoId" id="cursoId" required>
                        <option value="" disabled ${curriculo == null || curriculo.cursoId == 0 ? 'selected' : ''}>Selecione o Curso</option>
                        <c:forEach var="curso" items="${requestScope.cursos}">
                            <option value="${curso.id}" 
                                ${curriculo != null && curriculo.cursoId == curso.id ? 'selected' : ''}>
                                ${curso.nome} (${curso.codigo})
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="input_group">
                    <label for="versao">Versão do Currículo:</label>
                    <input class="input_style" type="text" name="versao" id="versao" placeholder="Ex: 01.2024" required 
                        value="${curriculo != null ? curriculo.versao : ''}">
                </div>
                <div class="input_group">
                    <label for="anoImplantacao">Ano de Implantação:</label>
                    <input class="input_style" type="number" name="anoImplantacao" id="anoImplantacao" placeholder="Ex: 2024" required
                        value="${curriculo != null ? curriculo.anoImplantacao : ''}">
                </div>
                <div class="input_group" style="flex: 2 1 350px;">
                    <label for="portariaAprovacao">Portaria de Aprovação:</label>
                    <input class="input_style" type="text" name="portariaAprovacao" id="portariaAprovacao" placeholder="Ex: Portaria n°123/2024" required
                        value="${curriculo != null ? curriculo.portariaAprovacao : ''}">
                </div>
            </div>

            <h2 class="ch-title">Carga Horária (CH)</h2>

            <div class="form-section">
                <div class="input_group">
                    <label for="chObrigatoria">CH Obrigatória:</label>
                    <input class="input_style" type="number" name="chObrigatoria" id="chObrigatoria" placeholder="0" required value="${curriculo != null ? curriculo.chObrigatoria : 0}">
                </div>

                <div class="input_group">
                    <label for="chOptativa">CH Optativas:</label>
                    <input class="input_style" type="number" name="chOptativa" id="chOptativa" placeholder="0" required value="${curriculo != null ? curriculo.chOptativa : 0}">
                </div>
                <div class="input_group">
                    <label for="chAtividadesFormativas">CH Atividades Formativas:</label>
                    <input class="input_style" type="number" name="chAtividadesFormativas" id="chAtividadesFormativas" placeholder="0" value="${curriculo != null ? curriculo.chAtividadesFormativas : 0}">
                </div>
                <div class="input_group">
                    <label for="chExtensao">CH de Extensão:</label>
                    <input class="input_style" type="number" name="chExtensao" id="chExtensao" placeholder="0" value="${curriculo != null ? curriculo.chExtensao : 0}">
                </div>
                
                <div class="input_group" style="flex: 1 1 100px;">
                    <label for="chTotal">CH Total:</label>
                    <input class="input_style" type="text" id="chTotal" placeholder="Calculado" disabled 
                        value="${curriculo != null ? curriculo.chTotal : 0}">
                </div>

                <script>
                    function calcularCHTotal() {
                        const obr = parseInt(document.getElementById('chObrigatoria').value) || 0;
                        const opt = parseInt(document.getElementById('chOptativa').value) || 0;
                        const form = parseInt(document.getElementById('chAtividadesFormativas').value) || 0;
                        const ext = parseInt(document.getElementById('chExtensao').value) || 0;
                        document.getElementById('chTotal').value = obr + opt + form + ext;
                    }
                    document.querySelectorAll('.form-section input[type="number"]').forEach(input => {
                        input.addEventListener('input', calcularCHTotal);
                    });
                    // Roda na inicialização se houver dados
                    document.addEventListener('DOMContentLoaded', calcularCHTotal);
                </script>
            </div>

            <div class="form-section third-section">
                <div class="input_group tcc-group">
                    <label>TCC Obrigatório?</label>
                    <div class="input-radio-group">
                        <input type="radio" name="tccObrigatorio" id="tccSim" value="sim" 
                            ${curriculo == null || curriculo.tccObrigatorio ? 'checked' : ''}>
                        <label for="tccSim">Sim</label>

                        <input type="radio" name="tccObrigatorio" id="tccNao" value="nao" 
                            ${curriculo != null && !curriculo.tccObrigatorio ? 'checked' : ''}>
                        <label for="tccNao">Não</label>
                    </div>
                </div>

                <div class="input_group obs-group">
                    <label for="observacoes">Observações:</label>
                    <textarea name="observacoes" id="observacoes">${curriculo != null ? curriculo.observacoes : ''}</textarea>
                </div>
            </div>

            <div class="buttons">
                <a href="${pageContext.request.contextPath}/coordenador/curriculo?acao=listar" class="btn-cancelar">Cancelar</a>
                <button type="submit" class="btn-salvar">Salvar Currículo</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="rodape.jsp" />