package br.com.softbit.brewer.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.softbit.brewer.model.Cliente;
import br.com.softbit.brewer.repository.filter.ClienteFilter;

public interface ClientesRepositoryQueries {
	
	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable);

}
