package it.dst.azienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dst.azienda.model.Ruolo;
import it.dst.azienda.repository.RuoloRepository;

@Service
public class RuoloServiceDAOImpl implements RuoloServiceDAO {

	@Autowired
	private RuoloRepository ruoloRepo;

	@Override
	public Ruolo add(Ruolo ruolo) {
		return ruoloRepo.save(ruolo);
	}

	@Override
	public List<Ruolo> findAll() {
		return ruoloRepo.findAll();
	}

	@Override
	public void remove(Ruolo ruolo) {
		ruoloRepo.delete(ruolo);

	}

	@Override
	public Ruolo edit(Ruolo ruolo) {
		return ruoloRepo.save(ruolo);
	}

	@Override
	public Ruolo findById(Long id) {
		return ruoloRepo.findById(id).get();
	}

	@Override
	public Ruolo findByRuolo(String ruolo) {
		return ruoloRepo.findByRuolo(ruolo);
	}

}
