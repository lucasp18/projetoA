package com.tcc.projeto_a.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcc.projeto_a.model.QuartilSalario;

@Repository("quartilSalarioR")
public interface QuartilSalarioRepository extends JpaRepository<QuartilSalario, Long> {

}
