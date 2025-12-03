package br.ufpr.sistemaavaliacao.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.*;


@Setter
@Getter
public class ProcessoAvaliativo {
    
    private int id;
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private String observacoes;

    private List<Formulario> formularios = new ArrayList<>();
    private List<Turma> turmas = new ArrayList<>();

    public String getStatus(){
        Date hoje = new Date(System.currentTimeMillis());
        if (hoje.before(dataInicio)) return "Agendado";
        if (hoje.after(dataFim)) return "Finalizado";
        return "Em Andamento";
    }

}
