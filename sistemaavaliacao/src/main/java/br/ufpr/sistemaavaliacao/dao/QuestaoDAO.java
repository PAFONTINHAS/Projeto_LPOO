package br.ufpr.sistemaavaliacao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufpr.sistemaavaliacao.model.Formulario;
import br.ufpr.sistemaavaliacao.model.Questao;

public class QuestaoDAO {

    private Connection conn;

    public QuestaoDAO(Connection conn){
        this.conn = conn;
    }

    public List<Formulario> listarTodos() throws SQLException {

        List<Formulario> formularios = new ArrayList<>();

        String sql = "SELECT * FROM formularios WHERE processo_avaliativo_id IS NOT NULL";

        try (PreparedStatement statement = conn.prepareStatement(sql); ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Formulario form = new Formulario(
                    result.getString("titulo"),
                    result.getBoolean("is_anonimo")
                );

                form.setId(result.getInt("id"));
                form.setInstrucoes(result.getString("instrucoes"));
            

                formularios.add(form);
            }
        }

        return formularios;
    }

    // public List<Questao> listarTodosPorFormulario(int formId) throws SQLException{

    //     List<Questao> questoes = new ArrayList<>();

    //     String sql = "SELECT * FROM questoes WHERE formulario_id = ?";

    //     try(PreparedStatement statement = conn.prepareStatement(sql); ResultSet result = statement.executeQuery()){

    //         while (result.next()){

                
                
    //         }

    //     }


    // }
    
}
