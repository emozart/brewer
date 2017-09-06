package br.com.softbit.brewer.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.softbit.brewer.model.Estilo;
import br.com.softbit.brewer.repository.filter.EstiloFilter;


public interface EstilosRepositoryQueries {
	
	public Page<Estilo> filtrar(EstiloFilter filtro, Pageable pageable);

}
