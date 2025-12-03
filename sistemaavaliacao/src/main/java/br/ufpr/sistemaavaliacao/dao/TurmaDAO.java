package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.model.*;
import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import java.sql.*;
import java.util.*;

public class TurmaDAO {
    
    public void inserir(Turma turma) throws Exception {
        String sql = "INSERT INTO turmas (unidade_curricular_id, ano_semestre) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(2, turma.getAnoSemestre());
            ps.setInt(3, turma.getUnidadeCurricular().getId());
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
                UnidadeCurricular uc = new UnidadeCurricular();

                uc.setId(rs.getInt("uc_id"));
                uc.setNome(rs.getString("uc_nome"));

                Turma turma = new Turma(
                    rs.getInt("id"),
                    rs.getString("anoSemestre"),
                    uc
                );

                lista.add(turma);
            }
        }
        return lista;
    }
    

    public List<Turma> listarPorNomesDeCursos(String[] nomesCursos) throws SQLException{

        List<Turma> turmas = new ArrayList<>();

        if(nomesCursos == null || nomesCursos.length == 0) return turmas;

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT t.id, t.ano_semestre, t.unidade_curricular_id, uc.nome as disciplina_nome, c.id as curso_id, c.nome as curso_nome ");
        sql.append("FROM turmas t ");
        sql.append("JOIN unidades_curriculares uc ON t.unidade_curricular_id = uc.id ");
        sql.append("JOIN cursos c ON uc.curso_id = c.id ");
        sql.append("WHERE c.nome IN (");

        for(int i = 0; i< nomesCursos.length; i++){
            sql.append(i == 0 ? "?" : ", ?");
        }

        sql.append(")");

        try(
            Connection connection = ConnectionFactory.getConnection(); 
            PreparedStatement statement = connection.prepareStatement(sql.toString())
        ){

            for(int i = 0; i< nomesCursos.length; i++){
                statement.setString(i + 1, nomesCursos[i].trim());
            }

            try(ResultSet result = statement.executeQuery()){

                while (result.next()) {

                    UnidadeCurricular uc = new UnidadeCurricular();

                    Curso curso = new Curso(result.getInt("curso_id"), result.getString("curso_nome"));

                    uc.setId(result.getInt("unidade_curricular_id"));
                    uc.setNome(result.getString("disciplina_nome"));
                    uc.setCurso(curso);
    
                    Turma t = new Turma(
                       result.getInt("id"),
                       result.getString("ano_semestre"),
                       uc
                    );

                    turmas.add(t);
                }
                
            }

        }

        return turmas;
    }

    public List<UnidadeCurricular> listarDisciplinasPorCursos(String[] nomesCurso) throws SQLException{

        List<UnidadeCurricular> disciplinas = new ArrayList<>();

        if(nomesCurso == null || nomesCurso.length == 0) return disciplinas;

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT c.id as id_curso, c.nome as nome_curso, uc.id as uc_id, uc.nome as uc_nome, uc.tipo as uc_tipo ");
        sql.append("FROM unidades_curriculares uc ");
        sql.append("JOIN cursos c ON uc.curso_id = c.id ");
        sql.append("WHERE c.nome IN (");

        for(int i = 0; i < nomesCurso.length; i++){
            sql.append(i == 0 ? "?" : ", ?");
        }

        sql.append(")");

        try(
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql.toString());
        ){

            for(int i = 0; i <nomesCurso.length; i++){
                statement.setString(i + 1, nomesCurso[i].trim());
            }

            try(ResultSet result = statement.executeQuery()){
                while (result.next()) {
                    Curso curso = new Curso(result.getInt("id_curso"), result.getString("nome_curso"));

                    UnidadeCurricular uc = new UnidadeCurricular();

                    uc.setId(result.getInt("uc_id"));
                    uc.setNome(result.getString("uc_nome"));
                    uc.setTipo(result.getString("uc_tipo"));
                    uc.setCurso(curso);

                    disciplinas.add(uc);
                }
            }
        }

        return disciplinas;
    }
}
