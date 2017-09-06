package br.com.softbit.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softbit.brewer.model.Estilo;
import br.com.softbit.brewer.repository.EstilosRepository;
import br.com.softbit.brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {

	@Autowired
	private EstilosRepository estilos;
	
	@Transactional
	public Estilo salvar(Estilo estilo){
		
		Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());
		if(estiloOptional.isPresent()){
			throw new NomeEstiloJaCadastradoException("O Nome do estilo escolhido já está cadastrado.");
		}
		return estilos.saveAndFlush(estilo);
	}
}
