package it.dst.azienda.memento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {

	private List<Memento> mementoList = new ArrayList<Memento>();

	public void add(Memento memento) {
		mementoList.add(memento);
	}

	public List<Memento> getMementoList() {
		return mementoList;
	}

}
