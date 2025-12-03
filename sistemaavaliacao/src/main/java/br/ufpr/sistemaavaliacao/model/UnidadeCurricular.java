package br.ufpr.sistemaavaliacao.model;

import lombok.*;

@Getter @Setter
public class UnidadeCurricular {

    private int id;
    private String nome;
    private String tipo;
    private Curso curso;

    // public UnidadeCurricular(int id, String nome) {
    //     this.id = id;
    //     this.nome = nome;
    // }

    // public UnidadeCurricular(String nome) {
    //     this.nome = nome;
    // }

}
