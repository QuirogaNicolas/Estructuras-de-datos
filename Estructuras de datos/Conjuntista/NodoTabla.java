package Conjuntista;
public class NodoTabla {
	private Comparable codigo;
	private int altura=-1;
	private Object estructura;
	private NodoTabla izquierdo;
	private NodoTabla derecho;
	
	public NodoTabla(Comparable nuevo, Object nueva) {
		this.codigo=nuevo;
		this.estructura= nueva;
		this.izquierdo=null;
		this.derecho=null;
		this.altura=0;
	}
	public Comparable getCodigo() {
		return this.codigo;
	}
	public void setCodigo(Comparable nuevo) {
		this.codigo=nuevo;
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
	public NodoTabla getIzquierdo() {
		return this.izquierdo;
	}
	public void setIzquierdo(NodoTabla izq) {
		this.izquierdo=izq;
	}
	public NodoTabla getDerecho() {
		return this.derecho;
	}
	public void setDerecho(NodoTabla der) {
		this.derecho=der;
	}
	public Object getEstructura() {
		return this.estructura;
	}
	public void setEstructura(Object nueva) {
		this.estructura= nueva;
	}
}
