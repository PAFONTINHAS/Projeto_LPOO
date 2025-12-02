package br.ufpr.sistemaavaliacao.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Formulario {

    private int id; 
    private Integer processoAvaliativoId; 
    private String titulo; 
    private boolean isAnonimo; // RF11
    private String instrucoes; 
    private List<Questao> questoes; 

    public Formulario(String titulo, boolean isAnonimo){
        this.titulo = titulo;
        this.isAnonimo = isAnonimo;
        this.questoes = new ArrayList<>();
    }

    public void adicionarQuestao(Questao questao){
        this.questoes.add(questao);
    }

    public boolean getIsAnonimo(){
        return this.isAnonimo;
    }

}