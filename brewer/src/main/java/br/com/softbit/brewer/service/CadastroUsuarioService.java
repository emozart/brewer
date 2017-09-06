package br.com.softbit.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import br.com.softbit.brewer.model.Usuario;
import br.com.softbit.brewer.repository.UsuariosRepository;
import br.com.softbit.brewer.service.exception.EmailJaCadastradoException;
import br.com.softbit.brewer.service.exception.SenhaObrigadtoriaNovoUsuarioException;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void salvar(Usuario usuario) {
		
		Optional<Usuario> emailJaExiste = usuariosRepository.findByEmail(usuario.getEmail());
		
		if(emailJaExiste.isPresent()) {
			throw new EmailJaCadastradoException("E-mail já cadastrado.");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigadtoriaNovoUsuarioException("Senha é obrigatória para novos usuários.");
		}
		
		if(usuario.isNovo()) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		
		usuariosRepository.save(usuario);			
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
		statusUsuario.executar(codigos, usuariosRepository);	
	}

}
