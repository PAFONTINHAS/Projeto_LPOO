package br.ufpr.sistemaavaliacao.model;

public class TurmaProfessor {

    private int idTurma;
    private int idProfessor;

    public TurmaProfessor() {}

    public TurmaProfessor(int idTurma, int idProfessor) {
        this.idTurma = idTurma;
        this.idProfessor = idProfessor;
    }

    // GETTERS E SETTERS

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }
}
