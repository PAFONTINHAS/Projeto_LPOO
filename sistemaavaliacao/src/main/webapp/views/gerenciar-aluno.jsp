<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Alunos - AvaliaUFPR</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #0047AB 0%, #003380 100%);
            min-height: 100vh;
        }

        .header {
            background-color: #f0f0f0;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .logo {
            height: 60px;
        }

        .user-icon {
            width: 40px;
            height: 40px;
            background-color: #333;
            border-radius: 50%;
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .card {
            background: white;
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.2);
        }

        h2 {
            color: #0047AB;
            margin-bottom: 20px;
            font-size: 24px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }

        .required {
            color: red;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s;
        }

        input:focus {
            outline: none;
            border-color: #0047AB;
        }

        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            transition: all 0.3s;
        }

        .btn-primary {
            background-color: #0047AB;
            color: white;
        }

        .btn-primary:hover {
            background-color: #003380;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,71,171,0.3);
        }

        .btn-secondary {
            background-color: #6c757d;
            color: white;
            margin-left: 10px;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
        }

        .btn-edit {
            background-color: #ffc107;
            color: #333;
            padding: 8px 15px;
            font-size: 12px;
        }

        .btn-edit:hover {
            background-color: #e0a800;
        }

        .btn-delete {
            background-color: #dc3545;
            color: white;
            padding: 8px 15px;
            font-size: 12px;
            margin-left: 5px;
        }

        .btn-delete:hover {
            background-color: #c82333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        thead {
            background-color: #0047AB;
            color: white;
        }

        th, td {
            padding: 15px;
            text-align: left;
        }

        tbody tr {
            border-bottom: 1px solid #ddd;
        }

        tbody tr:hover {
            background-color: #f5f5f5;
        }

        .alert {
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .no-data {
            text-align: center;
            padding: 40px;
            color: #666;
            font-style: italic;
        }

        @media (max-width: 768px) {
            .container {
                padding: 0 10px;
            }

            table {
                font-size: 12px;
            }

            th, td {
                padding: 10px 5px;
            }

            .btn {
                padding: 10px 20px;
                font-size: 12px;
            }
        }
    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <img src="https://via.placeholder.com/150x60/0047AB/FFFFFF?text=UFPR" alt="UFPR Logo" class="logo">
        <div class="user-icon"></div>
    </div>

    <!-- Container Principal -->
    <div class="container">
        
        <!-- Mensagens de Sucesso/Erro -->
        <c:if test="${param.msg == 'criado'}">
            <div class="alert alert-success">✓ Aluno cadastrado com sucesso!</div>
        </c:if>
        <c:if test="${param.msg == 'atualizado'}">
            <div class="alert alert-success">✓ Aluno atualizado com sucesso!</div>
        </c:if>
        <c:if test="${param.msg == 'excluido'}">
            <div class="alert alert-success">✓ Aluno excluído com sucesso!</div>
        </c:if>
        <c:if test="${param.erro != null}">
            <div class="alert alert-error">✗ Erro ao processar a operação. Tente novamente.</div>
        </c:if>

        <!-- Formulário de Cadastro/Edição -->
        <div class="card">
            <h2>
                <c:choose>
                    <c:when test="${alunoEdicao != null}">Editar Aluno</c:when>
                    <c:otherwise>Cadastrar Novo Aluno</c:otherwise>
                </c:choose>
            </h2>

            <form action="aluno" method="post">
                <input type="hidden" name="acao" value="${alunoEdicao != null ? 'atualizar' : 'criar'}">
                <c:if test="${alunoEdicao != null}">
                    <input type="hidden" name="id" value="${alunoEdicao.usuarioId}">
                </c:if>

                <div class="form-group">
                    <label>Nome do Aluno <span class="required">*</span></label>
                    <input type="text" name="nome" placeholder="Digite o nome completo" 
                           value="${alunoEdicao != null ? alunoEdicao.usuario.nome : ''}" required>
                </div>

                <div class="form-group">
                    <label>Matrícula (GRR) <span class="required">*</span></label>
                    <input type="text" name="matricula" placeholder="Ex: GRR20230001" 
                           value="${alunoEdicao != null ? alunoEdicao.matricula : ''}" required>
                </div>

                <div class="form-group">
                    <label>Email <span class="required">*</span></label>
                    <input type="email" name="email" placeholder="aluno@ufpr.br" 
                           value="${alunoEdicao != null ? alunoEdicao.usuario.email : ''}" required>
                </div>

                <div class="form-group">
                    <label>Login <span class="required">*</span></label>
                    <input type="text" name="login" placeholder="Nome de usuário" 
                           value="${alunoEdicao != null ? alunoEdicao.usuario.login : ''}" required>
                </div>

                <c:if test="${alunoEdicao == null}">
                    <div class="form-group">
                        <label>Senha <span class="required">*</span></label>
                        <input type="password" name="senha" placeholder="Digite uma senha segura" required>
                    </div>
                </c:if>

                <button type="submit" class="btn btn-primary">
                    ${alunoEdicao != null ? 'Atualizar Aluno' : 'Cadastrar Aluno'}
                </button>

                <c:if test="${alunoEdicao != null}">
                    <a href="aluno?acao=listar" class="btn btn-secondary" style="text-decoration: none;">Cancelar</a>
                </c:if>
            </form>
        </div>

        <!-- Lista de Alunos -->
        <div class="card">
            <h2>Lista de Alunos Cadastrados</h2>

            <c:choose>
                <c:when test="${empty alunos}">
                    <div class="no-data">
                        Nenhum aluno cadastrado ainda. Use o formulário acima para cadastrar o primeiro aluno.
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Matrícula</th>
                                <th>Email</th>
                                <th>Login</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="aluno" items="${alunos}">
                                <tr>
                                    <td>${aluno.usuario.nome}</td>
                                    <td>${aluno.matricula}</td>
                                    <td>${aluno.usuario.email}</td>
                                    <td>${aluno.usuario.login}</td>
                                    <td>
                                        <a href="aluno?acao=editar&id=${aluno.usuarioId}" class="btn btn-edit">Editar</a>
                                        <a href="aluno?acao=excluir&id=${aluno.usuarioId}" 
                                           class="btn btn-delete"
                                           onclick="return confirm('Tem certeza que deseja excluir este aluno?')">
                                            Excluir
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>