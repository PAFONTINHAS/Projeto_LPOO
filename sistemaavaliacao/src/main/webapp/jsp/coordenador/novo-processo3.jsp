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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/novo-processo3.css">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">

    <title>Gerenciamento de Processos Avaliativos</title>
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
        <div class="container-filho">
            <div class="titulos">
                <h2>Novo Processo Avaliativo</h2>
                <p class="selec-turmas">Selecione as turmas participantes:</p>
            </div>

            <div class="cards">
                
                <div class="card1">
                    <label>Filtrar por Curso:</label>
                    <button type="button" onclick="selecionarTodos('selectCursos')">Selecionar todos</button>
                    
                    <select id="selectCursos" multiple size="5" onchange="aplicarFiltros()">
                        <c:forEach var="nome" items="${cursosSelecionados}">
                            <option value="${nome}" selected>${nome}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="card2">
                    <label>Disciplinas Encontradas:</label>
                    <button type="button" onclick="selecionarTodos('selectDisciplinas')">Selecionar todos</button>
                    
                    <select id="selectDisciplinas" multiple size="5" onchange="aplicarFiltros()">
                        <c:forEach var="disc" items="${disciplinas}">
                            <option value="${disc.id}" data-curso="${disc.curso.nome}">
                                ${disc.nome}
                            </option>
                        </c:forEach>
                    </select>                    
                </div>

                <div class="card3">
                    <label>Turmas Disponíveis (Selecione para Salvar):</label>
                    <button type="button" onclick="selecionarTodos('selectTurmas')">Selecionar todos</button>
                    
                    <form id="formSalvarTurmas" action="${pageContext.request.contextPath}/SalvarTurmasProcessoServlet" method="post">
                        
                        <select id="selectTurmas" name="idsTurmasSelecionadas" multiple size="5">
                            <c:forEach var="turma" items="${turmas}">
                                <option value="${turma.id}" 
                                        data-curso="${turma.unidadeCurricular.curso.nome}"
                                        data-disciplina="${turma.unidadeCurricular.id}">
                                    [${turma.unidadeCurricular.curso.nome}] ${turma.unidadeCurricular.nome} - ${turma.anoSemestre}
                                </option>
                            </c:forEach>
                        </select>
                    </form>
                </div>
            </div>

            <div class="botoes">
                <button type="button" class="cancelar" onclick="history.back()">Voltar</button>
                
                <button type="button" class="prosseguir" onclick="enviarFormulario()">Finalizar e Vincular</button>
            </div>
        </div>
    </div>

    <script>
    
        function aplicarFiltros() {
            // 1. Pega Cursos Selecionados
            const selectCursos = document.getElementById('selectCursos');
            const cursosSelecionados = Array.from(selectCursos.selectedOptions).map(opt => opt.value);

            // 2. Filtra Disciplinas (Baseado nos Cursos)
            const selectDisciplinas = document.getElementById('selectDisciplinas');
            const optionsDisc = Array.from(selectDisciplinas.options);
            
            optionsDisc.forEach(opt => {
                const cursoDaDisc = opt.getAttribute('data-curso');
                const mostrar = cursosSelecionados.length === 0 || cursosSelecionados.includes(cursoDaDisc);
                opt.style.display = mostrar ? '' : 'none';
                if (!mostrar) opt.selected = false;
            });

            // --- AQUI ESTÁ A MUDANÇA ---
            
            // 3. Pega Disciplinas Selecionadas (para refinar o filtro de turmas)
            // Se nenhuma disciplina estiver selecionada, assumimos "Todas as visíveis"
            const disciplinasSelecionadas = Array.from(selectDisciplinas.selectedOptions).map(opt => opt.value);

            // 4. Filtra Turmas (Baseado em Curso E Disciplina)
            const selectTurmas = document.getElementById('selectTurmas');
            const optionsTurma = Array.from(selectTurmas.options);

            optionsTurma.forEach(opt => {
                const cursoDaTurma = opt.getAttribute('data-curso');
                const idDisciplinaDaTurma = opt.getAttribute('data-disciplina');
                
                // Regra 1: O curso tem que estar selecionado
                const cursoOk = cursosSelecionados.length === 0 || cursosSelecionados.includes(cursoDaTurma);
                
                // Regra 2: Se houver disciplinas selecionadas, a turma tem que pertencer a uma delas
                const disciplinaOk = disciplinasSelecionadas.length === 0 || disciplinasSelecionadas.includes(idDisciplinaDaTurma);

                const mostrar = cursoOk && disciplinaOk;
                
                opt.style.display = mostrar ? '' : 'none';
                if (!mostrar) opt.selected = false;
            });
        }

        // Função utilitária para os botões "Selecionar Todos"
        function selecionarTodos(idSelect) {
            const select = document.getElementById(idSelect);
            const options = Array.from(select.options);
            
            // Seleciona apenas os que estão visíveis (não ocultos pelo filtro)
            options.forEach(opt => {
                if (opt.style.display !== 'none') {
                    opt.selected = true;
                }
            });
            
            // Atualiza os filtros em cascata
            aplicarFiltros();
        }

        function enviarFormulario() {
            // Garante que pelo menos uma turma foi selecionada
            const selectTurmas = document.getElementById('selectTurmas');
            if (selectTurmas.selectedOptions.length === 0) {
                alert("Por favor, selecione pelo menos uma turma para vincular ao processo.");
                return;
            }
            document.getElementById('formSalvarTurmas').submit();
        }
    </script>
    
</body>
</html>