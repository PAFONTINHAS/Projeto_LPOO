package br.ufpr.sistemaavaliacao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import br.ufpr.sistemaavaliacao.dao.ConexaoDAO;

/**
 * Servlet para testar a conexão com o banco de dados MySQL.
 * Acesse: http://localhost:8080/sistemaavaliacao/testar-conexao
 * 
 * IMPORTANTE: Este servlet é apenas para fins de desenvolvimento/teste.
 * Remova ou desabilite em produção para evitar exposição de informações sensíveis.
 */
@WebServlet("/testar-conexao")
public class TestarConexaoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Teste de Conexão MySQL</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }");
        out.println(".container { background: white; padding: 30px; border-radius: 10px; max-width: 800px; margin: auto; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println("h1 { color: #333; }");
        out.println(".success { color: green; background: #e8f5e9; padding: 15px; border-radius: 5px; border-left: 4px solid green; }");
        out.println(".error { color: red; background: #ffebee; padding: 15px; border-radius: 5px; border-left: 4px solid red; }");
        out.println(".info { color: #1976d2; background: #e3f2fd; padding: 15px; border-radius: 5px; margin: 10px 0; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
        out.println("th { background-color: #4CAF50; color: white; }");
        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("</style></head><body>");
        out.println("<div class='container'>");
        out.println("<h1>🔌 Teste de Conexão com MySQL</h1>");
        
        Connection conexao = null;
        try {
            conexao = ConexaoDAO.getConexao();
            
            out.println("<div class='success'>");
            out.println("<h2>✓ Conexão estabelecida com sucesso!</h2>");
            out.println("</div>");
            
            // Mostrar informações do banco
            DatabaseMetaData metaData = conexao.getMetaData();
            out.println("<div class='info'>");
            out.println("<h3>📊 Informações do Banco de Dados:</h3>");
            out.println("<ul>");
            out.println("<li><strong>Banco:</strong> " + metaData.getDatabaseProductName() + "</li>");
            out.println("<li><strong>Versão:</strong> " + metaData.getDatabaseProductVersion() + "</li>");
            out.println("<li><strong>Driver:</strong> " + metaData.getDriverName() + "</li>");
            out.println("<li><strong>URL:</strong> " + metaData.getURL() + "</li>");
            out.println("</ul>");
            out.println("</div>");
            
            // Listar tabelas
            out.println("<h3>📋 Tabelas encontradas no banco '" + ConexaoDAO.NOME_BANCO + "':</h3>");
            
            int count = 0;
            try (ResultSet tabelas = metaData.getTables(ConexaoDAO.NOME_BANCO, null, "%", new String[]{"TABLE"})) {
                out.println("<table>");
                out.println("<tr><th>#</th><th>Nome da Tabela</th><th>Tipo</th></tr>");
                
                while (tabelas.next()) {
                    count++;
                    String nomeTabela = tabelas.getString("TABLE_NAME");
                    String tipo = tabelas.getString("TABLE_TYPE");
                    out.println("<tr><td>" + count + "</td><td>" + nomeTabela + "</td><td>" + tipo + "</td></tr>");
                }
                
                out.println("</table>");
            }
            
            if (count == 0) {
                out.println("<div class='info'>");
                out.println("<p>⚠️ Nenhuma tabela encontrada. Execute o arquivo <code>schema.sql</code> para criar as tabelas.</p>");
                out.println("</div>");
            } else {
                out.println("<p><strong>Total:</strong> " + count + " tabelas encontradas.</p>");
            }
            
        } catch (SQLException e) {
            out.println("<div class='error'>");
            out.println("<h2>✗ Erro ao conectar!</h2>");
            out.println("<p><strong>Mensagem:</strong> " + e.getMessage() + "</p>");
            out.println("</div>");
            
            out.println("<div class='info'>");
            out.println("<h3>🔧 Possíveis soluções:</h3>");
            out.println("<ol>");
            out.println("<li>Verifique se o MySQL está instalado e rodando</li>");
            out.println("<li>Verifique se o banco 'avaliaufpr' foi criado (execute o schema.sql)</li>");
            out.println("<li>Verifique o usuário e senha em ConexaoDAO.java</li>");
            out.println("<li>Se estiver usando XAMPP, inicie o MySQL pelo painel</li>");
            out.println("</ol>");
            out.println("</div>");
        } finally {
            ConexaoDAO.fecharConexao(conexao);
        }
        
        out.println("<hr>");
        out.println("<p><a href='index.jsp'>← Voltar para página inicial</a></p>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
