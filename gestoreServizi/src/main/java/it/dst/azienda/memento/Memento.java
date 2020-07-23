package it.dst.azienda.memento;

import it.dst.azienda.model.Servizio;

public class Memento {

	private Servizio servizio;

	
	
	public Memento(Servizio servizio) {
		this.servizio = servizio;
	}

	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}
	
	
	
}
