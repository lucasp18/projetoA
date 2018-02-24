package com.tcc.projeto_a.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcc.projeto_a.model.Escolaridade;


public interface Escolaridade2Repository extends JpaRepository<Escolaridade, Long>{
	
	/*
	@Query("SELECT * FROM projeto_a.escolaridade escolaridade where escolaridade.nome = :nome")
	Escolaridade2 buscaPorNome(String nome);
	@Query("SELECT * FROM projeto_a.escolaridade")
	List<Escolaridade2> buscarTodosNomes();
	*/
}
