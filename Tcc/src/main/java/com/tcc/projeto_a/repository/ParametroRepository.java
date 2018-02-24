package com.tcc.projeto_a.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.projeto_a.formulario.FormParametro;
import com.tcc.projeto_a.model.Parametro;
import com.tcc.projeto_a.model.ParametroEdital;

public interface ParametroRepository extends JpaRepository<Parametro,Long> {
		
	
	@Query(value="SELECT * FROM projeto_a.parametro "
			+ "WHERE id in ( :#{#parametrosEdital} )",nativeQuery=true)
	public Parametro buscarParametro(@Param("parametrosEdital") List<ParametroEdital> parametrosEdital);
	
	@Query(value="SELECT * FROM projeto_a.parametro "
			+ "WHERE "
			+ "id_esfera = :#{#formParametro.idEsfera} "
			+ " AND id_salario in ( :#{#idsSalarios} )"
			+ " AND id_escolaridade = :#{#formParametro.idEscolaridade}"
			+ " AND id_disciplina = :#{#formParametro.idDisciplina}"
			+ " AND id_cidade = :#{#formParametro.idCidade} "
			+ " AND id_area = :#{#formParametro.idArea}"
			+ " AND id_requisito_cargo = :#{#formParametro.idRequisitoCargo} ",nativeQuery=true)
	public Parametro buscarParametro(@Param("idsSalarios") List<Long> idsSalarios,@Param("formParametro") FormParametro formParametro);
	
	
	@Query(value="SELECT * FROM projeto_a.parametro "
			+ "WHERE "
			+ "id_esfera = :#{#formParametro.idEsfera} "
			+ " AND id_salario is null"
			+ " AND id_escolaridade is null"
			+ " AND id_disciplina is null"
			+ " AND id_cidade is null "
			+ " AND id_area is null"
			+ " AND id_requisito_cargo is null ",nativeQuery=true)
	public Parametro buscarParametroIdEsfera(@Param("formParametro") FormParametro formParametro);
	
	@Query(value="SELECT * FROM projeto_a.parametro "
			+ "WHERE "
			+ "id_esfera is null "
			+ " AND id_salario is null"
			+ " AND id_escolaridade = :#{#formParametro.idEscolaridade}"
			+ " AND id_disciplina is null"
			+ " AND id_cidade is null "
			+ " AND id_area is null"
			+ " AND id_requisito_cargo is null ",nativeQuery=true)
	public Parametro buscarParametroIdEscolaridade(@Param("formParametro") FormParametro formParametro);
	
	@Query(value="SELECT * FROM projeto_a.parametro "
			+ "WHERE "
			+ "id_esfera is null "
			+ " AND id_salario is null"
			+ " AND id_escolaridade is null"
			+ " AND id_disciplina = :#{#formParametro.idDisciplina}"
			+ " AND id_cidade is null "
			+ " AND id_area is null"
			+ " AND id_requisito_cargo is null ",nativeQuery=true)
	public Parametro buscarParametroIdDisciplina(@Param("formParametro") FormParametro formParametro);
	
	@Query(value="SELECT * FROM projeto_a.parametro "
			+ "WHERE "
			+ "id_esfera is null "
			+ " AND id_salario is null "
			+ " AND id_escolaridade is null"
			+ " AND id_disciplina  is null"
			+ " AND id_cidade = :#{#formParametro.idCidade} "
			+ " AND id_area  is null"
			+ " AND id_requisito_cargo is null ",nativeQuery=true)
	public Parametro buscarParametroIdCidade(@Param("formParametro") FormParametro formParametro);
	
	
	@Query(value="SELECT * FROM projeto_a.parametro "
			+ "WHERE "
			+ "id_esfera is null "
			+ " AND id_salario is null"
			+ " AND id_escolaridade is null"
			+ " AND id_disciplina is null"
			+ " AND id_cidade is null "
			+ " AND id_area = :#{#formParametro.getIdArea()}"
			+ " AND id_requisito_cargo is null ",nativeQuery=true)
	public Parametro buscarParametroIdArea(@Param("formParametro") FormParametro formParametro);
	

	@Query(value="SELECT * FROM projeto_a.parametro "
			+ "WHERE "
			+ "id_esfera is null "
			+ " AND id_salario is null"
			+ " AND id_escolaridade is null"
			+ " AND id_disciplina is null"
			+ " AND id_cidade is null "
			+ " AND id_area is null"
			+ " AND id_requisito_cargo = :#{#formParametro.idRequisitoCargo} ",nativeQuery=true)
	public Parametro buscarParametroIdRequisitoCargo(@Param("formParametro") FormParametro formParametro);
	
	
	@Query(value="SELECT * FROM projeto_a.parametro "
			+ "WHERE "
			+ "id_esfera is null "
			+ " AND id_salario in ( :#{#idsSalarios} )"
			+ " AND id_escolaridade is null"
			+ " AND id_disciplina is null"
			+ " AND id_cidade is null "
			+ " AND id_area is null"
			+ " AND id_requisito_cargo is null ",nativeQuery=true)
	public List<Parametro> buscarParametroIdSalarios(@Param("idsSalarios") List<Long> idsSalarios);
	
	
	@Query(value="SELECT unique count(id_escolaridade) FROM projeto_a.parametro where id_edital = idEdital "
			,nativeQuery=true)
	public List<Parametro> quantidadeEscolaridade(@Param("idEdital") long idEdital);

	@Query(value="select distinct id_escolaridade from projeto_a.parametro where id_escolaridade is not null",nativeQuery=true)
	public List<Integer> todosIdsEscolaridade();
	
	@Query(value="select distinct id_area from projeto_a.parametro where id_area is not null",nativeQuery=true)
	public List<Integer> todosIdsArea();
		
	@Query(value="select distinct id_disciplina from projeto_a.parametro where id_disciplina is not null",nativeQuery=true)
	public List<Integer> todosIdsDisciplina();
	
	@Query(value="select distinct id_salario from projeto_a.parametro where id_salario is not null",nativeQuery=true)
	public List<Integer> todosIdsSalario();
	
	@Query(value="select distinct id_requisito_cargo from projeto_a.parametro where id_requisito_cargo is not null",nativeQuery=true)
	public List<Integer> todosIdsRequisitoCargo();
	
	@Query(value="select distinct id_esfera from projeto_a.parametro where id_esfera is not null",nativeQuery=true)
	public List<Integer> todosIdsEsfera();
	
	@Query(value="select distinct id_cidade from projeto_a.parametro where id_cidade is not null",nativeQuery=true)
	public List<Integer> todosIdsCidade();
	
}
	