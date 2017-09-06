package br.com.softbit.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softbit.brewer.model.Cidade;
import br.com.softbit.brewer.model.Estado;
import br.com.softbit.brewer.repository.helper.cidade.CidadesRepositoryQueries;

public interface CidadesRepository extends JpaRepository<Cidade, Long>, CidadesRepositoryQueries{

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);

	public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);
	
}
