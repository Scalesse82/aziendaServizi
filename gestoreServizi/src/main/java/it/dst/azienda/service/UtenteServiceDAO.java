package it.dst.azienda.service;

import java.util.List;

import it.dst.azienda.model.Servizio;
import it.dst.azienda.model.Utente;

public interface UtenteServiceDAO {

	Utente add(Utente utente);

	List<Utente> findAll();

	void remove(Utente utente);

	Utente edit(Utente utente);

	Utente findById(Long Id);

	Utente findByUsername(String username);

	Utente findByUsernameAndPassword(String username, String password);

	void addServizio(Utente utente, Servizio servizio);

}
