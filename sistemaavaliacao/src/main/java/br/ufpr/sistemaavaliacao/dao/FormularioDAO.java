package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.model.Alternativa; // Necessário para compilação
import br.ufpr.sistemaavaliacao.model.Formulario;
import br.ufpr.sistemaavaliacao.model.Questao;
import br.ufpr.sistemaavaliacao.model.QuestaoAberta;
import br.ufpr.sistemaavaliacao.model.QuestaoMultiplaEscolha;

import java.sql.*;
import java.util.logging.Logger;

public class FormularioDAO {

    private Connection conn;

    private Logger logger = Logger.getLogger(getClass().getName());

    public FormularioDAO(Connection conn){
        this.conn = conn;
    }

    public void salvar(Formulario formulario) throws SQLException {

        String sqlForm = "INSERT INTO formularios (titulo, is_anonimo, instrucoes, processo_avaliativo_id) VALUES (?,?,?,?)"; 

        PreparedStatement statement = null;

        try{

            conn.setAutoCommit(false);

            statement = conn.prepareStatement(sqlForm, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, formulario.getTitulo());
            statement.setBoolean(2, formulario.getIsAnonimo());
            statement.setString(3, formulario.getInstrucoes());

            if(formulario.getProcessoAvaliativoId() != null){
                statement.setInt(4, formulario.getProcessoAvaliativoId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            statement.executeUpdate();

            try(ResultSet generatedKeys = statement.getGeneratedKeys()){

                if(generatedKeys.next()){
                    formulario.setId(generatedKeys.getInt(1));
                } else{
                    throw new SQLException("Falha ao criar formulário, nenhum ID obtido");
                }

                for(Questao questao: formulario.getQuestoes()){
                    salvarQuestao(questao, formulario.getId());
                }

                conn.commit();
                logger.info("Formulário salvo com sucesso!");
            }

        } catch(SQLException e){
            try{
                conn.rollback();
            } catch( SQLException ex){
                ex.printStackTrace();
            }

            throw new RuntimeException("Erro ao salvar formulário completo: " + e.getMessage(), e);
        } finally{

            try{
                conn.setAutoCommit(true);
                if(statement != null) statement.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


    private void salvarQuestao(Questao questao, int formularioId) throws SQLException{
        String sqlQuestao = "INSERT INTO questoes (enunciado, is_obrigatoria, tipo, formulario_id) VALUES (?,?,?,?)";

        String tipoString = (questao instanceof QuestaoAberta) ? "ABERTA" : "FECHADA";

        try (PreparedStatement statement = conn.prepareStatement(sqlQuestao, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, questao.getEnunciado());
            statement.setBoolean(2, questao.getIsObrigatoria()); 
            statement.setString(3, tipoString);
            statement.setInt(4, formularioId);

            statement.executeUpdate();

            int questaoId = 0;

            try(ResultSet rs = statement.getGeneratedKeys()){

                if(rs.next()){
                    questaoId = rs.getInt(1);
                }
            }

            if(questao instanceof QuestaoMultiplaEscolha){
                salvarDetalhesMultiplaEscolha((QuestaoMultiplaEscolha) questao, questaoId);
            }
        }
    }

    private void salvarDetalhesMultiplaEscolha(QuestaoMultiplaEscolha questao, int questaoId) throws SQLException{

        String sqlQuestao = "INSERT INTO questoes_multipla_escolha (questao_id, permite_multipla_selecao) VALUES (?,?)";

        int idTabelaMultipla = questaoId;

        try( PreparedStatement statement = conn.prepareStatement(sqlQuestao, Statement.NO_GENERATED_KEYS)){
            statement.setInt(1, questaoId);
            statement.setBoolean(2, questao.getPermiteMultiplaSelecao()); // Verifique seu Getter
            statement.executeUpdate();
        }

        String sqlAlternativas = "INSERT INTO alternativas (texto, peso, questao_multipla_escolha_id) VALUES (?,?,?)";

        try (PreparedStatement statementAlternativas = conn.prepareStatement(sqlAlternativas)){

            for(Alternativa alternativa: questao.getAlternativas()){
                statementAlternativas.setString(1, alternativa.getTexto());
                statementAlternativas.setInt(2, alternativa.getPeso());
                statementAlternativas.setInt(3, idTabelaMultipla); // Usa o ID correto aqui
                statementAlternativas.executeUpdate(); 
            }

        }

    }
}