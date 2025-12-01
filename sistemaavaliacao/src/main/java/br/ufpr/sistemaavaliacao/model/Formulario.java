package br.ufpr.sistemaavaliacao.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Formulario {
    private int id; 
    private int processoAvaliativoId; 
    private String titulo; 
    private boolean isAnonimo; // RF11
    private String instrucoes; 
    private List<Questao> questoes; 

}