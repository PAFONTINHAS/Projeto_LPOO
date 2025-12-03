/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.sistemaavaliacao.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@ToString
/**
 *
 * @author peter
 */
public class RespostaMultiplaEscolha extends Resposta{
    
    final private List<Alternativa> alternativas;
    
    public RespostaMultiplaEscolha(Questao questao){
        super(questao);
        this.alternativas = new ArrayList<>();
    }
    
    public void adicionarAlternativa(Alternativa alternativa){
        this.alternativas.add(alternativa);
    }

    public void adicionarAlternativasSelecionadas(List<Alternativa> alternativas){
        this.alternativas.clear();
        this.alternativas.addAll(alternativas);
    }
}
