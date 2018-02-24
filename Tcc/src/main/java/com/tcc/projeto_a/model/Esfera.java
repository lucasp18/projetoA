package com.tcc.projeto_a.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(schema="projeto_a",name = "esfera" )
public class Esfera {
	
	private @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@Column(name = "nome")
	private String nome;
	public Esfera(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public Esfera() {		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
		
}
