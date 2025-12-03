package br.ufpr.sistemaavaliacao.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.ufpr.sistemaavaliacao.model.ProcessoAvaliativo;

public class ProcessoAvaliativoDAO {

   private Connection connection;

   public ProcessoAvaliativoDAO(Connection connection) {
      this.connection = connection;
   }

   // Dentro de ProcessoAvaliativoDAO.java

   // 1. Listar TODOS (Para a tela principal)
   public List<ProcessoAvaliativo> listarTodos() throws SQLException {
      List<ProcessoAvaliativo> processos = new ArrayList<>();
      String sql = "SELECT * FROM processos_avaliativos ORDER BY data_inicio DESC";

      try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

         while (rs.next()) {
            ProcessoAvaliativo p = new ProcessoAvaliativo();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setDataInicio(rs.getDate("data_inicio"));
            p.setDataFim(rs.getDate("data_fim"));
            // p.setObservacoes(rs.getString("observacoes")); // Se tiver essa coluna

            processos.add(p);
         }
      }
      return processos;
   }

   // 2. Buscar UM pelo ID (Para a tela de detalhes)
   public ProcessoAvaliativo buscarPorId(int id) throws SQLException {
      String sql = "SELECT * FROM processos_avaliativos WHERE id = ?";

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
         stmt.setInt(1, id);

         try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
               ProcessoAvaliativo p = new ProcessoAvaliativo();
               p.setId(rs.getInt("id"));
               p.setNome(rs.getString("nome"));
               p.setDataInicio(rs.getDate("data_inicio"));
               p.setDataFim(rs.getDate("data_fim"));
               return p;
            }
         }
      }
      return null; // Não achou
   }

   public void salvar(ProcessoAvaliativo processo) {

      String sql = "INSERT INTO processos_avaliativos (nome, data_inicio, data_fim, observacoes) VALUES (?,?,?,?)";

      try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

         statement.setString(1, processo.getNome());
         statement.setDate(2, processo.getDataInicio());
         statement.setDate(3, processo.getDataFim());
         statement.setString(4, processo.getObservacoes());

         statement.executeUpdate();

         try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
               processo.setId(generatedKeys.getInt(1));
            }
         }
      } catch (SQLException e) {
         throw new RuntimeException("Erro ao salver processo avaliativo", e);
      }
   }

   public void vincularTurma(int processoId, int turmaId) throws SQLException {
      String sql = "INSERT INTO processos_turmas (processo_id, turma_id) VALUES (?,?)";

      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setInt(1, processoId);
         statement.setInt(2, turmaId);
         statement.executeUpdate();
      }
   }

}
