package br.ufpr.sistemaavaliacao.model;

public class Administrador {
    private int usuarioId;

    public Administrador() {}
    public Administrador(int usuarioId) { this.usuarioId = usuarioId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
}