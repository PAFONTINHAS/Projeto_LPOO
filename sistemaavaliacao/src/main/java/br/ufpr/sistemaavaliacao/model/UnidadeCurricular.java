package br.ufpr.sistemaavaliacao.model;

public class UnidadeCurricular {

    private int id;
    private String nome;
    private String descricao;

    public UnidadeCurricular() {}

    public UnidadeCurricular(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public UnidadeCurricular(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // GETTERS E SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
