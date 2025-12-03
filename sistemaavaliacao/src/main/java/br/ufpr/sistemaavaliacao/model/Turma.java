package br.ufpr.sistemaavaliacao.model;

import lombok.*;

@Getter @Setter
public class Turma {

    private int id;
    // private String nome;
    private String anoSemestre; // 2024/01 || 2024/1
    private UnidadeCurricular unidadeCurricular;


    public Turma(int id, String anoSemestre, UnidadeCurricular uc) {
        this.id = id;
        this.anoSemestre = anoSemestre;
        this.unidadeCurricular = uc;
    }

    public UnidadeCurricular getUnidadeCurricular() {
        return unidadeCurricular;
    }

    public void setUnidadeCurricular(UnidadeCurricular unidadeCurricular) {
        this.unidadeCurricular = unidadeCurricular;
    }
}
