package br.ufpr.sistemaavaliacao.model;

public class Turma {

    private int id;
    private String nome;
    private int ano;
    private int semestre;

    private UnidadeCurricular unidadeCurricular;

    public Turma() {}

    public Turma(int id, String nome, int ano, int semestre, UnidadeCurricular uc) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.unidadeCurricular = uc;
    }

    public Turma(String nome, int ano, int semestre, UnidadeCurricular uc) {
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.unidadeCurricular = uc;
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

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public UnidadeCurricular getUnidadeCurricular() {
        return unidadeCurricular;
    }

    public void setUnidadeCurricular(UnidadeCurricular unidadeCurricular) {
        this.unidadeCurricular = unidadeCurricular;
    }
}
