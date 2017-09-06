package br.com.softbit.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.softbit.brewer.controller.page.PageWrapper;
import br.com.softbit.brewer.model.Cidade;
import br.com.softbit.brewer.repository.CidadesRepository;
import br.com.softbit.brewer.repository.EstadosRepository;
import br.com.softbit.brewer.repository.filter.CidadeFilter;
import br.com.softbit.brewer.service.CadastroCidadeService;
import br.com.softbit.brewer.service.exception.CidadeJaCadastradaException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private EstadosRepository estadosRepository;
	
	@Autowired
	private CidadesRepository cidadesRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@RequestMapping("/nova")
	public ModelAndView nova(Cidade cidade){
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		mv.addObject("estados", estadosRepository.findAll());
		return mv;
	}
	
	@RequestMapping(value = "/nova", method = RequestMethod.POST)
	@CacheEvict(value="cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()")
	public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return nova(cidade);
		}
		
		try {
			cadastroCidadeService.salvar(cidade);			
		}catch(CidadeJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(cidade);
		}
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!.");
		return new ModelAndView("redirect:/cidades/nova");
	}
	
	@Cacheable(value = "cidades", key = "#codigoEstado")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(@RequestParam(name="estado", defaultValue="-1") Long codigoEstado){
		return cidadesRepository.findByEstadoCodigo(codigoEstado);
	}
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter, BindingResult result, @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");
		mv.addObject("estados", estadosRepository.findAll());
		
		PageWrapper<Cidade> pageWrapper = new PageWrapper<>(cidadesRepository.filtrar(cidadeFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}
}
