package br.ufpr.sistemaavaliacao.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Curso {
    
    private int id;
    private String codigo;
    private String nome;
    private int coordenadorUsuarioId; // ID do usuário que é o coordenador (pode ser Professor ou Coordenador)
    private boolean isAtivo;
    private String campus;
    private String modalidade;
    private String turno;
    private String setor;

    public Curso() {}

    // Construtor completo (útil para listagem/consulta)
    public Curso(int id, String codigo, String nome, int coordenadorUsuarioId, boolean isAtivo, String campus, String modalidade, String turno, String setor) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.coordenadorUsuarioId = coordenadorUsuarioId;
        this.isAtivo = isAtivo;
        this.campus = campus;
        this.modalidade = modalidade;
        this.turno = turno;
        this.setor = setor;
    }
}