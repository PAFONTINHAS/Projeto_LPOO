# 🗄️ Guia de Conexão com MySQL - Sistema de Avaliação UFPR

Este guia explica como configurar e testar a conexão com o banco de dados MySQL no projeto.

---

## 📋 Pré-requisitos

Antes de começar, você precisa ter instalado:

- **Java JDK 17** ou superior
- **Apache Maven** (para compilar o projeto)
- **Apache Tomcat 9** ou superior (servidor web)
- **MySQL 8.0** ou **XAMPP/WAMP** (que inclui MySQL)

---

## 🚀 Passo a Passo para Configurar o MySQL

### Passo 1: Instalar o MySQL

**Opção A - Usando XAMPP (mais fácil para iniciantes):**
1. Baixe o XAMPP: https://www.apachefriends.org/pt_br/download.html
2. Instale o XAMPP
3. Abra o Painel de Controle do XAMPP
4. Clique em **Start** no módulo **MySQL**
5. O MySQL estará rodando na porta 3306

**Opção B - Instalação direta do MySQL:**
1. Baixe o MySQL: https://dev.mysql.com/downloads/mysql/
2. Durante a instalação, defina uma senha para o usuário `root`
3. Inicie o serviço MySQL

### Passo 2: Criar o Banco de Dados

**Usando o phpMyAdmin (se estiver usando XAMPP):**
1. Abra o navegador e acesse: `http://localhost/phpmyadmin`
2. Clique na aba **SQL**
3. Copie todo o conteúdo do arquivo `schema.sql` (está na pasta `sistemaavaliacao/`)
4. Cole no campo de texto e clique em **Executar**

**Usando o terminal MySQL:**
```bash
# Conectar ao MySQL
mysql -u root -p

# Executar o script (ajuste o caminho conforme necessário)
source /caminho/para/sistemaavaliacao/schema.sql
```

### Passo 3: Configurar as Credenciais

Abra o arquivo `src/main/java/br/ufpr/sistemaavaliacao/dao/ConexaoDAO.java` e ajuste conforme suas credenciais:

```java
// Configurações do banco de dados - ALTERE CONFORME SUA MÁQUINA
private static final String URL = "jdbc:mysql://localhost:3306/avaliaufpr?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
private static final String USUARIO = "root";      // Seu usuário MySQL
private static final String SENHA = "";            // Sua senha MySQL (vazio no XAMPP por padrão)
```

**Configurações comuns:**

| Cenário | Usuário | Senha |
|---------|---------|-------|
| XAMPP (padrão) | `root` | `` (vazio) |
| MySQL instalado | `root` | `sua_senha_aqui` |
| WAMP (padrão) | `root` | `` (vazio) |

### Passo 4: Compilar o Projeto

```bash
# Navegue até a pasta do projeto
cd sistemaavaliacao

# Compile com Maven
mvn clean package
```

O arquivo `sistemaavaliacao.war` será gerado em `target/`.

### Passo 5: Fazer Deploy no Tomcat

1. Copie o arquivo `target/sistemaavaliacao.war` para a pasta `webapps` do Tomcat
2. Inicie o Tomcat (se não estiver rodando)
3. Aguarde alguns segundos para o deploy

---

## ✅ Como Saber se Está Funcionando

### Teste 1: Via Navegador

Acesse no seu navegador:
```
http://localhost:8080/sistemaavaliacao/testar-conexao
```

Se a conexão estiver funcionando, você verá:
- ✓ **Mensagem de sucesso** em verde
- Lista de todas as tabelas do banco de dados
- Informações sobre a versão do MySQL

Se houver erro, você verá:
- ✗ **Mensagem de erro** em vermelho
- Dicas para resolver o problema

### Teste 2: Verificar no Console do Tomcat

Ao acessar a página de teste, o console do Tomcat mostrará:
```
✓ Conexão com MySQL estabelecida com sucesso!
✓ Conexão fechada com sucesso!
```

---

## ❌ Problemas Comuns e Soluções

### Erro: "Communications link failure"
**Causa:** MySQL não está rodando
**Solução:** 
- XAMPP: Clique em "Start" no MySQL
- MySQL direto: `sudo service mysql start` (Linux) ou inicie o serviço no Windows

### Erro: "Access denied for user 'root'"
**Causa:** Senha incorreta
**Solução:** Verifique a senha no arquivo `ConexaoDAO.java`

### Erro: "Unknown database 'avaliaufpr'"
**Causa:** Banco de dados não foi criado
**Solução:** Execute o arquivo `schema.sql` conforme Passo 2

### Erro: "Driver not found"
**Causa:** Dependência MySQL não está instalada
**Solução:** Execute `mvn clean package` para baixar as dependências

### Erro: "Public Key Retrieval is not allowed"
**Causa:** Configuração de segurança do MySQL 8
**Solução:** Já está resolvido na URL de conexão com `allowPublicKeyRetrieval=true`

---

## 📁 Estrutura dos Arquivos de Conexão

```
sistemaavaliacao/
├── pom.xml                          # Dependência do MySQL Connector
├── schema.sql                       # Script para criar o banco e tabelas
└── src/main/java/br/ufpr/sistemaavaliacao/
    ├── dao/
    │   └── ConexaoDAO.java          # Classe de conexão com o banco
    └── TestarConexaoServlet.java    # Servlet para testar conexão
```

---

## 🔧 Usando a Conexão no Código

Exemplo de como usar a classe `ConexaoDAO` em outros DAOs:

```java
import br.ufpr.sistemaavaliacao.dao.ConexaoDAO;
import java.sql.*;

public class UsuarioDAO {
    
    public void inserirUsuario(String nome, String email) {
        Connection conexao = null;
        PreparedStatement stmt = null;
        
        try {
            conexao = ConexaoDAO.getConexao();
            
            String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.executeUpdate();
            
            System.out.println("Usuário inserido com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        } finally {
            // Sempre feche os recursos
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
            ConexaoDAO.fecharConexao(conexao);
        }
    }
}
```

---

## 📞 Precisa de Ajuda?

Se ainda tiver dúvidas:
1. Verifique se o MySQL está rodando (porta 3306)
2. Acesse `http://localhost:8080/sistemaavaliacao/testar-conexao` para diagnóstico
3. Verifique os logs do Tomcat para mensagens de erro detalhadas

---

**Última atualização:** Novembro 2024
