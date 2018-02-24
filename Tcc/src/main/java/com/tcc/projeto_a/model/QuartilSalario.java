package com.tcc.projeto_a.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="projeto_a",name="quartil_salario")
public class QuartilSalario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="de")
	private double de;
	
	@Column(name="ate")
	private double ate;
	
	@Column(name="separador")
	private String separador;

	public QuartilSalario(long id, double de, double ate, String separador) {
		super();
		this.id = id;
		this.de = de;
		this.ate = ate;
		this.separador = separador;
	}
	
	public QuartilSalario() {		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getDe() {
		return de;
	}

	public void setDe(double de) {
		this.de = de;
	}

	public double getAte() {
		return ate;
	}

	public void setAte(double ate) {
		this.ate = ate;
	}

	public String getSeparador() {
		return separador;
	}

	public void setSeparador(String separador) {
		this.separador = separador;
	}
	
	

	
	
	
}
