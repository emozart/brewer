package br.com.softbit.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.softbit.brewer.model.Usuario;
import br.com.softbit.brewer.repository.filter.UsuarioFilter;

public interface UsuariosRepositoryQueries {

	public Optional<Usuario> porEmailEAtivo(String email);
	
	public List<String> Permissoes(Usuario usuario);

	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);
}
