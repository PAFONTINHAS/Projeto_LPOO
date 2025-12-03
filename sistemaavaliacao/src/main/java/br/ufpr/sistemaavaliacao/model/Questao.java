package br.ufpr.sistemaavaliacao.model;

import java.util.List;
import br.ufpr.sistemaavaliacao.model.Alternativa; // Necessário
import lombok.*;

@Getter
@Setter
public abstract class Questao {
    protected int id;
    protected String enunciado;
    protected boolean isObrigatoria;

    protected Questao(String enunciado, boolean isObrigatoria){
        this.enunciado = enunciado;
        this.isObrigatoria = isObrigatoria;
    }

    public boolean getIsObrigatoria(){
        return this.isObrigatoria;
    }

    public boolean isMultiplaEscolha(){
        return this instanceof QuestaoMultiplaEscolha;
    }

    // private int id; 
    // private int formularioId; 
    // private String enunciado; 
    // private boolean isObrigatoria; // RF10
    // private String tipo; // "Aberta", "MultiplaEscolha"
    // private boolean permiteMultiplaSelecao; // RF08: Apenas para Multipla Escolha
    // private List<Alternativa> alternativas; // Apenas para Multipla Escolha
}