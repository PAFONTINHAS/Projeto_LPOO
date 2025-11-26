package br.ufpr.sistemaavaliacao.model;

public class Aluno {
    private int usuarioId;
    private String matricula;

    public Aluno() {}
    public Aluno(int usuarioId, String matricula) {
        this.usuarioId = usuarioId;
        this.matricula = matricula;
    }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}