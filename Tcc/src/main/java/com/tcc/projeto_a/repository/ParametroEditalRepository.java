package com.tcc.projeto_a.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.projeto_a.model.Edital;
import com.tcc.projeto_a.model.ParametroBuscado;
import com.tcc.projeto_a.model.ParametroEdital;

public interface ParametroEditalRepository extends JpaRepository<ParametroEdital, Long> {

	@Query(value="SELECT id,id_edital,id_parametro FROM projeto_a.parametro_edital WHERE id_edital = ?1 ",nativeQuery=true)
	List<ParametroEdital> buscarEdital(@Param("idEdital") long idEdital );

	@Query(value="SELECT id,id_edital,id_parametro FROM projeto_a.parametro_edital WHERE id_parametro = ?1 ",nativeQuery=true)
	ParametroEdital buscarParametroEdital(@Param("idParametro") long idParametro );

	@Query(value="SELECT * FROM projeto_a.parametro_edital pe "
			+ "inner join projeto_a.parametro p on pe.id_parametro = p.id WHERE pe.id_edital = :#{#edital.id} and p.id_disciplina is not null and "
			+ "p.id_esfera is not null and p.id_salario is not null and p.id_escolaridade is not null and p.id_cidade is not null and "
			+ "p.id_area is not null and p.id_requisito_cargo is not null ",nativeQuery=true)
	List<ParametroEdital> buscarEdital(@Param("edital") Edital edital );
	
}
