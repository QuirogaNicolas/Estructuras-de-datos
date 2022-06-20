package conjuntistas;
public class NodoArbol {
	private Comparable elemento;
	private NodoArbol izquierdo;
	private NodoArbol derecho;
	public NodoArbol(Comparable x, NodoArbol y, NodoArbol z) {
		this.elemento=x;
		this.izquierdo=y;
		this.derecho=z;
	}
	public Comparable getElem() {
		return this.elemento;
	}
	public NodoArbol getIzquierdo() {
		return this.izquierdo;
	}
	public NodoArbol getDerecho() {
		return this.derecho;
	}
	public void setElem(Comparable x) {
		this.elemento=x;
	}
	public void setIzquierdo(NodoArbol y) {
		this.izquierdo=y;
	}
	public void setDerecho(NodoArbol z) {
		this.derecho=z;
	}
}
