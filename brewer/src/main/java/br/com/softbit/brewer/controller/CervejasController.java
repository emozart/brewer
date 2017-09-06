package br.com.softbit.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.softbit.brewer.controller.page.PageWrapper;
import br.com.softbit.brewer.model.Cerveja;
import br.com.softbit.brewer.model.Origem;
import br.com.softbit.brewer.model.Sabor;
import br.com.softbit.brewer.repository.CervejasRepository;
import br.com.softbit.brewer.repository.EstilosRepository;
import br.com.softbit.brewer.repository.filter.CervejaFilter;
import br.com.softbit.brewer.service.CadastroCervejaService;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {
	
	@Autowired
	private EstilosRepository estilos;
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@Autowired
	private CervejasRepository cervejasRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja){
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		
		mv.addObject("Sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("Origens", Origem.values());
		
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(cerveja);
		}
		
		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso!.");
		return new ModelAndView("redirect:/cervejas/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size=2) Pageable pageable,
			HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");
		mv.addObject("Sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("Origens", Origem.values());
		
		PageWrapper<Cerveja> pageWrapper = new PageWrapper<>(cervejasRepository.filtrar(cervejaFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", pageWrapper);
		return mv;
	}

}
