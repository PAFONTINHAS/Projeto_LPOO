package br.ufpr.sistemaavaliacao.model;

public class Professor {
    private int usuarioId;
    private String registro;
    private String departamento;

    public Professor() {}
    public Professor(int usuarioId, String registro, String departamento) {
        this.usuarioId = usuarioId;
        this.registro = registro;
        this.departamento = departamento;
    }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public String getRegistro() { return registro; }
    public void setRegistro(String registro) { this.registro = registro; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}