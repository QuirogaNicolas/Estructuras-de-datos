package lineales.dinamicas;

public class Nodo {
	private Object elemento;
	private Nodo enlace;
	//metodo constructor
	public Nodo(Object elemento, Nodo enlace) {
		this.elemento=elemento;
		this.enlace=enlace;
	}
	//metodos modificadores
	public void setElemento(Object elemento) {
		this.elemento=elemento;
	}
	public void setEnlace(Nodo enlace) {
		this.enlace=enlace;
	}
	//metodos observadores
	public Object getElem() {
		return elemento;
	}
	public Nodo getEnlace() {
		return enlace;
	}
}
