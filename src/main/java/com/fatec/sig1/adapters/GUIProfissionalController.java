package com.fatec.sig1.adapters;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.sig1.model.Profissional;
import com.fatec.sig1.ports.MantemProfissional;

@Controller
@RequestMapping(path = "/sig")
public class GUIProfissionalController {
	Logger logger = LogManager.getLogger(GUIProfissionalController.class);
	@Autowired
	MantemProfissional servico;

	@GetMapping("/profissionais")
	public ModelAndView retornaFormDeConsultaTodosProfissionais() {
		ModelAndView modelAndView = new ModelAndView("consultarProfissional");
		modelAndView.addObject("profissionais", servico.consultaTodos());
		return modelAndView;
	}

	@GetMapping("/profissional")
	public ModelAndView retornaFormDeCadastroDe(Profissional profissional) {
		ModelAndView mv = new ModelAndView("cadastrarProfissional");
		mv.addObject("profissional", profissional);
		return mv;
	}

	@GetMapping("/profissionais/{cnpj}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarProfissional(@PathVariable("cnpj") String cnpj) {
		ModelAndView modelAndView = new ModelAndView("atualizarProfissional");
		modelAndView.addObject("profissional", servico.consultaPorCnpj(cnpj).get()); // retorna um objeto do tipo profissional
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/profissionais/id/{id}")
	public ModelAndView excluirNoFormDeConsultaProfissional(@PathVariable("id") Long id) {
		servico.delete(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarProfissional");
		modelAndView.addObject("profissionais", servico.consultaTodos());
		return modelAndView;
	}

	@PostMapping("/profissionais")
	public ModelAndView save(@Valid Profissional profissional, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarProfissional");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarProfissional");
		} else {
			if (servico.save(profissional).isPresent()) {
				logger.info(">>>>>> controller chamou adastrar e consulta todos");
				modelAndView.addObject("profissionais", servico.consultaTodos());
			} else {
				logger.info(">>>>>> controller cadastrar com dados invalidos");
				modelAndView.setViewName("cadastrarProfissional");
				modelAndView.addObject("message", "Dados invalidos");
			}
		}
		return modelAndView;
	}

	@PostMapping("/profissionais/id/{id}")
	public ModelAndView atualizaProfissional(@PathVariable("id") Long id, @Valid Profissional profissional, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarProfissional");
		logger.info(">>>>>> servico para atualizacao de dados chamado para o id => " + id);
		if (result.hasErrors()) {
			logger.info(">>>>>> servico para atualizacao de dados com erro => " + result.getFieldError().toString());
			profissional.setId(id);
			return new ModelAndView("atualizarProfissional");
		} else {
			servico.altera(profissional);
			modelAndView.addObject("profissional", servico.consultaTodos());
		}
		return modelAndView;
	}
}