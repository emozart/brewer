package br.com.softbit.brewer.repository.helper.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.softbit.brewer.model.Cerveja;
import br.com.softbit.brewer.repository.filter.CervejaFilter;

public interface CervejasRepositoryQueries {
	
	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);

}
