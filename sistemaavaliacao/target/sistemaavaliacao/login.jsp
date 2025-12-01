<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <title>Cadastro Aluno</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">
</head>
<body>

    <header>
        <img src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="" class="logo">
    </header>

    <div class="container">

        <div class="titulo">
            <p class="titulo_cadastro">Faça seu login aqui</p>
        </div>

        <c:if test="${not empty requestScope.mensagemErro}">
            <p style="color: red; font-weight: bold;">${requestScope.mensagemErro}</p>
        </c:if>
        <c:if test="${not empty requestScope.mensagemSucesso}">
            <p style="color: green; font-weight: bold;">${requestScope.mensagemSucesso}</p>
        </c:if>

        <div class="formulario">

            <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                
                <label for="login">Perfil:</label>
                <input type="text" name="login" required>
                
                <label for="senha">Senha:</label>
                <input type="password" name="senha" placeholder="XXXXXXXXX">

                <p>Não possui login? <a href="${pageContext.request.contextPath}/cadastro.jsp">Faça seu cadastro aqui</a></p>

                <button type="submit" class="entrar">Entrar</button>

                <%-- <a href="./aluno/home.html">Aluno</a>
                <a href="./coordenador/home.html">Coordenador</a> --%>
                    
            </form>
        </div>
        

        
    </div>
    
    
</body>
</html>