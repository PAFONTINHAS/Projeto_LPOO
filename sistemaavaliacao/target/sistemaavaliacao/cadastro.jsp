<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastro.css">
    <title>Cadastro Aluno</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&display=swap" rel="stylesheet">
</head>
<body>

    <header>
        <img src="../images/logo_ufpr.png" alt="" class="logo">
    </header>

    <div class="container">

        <div class="titulo">
            <p class="titulo_cadastro">Cadastre-se</p>
            
            <p>Já tem conta? <a href="login.jsp">Faça o login aqui</a></p>
        </div>

        <c:if test="${not empty requestScope.mensagemErro}">
            <p style="color: red; font-weight: bold;">${requestScope.mensagemErro}</p>
        </c:if>
        <c:if test="${not empty requestScope.mensagemSucesso}">
            <p style="color: green; font-weight: bold;">${requestScope.mensagemSucesso}</p>
        </c:if>


        <div class="formulario">

            <form action="${pageContext.request.contextPath}/CadastroServlet" method="post">
                
                <div class="grupo-esquerdo">
                    <label for="nome">Nome:</label>
                    <input type="text" name="nome" placeholder="Nome completo">
                    
                    <label for="email">E-mail:</label>
                    <input type="email" name="email" placeholder="user@email.com">
                    
                    <label for="senha">Senha:</label>
                    <input type="password" name="senha" placeholder="XXXXXXXXX">
                    
                </div>
                
                <div class="grupo-direito">
                    <label for="login">Login:</label>
                    <input type="text" name="login" placeholder="">
                    
                    <label for="perfil">Perfil:</label>
                    <select name="perfil" id="perfil" onchange="toggleInfoEspecifica()">
                        <option value="Selecione" selected disabled>Selecione</option>
                        <option value="aluno">Aluno</option>
                        <option value="professor">Professor</option>
                        <option value="coordenador">Coordenador</option>
                        <option value="administrador">Administrador</option>
                    </select>

                    <div class="input-group" id="infoEspecificaGroup" style="display:none;">
                        <label for="info_especifica" id="infoEspecificaLabel"></label>
                        <input type="text" id="info_especifica" name="info_especifica">
                    </div>
                    
                    <div class="input-group" id="departamentoGroup" style="display:none;">
                        <label for="departamento">Departamento (Opcional)</label>
                        <input type="text" id="departamento" name="departamento">
                    </div>

                    <div class="cadastrar">
                        <button type="submit">Criar Conta</button>
                    </div>
                </div>
    
            </form>
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