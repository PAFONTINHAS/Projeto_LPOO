<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>

<% 
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null || !"professor".equalsIgnoreCase(usuario.getPerfil())) {
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/professor/alunos.css">
    <link href="https://fonts.googleapis.com/css2?family=Gabarito:wght@400..900&family=Manrope:wght@200..800&display=swap" rel="stylesheet">

    <title>Gerenciar Alunos</title>
</head>
<body>

    <header>
        <div class="header-esquerdo">
            <img class="imagem" src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="" class="logo">
        </div>
        
        <div class="header-centro">
            <div class="cards-header">
                <div class="card-header textCenter translate120 azul">
                    <a href="./cursos.html"><p>1. Gerenciar Alunos</p></a>
                </div>
                <div class="card-header textEnd translate90 cinza-escuro">
                    <a href="./curriculos.html"><p>2. Relatórios e Dados</p></a>
                </div>
            </div>
        </div>

        <div class="header-direito">
            <button>Sair</button>
            <button>Perfil</button>
        </div>
    </header>


    <div class="container">
        <div class="card-cadastro"> 
            <form action="" method="post">

                <div class="linha">
                    <div class="input-group">
                        <label for="nome">Nome do Aluno:</label>
                        <input class="grow" type="text" name="nome" id="" required>
                    </div>
        
                    <div class="input-group">
                        <label for="grr">GRR do Aluno:</label>
                        <input class="grow" type="text" name="grr" id="" required>
                    </div>
                </div>
    
                <div class="linha">
                    <div class="input-group">
                        <label for="codigo">Código da Disciplina:</label>
                        <input type="text" name="codigo" id="" required>
                    </div>
    
                    <div class="select-group">
                        <label for="turno">Turno:</label>
                        <select name="" id="" required>
                            <option value="" selected disabled>Selecione</option>
                            <option value="">Matutino</option>
                            <option value="">Vespertino</option>
                            <option value="">Noturno</option>
                            <option value="">Integral</option>
                        </select>
                    </div>
    
                    <div class="select-group">
                        <label for="turno">Curso:</label>
                        <select class="grow" name="" id="" required>
                            <option value="" selected disabled>Selecione</option>
                            <option value="">Ciência da Computação</option>
                            <option value="">Análise de Desenvolvimento de Sistemas</option>
                            <option value="">Engenharia de Software</option>
                            <option value="">Informática Biomédica</option>
                        </select>
                    </div>
                </div>

                <div class="botao">
                    <button type="submit">Associar</button>
                </div>
            </form>
        </div>


        <div class="tabela">

            <div class="titulo-tabela">
                
                <h1>Disciplina - Turno</h1>
    
                <h3>Aluno Matriculado:</h3>
            </div>

            <div class="alunos">
                <div class="aluno">
                    <div class="infoAluno">

                        <p>Aluno 1</p>
                        <p>GRR 1</p>

                    </div>

                    <div class="acaoAluno">

                        <button class="editar">Editar</button>
                        <button class="deletar">Deletar</button>
                    </div>
                </div>

                <div class="aluno">
                    <div class="infoAluno">
                        <p>Aluno 2</p>
                        <p>GRR 2</p>
                    </div>

                    <div class="acaoAluno">

                        <button class="editar">Editar</button>
                        <button class="deletar">Deletar</button>
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
</html>