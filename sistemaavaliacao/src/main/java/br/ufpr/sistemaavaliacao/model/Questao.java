package br.ufpr.sistemaavaliacao.model;

import java.util.List;
import br.ufpr.sistemaavaliacao.model.Alternativa; // Necessário

public class Questao {
    private int id; 
    private int formularioId; 
    private String enunciado; 
    private boolean isObrigatoria; // RF10
    private String tipo; // "Aberta", "MultiplaEscolha"
    private boolean permiteMultiplaSelecao; // RF08: Apenas para Multipla Escolha
    private List<Alternativa> alternativas; // Apenas para Multipla Escolha

    // Construtores e Getters/Setters (Completo)
    public Questao() {}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFormularioId() { return formularioId; }
    public void setFormularioId(int formularioId) { this.formularioId = formularioId; }
    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public boolean isObrigatoria() { return isObrigatoria; }
    public void setObrigatoria(boolean obrigatoria) { isObrigatoria = obrigatoria; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public boolean isPermiteMultiplaSelecao() { return permiteMultiplaSelecao; }
    public void setPermiteMultiplaSelecao(boolean permiteMultiplaSelecao) { this.permiteMultiplaSelecao = permiteMultiplaSelecao; }
    public List<Alternativa> getAlternativas() { return alternativas; }
    public void setAlternativas(List<Alternativa> alternativas) { this.alternativas = alternativas; }
}