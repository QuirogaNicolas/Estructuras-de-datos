package Grafo;
public class NodoAdyacente {
	private NodoVertice vertice; 
	private NodoAdyacente sigAd;
	private Object etiqueta;
	public NodoAdyacente(NodoVertice n, NodoAdyacente m, Object etiq ) {
		this.vertice= n;
		this.sigAd=m;
		this.etiqueta= etiq;
	}
	public NodoAdyacente getAdy() {
		return sigAd;
	}
	public void setAdy(NodoAdyacente n) {
		this.sigAd=n;
	}
	public NodoVertice getVertice() {
		return vertice;
	}
	public void setVertice(NodoVertice n) {
		this.vertice=n;
	}
	public Object getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(Object nueva) {
		this.etiqueta= nueva;
	}
}
