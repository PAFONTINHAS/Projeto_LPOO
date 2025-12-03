/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.sistemaavaliacao.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author peter
 */
@Setter
@Getter
public abstract class Resposta {
    
    protected Questao questao;
    
    
    public Resposta(Questao questao){
        this.questao = questao;
    }

}
