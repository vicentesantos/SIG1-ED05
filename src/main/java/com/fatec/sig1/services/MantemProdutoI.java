package com.fatec.sig1.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.sig1.model.Produto;
import com.fatec.sig1.ports.MantemProduto;
import com.fatec.sig1.ports.ProdutoRepository;

@Service
public class MantemProdutoI implements MantemProduto {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ProdutoRepository repository;

	public List<Produto> consultaTodos() {
		logger.info(">>>>>> servico consultaTodos chamado");
		return repository.findAll();
	}

	@Override
	public Optional<Produto> consultaPorNome(String nome) {
		logger.info(">>>>>> servico consultaPorNome chamado");
		return repository.findByNome(nome);
	}

	@Override
	public Optional<Produto> consultaPorId(Long id) {
		logger.info(">>>>>> servico consultaPorId chamado");
		return repository.findById(id);
	}

	@Override
	public Optional<Produto> save(Produto produto) {
		logger.info(">>>>>> servico save chamado");
			Optional<Produto> umProduto = consultaPorNome(produto.getNome());
			Object preco = null;
			if (umProduto.isEmpty() & preco != null) {
				logger.info(">>>>>> servico save - dados validos");
				produto.obtemDataAtual(new DateTime());
				produto.setPreco(0);
				return Optional.ofNullable(repository.save(produto));
			} else {
				return Optional.empty();
			}
	}

	@Override
	public void delete(Long id) {
		logger.info(">>>>>> servico delete por id chamado");
		repository.deleteById(id);
	}

	@Override
	public Optional<Produto> altera(Produto produto) {
		logger.info(">>>>>> 1.servico altera produto chamado");
		Optional<Produto> umProduto = consultaPorId(produto.getId());
		Object preco = null;
		//Endereco endereco = obtemEndereco(cliente.getCep());
		if (umProduto.isPresent() & preco != null) {
			Produto produtoModificado = new Produto();
			produtoModificado.setId(produto.getId());
			produtoModificado.obtemDataAtual(new DateTime());
			produtoModificado.setEspecificacoes(produto.getEspecificacoes());
			logger.info(">>>>>> 2. servico altera produto preco valido para o id => " + produtoModificado.getId());
			return Optional.ofNullable(repository.save(produtoModificado));
		} else {
			return Optional.empty();
		}
	}
}
