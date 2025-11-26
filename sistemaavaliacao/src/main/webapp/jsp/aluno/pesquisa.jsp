<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno/pesquisa.css">
    <title>Pesquisa</title>
</head>
<body>
    <header>
        <img src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="Logo UFPR" class="logo">
    </header>
    
    <div class="container">
        <span class="badge-anonimo">Formulário Anônimo</span>
        
        <div class="titulo">
            <h1>Avaliação de Ferramentas IA</h1>
            <h2>Professor X</h2>
        </div>
        
        <form id="formAvaliacao">
            <div class="box-avaliacao">
                <p class="instrucao"> Classifique de 1 a 5 os seguintes temas: <span class="obrigatorio">*</span></p>
                
                <div class="categorias">
                    
                    <div class="categoria">
                        <h3>Didática</h3>
                        <div class="opcoes">
                            <label class="opcao">
                                <div class="cor-5"></div>
                                <input type="radio" name="didatica" value="5" required>
                                <span>5 - Muito bom</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-4"></div>
                                <input type="radio" name="didatica" value="4">
                                <span>4 - Bom</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-3"></div>
                                <input type="radio" name="didatica" value="3">
                                <span>3 - Regular</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-2"></div>
                                <input type="radio" name="didatica" value="2">
                                <span>2 - Ruim</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-1"></div>
                                <input type="radio" name="didatica" value="1">
                                <span>1 - Muito ruim</span>
                            </label>
                        </div>
                    </div>

                  
                    <div class="categoria">
                        <h3>Domínio do Conteúdo</h3>
                        <div class="opcoes">
                            <label class="opcao">
                                <div class="cor-5"></div>
                                <input type="radio" name="dominio" value="5" required>
                                <span>5 - Muito bom</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-4"></div>
                                <input type="radio" name="dominio" value="4">
                                <span>4 - Bom</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-3"></div>
                                <input type="radio" name="dominio" value="3">
                                <span>3 - Regular</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-2"></div>
                                <input type="radio" name="dominio" value="2">
                                <span>2 - Ruim</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-1"></div>
                                <input type="radio" name="dominio" value="1">
                                <span>1 - Muito ruim</span>
                            </label>
                        </div>
                    </div>

              
                    <div class="categoria">
                        <h3>Responsabilidade</h3>
                        <div class="opcoes">
                            <label class="opcao">
                                <div class="cor-5"></div>
                                <input type="radio" name="responsabilidade" value="5" required>
                                <span>5 - Muito bom</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-4"></div>
                                <input type="radio" name="responsabilidade" value="4">
                                <span>4 - Bom</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-3"></div>
                                <input type="radio" name="responsabilidade" value="3">
                                <span>3 - Regular</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-2"></div>
                                <input type="radio" name="responsabilidade" value="2">
                                <span>2 - Ruim</span>
                            </label>
                            <label class="opcao">
                                <div class="cor-1"></div>
                                <input type="radio" name="responsabilidade" value="1">
                                <span>1 - Muito ruim</span>
                            </label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="box-feedback">
                <label for="feedback">O que você melhoraria na matéria?</label>
                <textarea id="feedback" name="feedback" placeholder="Escreva aqui sua resposta"></textarea>
            </div>

            <button type="submit" class="btn-enviar">Enviar</button>
        </form>
    </div>

    <script>

        document.getElementById('formAvaliacao').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const didatica = document.querySelector('input[name="didatica"]:checked');
            const dominio = document.querySelector('input[name="dominio"]:checked');
            const responsabilidade = document.querySelector('input[name="responsabilidade"]:checked');
            
            if (!didatica || !dominio || !responsabilidade) {
                alert('Por favor, avalie tudo antes de enviar!');
                return;
            }
            
            const feedback = document.getElementById('feedback').value;
            
            console.log({
                didatica: didatica.value,
                dominio: dominio.value,
                responsabilidade: responsabilidade.value,
                feedback: feedback
            });
            
            alert('Enviada com sucesso. Agradecemos pelo seu feedback! :) ');
            this.reset();
        });
    </script>
</body>
</html>