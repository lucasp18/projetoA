package com.tcc.projeto_a.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.projeto_a.model.ClassificadorSvm;
import com.tcc.projeto_a.model.Edital;
import com.tcc.projeto_a.model.Parametro;
import com.tcc.projeto_a.model.ParametroBuscado;

public interface ParametroBuscadoRepository extends JpaRepository<ParametroBuscado, Long> {

	
	@Query(value="SELECT * FROM WHERE id_parametro IN(parametros.id) ",nativeQuery=true)
	List<ParametroBuscado> buscarClassificadores(@Param("parametros") List<Parametro> parametros);
	
	@Query(value="SELECT * FROM projeto_a.parametro_buscado p_b WHERE id_classificador_svm = :#{#classificadorSvm.id} ",nativeQuery=true)
	ParametroBuscado buscarClassificadorId(@Param("classificadorSvm") ClassificadorSvm classificadorSvm);
			
	
}
