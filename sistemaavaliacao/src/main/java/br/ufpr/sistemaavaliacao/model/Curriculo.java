package br.ufpr.sistemaavaliacao.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Curriculo {
    
    private int id;
    private int cursoId; // FK para a tabela Cursos
    private String cursoNome; // Nome do curso vinculado (para exibição)
    private String versao;
    private int anoImplantacao;
    private String portariaAprovacao;
    private int chObrigatoria;
    private int chOptativa;
    private int chAtividadesFormativas;
    private int chExtensao;
    private boolean tccObrigatorio;
    private String observacoes;

    public Curriculo() {}
    
    // Método para calcular a CH total, conforme a tela do HTML
    public int getChTotal() {
        return chObrigatoria + chOptativa + chAtividadesFormativas + chExtensao;
    }
}