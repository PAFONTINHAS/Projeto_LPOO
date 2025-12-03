package br.ufpr.sistemaavaliacao.dto;

public class AvaliacaoPendenteDTO {
    private int idFormulario;
    private int idTurma;
    private String tituloFormulario;
    private String nomeDisciplina;
    private String nomeProcesso;

    public AvaliacaoPendenteDTO(int idFormulario, int idTurma, String tituloFormulario, String nomeDisciplina, String nomeProcesso) {
        this.idFormulario = idFormulario;
        this.idTurma = idTurma;
        this.tituloFormulario = tituloFormulario;
        this.nomeDisciplina = nomeDisciplina;
        this.nomeProcesso = nomeProcesso;
    }

    // Getters
    public int getIdFormulario() { return idFormulario; }
    public int getIdTurma() { return idTurma; }
    public String getTituloFormulario() { return tituloFormulario; }
    public String getNomeDisciplina() { return nomeDisciplina; }
    public String getNomeProcesso() { return nomeProcesso; }
}