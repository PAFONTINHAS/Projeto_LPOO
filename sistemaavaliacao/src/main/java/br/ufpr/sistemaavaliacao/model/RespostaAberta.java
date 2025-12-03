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
@Getter
@Setter
public class RespostaAberta extends Resposta{
    
    private String textoResposta;
    public RespostaAberta(Questao questao, String textoResposta){
        super(questao);
        this.textoResposta = textoResposta;
    }

}
