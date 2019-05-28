package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Poweroutages {

	private int id;
	private Nerc nerc;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	
	public Poweroutages(int id, Nerc n, LocalDateTime inizio, LocalDateTime fine) {
		super();
		this.id = id;
		this.nerc = n;
		this.inizio = inizio;
		this.fine = fine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fine == null) ? 0 : fine.hashCode());
		result = prime * result + id;
		result = prime * result + ((inizio == null) ? 0 : inizio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poweroutages other = (Poweroutages) obj;
		if (fine == null) {
			if (other.fine != null)
				return false;
		} else if (!fine.equals(other.fine))
			return false;
		if (id != other.id)
			return false;
		if (inizio == null) {
			if (other.inizio != null)
				return false;
		} else if (!inizio.equals(other.inizio))
			return false;
		return true;
	}

	public Nerc getNerc() {
		// TODO Auto-generated method stub
		return this.nerc;
	}
	
	
	
}
