package it.dst.azienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dst.azienda.model.Ruolo;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {

	Ruolo findByRuolo(String ruolo);
}
