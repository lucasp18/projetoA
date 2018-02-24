package com.tcc.projeto_a.service;

import java.util.List;

import com.tcc.projeto_a.model.Edital;

public class ObjAcumulado {
	private Edital edital;
	private String esferas;
	private List<String> disciplinas;
	private List<String> areas;
	private List<String> cidades;
	private List<String> escolaridades;
	private List<String> salarios;
	private List<String> requisitosCargos;
	public Edital getEdital() {
		return edital;
	}
	public void setEdital(Edital edital) {
		this.edital = edital;
	}
	public String getEsferas() {
		return esferas;
	}
	public void setEsferas(String esferas) {
		this.esferas = esferas;
	}
	public List<String> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<String> disciplinas) {
		this.disciplinas = disciplinas;
	}
	public List<String> getAreas() {
		return areas;
	}
	public void setAreas(List<String> areas) {
		this.areas = areas;
	}
	public List<String> getCidades() {
		return cidades;
	}
	public void setCidades(List<String> cidades) {
		this.cidades = cidades;
	}
	public List<String> getEscolaridades() {
		return escolaridades;
	}
	public void setEscolaridades(List<String> escolaridades) {
		this.escolaridades = escolaridades;
	}
	public List<String> getSalarios() {
		return salarios;
	}
	public void setSalarios(List<String> salarios) {
		this.salarios = salarios;
	}
	public List<String> getRequisitosCargos() {
		return requisitosCargos;
	}
	public void setRequisitosCargos(List<String> requisitosCargos) {
		this.requisitosCargos = requisitosCargos;
	}
	
	
	
}
