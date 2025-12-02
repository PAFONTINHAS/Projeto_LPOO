package br.ufpr.sistemaavaliacao.dao;

import br.ufpr.sistemaavaliacao.config.ConnectionFactory;
import br.ufpr.sistemaavaliacao.model.Curriculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurriculoDAO {

    /**
     * Insere um novo currículo.
     */
    public void inserir(Curriculo curriculo) throws SQLException {
        String sql = "INSERT INTO curriculos (curso_id, versao, ano_implantacao, portaria_aprovacao, ch_obrigatoria, ch_optativa, ch_atividades_formativas, ch_extensao, tcc_obrigatorio, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, curriculo.getCursoId());
            ps.setString(2, curriculo.getVersao());
            ps.setInt(3, curriculo.getAnoImplantacao());
            ps.setString(4, curriculo.getPortariaAprovacao());
            ps.setInt(5, curriculo.getChObrigatoria());
            ps.setInt(6, curriculo.getChOptativa());
            ps.setInt(7, curriculo.getChAtividadesFormativas());
            ps.setInt(8, curriculo.getChExtensao());
            ps.setBoolean(9, curriculo.isTccObrigatorio());
            ps.setString(10, curriculo.getObservacoes());

            ps.executeUpdate();
        }
    }

    /**
     * Lista todos os currículos com o nome do curso.
     */
    public List<Curriculo> listar() throws SQLException {
        List<Curriculo> lista = new ArrayList<>();
        String sql = "SELECT c.*, cu.nome as curso_nome FROM curriculos c JOIN cursos cu ON c.curso_id = cu.id ORDER BY cu.nome, c.versao DESC";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Curriculo curriculo = mapearCurriculo(rs);
                curriculo.setCursoNome(rs.getString("curso_nome"));
                lista.add(curriculo);
            }
        }
        return lista;
    }

    /**
     * Deleta todos os currículos de um curso. Usado no CursoDAO.deletar().
     */
    public void deletarPorCurso(int cursoId) throws SQLException {
        String sql = "DELETE FROM curriculos WHERE curso_id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cursoId);
            ps.executeUpdate();
        }
    }
    
    /**
     * Deleta um currículo específico por ID.
     */
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM curriculos WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    /**
     * Busca um currículo por ID.
     */
    public Curriculo buscarPorId(int id) throws SQLException {
        String sql = "SELECT c.*, cu.nome as curso_nome FROM curriculos c JOIN cursos cu ON c.curso_id = cu.id WHERE c.id = ?";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Curriculo curriculo = mapearCurriculo(rs);
                curriculo.setCursoNome(rs.getString("curso_nome"));
                return curriculo;
            }
        }
        return null;
    }
    
    /**
     * Atualiza um currículo existente.
     */
    public void atualizar(Curriculo curriculo) throws SQLException {
        String sql = "UPDATE curriculos SET curso_id=?, versao=?, ano_implantacao=?, portaria_aprovacao=?, ch_obrigatoria=?, ch_optativa=?, ch_atividades_formativas=?, ch_extensao=?, tcc_obrigatorio=?, observacoes=? WHERE id=?";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, curriculo.getCursoId());
            ps.setString(2, curriculo.getVersao());
            ps.setInt(3, curriculo.getAnoImplantacao());
            ps.setString(4, curriculo.getPortariaAprovacao());
            ps.setInt(5, curriculo.getChObrigatoria());
            ps.setInt(6, curriculo.getChOptativa());
            ps.setInt(7, curriculo.getChAtividadesFormativas());
            ps.setInt(8, curriculo.getChExtensao());
            ps.setBoolean(9, curriculo.isTccObrigatorio());
            ps.setString(10, curriculo.getObservacoes());
            ps.setInt(11, curriculo.getId());

            ps.executeUpdate();
        }
    }

    // Método auxiliar para mapear o ResultSet para o objeto Curriculo
    private Curriculo mapearCurriculo(ResultSet rs) throws SQLException {
        Curriculo curriculo = new Curriculo();
        curriculo.setId(rs.getInt("id"));
        curriculo.setCursoId(rs.getInt("curso_id"));
        curriculo.setVersao(rs.getString("versao"));
        curriculo.setAnoImplantacao(rs.getInt("ano_implantacao"));
        curriculo.setPortariaAprovacao(rs.getString("portaria_aprovacao"));
        curriculo.setChObrigatoria(rs.getInt("ch_obrigatoria"));
        curriculo.setChOptativa(rs.getInt("ch_optativa"));
        curriculo.setChAtividadesFormativas(rs.getInt("ch_atividades_formativas"));
        curriculo.setChExtensao(rs.getInt("ch_extensao"));
        curriculo.setTccObrigatorio(rs.getBoolean("tcc_obrigatorio"));
        curriculo.setObservacoes(rs.getString("observacoes"));
        return curriculo;
    }
}