package br.com.softbit.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softbit.brewer.model.Estilo;
import br.com.softbit.brewer.repository.helper.estilo.EstilosRepositoryQueries;

@Repository
public interface EstilosRepository extends JpaRepository<Estilo, Long>, EstilosRepositoryQueries{
	
	public Optional<Estilo> findByNomeIgnoreCase(String estilo);

}
