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
@Table(schema="projeto_a",name = "view_informacao_edital" )
public class ViewInformacaoEdital {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	
	@OneToOne
	@JoinColumn(name = "id_classificador_svm",referencedColumnName="id")
	private ClassificadorSvm classificadorSvm;
		
	@Column(name="nome_view_sim")	
	private String nomeViewSim;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClassificadorSvm getClassificadorSvm() {
		return classificadorSvm;
	}

	public void setClassificadorSvm(ClassificadorSvm classificadorSvm) {
		this.classificadorSvm = classificadorSvm;
	}

	public String getNomeViewSim() {
		return nomeViewSim;
	}

	public void setNomeViewSim(String nomeViewSim) {
		this.nomeViewSim = nomeViewSim;
	}

	public String getNomeViewNao() {
		return nomeViewNao;
	}

	public void setNomeViewNao(String nomeViewNao) {
		this.nomeViewNao = nomeViewNao;
	}

	@Column(name="nome_view_nao")
	private String nomeViewNao;
	
	




}
