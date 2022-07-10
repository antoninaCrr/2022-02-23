package it.polito.tdp.yelp.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<Review, DefaultWeightedEdge> grafo;
	private Map<String,Review> revMap;
	

	public Model() {
		super();
		this.dao = new YelpDao();
	}
	
	public List<String> getAllCities(){
		return dao.getAllCities();
	}
	
	public List<Business> getBusinessByCity(String city){
		List<Business> locali = new ArrayList<Business>(dao.getBusinessByCity(city));
		Collections.sort(locali);
		return locali;
	}
	
	public void creaGrafo(Business b) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.revMap = new HashMap<>();
		
		// vertici
		Graphs.addAllVertices(this.grafo, dao.getVertices(b, revMap));
		
		// archi
		for(Review r1: this.revMap.values()) {
			for(Review r2 : this.revMap.values()) {
				if(!r1.equals(r2)) {
					if(r2.getDate().isAfter(r1.getDate())) {
						double peso = ChronoUnit.DAYS.between(r1.getDate(),r2.getDate());
						if(peso!=0) {
							Graphs.addEdgeWithVertices(this.grafo, r1, r2, Math.abs(peso));
						}
					}
				}
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Rev_Archi getRevMax(){
		Rev_Archi revA = null;
		int max = Integer.MIN_VALUE;
		
		for(Review r : this.grafo.vertexSet()) {
			int archi = 0;
			archi = this.grafo.outgoingEdgesOf(r).size();
			if(archi > max) {
				max = archi;
				revA = new Rev_Archi(r,archi);
				
			}
			
		}
		
		return revA;
		
	}
	
	
	
}
