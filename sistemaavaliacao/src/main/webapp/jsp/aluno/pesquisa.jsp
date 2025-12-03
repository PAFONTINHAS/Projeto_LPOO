<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.ufpr.sistemaavaliacao.model.Usuario" %>

<% 
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null || !"aluno".equalsIgnoreCase(usuario.getPerfil())) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp?msg=Acesso negado.");
        return;
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno/pesquisa.css">
    <title>${formulario.titulo}</title>
</head>
<body>
    <header>
        <img src="${pageContext.request.contextPath}/images/logo_ufpr.png" alt="Logo UFPR" class="logo">
    </header>
    
    <div class="container">
        
        <div class="titulo">
            <h1>${formulario.titulo}</h1>
            <c:if test="${formulario.isAnonimo}">
                <span class="badge-anonimo">Formulário Anônimo</span>
            </c:if>
            <p>${formulario.instrucoes}</p>
        </div>
        
        <form action="${pageContext.request.contextPath}/SalvarRespostaServlet" method="post">
            
            <input type="hidden" name="idFormulario" value="${formulario.id}">
            <input type="hidden" name="idTurma" value="${idTurma}">

            <div class="box-avaliacao">
                
                <c:forEach var="questao" items="${formulario.questoes}" varStatus="status">
                    
                    <div class="categoria">
                        <input type="hidden" name="tipo_q_${questao.id}" value="${questao.multiplaEscolha ? 'FECHADA' : 'ABERTA'}">
                    
                        <h3>${status.count}. ${questao.enunciado} 
                            <c:if test="${questao.isObrigatoria}"> <span style="color:red">*</span> </c:if>
                        </h3>

                        <c:if test="${questao.multiplaEscolha}">
                            <div class="opcoes">
                                <c:forEach var="alt" items="${questao.alternativas}">
                                    <label class="opcao">
                                        <input type="${questao.permiteMultiplaSelecao ? 'checkbox' : 'radio'}" 
                                               name="resposta_q_${questao.id}" 
                                               value="${alt.id}"
                                               <c:if test="${questao.isObrigatoria && !questao.permiteMultiplaSelecao}">required</c:if> >
                                        
                                        <span>${alt.texto}</span>
                                    </label>
                                </c:forEach>
                            </div>
                        </c:if>

                        <c:if test="${!questao.multiplaEscolha}">
                            <textarea name="resposta_q_${questao.id}" rows="4" style="width:100%"
                                      placeholder="Digite sua resposta aqui..."
                                      <c:if test="${questao.isObrigatoria}">required</c:if>>
                            </textarea>
                        </c:if>

                    </div>
                    <hr>
                </c:forEach>

            </div>

            <button type="submit" class="btn-enviar">Enviar Avaliação</button>
        </form>
    </div>

    <%-- <script>

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
    </script> --%>
</body>
</html>