package br.com.softbit.brewer.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import br.com.softbit.brewer.model.Cliente;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente>{

	@Override
	public List<Class<?>> getValidationGroups(Cliente cliente) {
		
		List<Class<?>> grupos = new ArrayList<>();
		grupos.add(Cliente.class);
		
		if(isTipoPessoaSelecionada(cliente)) {
			grupos.add(cliente.getTipoPessoa().getGrupo());
		}
		
		return grupos;
	}
	
	private boolean isTipoPessoaSelecionada(Cliente cliente) {
		return cliente != null && cliente.getTipoPessoa() != null;
	}

}
