CREATE DATABASE avaliaufpr;
USE avaliaufpr;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150),
    email VARCHAR(255),
    login VARCHAR(255),
    senha VARCHAR(255),
    perfil VARCHAR(100)  
);


CREATE TABLE administradores (
    usuario_id INT PRIMARY KEY,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE coordenadores (
    usuario_id INT PRIMARY KEY,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE alunos (
    usuario_id INT PRIMARY KEY,
    matricula VARCHAR(20),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE professores (
    usuario_id INT PRIMARY KEY,
    registro VARCHAR(255),
    departamento VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE processos_avaliativos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    data_inicio DATE,
    data_fim DATE
);


CREATE TABLE formularios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    processo_avaliativo_id INT,
    titulo VARCHAR(255),
    is_anonimo TINYINT(1),
    instrucoes TEXT,
    FOREIGN KEY (processo_avaliativo_id) REFERENCES processos_avaliativos(id)
);

CREATE TABLE questoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    formulario_id INT,
    enunciado TEXT,
    is_obrigatoria TINYINT(1),
    tipo VARCHAR(255),
    FOREIGN KEY (formulario_id) REFERENCES formularios(id)
);

CREATE TABLE questoes_multipla_escolha (
    questao_id INT PRIMARY KEY,
    permite_multipla_selecao TINYINT(1),
    FOREIGN KEY (questao_id) REFERENCES questoes(id)
);

CREATE TABLE alternativas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    questao_multipla_escolha_id INT,
    texto VARCHAR(255),
    peso INT,
    FOREIGN KEY (questao_multipla_escolha_id) REFERENCES questoes_multipla_escolha(questao_id)
);

CREATE TABLE avaliacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    aluno_usuario_id INT,
    formulario_id INT,
    data_submissao DATETIME,
    FOREIGN KEY (aluno_usuario_id) REFERENCES alunos(usuario_id),
    FOREIGN KEY (formulario_id) REFERENCES formularios(id)
);

CREATE TABLE respostas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    avaliacao_id INT,
    questao_id INT,
    FOREIGN KEY (avaliacao_id) REFERENCES avaliacoes(id),
    FOREIGN KEY (questao_id) REFERENCES questoes(id)
);

CREATE TABLE respostas_abertas (
    resposta_id INT PRIMARY KEY,
    texto_resposta TEXT,
    FOREIGN KEY (resposta_id) REFERENCES respostas(id)
);

CREATE TABLE respostas_multipla_escolha_alternativas (
    resposta_id INT,
    alternativa_id INT,
    PRIMARY KEY (resposta_id, alternativa_id),
    FOREIGN KEY (alternativa_id) REFERENCES alternativas(id)
);

CREATE TABLE unidades_curriculares (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    tipo VARCHAR(255)  -- extensão, disciplina, formativa
);

CREATE TABLE turmas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    unidade_curricular_id INT,
    ano_semestre VARCHAR(10),
    FOREIGN KEY (unidade_curricular_id) REFERENCES unidades_curriculares(id)
);

CREATE TABLE turmas_professores (
    turma_id INT,
    professor_usuario_id INT,
    PRIMARY KEY (turma_id, professor_usuario_id),
    FOREIGN KEY (turma_id) REFERENCES turmas(id),
    FOREIGN KEY (professor_usuario_id) REFERENCES professores(usuario_id)
);

CREATE TABLE turmas_alunos (
    turma_id INT,
    aluno_usuario_id INT,
    PRIMARY KEY (turma_id, aluno_usuario_id),
    FOREIGN KEY (turma_id) REFERENCES turmas(id),
    FOREIGN KEY (aluno_usuario_id) REFERENCES alunos(usuario_id)
);
