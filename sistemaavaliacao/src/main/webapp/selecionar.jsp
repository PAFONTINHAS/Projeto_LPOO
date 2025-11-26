<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Selecionar</title>
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

        .secao2 {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin: 0 auto;
            width: 70%;
            padding: 10px;
        }

        .secao2 .subtitulo {
            color: white;
            font-size: 1.2rem;
        }

        .secao2 button {
            background-color: #1F18AB;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 10px;
            font-size: 1rem;
            cursor: pointer;
        }

        .secao2 button a {
            color: white;
            text-decoration: none;
        }

        .container {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            margin-top: 20px;
        }

        .cards {
            display: flex;
            flex-direction: column;
            gap: 15px;
            width: 70%;
        }

        .card {
            background-color: #E9E3E3;
            border-radius: 15px;
            padding: 20px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .itens-avaliacao {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .itens-avaliacao a {
            color: black;
            font-size: 1.2rem;
            font-weight: bold;
        }

        .itens-avaliacao .periodo,
        .itens-avaliacao .status,
        .itens-avaliacao .descricao {
            font-size: 0.9rem;
            color: #555;
        }

        .botoes {
            display: flex;
            gap: 10px;
        }

        .botoes button {
            border: none;
            padding: 10px 20px;
            border-radius: 10px;
            font-size: 0.9rem;
            font-weight: bold;
            cursor: pointer;
            color: white;
        }

        .selecionar-btn {
            background-color: #1F18AB;
        }

        .editar {
            background-color: #efb907;
        }

        .excluir {
            background-color: #FF0000;
        }

        .ver-resultados {
            background-color: #28a745;
        }

        .form-selecao {
            background-color: #F1F1F1;
            border-radius: 10px;
            padding: 30px;
            width: 70%;
            margin-top: 30px;
        }

        .form-selecao h3 {
            margin-bottom: 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-bottom: 20px;
        }

        .form-group label {
            font-weight: bold;
        }

        .form-group select {
            padding: 10px;
            border-radius: 5px;
            border: 2px solid #160EA2;
            font-size: 1rem;
        }

        .form-group input[type="text"] {
            padding: 10px;
            border-radius: 5px;
            border: 2px solid #3CA1E9;
            font-size: 1rem;
        }

        .form-buttons {
            display: flex;
            justify-content: flex-end;
            gap: 15px;
        }

        .form-buttons button {
            padding: 10px 25px;
            border: none;
            border-radius: 10px;
            font-size: 1rem;
            cursor: pointer;
        }

        .btn-cancelar {
            background-color: #941111;
            color: white;
        }

        .btn-confirmar {
            background-color: #1F18AB;
            color: white;
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
                <div class="card-header textCenter translate120 cinza-claro">
                    <a href="basico.jsp" class="preto"><p>1. Início</p></a>
                </div>
                <div class="card-header textEnd translate90 cinza-escuro">
                    <a href="listar.jsp"><p>2. Listagem</p></a>
                </div>
                <div class="card-header textCenter translate60 azul">
                    <a href="selecionar.jsp"><p>3. Seleção</p></a>
                </div>
                <div class="card-header textCenter translate30 branco">
                    <p>4. Configurações</p>
                </div>
            </div>
        </div>

        <div class="header-direito">
            <button>Sair</button>
            <button>Perfil</button>
        </div>
    </header>

    <div class="titulo">
        <h2>Selecionar Item</h2>
    </div>

    <div class="secao2">
        <p class="subtitulo">Itens disponíveis para seleção:</p>
        <button><a href="#">+ Novo Item</a></button>
    </div>

    <div class="container">

        <div class="cards">
            <!-- Iteração dinâmica com JSTL -->
            <c:forEach var="item" items="${itensDisponiveis}">
                <div class="card">
                    <div class="itens-avaliacao">
                        <a href="#">${item.nome}</a>
                        <p class="descricao">${item.descricao}</p>
                        <p class="status">Status: ${item.status}</p>
                    </div>
                    <div class="botoes">
                        <button class="selecionar-btn">Selecionar</button>
                        <button class="editar">Editar</button>
                        <button class="excluir">Excluir</button>
                        <button class="ver-resultados">Ver Detalhes</button>
                    </div>
                </div>
            </c:forEach>

            <!-- Exemplos estáticos para visualização -->
            <c:if test="${empty itensDisponiveis}">
                <div class="card">
                    <div class="itens-avaliacao">
                        <a href="#">Avaliação Docente 2025</a>
                        <p class="periodo">Período: 20/10/2025 a 23/12/2025</p>
                        <p class="status">Status: Em Andamento</p>
                    </div>
                    <div class="botoes">
                        <button class="selecionar-btn">Selecionar</button>
                        <button class="editar">Editar</button>
                        <button class="excluir">Excluir</button>
                        <button class="ver-resultados">Ver Resultados</button>
                    </div>
                </div>

                <div class="card">
                    <div class="itens-avaliacao">
                        <a href="#">Avaliação de Infraestrutura</a>
                        <p class="periodo">Período: 01/11/2025 a 30/11/2025</p>
                        <p class="status">Status: Aguardando Início</p>
                    </div>
                    <div class="botoes">
                        <button class="selecionar-btn">Selecionar</button>
                        <button class="editar">Editar</button>
                        <button class="excluir">Excluir</button>
                        <button class="ver-resultados">Ver Resultados</button>
                    </div>
                </div>
            </c:if>
        </div>

        <!-- Formulário de seleção com filtros -->
        <div class="form-selecao">
            <h3>Filtrar Seleção</h3>
            <form action="selecionar.jsp" method="get">
                <div class="form-group">
                    <label for="tipo">Tipo:</label>
                    <select name="tipo" id="tipo">
                        <option value="" disabled selected>Selecione o tipo</option>
                        <option value="avaliacao">Avaliação</option>
                        <option value="formulario">Formulário</option>
                        <option value="processo">Processo</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="status">Status:</label>
                    <select name="status" id="status">
                        <option value="" disabled selected>Selecione o status</option>
                        <option value="ativo">Ativo</option>
                        <option value="inativo">Inativo</option>
                        <option value="andamento">Em Andamento</option>
                        <option value="concluido">Concluído</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="busca">Buscar por nome:</label>
                    <input type="text" name="busca" id="busca" placeholder="Digite para buscar...">
                </div>

                <div class="form-buttons">
                    <button type="reset" class="btn-cancelar">Limpar</button>
                    <button type="submit" class="btn-confirmar">Filtrar</button>
                </div>
            </form>
        </div>

    </div>

</body>
</html>
