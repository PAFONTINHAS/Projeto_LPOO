package br.ufpr.sistemaavaliacao.model;

public class UnidadeCurricular {

    private int id;
    private String codigo;
    private String nome;
    private String periodo;
    private int cargaHoraria;
    private String tipo;
    private String observacoes;
    private int cursoId;

    public UnidadeCurricular() {}

    // *** CONSTRUTOR NOVO DE COMPATIBILIDADE COM O CÓDIGO ANTIGO ***
    public UnidadeCurricular(int id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    // Construtor completo
    public UnidadeCurricular(int id, String codigo, String nome, String periodo,
                             int cargaHoraria, String tipo, String observacoes, int cursoId) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.periodo = periodo;
        this.cargaHoraria = cargaHoraria;
        this.tipo = tipo;
        this.observacoes = observacoes;
        this.cursoId = cursoId;
    }

    // Construtor sem ID (para inserir)
    public UnidadeCurricular(String codigo, String nome, String periodo,
                             int cargaHoraria, String tipo, String observacoes, int cursoId) {
        this.codigo = codigo;
        this.nome = nome;
        this.periodo = periodo;
        this.cargaHoraria = cargaHoraria;
        this.tipo = tipo;
        this.observacoes = observacoes;
        this.cursoId = cursoId;
    }

    // GETTERS E SETTERS GENERADOS

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
}
