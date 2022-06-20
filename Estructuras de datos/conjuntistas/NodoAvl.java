package conjuntistas;

import jerarquicas.NodoArbol;

public class NodoAvl {
	private Comparable elem;
	private int altura=-1;
	private NodoAvl izquierdo;
	private NodoAvl derecho;
	
	public NodoAvl(Comparable nuevo, NodoAvl izq, NodoAvl der) {
		this.elem=nuevo;
		this.izquierdo=izq;
		this.derecho=der;
		this.altura=0;
	}
	public Comparable getElem() {
		return this.elem;
	}
	public void setElem(Comparable nuevo) {
		this.elem=nuevo;
	}
	public int getAltura() {
		return this.altura;
	}
	public void recalcularAltura() {
		int longitud=-1, longitud2=-1;
		if(this.izquierdo!=null) {
			longitud=this.izquierdo.getAltura();
		}
		if(this.derecho!=null) {
			longitud2=this.derecho.getAltura();
		}
		this.altura = Math.max(longitud, longitud2)+1;
	}
	public NodoAvl getIzquierdo() {
		return this.izquierdo;
	}
	public void setIzquierdo(NodoAvl izq) {
		this.izquierdo=izq;
	}
	public NodoAvl getDerecho() {
		return this.derecho;
	}
	public void setDerecho(NodoAvl der) {
		this.derecho=der;
	}
}
