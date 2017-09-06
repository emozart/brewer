package br.com.softbit.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softbit.brewer.model.Estado;

@Repository
public interface EstadosRepository extends JpaRepository<Estado, Long>{

}
