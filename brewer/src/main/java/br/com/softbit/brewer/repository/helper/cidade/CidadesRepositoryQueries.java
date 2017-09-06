package br.com.softbit.brewer.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.softbit.brewer.model.Cidade;
import br.com.softbit.brewer.repository.filter.CidadeFilter;

public interface CidadesRepositoryQueries {
	
	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable);

}
