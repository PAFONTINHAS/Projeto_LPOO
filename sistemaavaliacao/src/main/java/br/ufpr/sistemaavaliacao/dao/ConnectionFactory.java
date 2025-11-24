package br.ufpr.sistemaavaliacao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar conexões com o banco de dados MySQL.
 * 
 * CONFIGURAÇÃO:
 * Antes de usar, certifique-se de que o MySQL está instalado e configurado.
 * Veja o arquivo README_DATABASE.md para instruções completas.
 * 
 * NOTA DE SEGURANÇA:
 * - Em ambiente de produção, use variáveis de ambiente para as credenciais
 * - Em ambiente de produção, habilite SSL (useSSL=true)
 * - Este arquivo está configurado para facilitar o desenvolvimento local
 */
public class ConnectionFactory {

    // Nome do banco de dados - usado pela aplicação
    public static final String DATABASE_NAME = "avaliaufpr";

    // Configurações de conexão - MODIFIQUE conforme seu ambiente
    // NOTA: useSSL=false é aceitável para desenvolvimento local, mas em produção habilite SSL
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    // IMPORTANTE: Configure sua senha aqui. Em produção, use variáveis de ambiente.
    private static final String SENHA = "";  // Coloque sua senha do MySQL aqui

    static {
        try {
            // Carrega o driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado. Verifique se o mysql-connector-j está no classpath.", e);
        }
    }

    /**
     * Obtém uma conexão com o banco de dados.
     * 
     * @return Connection - conexão ativa com o banco de dados
     * @throws SQLException se houver erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    /**
     * Fecha a conexão de forma segura.
     * 
     * @param connection - conexão a ser fechada
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
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
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Erro ao testar conexão: " + e.getMessage());
            return false;
        }
    }
}
