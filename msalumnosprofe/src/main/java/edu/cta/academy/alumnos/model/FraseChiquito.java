package edu.cta.academy.alumnos.model;

public class FraseChiquito {
	
	private int id;
	private String quote;
	
	public FraseChiquito() {
		
	}
	
	public FraseChiquito(int id, String quote) {
		super();
		this.id = id;
		this.quote = quote;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	
	

}
