package it.dst.azienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dst.azienda.model.Servizio;
import it.dst.azienda.model.Utente;
import it.dst.azienda.repository.ServizioRepository;

@Service
public class ServizioServiceDAOImpl implements ServizioServiceDAO {

	@Autowired
	private ServizioRepository servizioRepo;

	@Override
	public Servizio add(Servizio servizio) {
		return servizioRepo.save(servizio);
	}

	@Override
	public List<Servizio> findAll() {
		return servizioRepo.findAll();
	}

	@Override
	public void remove(Servizio servizio) {
		servizioRepo.delete(servizio);

	}

	@Override
	public Servizio edit(Servizio servizio) {
		return servizioRepo.save(servizio);
	}

	@Override
	public Servizio findById(Long id) {
		return servizioRepo.findById(id).get();
	}

	@Override
	public void addUtente(Servizio servizio, Utente utente) {
		servizio.getListaUtenti().add(utente);
		servizioRepo.save(servizio);
	}

}
