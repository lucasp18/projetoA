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
@Table(schema="projeto_a", name = "classificador_svm" )
public class ClassificadorSvm {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
		
	public ClassificadorSvm(Long id, byte[] byteClassificador, double precisao, double kappa, ViewInformacaoEdital viewInformacaoEdital) {
		super();
		this.id = id;
		this.byteClassificador = byteClassificador;
		this.precisao = precisao;
		this.viewInformacaoEdital = viewInformacaoEdital;
		this.kappa = kappa;
	}

	public ClassificadorSvm() {
	}
	
	@Column(name="byte")	
	private byte[] byteClassificador;
	
	@Column(name="precisao")
	private double precisao;	
	
	@Column(name="kappa")
	private double kappa;	
		
	public double getKappa() {
		return kappa;
	}

	public void setKappa(double kappa) {
		this.kappa = kappa;
	}

	@OneToOne
	@JoinColumn(name = "id",referencedColumnName="id_classificador_svm")
	private ViewInformacaoEdital viewInformacaoEdital;
	
	
	public Edital getViewEditalSim(){
		this.viewInformacaoEdital.getNomeViewSim();
		return null ;
	}
	
	public Edital getViewEditalNao(){
		this.viewInformacaoEdital.getNomeViewNao();
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getByteClassificador() {
		return byteClassificador;
	}

	public void setByteClassificador(byte[] byteA) {
		this.byteClassificador = byteA;
	}

	public double getPrecisao() {
		return precisao;
	}

	public void setPrecisao(double precisao) {
		this.precisao = precisao;
	}

	public ViewInformacaoEdital getViewInformacaoEdital() {
		return viewInformacaoEdital;
	}

	public void setViewInformacaoEdital(ViewInformacaoEdital viewInformacaoEdital) {
		this.viewInformacaoEdital = viewInformacaoEdital;
	}
	
	
}
