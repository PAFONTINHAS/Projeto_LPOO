package br.ufpr.sistemaavaliacao.model;

/**
 * Mapeia para a tabela 'alternativas'.
 */
public class Alternativa {
    private int id;
    private int questaoMultiplaEscolhaId;
    private String texto;
    private int peso;

    public Alternativa() {}

    public Alternativa(String texto, int peso) {
        this.texto = texto;
        this.peso = peso;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getQuestaoMultiplaEscolhaId() { return questaoMultiplaEscolhaId; }
    public void setQuestaoMultiplaEscolhaId(int questaoMultiplaEscolhaId) { this.questaoMultiplaEscolhaId = questaoMultiplaEscolhaId; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }
}