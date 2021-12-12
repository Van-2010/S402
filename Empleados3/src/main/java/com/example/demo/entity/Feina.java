package com.example.demo.entity;

public enum Feina {
	SECRETARIA("SECRETARIA",1200),PROFESSORA("PROFESSORA",2500),METGE("METGE",3000),CUINER("CUINER",2000),
	FISIOTERAPEUTA("FISIOTERAPEUTA",1800),ACTOR("ACTOR",1000);
	
	private String carrec;
	private double salari;
	
	private Feina() {
		
	}
	
	private Feina(String carrec,double salari) {
		this.carrec=carrec;
		this.salari=salari;
	}

	public String getCarrec() {
		return carrec;
	}

	public void setCarrec(String carrec) {
		this.carrec = carrec;
	}

	public double getSalari() {
		return salari;
	}

	public void setSalari(double salari) {
		this.salari = salari;
	}
	
	

}
