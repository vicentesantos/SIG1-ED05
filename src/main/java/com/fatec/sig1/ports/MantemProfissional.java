package com.fatec.sig1.ports;

import java.util.List;
import java.util.Optional;
import com.fatec.sig1.model.Profissional;

public interface MantemProfissional {
	List<Profissional> consultaTodos();

	Optional<Profissional> consultaPorCnpj(String cnpj);

	Optional<Profissional> consultaPorId(Long id);

	Optional<Profissional> save(Profissional profissional);

	void delete(Long id);

	Optional<Profissional> altera(Profissional profissional);
}