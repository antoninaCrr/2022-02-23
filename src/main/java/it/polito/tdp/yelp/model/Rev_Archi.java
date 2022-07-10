package it.polito.tdp.yelp.model;

public class Rev_Archi {

	private Review rev;
	private Integer archi;
	
	public Rev_Archi(Review rev, Integer archi) {
		super();
		this.rev = rev;
		this.archi = archi;
	}

	public Review getRev() {
		return rev;
	}

	public void setRev(Review rev) {
		this.rev = rev;
	}

	public Integer getArchi() {
		return archi;
	}

	public void setArchi(Integer archi) {
		this.archi = archi;
	}

	@Override
	public String toString() {
		return rev.getReviewId()+"       "+"#ARCHI USCENTI: "+archi;
	}
	
	
	
	
}
