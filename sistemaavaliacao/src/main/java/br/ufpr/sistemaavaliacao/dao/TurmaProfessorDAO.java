package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import java.sql.*;

public class TurmaProfessorDAO {

    public boolean professorJaNaTurma(int idProfessor, int idTurma) throws Exception {
        String sql = "SELECT COUNT(*) FROM turma_professor WHERE id_professor=? AND id_turma=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProfessor);
            ps.setInt(2, idTurma);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public void vincular(int idProfessor, int idTurma) throws Exception {
        String sql = "INSERT INTO turma_professor (id_professor, id_turma) VALUES (?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProfessor);
            ps.setInt(2, idTurma);
            ps.executeUpdate();
        }
    }
}
