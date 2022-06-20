package grafos;
public class NodoAdyacente {
	private NodoVertice vertice; 
	private NodoAdyacente sigAd;
	public NodoAdyacente(NodoVertice n, NodoAdyacente m ) {
		this.vertice= n;
		this.sigAd=m;
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
}
