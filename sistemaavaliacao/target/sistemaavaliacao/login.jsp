<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - AvaliaUFPR</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<div class="login-container">
    <div class="login-form">
        <h2>Sistema de Avaliação de Disciplinas</h2>
        
        <c:if test="${not empty requestScope.mensagemErro}">
            <p style="color: red; font-weight: bold;">${requestScope.mensagemErro}</p>
        </c:if>
        <c:if test="${not empty requestScope.mensagemSucesso}">
            <p style="color: green; font-weight: bold;">${requestScope.mensagemSucesso}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <div class="input-group">
                <label for="login">Login</label>
                <input type="text" id="login" name="login" required>
            </div>
            <div class="input-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" required>
            </div>
            <button type="submit" class="btn-login">ENTRAR</button>
        </form>
        <p class="link-cadastro"><a href="cadastro.jsp">Cadastre-se</a></p>
    </div>
</div>
</body>
</html>