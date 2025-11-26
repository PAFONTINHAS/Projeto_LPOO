package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import java.sql.*;

public class TurmaAlunoDAO {

    public boolean alunoJaNaTurma(int idAluno, int idTurma) throws Exception {
        String sql = "SELECT COUNT(*) FROM turma_aluno WHERE id_aluno=? AND id_turma=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAluno);
            ps.setInt(2, idTurma);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public void vincular(int idAluno, int idTurma) throws Exception {
        String sql = "INSERT INTO turma_aluno (id_aluno, id_turma) VALUES (?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAluno);
            ps.setInt(2, idTurma);
            ps.executeUpdate();
        }
    }
}
