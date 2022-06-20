package jerarquicas;
public class NodoArbol {
	private Object elemento;
	private NodoArbol izquierdo;
	private NodoArbol derecho;
	public NodoArbol(Object x, NodoArbol y, NodoArbol z) {
		this.elemento=x;
		this.izquierdo=y;
		this.derecho=z;
	}
	public Object getElem() {
		return this.elemento;
	}
	public NodoArbol getIzquierdo() {
		return this.izquierdo;
	}
	public NodoArbol getDerecho() {
		return this.derecho;
	}
	public void setElem(Object x) {
		this.elemento=x;
	}
	public void setIzquierdo(NodoArbol y) {
		this.izquierdo=y;
	}
	public void setDerecho(NodoArbol z) {
		this.derecho=z;
	}
}
