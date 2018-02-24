package com.tcc.projeto_a.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="projeto_a",name = "edital" )
public class Edital {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	
	@Column(name = "titulo")
	private String titulo;
	

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}	
	
	public Long getId(){
		return this.id;
	}
}
