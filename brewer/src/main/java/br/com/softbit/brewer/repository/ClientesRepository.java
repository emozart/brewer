package br.com.softbit.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softbit.brewer.model.Cliente;
import br.com.softbit.brewer.repository.helper.cliente.ClientesRepositoryQueries;

public interface ClientesRepository extends JpaRepository<Cliente, Long>, ClientesRepositoryQueries{

	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

	public List<Cliente> findByNomeStartingWithIgnoreCase(String nome);

}
