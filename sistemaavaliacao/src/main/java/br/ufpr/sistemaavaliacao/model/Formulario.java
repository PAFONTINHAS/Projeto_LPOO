package br.ufpr.sistemaavaliacao.model;

import java.util.List;

public class Formulario {
    private int id; 
    private int processoAvaliativoId; 
    private String titulo; 
    private boolean isAnonimo; // RF11
    private String instrucoes; 
    private List<Questao> questoes; 

    // Construtores e Getters/Setters (Omitidos para brevidade)
    public Formulario() {}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProcessoAvaliativoId() { return processoAvaliativoId; }
    public void setProcessoAvaliativoId(int processoAvaliativoId) { this.processoAvaliativoId = processoAvaliativoId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public boolean isAnonimo() { return isAnonimo; }
    public void setAnonimo(boolean isAnonimo) { this.isAnonimo = isAnonimo; }
    public String getInstrucoes() { return instrucoes; }
    public void setInstrucoes(String instrucoes) { this.instrucoes = instrucoes; }
    public List<Questao> getQuestoes() { return questoes; }
    public void setQuestoes(List<Questao> questoes) { this.questoes = questoes; }
}