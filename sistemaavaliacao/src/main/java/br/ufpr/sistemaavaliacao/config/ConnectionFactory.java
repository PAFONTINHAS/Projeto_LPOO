package br.ufpr.sistemaavaliacao.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // Configurações do Banco de Dados
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // Mantenha o nome do DB como avaliaufpr, conforme seu schema.sql
    private static final String URL = "jdbc:mysql://localhost:3306/avaliaufpr?useTimezone=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root"; // <== ATENÇÃO: Altere conforme sua configuração local
    private static final String PASS = "admin"; // <== ATENÇÃO: Altere conforme sua configuração local

    /**
     * Estabelece a conexão com o banco de dados.
     * @return Objeto Connection.
     * @throws RuntimeException se a conexão falhar.
     */
    public static Connection getConnection() {
        try {
            // Carrega o driver
            Class.forName(DRIVER);
            // Estabelece a conexão
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar o driver JDBC do MySQL.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao estabelecer a conexão com o banco de dados.", e);
        }
    }

    /**
     * Fecha um objeto Connection.
     * @param conn A conexão a ser fechada.
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}