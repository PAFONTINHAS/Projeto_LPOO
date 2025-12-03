package br.ufpr.sistemaavaliacao.controller;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.model.Usuario;
import java.io.IOException;
import java.sql.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SalvarRespostaServlet")
public class SalvarRespostaServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Validar Usuário
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Pegar IDs principais
        String idFormStr = request.getParameter("idFormulario");
        String idTurmaStr = request.getParameter("idTurma");
        // Nota: O idTurma não está sendo salvo na tabela 'avaliacoes' no seu esquema
        // atual (DL.png),
        // mas seria ideal ter para saber de qual turma é essa avaliação.
        // Vou seguir seu esquema atual salvando apenas Aluno e Formulario.

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false); // Importante para garantir integridade

            try {
                // 3. Criar registro na tabela 'avaliacoes'
                int idAvaliacao = criarAvaliacao(conn, usuario.getId(), Integer.parseInt(idFormStr));

                // 4. Iterar sobre todos os parâmetros enviados para achar as respostas
                Map<String, String[]> parametros = request.getParameterMap();

                for (String paramName : parametros.keySet()) {

                    // Verifica se é uma resposta (ex: resposta_q_12)
                    if (paramName.startsWith("resposta_q_")) {

                        // 1. Extrai o ID da questão
                        int idQuestao = Integer.parseInt(paramName.substring("resposta_q_".length()));
                        String[] valores = parametros.get(paramName);

                        // 2. Cria a resposta genérica
                        int idResposta = criarRespostaGenerica(conn, idAvaliacao, idQuestao);

                        // 3. RECUPERA O TIPO QUE MANDAMOS NO JSP (AQUI É O PULO DO GATO)
                        String tipoQuestao = request.getParameter("tipo_q_" + idQuestao);

                        // 4. Salva na tabela correta com certeza absoluta
                        if ("FECHADA".equals(tipoQuestao)) {
                            // Salva IDs das alternativas (Tabela de ligação)
                            for (String val : valores) {
                                // Garante que o valor é numérico antes de salvar
                                if (val != null && !val.isEmpty()) {
                                    salvarRespostaAlternativa(conn, idResposta, Integer.parseInt(val));
                                }
                            }
                        } else {
                            // Salva Texto (Tabela respostas_abertas)
                            // Pega apenas o primeiro valor (textarea retorna string única)
                            salvarRespostaAberta(conn, idResposta, valores[0]);
                        }
                    }
                }

                conn.commit(); // Deu tudo certo!
                response.sendRedirect("jsp/aluno/home.jsp?msg=Sucesso");

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                response.sendRedirect("jsp/aluno/home.jsp?msg=ErroAoSalvar");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Métodos Auxiliares de Banco de Dados ---

    private int criarAvaliacao(Connection conn, int idAluno, int idForm) throws SQLException {
        // Ajuste aqui se sua tabela 'alunos' usa 'usuario_id' como PK ou ID próprio.
        // Baseado no seu DL.png, avaliacoes usa aluno_usuario_id.
        String sql = "INSERT INTO avaliacoes (aluno_usuario_id, formulario_id, data_submissao) VALUES (?, ?, NOW())";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idForm);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next())
                    return rs.getInt(1);
            }
        }
        throw new SQLException("Erro ao criar avaliação");
    }

    private int criarRespostaGenerica(Connection conn, int idAvaliacao, int idQuestao) throws SQLException {
        String sql = "INSERT INTO respostas (avaliacao_id, questao_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idAvaliacao);
            stmt.setInt(2, idQuestao);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next())
                    return rs.getInt(1);
            }
        }
        throw new SQLException("Erro ao criar resposta genérica");
    }

    private void salvarRespostaAlternativa(Connection conn, int idResposta, int idAlternativa) throws SQLException {
        String sql = "INSERT INTO respostas_multipla_escolha_alternativas (resposta_id, alternativa_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idResposta);
            stmt.setInt(2, idAlternativa);
            stmt.executeUpdate();
        }
    }

    private void salvarRespostaAberta(Connection conn, int idResposta, String texto) throws SQLException {
        String sql = "INSERT INTO respostas_abertas (resposta_id, texto_resposta) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idResposta);
            stmt.setString(2, texto);
            stmt.executeUpdate();
        }
    }

    // Função utilitária simples para checar se é número
    private boolean isNumeric(String str) {
        if (str == null)
            return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}