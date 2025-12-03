package br.ufpr.sistemaavaliacao.model;

import lombok.*;

@Getter @Setter
public class Curso {

    private int id;
    private String nome;

    public Curso(int id, String nome){
        this.id = id;
        this.nome = nome;
    }
    
}
