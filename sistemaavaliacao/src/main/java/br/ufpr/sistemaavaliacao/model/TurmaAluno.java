package br.ufpr.sistemaavaliacao.model;

public class TurmaAluno {

    private int idTurma;
    private int idAluno;

    public TurmaAluno() {}

    public TurmaAluno(int idTurma, int idAluno) {
        this.idTurma = idTurma;
        this.idAluno = idAluno;
    }

    // GETTERS E SETTERS

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }
}
