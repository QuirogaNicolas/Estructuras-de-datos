package jerarquicas;
public class NodoGen {
	private Object elem;
	private NodoGen hijoIzquierdo;
	private NodoGen hermanoDerecho;
	
	public NodoGen(Object x, NodoGen y, NodoGen w) {
		this.elem=x;
		this.hijoIzquierdo=y;
		this.hermanoDerecho=w;
	}
	public Object getElem() {
		return this.elem;
	}
	public NodoGen getHijoIzquierdo() {
		return this.hijoIzquierdo;
	}
	public NodoGen getHermanoDerecho() {
		return this.hermanoDerecho;
	}
	public void setElem(Object x) {
		this.elem=x;
	}
	public void setHijoIzquierdo(NodoGen y) {
		this.hijoIzquierdo=y;
	}
	public void setHermanoDerecho(NodoGen w) {
		this.hermanoDerecho=w;
	}
}
