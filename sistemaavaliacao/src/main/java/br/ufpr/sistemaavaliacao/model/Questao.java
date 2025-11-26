package br.ufpr.sistemaavaliacao.model;

public class Questao {

    private int id;
    private String descricao;
    private String tipo; // aberta ou fechada
    private int idFormulario;

    public Questao() {}

    public Questao(int id, String descricao, String tipo, int idFormulario) {
        this.id = id;
        this.descricao = descricao;
        this.tipo = tipo;
        this.idFormulario = idFormulario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(int idFormulario) {
        this.idFormulario = idFormulario;
    }
}
