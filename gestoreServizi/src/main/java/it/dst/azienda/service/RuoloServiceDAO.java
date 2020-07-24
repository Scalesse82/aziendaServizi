package it.dst.azienda.service;

import java.util.List;

import it.dst.azienda.model.Ruolo;

public interface RuoloServiceDAO {

	Ruolo add(Ruolo ruolo);

	List<Ruolo> findAll();

	void remove(Ruolo ruolo);

	Ruolo edit(Ruolo ruolo);

	Ruolo findById(Long Id);

	Ruolo findByRuolo(String ruolo);
}
