package com.fatec.sig1.services;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fatec.sig1.model.Profissional;
import com.fatec.sig1.model.Endereco;
import com.fatec.sig1.ports.ProfissionalRepository;
import com.fatec.sig1.ports.MantemProfissional;

@Service
public class MantemProfissionalI implements MantemProfissional {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ProfissionalRepository repository;

	public List<Profissional> consultaTodos() {
		logger.info(">>>>>> servico consultaTodos chamado");
		return repository.findAll();
	}

	@Override
	public Optional<Profissional> consultaPorCnpj(String cnpj) {
		logger.info(">>>>>> servico consultaPorCnpj chamado");
		return repository.findByCnpj(cnpj);
	}

	@Override
	public Optional<Profissional> consultaPorId(Long id) {
		logger.info(">>>>>> servico consultaPorId chamado");
		return repository.findById(id);
	}

	@Override
	public Optional<Profissional> save(Profissional profissional) {
		logger.info(">>>>>> servico save chamado ");
		Optional<Profissional> umProfissional = consultaPorCnpj(profissional.getCnpj());
		Endereco endereco = obtemEndereco(profissional.getCep());
		if (umProfissional.isEmpty() & endereco != null) {
			logger.info(">>>>>> servico save - dados validos");
			profissional.obtemDataAtual(new DateTime());
			profissional.setEndereco(endereco.getLogradouro());
			return Optional.ofNullable(repository.save(profissional));
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
	public Optional<Profissional> altera(Profissional profissional) {
		logger.info(">>>>>> 1.servico altera profissional chamado");
		Optional<Profissional> umProfissional = consultaPorId(profissional.getId());
		Endereco endereco = obtemEndereco(profissional.getCep());
		if (umProfissional.isPresent() & endereco != null) {
			Profissional profissionalModificado = new Profissional(profissional.getNome(), profissional.getDataNascimento(), profissional.getSexo(),
					profissional.getCnpj(), profissional.getCep(), profissional.getComplemento());
			profissionalModificado.setId(profissional.getId());
			profissionalModificado.obtemDataAtual(new DateTime());
			profissionalModificado.setEndereco(endereco.getLogradouro());
			logger.info(">>>>>> 2. servico altera profissional cep valido para o id => " + profissionalModificado.getId());
			return Optional.ofNullable(repository.save(profissionalModificado));
		} else {
			return Optional.empty();
		}
	}

	public Endereco obtemEndereco(String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		logger.info(">>>>>> servico consultaCep - " + cep);
		ResponseEntity<Endereco> resposta = null;
		try {
			resposta = template.getForEntity(url, Endereco.class, cep);
			return resposta.getBody();
		} catch (ResourceAccessException e) {
			logger.info(">>>>>> consulta CEP erro nao esperado ");
			return null;
		} catch (HttpClientErrorException e) {
			logger.info(">>>>>> consulta CEP inválido erro HttpClientErrorException =>" + e.getMessage());
			return null;
		}
	}
}