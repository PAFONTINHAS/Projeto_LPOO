package br.ufpr.sistemaavaliacao.model;

import java.sql.Date;

import lombok.*;


@Setter
@Getter
public class ProcessoAvaliativo {
    
    private int id;
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private String observacoes;
    
}
