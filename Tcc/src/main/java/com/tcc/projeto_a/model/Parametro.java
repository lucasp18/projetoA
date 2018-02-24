package com.tcc.projeto_a.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema="projeto_a",name = "parametro")
public class Parametro {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_esfera",referencedColumnName="id")
	private Esfera esfera;
	
	@ManyToOne
	@JoinColumn(name = "id_salario",referencedColumnName="id")
	private Salario salario;
		
	@ManyToOne
	@JoinColumn(name = "id_escolaridade",referencedColumnName="id")
	private Escolaridade escolaridade;
	
	@ManyToOne
	@JoinColumn(name = "id_disciplina",referencedColumnName="id")
	private Disciplina disciplina;
	
	@ManyToOne
	@JoinColumn(name = "id_area",referencedColumnName="id")
	private Area area;
	
	@ManyToOne
	@JoinColumn(name = "id_requisito_cargo",referencedColumnName="id")
	private RequisitoCargo requisitoCargo;

	@ManyToOne
	@JoinColumn(name = "id_cidade",referencedColumnName="id")
	private Cidade cidade;
		
	@OneToOne(fetch = FetchType.LAZY, mappedBy="parametro", optional = true)
	private ParametroBuscado parametroBuscado;
	
	public ParametroBuscado getParametroBuscado(){
		return this.parametroBuscado;
	}
	
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Parametro(){
		
	}
	
	public Parametro(Disciplina disciplina){
		this.disciplina = disciplina;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Esfera getEsfera() {
		return esfera;
	}

	public void setEsfera(Esfera esfera) {
		this.esfera = esfera;
	}

	public Salario getSalario() {
		return salario;
	}

	public void setSalario(Salario salario) {
		this.salario = salario;
	}

	public Escolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public RequisitoCargo getRequisitoCargo() {
		return requisitoCargo;
	}

	public void setRequisitoCargo(RequisitoCargo requisitoCargo) {
		this.requisitoCargo = requisitoCargo;
	}

	public void limparAtributos(){
		this.area = null;
		this.id = null;
		this.disciplina = null;
		this.requisitoCargo = null;
		this.escolaridade = null;
		this.salario = null;
		this.esfera = null;
	}
	
	public String getAtributoNaoNulo(){
		
		if(this.area!= null){
			return "id_area";			
		}else if(this.disciplina != null){
			return "id_disciplina";
		}else if(this.requisitoCargo != null){
			return "id_requisito_cargo";
		}else if(this.escolaridade != null){
			return "id_escolaridade";
		}else if(this.salario != null){
			return "id_salario";
		}else if(this.esfera != null){
			return "id_esfera";
		}
		return null;
	}
	
	public Long getValorNaoNulo(){
		if(this.area!= null){
			return this.area.getId();			
		}else if(this.disciplina != null){
			return this.disciplina.getId();
		}else if(this.requisitoCargo != null){
			return this.requisitoCargo.getId();
		}else if(this.escolaridade != null){
			return this.escolaridade.getId();
		}else if(this.salario != null){
			return this.salario.getId();
		}else if(this.esfera != null){
			return this.esfera.getId();
		}
		return null;
	}
}
