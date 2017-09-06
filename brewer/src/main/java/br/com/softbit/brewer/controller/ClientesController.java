package br.com.softbit.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import br.com.softbit.brewer.controller.page.PageWrapper;
import br.com.softbit.brewer.model.Cliente;
import br.com.softbit.brewer.model.TipoPessoa;
import br.com.softbit.brewer.repository.ClientesRepository;
import br.com.softbit.brewer.repository.EstadosRepository;
import br.com.softbit.brewer.repository.filter.ClienteFilter;
import br.com.softbit.brewer.service.CadastroClienteService;
import br.com.softbit.brewer.service.exception.CpfCnpjClienteJaCadastradoException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private ClientesRepository clientesRepository;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@Autowired
	private EstadosRepository estadosRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente){
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados",estadosRepository.findAll());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(cliente);
		}
		
		try {
			cadastroClienteService.salvar(cliente);
		}catch(CpfCnpjClienteJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		
		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		
		return new ModelAndView("redirect:/clientes/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter, BindingResult result, @PageableDefault(size=2) Pageable pageable,
			HttpServletRequest httpServletRequest){
		
		ModelAndView mv = new ModelAndView("cliente/PesquisaClientes");
		
		PageWrapper<Cliente> pageWrapper = new PageWrapper<>(clientesRepository.filtrar(clienteFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}
	
	@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Cliente> pesquisar(String nome){
		validarTamanhoNome(nome);
		return clientesRepository.findByNomeStartingWithIgnoreCase(nome);
	}
	
	private void validarTamanhoNome(String nome) {
		if(StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e){
		return ResponseEntity.badRequest().build();
	}

}