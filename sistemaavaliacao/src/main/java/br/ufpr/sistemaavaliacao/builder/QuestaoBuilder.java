package br.ufpr.sistemaavaliacao.builder;
import br.ufpr.sistemaavaliacao.model.Alternativa;
import br.ufpr.sistemaavaliacao.model.Questao;
import br.ufpr.sistemaavaliacao.model.QuestaoAberta;
import br.ufpr.sistemaavaliacao.model.QuestaoMultiplaEscolha;

import java.util.ArrayList;


public class QuestaoBuilder {

    private String enunciado;
    private boolean obrigatoria;
    private String tipo;
    private boolean permiteMultiplaSelecao;
    private ArrayList<Alternativa> alternativas = new ArrayList<>();
    
    public QuestaoBuilder comEnunciado(String enunciado){
        this.enunciado = enunciado;
        return this;
    }

    public QuestaoBuilder isObrigatoria(boolean obrigatoria){
        this.obrigatoria = obrigatoria;
        return this;
    }

    public QuestaoBuilder doTipo(String tipo){
        this.tipo = tipo;
        return this;
    }

    public QuestaoBuilder permiteMultiplaSelecao(boolean permite){
        this.permiteMultiplaSelecao = permite;
        return this;
    }

    public QuestaoBuilder comAlternativa(String texto, int peso){
        this.alternativas.add(new Alternativa(texto, peso));
        return this;
    }

    public  Questao build(){

        if("ABERTA".equals(this.tipo)){
            return new QuestaoAberta(this.enunciado, this.obrigatoria);
        } else{

            QuestaoMultiplaEscolha q = new QuestaoMultiplaEscolha(
                this.enunciado,
                this.obrigatoria, 
                this.permiteMultiplaSelecao
            );

            for (Alternativa alt: this.alternativas){
                q.adicionarAlternativa(alt);
            }

            return q;

        }
    }
}
