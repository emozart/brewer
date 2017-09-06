package br.com.softbit.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softbit.brewer.model.Cidade;
import br.com.softbit.brewer.repository.CidadesRepository;
import br.com.softbit.brewer.service.exception.CidadeJaCadastradaException;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadesRepository cidadesRepository;

	@Transactional
	public void salvar(Cidade cidade){
		
		Optional<Cidade> cidadeExistente = cidadesRepository.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		if(cidadeExistente.isPresent()) {
			throw new CidadeJaCadastradaException("Cidade já cadastrada.");
		}
		cidadesRepository.save(cidade);
	}
}
