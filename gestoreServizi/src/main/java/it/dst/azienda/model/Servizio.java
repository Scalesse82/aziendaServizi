package it.dst.azienda.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import it.dst.azienda.memento.Memento;

@Entity
public class Servizio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String descrizione;
	private Integer qta;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getQta() {
		return qta;
	}
	public void setQta(Integer qta) {
		this.qta = qta;
	}
	
	
	public Memento saveStateToMemento(){
	      return new Memento(this);
	   }

	   public void getStateFromMemento(Memento memento){
	      this.id = memento.getServizio().getId();
	      this.nome=memento.getServizio().getNome();
	      this.descrizione=memento.getServizio().getDescrizione();
	      this.qta=memento.getServizio().getQta();

	       
	   }
	
	
}
