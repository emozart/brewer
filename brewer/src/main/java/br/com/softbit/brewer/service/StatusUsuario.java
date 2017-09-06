package br.com.softbit.brewer.service;

import br.com.softbit.brewer.repository.UsuariosRepository;

public enum StatusUsuario {
	
	ATIVAR {
		@Override
		public void executar(Long[] codigos, UsuariosRepository usuariosRepository) {
			usuariosRepository.findByCodigoIn(codigos).forEach(u -> u.setAtivo(true));			
		}
	},
	DESATIVAR {
		@Override
		public void executar(Long[] codigos, UsuariosRepository usuariosRepository) {
			usuariosRepository.findByCodigoIn(codigos).forEach(u -> u.setAtivo(false));			
		}
	};
	
	public abstract void executar(Long[] codigos, UsuariosRepository usuariosRepository);

}
