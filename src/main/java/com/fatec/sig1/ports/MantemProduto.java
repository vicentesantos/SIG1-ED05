package com.fatec.sig1.ports;

import java.util.List;
import java.util.Optional;
import com.fatec.sig1.model.Produto;

public interface MantemProduto {
	List<Produto> consultaTodos();

	Optional<Produto> consultaPorNome(String nome);

	Optional<Produto> consultaPorId(Long id);

	Optional<Produto> save(Produto produto);

	void delete(Long id);

	Optional<Produto> altera(Produto produto);
}