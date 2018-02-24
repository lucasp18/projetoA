package com.tcc.projeto_a.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema="projeto_a",name = "parametro_buscado")	

public class ParametroBuscado {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	
	@OneToOne
	@JoinColumn(name = "id_parametro",referencedColumnName="id")
	private Parametro parametro;
	
	@OneToOne
	@JoinColumn(name = "id_classificador_svm",referencedColumnName="id")
	private ClassificadorSvm classificadorSvm;
	
	@Column(name = "bom_classificador",columnDefinition="BIT")
	private Boolean bomClassificador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public ClassificadorSvm getClassificadorSvm() {
		return classificadorSvm;
	}

	public void setClassificadorSvm(ClassificadorSvm classificadorSvm) {
		this.classificadorSvm = classificadorSvm;
	}

	public Boolean getBomClassificador() {
		return bomClassificador;
	}

	public void setBomClassificador(Boolean bomClassificador) {
		this.bomClassificador = bomClassificador;
	}
	
	
		
}
