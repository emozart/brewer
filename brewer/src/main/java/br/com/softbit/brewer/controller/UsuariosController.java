package br.com.softbit.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.softbit.brewer.controller.page.PageWrapper;
import br.com.softbit.brewer.model.Usuario;
import br.com.softbit.brewer.repository.GruposRepository;
import br.com.softbit.brewer.repository.UsuariosRepository;
import br.com.softbit.brewer.repository.filter.UsuarioFilter;
import br.com.softbit.brewer.service.CadastroUsuarioService;
import br.com.softbit.brewer.service.StatusUsuario;
import br.com.softbit.brewer.service.exception.EmailJaCadastradoException;
import br.com.softbit.brewer.service.exception.SenhaObrigadtoriaNovoUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private GruposRepository gruposRepository;
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result, @PageableDefault(size=3) Pageable pageable,
			HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuario");
		mv.addObject("grupos", gruposRepository.findAll());
		//mv.addObject("pagina", usuariosRepository.findAll());
		
		PageWrapper<Usuario> pageWrapper = new PageWrapper<>(usuariosRepository.filtrar(usuarioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario){
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos", gruposRepository.findAll());
		return mv;
	}
	
	@PostMapping(value = "/novo")
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(usuario);
		}
		try {
			cadastroUsuarioService.salvar(usuario);		
		}catch(EmailJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		}catch(SenhaObrigadtoriaNovoUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!.");
		
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		cadastroUsuarioService.alterarStatus(codigos, statusUsuario);
	}
}
