package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.model.Alternativa; // Necessário para compilação
import br.ufpr.sistemaavaliacao.model.Formulario;
import br.ufpr.sistemaavaliacao.model.Questao;
import br.ufpr.sistemaavaliacao.model.QuestaoAberta;
import br.ufpr.sistemaavaliacao.model.QuestaoMultiplaEscolha;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FormularioDAO {

    private Connection conn;

    private Logger logger = Logger.getLogger(getClass().getName());

    public FormularioDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Formulario> listarTodos() throws SQLException {

        List<Formulario> formularios = new ArrayList<>();

        String sql = "SELECT * FROM formularios WHERE processo_avaliativo_id IS NOT NULL";

        try (PreparedStatement statement = conn.prepareStatement(sql); ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Formulario form = new Formulario(
                        result.getString("titulo"),
                        result.getBoolean("is_anonimo"));

                form.setId(result.getInt("id"));
                form.setInstrucoes(result.getString("instrucoes"));

                formularios.add(form);
            }
        }

        return formularios;
    }

    // No FormularioDAO.java
    public Formulario buscarPorIdCompleto(int id) throws SQLException {
        String sql = "SELECT * FROM formularios WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Formulario f = new Formulario(rs.getString("titulo"), rs.getBoolean("is_anonimo"));
                    f.setId(rs.getInt("id"));
                    f.setInstrucoes(rs.getString("instrucoes"));
                    // Reusa seu método existente que já busca questões e alternativas
                    f.setQuestoes(listarQuestoesPorFormulario(f.getId()));
                    return f;
                }
            }
        }
        return null;
    }

    public List<Formulario> listarTodosSemProcesso() throws SQLException {

        List<Formulario> formularios = new ArrayList<>();

        String sql = "SELECT * FROM formularios WHERE processo_avaliativo_id IS null";

        try (PreparedStatement statement = conn.prepareStatement(sql); ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Formulario form = new Formulario(
                        result.getString("titulo"),
                        result.getBoolean("is_anonimo"));

                form.setId(result.getInt("id"));
                form.setInstrucoes(result.getString("instrucoes"));

                formularios.add(form);
            }
        }

        return formularios;
    }

    public void vincularProcesso(int formId, int processoId) throws SQLException {

        String sql = "UPDATE formularios SET processo_avaliativo_id = ? WHERE id = ? ";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, processoId);
            statement.setInt(2, formId);
            statement.executeUpdate();
        }
    }

    public void salvar(Formulario formulario) throws SQLException {

        String sqlForm = "INSERT INTO formularios (titulo, is_anonimo, instrucoes, processo_avaliativo_id) VALUES (?,?,?,?)";

        PreparedStatement statement = null;

        try {

            conn.setAutoCommit(false);

            statement = conn.prepareStatement(sqlForm, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, formulario.getTitulo());
            statement.setBoolean(2, formulario.getIsAnonimo());
            statement.setString(3, formulario.getInstrucoes());

            if (formulario.getProcessoAvaliativoId() != null) {
                statement.setInt(4, formulario.getProcessoAvaliativoId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    formulario.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao criar formulário, nenhum ID obtido");
                }

                for (Questao questao : formulario.getQuestoes()) {
                    salvarQuestao(questao, formulario.getId());
                }

                conn.commit();
                logger.info("Formulário salvo com sucesso!");
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            throw new RuntimeException("Erro ao salvar formulário completo: " + e.getMessage(), e);
        } finally {

            try {
                conn.setAutoCommit(true);
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void salvarQuestao(Questao questao, int formularioId) throws SQLException {
        String sqlQuestao = "INSERT INTO questoes (enunciado, is_obrigatoria, tipo, formulario_id) VALUES (?,?,?,?)";

        String tipoString = (questao instanceof QuestaoAberta) ? "ABERTA" : "FECHADA";

        try (PreparedStatement statement = conn.prepareStatement(sqlQuestao, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, questao.getEnunciado());
            statement.setBoolean(2, questao.getIsObrigatoria());
            statement.setString(3, tipoString);
            statement.setInt(4, formularioId);

            statement.executeUpdate();

            int questaoId = 0;

            try (ResultSet rs = statement.getGeneratedKeys()) {

                if (rs.next()) {
                    questaoId = rs.getInt(1);
                }
            }

            if (questao instanceof QuestaoMultiplaEscolha) {
                salvarDetalhesMultiplaEscolha((QuestaoMultiplaEscolha) questao, questaoId);
            }
        }
    }

    private void salvarDetalhesMultiplaEscolha(QuestaoMultiplaEscolha questao, int questaoId) throws SQLException {

        String sqlQuestao = "INSERT INTO questoes_multipla_escolha (questao_id, permite_multipla_selecao) VALUES (?,?)";

        int idTabelaMultipla = questaoId;

        try (PreparedStatement statement = conn.prepareStatement(sqlQuestao, Statement.NO_GENERATED_KEYS)) {
            statement.setInt(1, questaoId);
            statement.setBoolean(2, questao.getPermiteMultiplaSelecao()); // Verifique seu Getter
            statement.executeUpdate();
        }

        String sqlAlternativas = "INSERT INTO alternativas (texto, peso, questao_multipla_escolha_id) VALUES (?,?,?)";

        try (PreparedStatement statementAlternativas = conn.prepareStatement(sqlAlternativas)) {

            for (Alternativa alternativa : questao.getAlternativas()) {
                statementAlternativas.setString(1, alternativa.getTexto());
                statementAlternativas.setInt(2, alternativa.getPeso());
                statementAlternativas.setInt(3, idTabelaMultipla); // Usa o ID correto aqui
                statementAlternativas.executeUpdate();
            }

        }

    }

    // Em FormularioDAO.java

    public List<Formulario> listarPorProcesso(int processoId) throws SQLException {
        List<Formulario> formularios = new ArrayList<>();
        // Busca só os formulários deste processo
        String sql = "SELECT * FROM formularios WHERE processo_avaliativo_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, processoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Formulario f = new Formulario(rs.getString("titulo"), rs.getBoolean("is_anonimo"));
                    f.setId(rs.getInt("id"));
                    f.setInstrucoes(rs.getString("instrucoes"));

                    // AQUI ESTÁ A MÁGICA: Já busca as questões deste formulário
                    f.setQuestoes(listarQuestoesPorFormulario(f.getId()));
                    f.setQuantidadeRespostas(contarRespostasDoFormulario(f.getId()));

                    formularios.add(f);
                }
            }
        }
        return formularios;
    }

    // Crie este método auxiliar na mesma classe DAO
    private int contarRespostasDoFormulario(int formId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM avaliacoes WHERE formulario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, formId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // Método auxiliar para buscar questões e suas alternativas
    private List<Questao> listarQuestoesPorFormulario(int formularioId) throws SQLException {
        List<Questao> questoes = new ArrayList<>();
        String sql = "SELECT * FROM questoes WHERE formulario_id = ? ORDER BY id ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, formularioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo");
                    String enunciado = rs.getString("enunciado");
                    boolean obrigatoria = rs.getBoolean("is_obrigatoria");
                    int questaoId = rs.getInt("id");

                    Questao q;
                    if ("ABERTA".equals(tipo)) {
                        q = new QuestaoAberta(enunciado, obrigatoria);
                    } else {
                        // Se for Fechada, precisa buscar se é múltipla seleção e as alternativas
                        boolean multi = buscarSePermiteMultipla(questaoId);
                        QuestaoMultiplaEscolha qme = new QuestaoMultiplaEscolha(enunciado, obrigatoria, multi);
                        qme.setAlternativas(listarAlternativas(questaoId));
                        q = qme;
                    }
                    q.setId(questaoId); // Se sua classe Questao tiver ID, setar aqui
                    questoes.add(q);
                }
            }
        }
        return questoes;
    }

    // Métodos auxiliares rápidos (pode implementar direto no listarQuestoes se
    // preferir, mas assim fica limpo)
    private boolean buscarSePermiteMultipla(int questaoId) throws SQLException {
        String sql = "SELECT permite_multipla_selecao FROM questoes_multipla_escolha WHERE questao_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return rs.getBoolean(1);
            }
        }
        return false;
    }

    private List<Alternativa> listarAlternativas(int questaoId) throws SQLException {
        // Atenção: aqui assumi que o ID na tabela alternativas é linkado na tabela
        // intermediária.
        // Se o seu banco liga alternativa direto na questão, ajuste o SQL.
        // Baseado no seu código anterior, parecia ser linkado em
        // `questao_multipla_escolha_id`.
        // Vou simplificar assumindo que você consegue recuperar pelo ID da questão via
        // JOIN se necessário.

        List<Alternativa> alts = new ArrayList<>();
        String sql = "SELECT a.* FROM alternativas a " +
                "JOIN questoes_multipla_escolha qme ON a.questao_multipla_escolha_id = qme.questao_id " +
                "WHERE qme.questao_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // 1. Cria o objeto com texto e peso
                    Alternativa alt = new Alternativa(rs.getString("texto"), rs.getInt("peso"));

                    // 2. IMPORTANTE: Recupera e Seta o ID que vem do banco!
                    alt.setId(rs.getInt("id"));

                    // 3. Adiciona na lista
                    alts.add(alt);
                }
            }
        }
        return alts;
    }
}