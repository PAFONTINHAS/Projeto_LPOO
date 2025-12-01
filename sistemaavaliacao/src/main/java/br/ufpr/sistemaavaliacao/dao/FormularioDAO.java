package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.model.Alternativa; // Necessário para compilação
import br.ufpr.sistemaavaliacao.model.Formulario;
import br.ufpr.sistemaavaliacao.model.Questao;

import java.sql.*;

public class FormularioDAO {
    // ... (restante da implementação com a lógica transacional)
    /**
     * Salva o formulário e todas as suas questões e alternativas em transação.
     * @param formulario O objeto Formulario a ser salvo.
     * @throws SQLException Se ocorrer um erro no banco de dados.
     */
    public void salvar(Formulario formulario) throws SQLException {
        // ... (SQLs e variáveis)
        Connection conn = null;
        // ... (demais PreparedStatements e ResultSet)
        
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // <== Importante: Inicia transação

            // 1. Inserir Formulário e obter ID
            // ... (Lógica de inserção do Formulário)
            
            int formularioId = 1; // ID simulado
            
            // 2. Inserir Questões (loop)
            for (Questao questao : formulario.getQuestoes()) {
                // ... (Lógica de inserção da Questão e obter ID)
                int questaoId = 1; // ID simulado

                // 3. Inserir detalhes específicos (Multipla Escolha)
                if ("MultiplaEscolha".equalsIgnoreCase(questao.getTipo())) {
                    // a) Inserir em questoes_multipla_escolha (RF08)
                    // ... (Lógica de inserção em questoes_multipla_escolha)

                    // // b) Inserir alternativas (loop)
                    // for (Alternativa alternativa : questao.getAlternativas()) {
                    //     // ... (Lógica de inserção em alternativas - usa Alternativa.java)
                    // }
                }
            }

            conn.commit(); // Confirma transação
        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Desfaz transação em caso de erro
            throw new SQLException("Erro ao salvar formulário e questões.", e);
        } finally {
            ConnectionFactory.closeConnection(conn);
            // Fechar todos os PreparedStatements e ResultSets
        }
    }
}