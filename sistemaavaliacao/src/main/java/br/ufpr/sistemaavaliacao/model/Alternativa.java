package br.ufpr.sistemaavaliacao.model;

/**
 * Mapeia para a tabela 'alternativas'.
 */
import lombok.*;

@Getter @Setter
public class Alternativa {
    private int id;
    // private int questaoMultiplaEscolhaId;
    private String texto;
    private int peso;

    public Alternativa(String texto, int peso) {
        this.texto = texto;
        this.peso = peso;
    }

}