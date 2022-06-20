package Dominio;

public class Desafio {
	private int puntaje;
	private String nombre;
	private String tipo;
	
	public Desafio(int punt, String nomb, String tip) {
		this.puntaje= punt; 
		this.nombre= nomb;
		this.tipo= tip;
	}
	public void setNombre(String nomb) {
		this.nombre= nomb;
	}
	public void setTipo(String tip) {
		this.tipo=tip;
	}
	public int getPuntaje() {
		return this.puntaje;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getTipo() {
		return this.tipo;
	}
	public String toString() {
		return "Puntaje del desafio: "+this.puntaje+". Nombre del desafio: \""+this.nombre+"\". Tipo de desafio: "+this.tipo+".";
	}
}
