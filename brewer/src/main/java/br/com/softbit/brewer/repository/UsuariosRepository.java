package br.com.softbit.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.softbit.brewer.model.Usuario;
import br.com.softbit.brewer.repository.helper.usuario.UsuariosRepositoryQueries;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long>, UsuariosRepositoryQueries{
	
	public Optional<Usuario> findByEmail(String email);

	public List<Usuario> findByCodigoIn(Long[] codigos);

}
