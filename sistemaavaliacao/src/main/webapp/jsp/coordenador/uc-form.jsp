<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.UnidadeCurricular" %>

<%
    UnidadeCurricular uc = (UnidadeCurricular) request.getAttribute("uc");
    boolean editando = uc != null;
%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title><%= editando ? "Editar UC" : "Cadastrar UC" %></title>

    <link rel="stylesheet" href="css/coordenador/unidades_curriculares.css">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">
</head>
<body>

<header>
    <div class="header-esquerdo">
        <img class="imagem" src="images/logo_ufpr.png" alt="UFPR">
    </div>
    
    <div class="header-centro">
        <div class="cards-header">
            <div class="card-header textCenter translate60 azul">
                <a href="uc?acao=listar">
                    <p>3. Gerenciar UCs</p>
                </a>
            </div>
        </div>
    </div>

    <div class="header-direito">
        <button onclick="window.location.href='logout'">Sair</button>
        <button onclick="window.location.href='perfil'">Perfil</button>
    </div>
</header>

<div class="titulo">
    <h1><%= editando ? "Editar Unidade Curricular" : "Cadastrar Unidade Curricular" %></h1>
    <p>*O curso vinculado é definido automaticamente</p>
</div>

<div class="container">

    <form action="uc" method="post">

        <% if(editando){ %>
            <input type="hidden" name="id" value="<%= uc.getId() %>">
        <% } %>

        <div class="first-section">

            <div class="input_group">
                <label for="codigo">Código da Disciplina:</label>
                <input class="input_style" type="text" name="codigo"
                       placeholder="EX: DS142"
                       value="<%= editando ? uc.getCodigo() : "" %>" required>
            </div>

            <div class="input_group grow">
                <label for="nome">Nome da Disciplina:</label>
                <input class="input_style" type="text" name="nome"
                       placeholder="Ex: Desenvolvimento Web III"
                       value="<%= editando ? uc.getNome() : "" %>" required>
            </div>

            <div class="input_group">
                <label for="periodo">Período</label>
                <input class="input_style" type="text" name="periodo"
                       placeholder="Ex: 4° Período"
                       value="<%= editando ? uc.getPeriodo() : "" %>" required>
            </div>

            <div class="input_group">
                <label for="cargaHoraria">Carga Horária</label>
                <input class="input_style" type="number" name="cargaHoraria"
                       placeholder="Ex: 60"
                       value="<%= editando ? uc.getCargaHoraria() : "" %>" required>
            </div>
        </div>

        <p class="pre-requisitos">Pré-requisitos</p>

        <div class="second-section">

            <div class="requisitos">

                <div class="adicionar-requisito">
                    <div class="input_group grow">
                        <input class="input_style" type="text" id="novoRequisito"
                               placeholder="Digite o código da disciplina (Ex: DS131)">
                    </div>

                    <button type="button" class="adicionar" onclick="addRequisito()">Adicionar</button>
                </div>

                <div class="card-requisitos" id="listaRequisitos">
                    <!-- Aqui você pode futuramente renderizar requisitos vindos do backend -->
                </div>
            </div>

            <div class="input-group">
                <label class="labelUnidade" for="tipo">Tipo de Unidade </label>
                <select class="tipoUnidade" name="tipo" id="tipo" required>
                    <option value="" disabled <%= !editando ? "selected" : "" %>>Selecione</option>
                    <option value="Obrigatória" <%= editando && "Obrigatória".equals(uc.getTipo()) ? "selected" : "" %>>Obrigatória</option>
                    <option value="Optativa"    <%= editando && "Optativa".equals(uc.getTipo())    ? "selected" : "" %>>Optativa</option>
                    <option value="Formativa"   <%= editando && "Formativa".equals(uc.getTipo())   ? "selected" : "" %>>Formativa</option>
                    <option value="Extensão"    <%= editando && "Extensão".equals(uc.getTipo())    ? "selected" : "" %>>Extensão</option>
                </select>
            </div>
        </div>

        <div class="third-section">
            <div class="input_group grow">
                <label for="observacoes">Observações:</label>
                <textarea name="observacoes" id="observacoes"
                          placeholder="Descreva o conteúdo básico da disciplina e objetivos..."><%= editando ? uc.getObservacoes() : "" %></textarea>
            </div>
        </div>

        <div class="buttons">
            <button class="vermelho" type="button">
                <a href="uc?acao=listar">Cancelar</a>
            </button>
            <button type="submit">Salvar</button>
        </div>

    </form>
</div>

<script>
    // Apenas efeito visual dos pré-requisitos por enquanto
    let requisitos = [];

    function addRequisito() {
        const input = document.getElementById("novoRequisito");
        const valor = input.value.trim();
        if (!valor) return;

        requisitos.push(valor);
        const container = document.getElementById("listaRequisitos");
        container.innerHTML = requisitos
            .map(r => `<div style="margin:8px;padding:8px 15px;border-radius:20px;background:#3CA1E9;color:#fff;display:inline-block;">${r}</div>`)
            .join("");

        input.value = "";
    }
</script>

</body>
</html>
