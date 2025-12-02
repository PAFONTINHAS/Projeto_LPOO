<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, br.ufpr.sistemaavaliacao.model.UnidadeCurricular" %>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Unidades Curriculares</title>

    <link rel="stylesheet" href="css/coordenador/unidades_curriculares.css">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">

    <style>
        .table-container {
            width: 90%;
            background: white;
            padding: 20px;
            border-radius: 15px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 1rem;
        }

        th, td {
            border-bottom: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background: #0351A6;
            color: white;
        }

        .btn {
            padding: 6px 12px;
            border-radius: 8px;
            border: none;
            cursor: pointer;
            font-size: 0.9rem;
            font-weight: bold;
        }

        .btn-editar { background: #3CA1E9; color:white; }
        .btn-excluir { background: #941111; color:white; }
        .btn-novo { background: #0351A6; color:white; margin-bottom: 15px; display:inline-block; text-decoration:none; }
    </style>
</head>
<body>

<header>
    <div class="header-esquerdo">
        <img class="imagem" src="images/logo_ufpr.png">
    </div>

    <div class="header-centro">
        <div class="cards-header">
            <div class="card-header textCenter translate60 azul">
                <a href="uc?acao=listar"><p>3. Gerenciar UCs</p></a>
            </div>
        </div>
    </div>

    <div class="header-direito">
        <button onclick="window.location.href='logout'">Sair</button>
        <button onclick="window.location.href='perfil'">Perfil</button>
    </div>
</header>

<div class="titulo">
    <h1>Unidades Curriculares Cadastradas</h1>
</div>

<div class="container">
    <div class="table-container">

        <a class="btn btn-novo" href="uc?acao=novo">+ Nova Unidade Curricular</a>

        <table>
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Período</th>
                <th>Carga Horária</th>
                <th>Tipo</th>
                <th>Ações</th>
            </tr>

            <%
                List<UnidadeCurricular> lista = (List<UnidadeCurricular>) request.getAttribute("ucs");
                if (lista != null && !lista.isEmpty()) {
                    for (UnidadeCurricular uc : lista) {
            %>
            <tr>
                <td><%= uc.getCodigo() %></td>
                <td><%= uc.getNome() %></td>
                <td><%= uc.getPeriodo() %></td>
                <td><%= uc.getCargaHoraria() %>h</td>
                <td><%= uc.getTipo() %></td>
                <td>
                    <a class="btn btn-editar" href="uc?acao=editar&id=<%= uc.getId() %>">Editar</a>
                    <a class="btn btn-excluir"
                       href="uc?acao=excluir&id=<%= uc.getId() %>"
                       onclick="return confirm('Tem certeza que deseja excluir esta UC?');">
                        Excluir
                    </a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="6" style="text-align:center;">Nenhuma unidade curricular cadastrada.</td>
            </tr>
            <% } %>
        </table>
    </div>
</div>

</body>
</html>
