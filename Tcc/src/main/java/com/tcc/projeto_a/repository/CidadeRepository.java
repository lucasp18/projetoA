package com.tcc.projeto_a.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.projeto_a.model.Cidade;
import com.tcc.projeto_a.model.Estado;

public interface CidadeRepository extends JpaRepository<Cidade, Long>  {

	@Query(value="SELECT DISTINCT cidade.id,cidade.nome,cidade.id_estado FROM projeto_a.cidade cidade "
			+ "	inner join projeto_a.parametro parametro ON parametro.id_cidade = cidade.id WHERE cidade.id_estado = :#{#estado.id} ORDER BY cidade.nome ",nativeQuery=true)
	public List<Cidade> cidadesParametro(@Param("estado") Estado estado); 
}
