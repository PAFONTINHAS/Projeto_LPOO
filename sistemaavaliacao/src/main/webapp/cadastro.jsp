<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro - AvaliaUFPR</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastro.css">
</head>
<body>
<div class="cadastro-container">
    <div class="cadastro-form">
        <h2>Cadastro de Novo Usuário (RF01)</h2>
        
        <c:if test="${not empty requestScope.mensagemErro}">
            <p style="color: red; font-weight: bold;">${requestScope.mensagemErro}</p>
        </c:if>
        <c:if test="${not empty requestScope.mensagemSucesso}">
            <p style="color: green; font-weight: bold;">${requestScope.mensagemSucesso}</p>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/CadastroServlet" method="post">
            <div class="input-group">
                <label for="nome">Nome Completo</label>
                <input type="text" id="nome" name="nome" required>
            </div>
            <div class="input-group">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="input-group">
                <label for="login">Login</label>
                <input type="text" id="login" name="login" required>
            </div>
            <div class="input-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" required>
            </div>
            
            <div class="input-group">
                <label for="perfil">Perfil</label>
                <select id="perfil" name="perfil" required onchange="toggleInfoEspecifica()">
                    <option value="">-- Selecione o Perfil --</option>
                    <option value="aluno">Aluno</option>
                    <option value="professor">Professor</option>
                    <option value="coordenador">Coordenador</option>
                    <option value="administrador">Administrador</option>
                </select>
            </div>

            <div class="input-group" id="infoEspecificaGroup" style="display:none;">
                <label for="info_especifica" id="infoEspecificaLabel"></label>
                <input type="text" id="info_especifica" name="info_especifica">
            </div>
            
            <div class="input-group" id="departamentoGroup" style="display:none;">
                <label for="departamento">Departamento (Opcional)</label>
                <input type="text" id="departamento" name="departamento">
            </div>
            
            <button type="submit" class="btn-cadastro">CADASTRAR</button>
        </form>
        <p class="link-login"><a href="login.jsp">Já tenho uma conta</a></p>
    </div>
</div>

<script>
    function toggleInfoEspecifica() {
        const perfil = document.getElementById('perfil').value;
        const infoGroup = document.getElementById('infoEspecificaGroup');
        const infoLabel = document.getElementById('infoEspecificaLabel');
        const infoInput = document.getElementById('info_especifica');
        const deptoGroup = document.getElementById('departamentoGroup');

        // Resetar todos os campos dinâmicos
        infoGroup.style.display = 'none';
        infoInput.required = false;
        infoInput.value = '';
        deptoGroup.style.display = 'none';
        
        // Configurar campos com base no perfil
        if (perfil === 'aluno') {
            infoLabel.textContent = 'Matrícula (Obrigatório)';
            infoGroup.style.display = 'block';
            infoInput.required = true;
        } else if (perfil === 'professor') {
            infoLabel.textContent = 'Registro (Obrigatório)';
            infoGroup.style.display = 'block';
            infoInput.required = true;
            deptoGroup.style.display = 'block';
        }
        // Coordenador e Administrador não precisam de campo específico no cadastro.
    }
</script>
</body>
</html>