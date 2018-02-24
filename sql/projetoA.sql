create database tcc;

create schema projeto_a;

CREATE TABLE projeto_a.classificador_svm (
  id serial NOT NULL,
  byte ByteA NULL,
  precisao DOUBLE PRECISION NULL,
  PRIMARY KEY(id)
);

CREATE TABLE projeto_a.disciplina (
  id serial NOT NULL,
  nome VARCHAR(255) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE projeto_a.edital (
  id serial NOT NULL,
  titulo VARCHAR(500) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE projeto_a.escolaridade (
  id serial NOT NULL,
  nome VARCHAR(15) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE projeto_a.esfera (
  id serial NOT NULL,
  nome VARCHAR(15) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE projeto_a.estado (
  id serial NOT NULL,
  nome VARCHAR(255) NOT NULL,
  sigla VARCHAR(10) NOT NULL,
  regiao VARCHAR(50) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE projeto_a.salario (
  id serial NOT NULL,
  valor NUMERIC(6,2) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE projeto_a.cidade (
  id serial NOT NULL,
  id_estado INTEGER NOT NULL,
  nome VARCHAR(255) NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(id_estado) REFERENCES projeto_a.estado(id)
);

CREATE TABLE projeto_a.parametro (
  id serial NOT NULL,
  id_esfera INTEGER NOT NULL,
  id_salario INTEGER NOT NULL,
  id_escolaridade INTEGER NOT NULL,
  id_disciplina INTEGER NOT NULL,
  id_cidade INTEGER NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(id_cidade) REFERENCES projeto_a.cidade(id),
  FOREIGN KEY(id_disciplina) REFERENCES projeto_a.disciplina(id),
  FOREIGN KEY(id_escolaridade) REFERENCES projeto_a.escolaridade(id),
  FOREIGN KEY(id_salario) REFERENCES projeto_a.salario(id),
  FOREIGN KEY(id_esfera) REFERENCES projeto_a.esfera(id)
);

CREATE TABLE projeto_a.parametro_buscado (
  id_parametro INTEGER NOT NULL,
  id_classificador_svm INTEGER NOT NULL,
  bom_classificador BIT NULL,
  PRIMARY KEY(id_parametro),
  FOREIGN KEY(id_parametro) REFERENCES projeto_a.parametro(id),
  FOREIGN KEY(id_classificador_svm) REFERENCES projeto_a.classificador_svm(id)    
);

CREATE TABLE projeto_a.parametro_edital (
  id_parametro INTEGER NOT NULL,
  id_edital INTEGER NOT NULL,
  PRIMARY KEY(id_parametro),
  FOREIGN KEY(id_parametro) REFERENCES projeto_a.parametro(id),
  FOREIGN KEY(id_edital) REFERENCES projeto_a.edital(id)
);


CREATE TABLE projeto_a.view_informacao_edital (
  id serial NOT NULL,
  id_classificador_svm INTEGER NOT NULL,
  nome_view_sim VARCHAR(255) NULL,
  nome_view_nao VARCHAR(255) NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(id_classificador_svm) REFERENCES projeto_a.classificador_svm(id)  
);

CREATE TABLE projeto_a.palavra_esfera (
	id serial NOT NULL,
	nome VARCHAR(50) NULL,
	ordem INTEGER NULL,
	id_esfera INTEGER, 
	PRIMARY KEY(id),
	FOREIGN KEY(id_esfera) REFERENCES projeto_a.esfera(id)  	
);

CREATE TABLE projeto_a.palavra_escolaridade (
  id serial NOT NULL,
  id_escolaridade INTEGER,
  nome VARCHAR(50) NULL,
  ordem INTEGER NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(id_escolaridade) REFERENCES projeto_a.escolaridade(id)  	  
);

CREATE TABLE projeto_a.palavra_salario (
  id serial NOT NULL,  
  nome VARCHAR(50) NULL,
  ordem INTEGER NULL,
  PRIMARY KEY(id)    	  
);
