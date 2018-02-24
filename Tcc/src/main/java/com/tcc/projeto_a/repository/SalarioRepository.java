package com.tcc.projeto_a.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.projeto_a.model.QuartilSalario;
import com.tcc.projeto_a.model.Salario;

public interface SalarioRepository extends JpaRepository<Salario, Long>{

	@Query(value="SELECT id,valor FROM projeto_a.salario WHERE valor BETWEEN :#{#quartilSalario.de} and :#{#quartilSalario.ate}",nativeQuery=true)
	public List<Salario> porQuartil(@Param("quartilSalario") QuartilSalario quartilSalario);

	
	@Query(value="SELECT DISTINCT valor FROM projeto_a.salario ",nativeQuery=true)
	public List<Double> valorSalarios();
}
