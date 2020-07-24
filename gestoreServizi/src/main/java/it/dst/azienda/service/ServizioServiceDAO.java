package it.dst.azienda.service;

import java.util.List;

import it.dst.azienda.model.Servizio;
import it.dst.azienda.model.Utente;

public interface ServizioServiceDAO {

	Servizio add(Servizio servizio);

	List<Servizio> findAll();

	void remove(Servizio servizio);

	Servizio edit(Servizio servizio);

	Servizio findById(Long Id);

	void addUtente(Servizio servizio, Utente utente);

}
