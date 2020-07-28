package it.dst.azienda.service;

import java.util.ArrayList;
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
	public List<Servizio> findDisponibili() {
		List<Servizio> disponibili = new ArrayList<>();
		for (Servizio servizio : servizioRepo.findAll()) {
			if (servizio.getQta() > 0) {
				disponibili.add(servizio);
			}
		}
		return disponibili;
	}

}
