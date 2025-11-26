<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página Inicial</title>
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            box-sizing: border-box;
            font-family: 'Gabarito', sans-serif;
        }

        a {
            text-decoration: none;
            color: white;
        }

        .preto {
            color: black;
        }

        body {
            background-color: #0351A6;
        }

        header {
            display: flex;
            justify-content: space-between;
            background-color: #E9E9E9;
        }

        .imagem {
            height: 130px;
            padding: 20px;
        }

        .header-direito {
            display: flex;
            align-items: center;
        }

        .header-direito button {
            margin: 10px;
            padding: 5px;
            font-size: 1rem;
        }

        .titulo {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 30px;
            color: white;
        }

        .container {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            margin-top: 30px;
        }

        .cards {
            display: flex;
            flex-wrap: wrap;
            gap: 30px;
            justify-content: center;
        }

        .card {
            background-color: #E9E3E3;
            border-radius: 15px;
            padding: 40px 60px;
            text-align: center;
            font-size: 1.2rem;
            transition: transform 0.3s;
            cursor: pointer;
        }

        .card:hover {
            transform: scale(1.05);
        }

        .card a {
            color: black;
            text-decoration: none;
        }

        .mensagem {
            background-color: #F1F1F1;
            padding: 20px 40px;
            border-radius: 10px;
            margin-bottom: 30px;
            text-align: center;
        }

        .mensagem-sucesso {
            background-color: #d4edda;
            color: #155724;
        }

        .mensagem-erro {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="images/logo_ufpr.png" alt="Logo UFPR" class="logo">
        </div>

        <div class="header-direito">
            <button>Sair</button>
            <button>Perfil</button>
        </div>
    </header>

    <div class="titulo">
        <h1>Página Inicial</h1>
    </div>

    <div class="container">

        <!-- Exibir mensagens de feedback -->
        <!-- tipoMensagem pode ser: 'mensagem-sucesso' ou 'mensagem-erro' -->
        <c:if test="${not empty mensagem}">
            <div class="mensagem <c:choose><c:when test='${not empty tipoMensagem}'>${tipoMensagem}</c:when><c:otherwise>mensagem-sucesso</c:otherwise></c:choose>">
                <p>${mensagem}</p>
            </div>
        </c:if>

        <div class="cards">
            <div class="card">
                <a href="listar.jsp">Gerenciar Cursos e Currículos</a>
            </div>

            <div class="card">
                <a href="selecionar.jsp">Gerenciar Processos Avaliativos</a>
            </div>
            
            <div class="card">
                <a href="#">Gerenciar Formulários</a>
            </div>
            
            <div class="card">
                <a href="#">Relatórios e Dados</a>
            </div>
        </div>

    </div>
    
</body>
</html>
