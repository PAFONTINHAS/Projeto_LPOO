package br.ufpr.sistemaavaliacao.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Formulario {

    private int id;
    private Integer processoAvaliativoId;
    private String titulo;
    private boolean isAnonimo; // RF11
    private String instrucoes;
    private List<Questao> questoes;
    private int quantidadeRespostas; // Adicione este atributo

    public Formulario(String titulo, boolean isAnonimo) {
        this.titulo = titulo;
        this.isAnonimo = isAnonimo;
        this.questoes = new ArrayList<>();
    }

    public void adicionarQuestao(Questao questao) {
        this.questoes.add(questao);
    }

    public boolean getIsAnonimo() {
        return this.isAnonimo;
    }

    // No arquivo Formulario.java

    // E os getters/setters
    public int getQuantidadeRespostas() {
        return quantidadeRespostas;
    }

    public void setQuantidadeRespostas(int quantidadeRespostas) {
        this.quantidadeRespostas = quantidadeRespostas;
    }

}