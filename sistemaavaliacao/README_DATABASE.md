# 🗄️ Guia de Conexão MySQL - Sistema de Avaliação UFPR

Este guia explica como configurar e verificar a conexão com o banco de dados MySQL.

---

## 📋 Pré-requisitos

- Java JDK 17+
- Apache Tomcat 9+ ou 10+
- Maven 3.6+
- MySQL 8.0+

---

## 🔧 Passo a Passo para Configurar o MySQL

### Passo 1: Instalar o MySQL

#### Windows:
1. Baixe o MySQL Installer em: https://dev.mysql.com/downloads/installer/
2. Execute o instalador e escolha "Developer Default" ou "Server only"
3. Durante a instalação, defina a senha do usuário `root`
4. Anote a senha, você vai precisar dela depois!

#### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

#### macOS (usando Homebrew):
```bash
brew install mysql
brew services start mysql
mysql_secure_installation
```

### Passo 2: Verificar se o MySQL está rodando

#### Windows:
```cmd
# Abra o Prompt de Comando como Administrador
net start mysql
```
Ou verifique no "Serviços do Windows" se o MySQL está executando.

#### Linux:
```bash
sudo systemctl status mysql
# Ou para iniciar:
sudo systemctl start mysql
```

#### macOS:
```bash
brew services list | grep mysql
# Ou para iniciar:
brew services start mysql
```

### Passo 3: Criar o Banco de Dados

1. Acesse o MySQL pelo terminal:
```bash
mysql -u root -p
```

2. Digite sua senha quando solicitado

3. Execute o script `schema.sql` que está na pasta do projeto:
```sql
SOURCE /caminho/para/Projeto_LPOO/sistemaavaliacao/schema.sql;
```

**Ou** copie e cole o conteúdo do `schema.sql` diretamente no terminal MySQL.

4. Verifique se o banco foi criado:
```sql
SHOW DATABASES;
USE avaliaufpr;
SHOW TABLES;
```

Você deve ver as seguintes tabelas:
- administradores
- alunos
- alternativas
- avaliacoes
- coordenadores
- formularios
- processos_avaliativos
- professores
- questoes
- questoes_multipla_escolha
- respostas
- respostas_abertas
- respostas_multipla_escolha_alternativas
- turmas
- turmas_alunos
- turmas_professores
- unidades_curriculares
- usuarios

### Passo 4: Configurar as Credenciais no Projeto

Abra o arquivo `src/main/java/br/ufpr/sistemaavaliacao/dao/ConnectionFactory.java` e modifique as configurações:

```java
private static final String URL = "jdbc:mysql://localhost:3306/avaliaufpr?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true";
private static final String USUARIO = "root";          // Seu usuário MySQL
private static final String SENHA = "sua_senha_aqui";  // Sua senha MySQL
```

⚠️ **Importante**: Substitua `"sua_senha_aqui"` pela senha que você definiu durante a instalação do MySQL.

### Passo 5: Compilar e Executar o Projeto

```bash
cd sistemaavaliacao
mvn clean package
```

Copie o arquivo `.war` gerado para o Tomcat:
```bash
# Windows
copy target\sistemaavaliacao.war C:\caminho\para\tomcat\webapps\

# Linux/macOS
cp target/sistemaavaliacao.war /caminho/para/tomcat/webapps/
```

Ou use o script `deploy.bat` se estiver no Windows.

### Passo 6: Testar a Conexão

1. Inicie o Tomcat
2. Acesse no navegador: http://localhost:8080/sistemaavaliacao/test-db

Se a conexão estiver funcionando, você verá:
- ✅ Mensagem de sucesso
- Informações sobre o banco de dados
- Lista de todas as tabelas criadas

---

## 🔍 Como Saber se Está Funcionando?

### Teste 1: Página de Teste
Acesse: `http://localhost:8080/sistemaavaliacao/test-db`

**Conexão OK**: Você verá uma página verde com lista de tabelas.
**Conexão Falhou**: Você verá uma página vermelha com a mensagem de erro.

### Teste 2: Via Terminal MySQL
```sql
mysql -u root -p
USE avaliaufpr;
SELECT COUNT(*) FROM usuarios;  -- Deve retornar 0 (tabela vazia mas existente)
```

### Teste 3: Verificar Logs do Tomcat
Se houver erros, verifique o arquivo `logs/catalina.out` no diretório do Tomcat.

---

## ❌ Problemas Comuns e Soluções

### Erro: "Access denied for user 'root'@'localhost'"
**Solução**: Verifique a senha no `ConnectionFactory.java`

### Erro: "Unknown database 'avaliaufpr'"
**Solução**: Execute o script `schema.sql` para criar o banco de dados

### Erro: "Communications link failure"
**Solução**: 
1. Verifique se o MySQL está rodando
2. Verifique se a porta 3306 está correta
3. Verifique se não há firewall bloqueando

### Erro: "No suitable driver found"
**Solução**: Verifique se o `mysql-connector-j` está no `pom.xml` e execute `mvn clean package`

### Erro: "Public Key Retrieval is not allowed"
**Solução**: Já adicionamos `allowPublicKeyRetrieval=true` na URL de conexão

---

## 📁 Estrutura dos Arquivos de Conexão

```
sistemaavaliacao/
├── pom.xml                           # Dependência do MySQL Connector
├── schema.sql                        # Script para criar o banco
├── src/main/java/br/ufpr/sistemaavaliacao/
│   ├── dao/
│   │   └── ConnectionFactory.java    # Classe de conexão com o BD
│   └── TestDatabaseServlet.java      # Servlet para testar conexão
└── README_DATABASE.md                # Este arquivo
```

---

## 💡 Dicas Importantes

1. **Nunca** commite senhas reais no código. Em produção, use variáveis de ambiente.
2. Sempre **feche** as conexões após usar (use try-with-resources).
3. O banco `avaliaufpr` deve ser criado **antes** de iniciar a aplicação.
4. Se mudar a senha do MySQL, lembre de atualizar o `ConnectionFactory.java`.

---

## 📞 Suporte

Se ainda tiver problemas, verifique:
1. Versão do MySQL instalada (`mysql --version`)
2. Se o serviço MySQL está ativo
3. Se o usuário e senha estão corretos
4. Se o banco de dados foi criado corretamente
