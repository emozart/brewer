package br.com.softbit.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softbit.brewer.model.Cerveja;
import br.com.softbit.brewer.repository.helper.cerveja.CervejasRepositoryQueries;

@Repository
public interface CervejasRepository extends JpaRepository<Cerveja, Long>, CervejasRepositoryQueries{

	Optional<Cerveja> findBySku(String sku);
}
