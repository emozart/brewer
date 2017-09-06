package br.com.softbit.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softbit.brewer.model.Cerveja;
import br.com.softbit.brewer.repository.CervejasRepository;
import br.com.softbit.brewer.service.event.cerveja.CervejaSalvaEvent;

@Service
public class CadastroCervejaService {

	@Autowired
	private CervejasRepository cervejasRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public void salvar(Cerveja cerveja){
		cervejasRepository.save(cerveja);
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}
}
