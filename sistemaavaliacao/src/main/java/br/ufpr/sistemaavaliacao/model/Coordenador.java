package br.ufpr.sistemaavaliacao.model;

public class Coordenador {
    private int usuarioId;

    public Coordenador() {}
    public Coordenador(int usuarioId) { this.usuarioId = usuarioId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
}