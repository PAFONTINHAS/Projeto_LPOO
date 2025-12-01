package br.ufpr.sistemaavaliacao.dao;

import java.sql.*;

import br.ufpr.sistemaavaliacao.model.ProcessoAvaliativo;

public class ProcessoAvaliativoDAO {

   private Connection connection;

   public ProcessoAvaliativoDAO(Connection connection){
     this.connection = connection;
   }

   public void salvar(ProcessoAvaliativo processo){

      String sql = "INSERT INTO processos_avaliativos (nome, data_inicio, data_fim) VALUES (?,?,?)";

      try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

         statement.setString(1, processo.getNome());
         statement.setDate(2, processo.getDataInicio());
         statement.setDate(3, processo.getDataFim());

         statement.executeUpdate();

         try(ResultSet generatedKeys = statement.getGeneratedKeys()){
            if(generatedKeys.next()){
               processo.setId(generatedKeys.getInt(1));
            }
         }
      } catch(SQLException e){
         throw new RuntimeException("Erro ao salver processo avaliativo", e);
      }
   }


    
}
