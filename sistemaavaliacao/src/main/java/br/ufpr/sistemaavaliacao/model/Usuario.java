package br.ufpr.sistemaavaliacao.model;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String perfil; // aluno, professor, coordenador, administrador

    // Construtores
    public Usuario() {}
    public Usuario(String nome, String email, String login, String senha, String perfil) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    // Getters e Setters (Omitidos para brevidade no texto, mas devem estar completos)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
}