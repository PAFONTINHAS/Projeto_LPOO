package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.model.*;
import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import java.sql.*;
import java.util.*;

public class TurmaDAO {

    public void inserir(Turma turma) throws Exception {
        String sql = "INSERT INTO turma (nome, ano, semestre, id_uc) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, turma.getNome());
            ps.setInt(2, turma.getAno());
            ps.setInt(3, turma.getSemestre());
            ps.setInt(4, turma.getUnidadeCurricular().getId());

            ps.executeUpdate();
        }
    }

    public List<Turma> listar() throws Exception {
        List<Turma> lista = new ArrayList<>();

        String sql = """
            SELECT t.id, t.nome, t.ano, t.semestre, uc.id AS uc_id, uc.nome AS uc_nome
            FROM turma t
            JOIN unidade_curricular uc ON uc.id = t.id_uc
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UnidadeCurricular uc = new UnidadeCurricular(
                    rs.getInt("uc_id"),
                    rs.getString("uc_nome"),
                    ""
                );

                Turma turma = new Turma(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("ano"),
                    rs.getInt("semestre"),
                    uc
                );

                lista.add(turma);
            }
        }
        return lista;
    }
}
