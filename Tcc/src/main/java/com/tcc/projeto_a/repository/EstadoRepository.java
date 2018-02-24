package com.tcc.projeto_a.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcc.projeto_a.model.Estado;


public interface EstadoRepository extends JpaRepository<Estado, Long> {

	@Query(value="SELECT DISTINCT estado.id,estado.nome,estado.sigla,estado.regiao FROM projeto_a.estado estado "
			+ "INNER JOIN projeto_a.cidade cidade ON cidade.id_estado = estado.id "
			+ "INNER JOIN projeto_a.parametro parametro ON parametro.id_cidade = cidade.id ORDER BY estado.nome",nativeQuery=true)
	public List<Estado> estadosParametro();

}
