package com.tcc.projeto_a.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.projeto_a.model.Edital;

public interface EditalRepository extends JpaRepository<Edital, Long> {

	@Query(value="SELECT * FROM projeto_a.edital WHERE id in ( :#{#idsEditais} )" ,nativeQuery=true)
	public List<Edital> buscarEditais(@Param("idsEditais") List<Long> idsEditais);
}
