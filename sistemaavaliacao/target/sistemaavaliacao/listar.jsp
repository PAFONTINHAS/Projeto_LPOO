<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listagem</title>
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

        .azul {
            background-color: #0351A6;
            color: white;
            z-index: 4;
        }

        .cinza-escuro {
            background-color: #999DA3;
            z-index: 3;
        }

        .cinza-claro {
            background-color: #D9D9D9;
            color: black;
            z-index: 2;
        }

        .branco {
            background-color: #ffffff;
            z-index: 4;
        }

        .vermelho {
            background-color: #941111;
        }

        .translate120 {
            transform: translateX(120px);
        }
        .translate90 {
            transform: translateX(90px);
        }
        .translate60 {
            transform: translateX(60px);
        }
        .translate30 {
            transform: translateX(30px);
        }

        .textEnd {
            text-align: end;
        }

        .textStart {
            text-align: start;
        }

        .textCenter {
            text-align: center;
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

        .header-centro {
            display: flex;
            align-items: center;
        }

        .cards-header {
            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: center;
        }

        .card-header {
            padding: 10px;
            border-radius: 20px;
            width: 210px;
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

        .tabela {
            display: flex;
            flex-direction: column;
            background-color: #F1F1F1;
            border-radius: 10px;
            align-items: center;
            width: 70%;
            padding: 20px;
        }

        .titulo-tabela {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
            width: 100%;
            justify-content: center;
            align-items: center;
        }

        .titulo-tabela h3 {
            align-self: start;
            margin-left: 20px;
        }

        .secao-topo {
            display: flex;
            justify-content: space-between;
            width: 100%;
            padding: 10px 20px;
            align-items: center;
        }

        .secao-topo button {
            background-color: #1F18AB;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 10px;
            font-size: 1rem;
            cursor: pointer;
        }

        .itens {
            display: flex;
            flex-direction: column;
            align-items: start;
            width: 100%;
            gap: 10px;
            padding: 10px;
        }

        .item {
            display: flex;
            width: 100%;
            padding: 20px;
            justify-content: space-between;
            background-color: #D9D9D9;
            border-radius: 50px;
        }

        .info-item {
            display: flex;
            gap: 20px;
            align-items: center;
        }

        .acao-item button {
            color: white;
            font-size: 1rem;
            border: none;
            padding: 8px 15px;
            border-radius: 10px;
            font-weight: bold;
            cursor: pointer;
            margin-left: 5px;
        }

        .editar {
            background-color: #efb907;
        }

        .deletar {
            background-color: #FF0000;
        }

        .visualizar {
            background-color: #1F18AB;
        }
    </style>
</head>
<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="images/logo_ufpr.png" alt="Logo UFPR" class="logo">
        </div>
        
        <div class="header-centro">
            <div class="cards-header">
                <div class="card-header textCenter translate120 azul">
                    <a href="#"><p>1. Cursos</p></a>
                </div>
                <div class="card-header textEnd translate90 cinza-escuro">
                    <a href="#"><p>2. Currículos</p></a>
                </div>
                <div class="card-header textCenter translate60 cinza-claro">
                    <p>3. UCs</p>
                </div>
                <div class="card-header textCenter translate30 branco">
                    <p>4. Alunos</p>
                </div>
            </div>
        </div>

        <div class="header-direito">
            <button>Sair</button>
            <button>Perfil</button>
        </div>
    </header>

    <div class="titulo">
        <h1>Listagem de Itens</h1>
    </div>

    <div class="container">
        <div class="tabela">
            <div class="secao-topo">
                <h2>Itens Cadastrados</h2>
                <button>+ Novo Item</button>
            </div>

            <div class="itens">
                <!-- Iteração dinâmica com JSTL -->
                <c:forEach var="item" items="${itens}">
                    <div class="item">
                        <div class="info-item">
                            <p><strong>${item.nome}</strong></p>
                            <p>${item.descricao}</p>
                        </div>
                        <div class="acao-item">
                            <button class="visualizar">Visualizar</button>
                            <button class="editar">Editar</button>
                            <button class="deletar">Deletar</button>
                        </div>
                    </div>
                </c:forEach>

                <!-- Exemplo estático para visualização -->
                <c:if test="${empty itens}">
                    <div class="item">
                        <div class="info-item">
                            <p><strong>Item Exemplo 1</strong></p>
                            <p>Descrição do item exemplo 1</p>
                        </div>
                        <div class="acao-item">
                            <button class="visualizar">Visualizar</button>
                            <button class="editar">Editar</button>
                            <button class="deletar">Deletar</button>
                        </div>
                    </div>
                    <div class="item">
                        <div class="info-item">
                            <p><strong>Item Exemplo 2</strong></p>
                            <p>Descrição do item exemplo 2</p>
                        </div>
                        <div class="acao-item">
                            <button class="visualizar">Visualizar</button>
                            <button class="editar">Editar</button>
                            <button class="deletar">Deletar</button>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

</body>
</html>
