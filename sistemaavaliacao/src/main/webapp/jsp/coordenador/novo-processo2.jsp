<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Curso" %>
<%@ page import="br.ufpr.sistemaavaliacao.dao.CursoDAO" %>
<%@ page import="br.ufpr.sistemaavaliacao.config.ConnectionFactory" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.*" %>
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

    List<Curso> listaCursos = null;

    try(Connection conn = ConnectionFactory.getConnection()){
        CursoDAO cursoDao = new CursoDAO(conn);
        listaCursos = cursoDao.listarTodos();
    } catch(Exception e){
        e.printStackTrace();
    }
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coordenador/novo-processo2.css">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/suggetion-list.js"></script>
    <title>Novo Processo Avaliativo</title>
</head>
<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="" class="logo">
        </div>
        
        <div class="header-centro">
            <div class="cards-header">
                <div class="card-header textCenter translate120 azul">
                    <p>1. Cursos e Currículos</p>
                </div>
                <div class="card-header textEnd translate90 cinza-escuro">
                    <p>2. Processos Avaliativos</p>
                </div>
                <div class="card-header textCenter translate60 cinza-claro">
                    <p>3. Formulários</p>
                </div>
                <div class="card-header textCenter translate30 branco">
                    <p>4. Relatórios</p>
                </div>
            </div>
        </div>

        <div class="header-direito">
            <button>Sair</button>
            <button>Perfil</button>
        </div>
    </header>


    <div class="container">

        <c:if test="${not empty param.erro}">
            <p style="color: red; font-weight: bold;">Erro: ${param.erro}</p>
        </c:if>

        <div class="content">
            <div class="titulo">
                <h1>Novo Processo Avaliativo</h1>
                <p>2/4</p>
            </div>

            <form action="${pageContext.request.contextPath}/FiltrarTurmasServlet" method="post" id="formCursos">

                <input type="hidden" name="nomesCursos" id="cursosSelecionados">
                
                <div class="card">
    
                    <div class="main-widget-container">
                        <label for="cursos">Cursos Participantes</label>
                        <div class="tag-input-wrapper">
                            <ul id="tags-container"></ul>
        
                            <input type="text" name="" id="tag-input" placeholder="Adicione tags">
                        </div>
        
                        <ul id="suggestions-list"></ul>
                    </div>
        
                    <div class="botoes">
        
                        <button type="button" class="cancelar">Cancelar</button>
                        <button type="button" class="prosseguir" onclick="prepararEnvio()">Prosseguir</button>
                    </div>
                </div>
            </form>
    
        </div>


    </div>


    <script>

        const suggestions = [
            <c:forEach var="curso" items="<%= listaCursos %>"> "${curso.nome}", </c:forEach>
        ];

        console.log(suggestions);

        function prepararEnvio(){
            
            const tagsLi = document.querySelectorAll('#tags-container li.tag');
            
            let listaNomes = [];

            tagsLi.forEach(li => {
                let texto = li.childNodes[0].textContent;
                listaNomes.push(texto);
            });

            if(listaNomes.length === 0){
                alert("Selecione pelo menos um curso");
                return;
            }

            document.getElementById("cursosSelecionados").value = listaNomes.join(';');

            document.getElementById("formCursos").submit();

        }

    </script>
</body>
</html>