<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>

<% 
    // Validação de Sessão (Scriptlet)
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    String perfil = (usuario != null) ? usuario.getPerfil().toLowerCase() : "";

    // Verifica se está logado E se o perfil é Coordenador ou Administrador (RF02)
    if (usuario == null || (!"coordenador".equals(perfil) && !"administrador".equals(perfil))) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp?msg=Acesso negado.");
        return;
    }
%> 

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/criar-formulario.css">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">
    
    <title>Novo Formulário</title>
</head>
<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="" class="logo">
        </div>
        
        <div class="header-centro">
            <div class="cards-header">
                <div class="card-header textCenter translate120 azul">
                    <a href="./cursos.html"><p>1. Gerenciar Cursos</p></a>
                </div>
                <div class="card-header textEnd translate90 cinza-escuro">
                    <a href="./curriculos.html"><p>2. Gerenciar Currículos</p></a>
                </div>
                <div class="card-header textCenter translate60 cinza-claro">
                    <p>3. Gerenciar UCs</p>
                </div>
                <div class="card-header textCenter translate30 branco">
                    <p>4. Gerenciar Alunos</p>
                </div>
            </div>
        </div>

        <div class="header-direito">
            <button>Sair</button>
            <button>Perfil</button>
        </div>
    </header>
    

    <div class="container">

        <form action="${pageContext.request.contextPath}/FormularioCriarServlet" method="post" id="mainForm">

            <input type="hidden" name="totalQuestoes" id="totalQuestoes" value="0">

            <div class="container-filho">
                <div class="titulos">
                    <label for="titulo">Título do Formulário</label>
                    <input type="text" name="titulo" style="font-size: 1.5rem; width: 100%; border: none; border-bottom: 1px solid #ccc;" placeholder="Ex: Avaliação de Algoritmos" required>
                </div>
    
                <button type="button" name="inputAnonimo" class="btn-anonimo">Formulário Anônimo</button>
                <div class="cards">
              
                    <div class="card1">
                        <label for="tipoPergunta">Tipo da Pergunta</label>
                        <select id="inputTipo" onchange="toggleAlternativas()">
                            <option value="ABERTA">Resposta aberta (Texto)</option>
                            <option value="FECHADA">Múltiplas Multipla Escolha (Única) </option>
                            <option value="MULTIPLA_SELECAO">Caixa de Seleção (Várias)</option>
                        </select>
                    </div>
    
                    <div class="card3">
                        <label for="">Obrigatória? </label>
                        <select id="inputObrigatoria">
                            <option value="sim">Sim</option>
                            <option value="nao">Não</option>
                        </select>              
                    </div>
    
                    <!-- <div class="card4">
                        <label for="">Peso</label>
                        <select name="cursos[]" multiple size="5">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>  
                        <button class="salvar">Salvar</button>            
                    </div> -->
                        
                    <button type="button" class="addP" onclick="adicionarQuestaoNaLista()">Adicionar Pergunta ao Formulário</button>
                </div>
                <div class="prgt-ab">
                    <textarea class="enunciado-ab" id="inputEnunciado" placeholder="Escreva aqui o enunciado da pergunta"></textarea>
                    
                    <div class="painel-alternativas" id="painel-alternativas" style="display:none; margin-top: 15px;">
                        <h4>Adicionar Alternativas</h4>
                        <input type="text" id="textoNovaAlt" placeholder="Texto da opção (ex: Ótima)" style="width: 60%">
                        <input type="number" id="pesoNovaAlt" placeholder="Peso (ex: 5)" style="width: 20%" value="0">
                        <button type="button" onclick="adicionarAlternativaTemp()">+ Opção</button>
                        
                        <ul id="lista-alternativas-temp"></ul>

                    </div>
                </div>
                <div id="lista-questoes-adicionadas">
                    <h3>Questões do Formulário: </h3>
                    <p id="msg-vazio"> Nenhuma questão adicionada ainda.</p>
                </div>

                <div class="botoes">
                    <button type="button" class="cancelar">Cancelar</button>
                    <button type="submit" class="prosseguir">Salvar Formulário Final</button>
            </div>
        </form>
    </div>

    <script>
        let contadorQuestoes = 0;
        let alternativasTemporarias = [];

        function toggleAlternativas(){
            const tipo = document.getElementById("inputTipo").value;
            const painel = document.getElementById("painel-alternativas");

            if(tipo === "ABERTA"){
                painel.style.display = "none";
                alternativasTemporarias = [];
                atualizarListaAlternativasTemp();
            } else{
                painel.style.display = "block";
            }
        }

        function adicionarAlternativaTemp(){
            const texto = document.getElementById("textoNovaAlt").value;
            const peso = document.getElementById("pesoNovaAlt").value;

            if(texto.trim() === "") return;

            alternativasTemporarias.push({texto: texto, peso: peso});

            // Limpa e foca
            document.getElementById("textoNovaAlt").value = "";
            document.getElementById("textoNovaAlt").focus();

            atualizarListaAlternativasTemp();
        }

        function atualizarListaAlternativasTemp(){
            const lista = document.getElementById("lista-alternativas-temp");
            lista.innerHTML = "";

            // Aqui usamos concatenação para evitar conflito com JSP
            alternativasTemporarias.forEach(function(alt) {
                lista.innerHTML += "<li>" + alt.texto + " (Peso: " + alt.peso + ")</li>";
            });
        }

        function adicionarQuestaoNaLista(){
            const enunciado = document.getElementById("inputEnunciado").value;

            if(enunciado.trim() === ""){
                alert("Digite um enunciado!");
                return;
            }

            const tipoRaw = document.getElementById("inputTipo").value;
            
            // CORREÇÃO: Lógica Sim/Não rigorosa
            const valObrigatoria = document.getElementById("inputObrigatoria").value;
            const isObrigatoria = (valObrigatoria === "sim"); 

            let tipoServlet = "FECHADA";
            let isMulti = false;

            if(tipoRaw === "ABERTA"){
                tipoServlet = "ABERTA";
            } else if (tipoRaw === "MULTIPLA_SELECAO"){
                isMulti = true;
            }

            if(tipoServlet === "FECHADA" && alternativasTemporarias.length < 2){
                alert("Adicione pelo menos 2 alternativas para questões fechadas.");
                return;
            }

            contadorQuestoes++;
            document.getElementById("totalQuestoes").value = contadorQuestoes;
            
            const msgVazio = document.getElementById("msg-vazio");
            if(msgVazio) msgVazio.style.display = "none";

            const container = document.getElementById("lista-questoes-adicionadas");

            // CORREÇÃO CRÍTICA: Usando aspas simples e concatenação (+) 
            // para o JSP não tentar processar as variáveis.
            
            let htmlQuestao = '<div class="questao-item" style="background: #f9f9f9; padding: 15px; margin-bottom: 10px; border: 1px solid #ddd; border-radius: 5px; color: #333;">';
            
            // Cabeçalho visual da questão
            htmlQuestao += '<div style="margin-bottom: 5px;">';
            htmlQuestao += '<strong>' + contadorQuestoes + '. ' + enunciado + '</strong> ';
            htmlQuestao += '<span style="background: #eee; padding: 2px 6px; font-size: 0.8em; border-radius: 4px; margin-left: 10px;">' + tipoRaw + '</span>';
            
            if(isObrigatoria) {
                htmlQuestao += '<span style="font-size: 0.8em; color: red; margin-left: 5px;">* Obrigatória</span>';
            } else {
                htmlQuestao += '<span style="font-size: 0.8em; color: green; margin-left: 5px;">(Opcional)</span>';
            }
            htmlQuestao += '</div>';

            // INPUTS HIDDEN (O que vai pro Banco)
            htmlQuestao += '<input type="hidden" name="enunciado_q' + contadorQuestoes + '" value="' + enunciado + '">';
            htmlQuestao += '<input type="hidden" name="tipo_q' + contadorQuestoes + '" value="' + tipoServlet + '">';

            if (isObrigatoria) {
                htmlQuestao += '<input type="hidden" name="obrigatoria_q' + contadorQuestoes + '" value="on">';
            }
            if (isMulti) {
                htmlQuestao += '<input type="hidden" name="multi_q' + contadorQuestoes + '" value="on">';
            }

            // Alternativas
            if(tipoServlet === "FECHADA"){
                htmlQuestao += '<ul style="margin-left: 20px; color: #555;">';
                
                alternativasTemporarias.forEach(function(alt) {
                    htmlQuestao += '<li>' + alt.texto + ' (Peso: ' + alt.peso + ')</li>';
                    
                    // Inputs das alternativas (Arrays)
                    htmlQuestao += '<input type="hidden" name="texto_alt_q' + contadorQuestoes + '" value="' + alt.texto + '">';
                    htmlQuestao += '<input type="hidden" name="peso_alt_q' + contadorQuestoes + '" value="' + alt.peso + '">';
                });
                
                htmlQuestao += '</ul>';
            }

            htmlQuestao += '</div>'; // Fecha div item

            // Insere no HTML
            container.insertAdjacentHTML('beforeend', htmlQuestao);

            // Limpa formulário
            document.getElementById("inputEnunciado").value = "";
            alternativasTemporarias = [];
            atualizarListaAlternativasTemp();
        }
    </script>
    
</body>
</html>