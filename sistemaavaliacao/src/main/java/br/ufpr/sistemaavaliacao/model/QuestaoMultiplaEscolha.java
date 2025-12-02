package br.ufpr.sistemaavaliacao.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter @Setter

public class QuestaoMultiplaEscolha extends Questao{

    private boolean permiteMultiplaSelecao;

    private List<Alternativa> alternativas;

    public QuestaoMultiplaEscolha(String enunciado, boolean isObrigatoria, boolean permiteMultiplaSelecao){
        super(enunciado, isObrigatoria);
        this.permiteMultiplaSelecao = permiteMultiplaSelecao;
        this.alternativas = new ArrayList<>();
    }

    public void adicionarAlternativa(Alternativa alternativa){
        this.alternativas.add(alternativa);
    }

    public boolean getPermiteMultiplaSelecao(){
        return this.permiteMultiplaSelecao;
    }

    
    
}
