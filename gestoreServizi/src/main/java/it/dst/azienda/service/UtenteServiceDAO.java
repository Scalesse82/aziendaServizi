package it.dst.azienda.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

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

	boolean addServizio(Utente utente, Servizio servizio);
	
	

}
