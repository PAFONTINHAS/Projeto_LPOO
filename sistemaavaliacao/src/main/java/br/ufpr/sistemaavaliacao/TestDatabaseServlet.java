package br.ufpr.sistemaavaliacao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpr.sistemaavaliacao.dao.ConnectionFactory;

/**
 * Servlet para testar a conexão com o banco de dados MySQL.
 * 
 * AVISO: Este servlet é APENAS para desenvolvimento e testes.
 * NÃO use em ambiente de produção pois exibe informações de diagnóstico.
 * 
 * Acesse: http://localhost:8080/sistemaavaliacao/test-db
 * 
 * Esta página mostra:
 * - Se a conexão foi bem sucedida
 * - Informações sobre o banco de dados
 * - Lista de tabelas existentes
 */
@WebServlet("/test-db")
public class TestDatabaseServlet extends HttpServlet {

    /**
     * Sanitiza texto para evitar XSS (Cross-Site Scripting)
     */
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Teste de Conexão MySQL</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }");
        out.println(".container { background: white; padding: 30px; border-radius: 10px; max-width: 800px; margin: 0 auto; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println(".success { color: #28a745; }");
        out.println(".error { color: #dc3545; }");
        out.println(".info { background: #e7f3ff; padding: 15px; border-radius: 5px; margin: 10px 0; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
        out.println("th { background-color: #007bff; color: white; }");
        out.println("tr:hover { background-color: #f5f5f5; }");
        out.println("h1 { color: #333; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>🔌 Teste de Conexão com MySQL</h1>");

        Connection conn = null;
        try {
            // Tenta obter conexão
            conn = ConnectionFactory.getConnection();

            if (conn != null && !conn.isClosed()) {
                out.println("<p class='success'>✅ <strong>Conexão estabelecida com sucesso!</strong></p>");

                // Informações do banco
                DatabaseMetaData metaData = conn.getMetaData();
                out.println("<div class='info'>");
                out.println("<h3>📊 Informações do Banco de Dados</h3>");
                out.println("<p><strong>URL:</strong> " + escapeHtml(metaData.getURL()) + "</p>");
                out.println("<p><strong>Usuário:</strong> " + escapeHtml(metaData.getUserName()) + "</p>");
                out.println("<p><strong>Produto:</strong> " + escapeHtml(metaData.getDatabaseProductName()) + " " + escapeHtml(metaData.getDatabaseProductVersion()) + "</p>");
                out.println("<p><strong>Driver:</strong> " + escapeHtml(metaData.getDriverName()) + " " + escapeHtml(metaData.getDriverVersion()) + "</p>");
                out.println("</div>");

                // Lista tabelas
                out.println("<h3>📋 Tabelas no Banco de Dados '" + escapeHtml(ConnectionFactory.DATABASE_NAME) + "'</h3>");
                out.println("<table>");
                out.println("<tr><th>#</th><th>Nome da Tabela</th><th>Tipo</th></tr>");

                ResultSet tables = metaData.getTables(ConnectionFactory.DATABASE_NAME, null, "%", new String[]{"TABLE"});
                int count = 0;
                while (tables.next()) {
                    count++;
                    out.println("<tr>");
                    out.println("<td>" + count + "</td>");
                    out.println("<td>" + escapeHtml(tables.getString("TABLE_NAME")) + "</td>");
                    out.println("<td>" + escapeHtml(tables.getString("TABLE_TYPE")) + "</td>");
                    out.println("</tr>");
                }
                tables.close();

                if (count == 0) {
                    out.println("<tr><td colspan='3'>Nenhuma tabela encontrada. Execute o script schema.sql primeiro.</td></tr>");
                }

                out.println("</table>");
                out.println("<p><strong>Total de tabelas:</strong> " + count + "</p>");
            }
        } catch (SQLException e) {
            // Log do erro no servidor para administradores
            System.err.println("Erro de conexão com banco de dados: " + e.getMessage());
            
            out.println("<p class='error'>❌ <strong>Erro na conexão!</strong></p>");
            out.println("<div class='info' style='background: #ffe6e6;'>");
            out.println("<h3>⚠️ Detalhes do Erro (somente desenvolvimento)</h3>");
            // Sanitização das mensagens para evitar XSS
            out.println("<p><strong>Mensagem:</strong> " + escapeHtml(e.getMessage()) + "</p>");
            out.println("<p><strong>Código SQL:</strong> " + escapeHtml(e.getSQLState()) + "</p>");
            out.println("<p><strong>Código Erro:</strong> " + e.getErrorCode() + "</p>");
            out.println("</div>");

            out.println("<div class='info'>");
            out.println("<h3>💡 Possíveis Soluções</h3>");
            out.println("<ul>");
            out.println("<li>Verifique se o MySQL está instalado e rodando</li>");
            out.println("<li>Verifique se o banco 'avaliaufpr' foi criado (execute schema.sql)</li>");
            out.println("<li>Verifique usuário e senha em ConnectionFactory.java</li>");
            out.println("<li>Verifique se a porta 3306 está correta</li>");
            out.println("</ul>");
            out.println("</div>");
        } finally {
            ConnectionFactory.closeConnection(conn);
        }

        out.println("<hr>");
        out.println("<p><a href='hello'>Voltar para Hello Servlet</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
