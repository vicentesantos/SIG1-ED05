package com.fatec.sig1.ports;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.sig1.model.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
	Optional<Profissional> findByCnpj(String cnpj);

	List<Profissional> findAllByNomeIgnoreCaseContaining(String nome);
}
