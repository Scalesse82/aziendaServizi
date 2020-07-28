package it.dst.azienda.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.dst.azienda.configuration.JwtUserFactory;
import it.dst.azienda.model.Ruolo;
import it.dst.azienda.model.Servizio;
import it.dst.azienda.model.Utente;
import it.dst.azienda.repository.RuoloRepository;
import it.dst.azienda.repository.ServizioRepository;
import it.dst.azienda.repository.UtenteRepository;

@Service
public class UtenteServiceDAOImpl implements UtenteServiceDAO {
	@Autowired
	private UtenteRepository utenteRepo;
	@Autowired
	private RuoloRepository ruoloRepo;
	@Autowired
	private ServizioRepository servizioRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	public Utente add(Utente utente) {
		utente.setPassword(bCryptPasswordEncoder.encode(utente.getPassword()));
		utente.setActive(true);
		Ruolo ruolo = ruoloRepo.findByRuolo("UTENTE");
		utente.setRuolo(new HashSet<Ruolo>(Arrays.asList(ruolo)));
		return utenteRepo.save(utente);
	}

	@Override
	public List<Utente> findAll() {
		return utenteRepo.findAll();
	}

	@Override
	public void remove(Utente utente) {
		utenteRepo.delete(utente);

	}

	@Override
	public Utente edit(Utente utente) {
		return utenteRepo.save(utente);
	}

	@Override
	public Utente findById(Long id) {
		return utenteRepo.findById(id).get();
	}

	@Override
	public Utente findByUsername(String username) {
		return utenteRepo.findByUsername(username);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return utenteRepo.findByUsernameAndPassword(username, password);
	}

	@Override
	public boolean addServizio(Utente u, Servizio servizio) {
		Utente utente = utenteRepo.findByUsername(u.getUsername());
		if(utente.getListaServizi().size() < 3) {
			servizio.setQta(servizio.getQta() -1);
			servizioRepo.save(servizio);
		utente.getListaServizi().add(servizio);
		utenteRepo.save(utente);
		return true;
	}   return false;
	}
	

	

}
