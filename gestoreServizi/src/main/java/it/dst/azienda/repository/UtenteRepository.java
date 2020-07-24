package it.dst.azienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dst.azienda.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

	public Utente findByUsername(String username);

	public Utente findByUsernameAndPassword(String username, String password);
}
