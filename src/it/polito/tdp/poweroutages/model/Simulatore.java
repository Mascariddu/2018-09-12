package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {

	//Modello lo stato del sistema
	private Graph<Nerc, DefaultWeightedEdge> grafo;
	private List<Poweroutages> poweroutages;
	private Map<Nerc, Set<Nerc>> prestiti; 
	
	//parametri
	private int k;
	
	//output
	private int CATASTROFI;
	private Map<Nerc, Long> bonus;
	
	//coda
	private PriorityQueue<Evento> queue;
	
	public void init(int k, List<Poweroutages> po, NercIdMap nerc, Graph<Nerc, DefaultWeightedEdge> grafo) {
		
		this.queue = new PriorityQueue<Evento>();
		this.bonus = new HashMap<Nerc, Long>();
		this.prestiti = new HashMap<Nerc, Set<Nerc>>();
		
		for(Nerc n : nerc.values()) {
			this.bonus.put(n,Long.valueOf(0));
			this.prestiti.put(n, new HashSet<Nerc>());
		}
		
		this.CATASTROFI = 0;
		this.k = k;
		this.poweroutages = poweroutages;
		this.grafo = grafo;
		
		//inserisci eventi iniziali
		for(Poweroutages pow: this.poweroutages) {
			Evento evento = new Evento(null, pow.getNerc(), Evento.Tipo.INIZIO_INTERRUZIONE, pow.getInizio(), pow.getInizio(), pow.getFine());
			queue.add(evento);
		}
		
	}
	
	public void run() {
		Evento evento;
		while((evento=queue.poll()) != null) {
			
			switch (evento.getTipo()) {
			case INIZIO_INTERRUZIONE:
				
				System.out.println("Inizio interruzione nerc:"+evento.getNerc());
				Nerc nerc = evento.getNerc();
				//cerco se c'è donatore o catastrofe
				Nerc donatore = null;
				
				if(this.prestiti.get(nerc).size() > 0) {
					//scelgo tra i miei debitori
					double min = Long.MAX_VALUE;
					for(Nerc n : this.prestiti.get(nerc)) {
						DefaultWeightedEdge edge = this.grafo.getEdge(nerc , n);
						if(this.grafo.getEdgeWeight(edge) < min) {
							if(!n.getPrestando()) {
								donatore = n;
								min = this.grafo.getEdgeWeight(edge);
							}
						}
					}
				}else {
					//prendo quello con peso arco <
					double min = Long.MAX_VALUE;
					for(Nerc n : Graphs.neighborListOf(this.grafo, nerc)) {
						DefaultWeightedEdge edge = this.grafo.getEdge(nerc , n);
						if(this.grafo.getEdgeWeight(edge) < min) {
							if(!n.getPrestando()) {
								donatore = n;
								min = this.grafo.getEdgeWeight(edge);
							}
						}
					}
				}
				
				if(donatore != null) {
					System.out.println("Trovato donatore:"+donatore);
					donatore.setPrestando(true);
					Evento fine = new Evento(donatore,evento.getNerc(),Evento.Tipo.FINE_INTERRUZIONE, evento.getDataFine(),evento.getDataInizio(),evento.getDataFine());
					queue.add(fine);
					this.prestiti.get(donatore).add(evento.getNerc());
					Evento cancella = new Evento(donatore,evento.getNerc(),Evento.Tipo.CANCELLA_PRESTITO,evento.getData().plusMonths(k),evento.getDataInizio(),evento.getDataFine());
					this.queue.add(cancella);
				}else {
					//catastrofe
					this.CATASTROFI++;
				}
				
				break;

			case FINE_INTERRUZIONE:
				
				System.out.println("Fine interruzione nerc:"+evento.getNerc());
				//assegnare bonus al donatore
				if(evento.getDonatore() != null) {
					this.bonus.put(evento.getDonatore(), bonus.get(evento.getDonatore())+Duration.between(evento.getDataInizio(), evento.getDataFine().to);
					evento.getDonatore().setPrestando(false);
				}
				break;
				
			case CANCELLA_PRESTITO:
				System.out.println("Cancellato prestito di:"+evento.getNerc());
				this.prestiti.remove(evento.getDonatore().remove(evento.getNerc()));
				
			}
		}
	}
}
