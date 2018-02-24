package com.tcc.projeto_a.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema="projeto_a",name = "parametro_edital" )
public class ParametroEdital {
	
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_parametro",referencedColumnName="id")
	private Parametro idParametro;
	
	@ManyToOne
	@JoinColumn(name = "id_edital",referencedColumnName="id")
	private Edital idEdital;
	
	public Parametro getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Parametro idParametro) {
		this.idParametro = idParametro;
	}

	public Edital getIdEdital() {
		return idEdital;
	}

	public void setIdEdital(Edital idEdital) {
		this.idEdital = idEdital;
	}
		
	
	
	
}
