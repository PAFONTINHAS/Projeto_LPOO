package br.ufpr.sistemaavaliacao.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Questao {

    // 🔹 Campos antigos que o DAO/Servlet esperam
    private int id;
    private int idFormulario;     // corresponde ao id_formulario no banco
    private String tipo;          // "aberta", "multipla", etc.

    // 🔹 Campos novos da model refatorada
    private String enunciado;
    private boolean isObrigatoria;

    // 🔹 Construtor vazio (necessário para JDBC / frameworks)
    public Questao() {
    }

    // 🔹 Construtor usado no novo modelo (se alguém já estiver usando)
    public Questao(String enunciado, boolean isObrigatoria) {
        this.enunciado = enunciado;
        this.isObrigatoria = isObrigatoria;
    }

    // 🔹 Compatibilidade com o código antigo que usa "descricao"
    public String getDescricao() {
        return this.enunciado;
    }

    public void setDescricao(String descricao) {
        this.enunciado = descricao;
    }

    // 🔹 Se quiser manter esse nome específico também
    public boolean getIsObrigatoria() {
        return this.isObrigatoria;
    }
}
