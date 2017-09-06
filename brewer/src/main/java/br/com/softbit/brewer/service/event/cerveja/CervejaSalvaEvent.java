package br.com.softbit.brewer.service.event.cerveja;

import br.com.softbit.brewer.model.Cerveja;

public class CervejaSalvaEvent {

	private Cerveja cerveja;

	public CervejaSalvaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	public Cerveja getCerveja() {
		return cerveja;
	}
	
	public boolean temFoto(){
		return !cerveja.getFoto().isEmpty();
	}
	
}
