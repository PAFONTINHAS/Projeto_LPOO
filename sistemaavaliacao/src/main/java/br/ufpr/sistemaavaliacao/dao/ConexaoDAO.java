package br.ufpr.sistemaavaliacao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável pela conexão com o banco de dados MySQL.
 * 
 * CONFIGURAÇÃO:
 * 1. Certifique-se de que o MySQL está instalado e rodando
 * 2. Execute o arquivo schema.sql para criar o banco e as tabelas
 * 3. Altere as credenciais abaixo conforme sua configuração local
 */
public class ConexaoDAO {
    
    // Nome do banco de dados
    public static final String NOME_BANCO = "avaliaufpr";
    
    // Configurações do banco de dados - ALTERE CONFORME SUA MÁQUINA
    private static final String URL = "jdbc:mysql://localhost:3306/" + NOME_BANCO + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";      // Altere para seu usuário
    private static final String SENHA = "";            // Altere para sua senha (vazio por padrão no XAMPP)
    
    /**
     * Obtém uma conexão com o banco de dados MySQL.
     * 
     * @return Connection objeto de conexão com o banco
     * @throws SQLException se houver erro na conexão
     */
    public static Connection getConexao() throws SQLException {
        try {
            // Carrega o driver do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Estabelece a conexão
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("✓ Conexão com MySQL estabelecida com sucesso!");
            return conexao;
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado. Verifique se o mysql-connector-j está no pom.xml", e);
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados. Verifique se o MySQL está rodando e as credenciais estão corretas. Detalhes: " + e.getMessage(), e);
        }
    }
    
    /**
     * Fecha a conexão com o banco de dados.
     * 
     * @param conexao a conexão a ser fechada
     */
    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("✓ Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    /**
     * Testa a conexão com o banco de dados.
     * 
     * @return true se a conexão foi bem sucedida, false caso contrário
     */
    public static boolean testarConexao() {
        Connection conexao = null;
        boolean resultado = false;
        try {
            conexao = getConexao();
            resultado = conexao != null && !conexao.isClosed();
        } catch (SQLException e) {
            System.err.println("✗ Falha ao testar conexão: " + e.getMessage());
            resultado = false;
        } finally {
            fecharConexao(conexao);
        }
        return resultado;
    }
}
