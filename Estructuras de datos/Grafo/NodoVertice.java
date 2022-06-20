package Grafo;
public class NodoVertice {
	private Object elem;
	private NodoVertice sigVertice;
	private NodoAdyacente primerAd;
	public NodoVertice (Object n, NodoVertice m, NodoAdyacente r) {
		this.elem=n;
		this.sigVertice=m;
	}
	public void setElem(Object elemento) {
		this.elem=elemento; 
	}
	public Object getElem() {
		return this.elem;
	}
	public void setAd(NodoAdyacente n) {
		this.primerAd=n;
	}
	public NodoAdyacente getAdy() {
		return this.primerAd;
	}
	public void setSig(NodoVertice n) {
		this.sigVertice=n;
	}
	public NodoVertice getSig() {
		return this.sigVertice;
	}
}
