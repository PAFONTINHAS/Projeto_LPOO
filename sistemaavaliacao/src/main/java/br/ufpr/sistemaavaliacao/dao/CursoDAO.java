package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private Connection connection;

    public CursoDAO(Connection connection){
        this.connection = connection;
    }

    public List<Curso> listarTodos() throws SQLException{

        List<Curso> cursos = new ArrayList<>();

        String sql = "SELECT * FROM cursos";

        try(PreparedStatement statement = connection.prepareStatement(sql); ResultSet result = statement.executeQuery()){

            while(result.next()){
                Curso curso = new Curso(
                    result.getInt("id"),
                    result.getString("nome")
                );

                cursos.add(curso);
            }

            return cursos;
            
        } catch(SQLException e){
            e.printStackTrace();

            throw new SQLException("Erro ao pegar os dados dos cursos: " + e);
        }



    } 
    
}
