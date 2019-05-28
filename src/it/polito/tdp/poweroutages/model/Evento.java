package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{

	public enum Tipo{
		INIZIO_INTERRUZIONE,
		FINE_INTERRUZIONE,
		CANCELLA_PRESTITO
	}
	
	private Nerc donatore;
	private Nerc nerc;
	private Tipo tipo;
	private LocalDateTime data;
	
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;
	
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.data.compareTo(o.data);
	}

	public Evento(Nerc donatore, Nerc nerc, Tipo tipo, LocalDateTime data, LocalDateTime dataInizio,
			LocalDateTime dataFine) {
		super();
		this.donatore = donatore;
		this.nerc = nerc;
		this.tipo = tipo;
		this.data = data;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}

	public Tipo getTipo() {
		// TODO Auto-generated method stub
		return this.tipo;
	}

	public Nerc getNerc() {
		// TODO Auto-generated method stub
		return this.nerc;
	}

	public Nerc getDonatore() {
		return donatore;
	}

	public LocalDateTime getData() {
		return data;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}
	
}
