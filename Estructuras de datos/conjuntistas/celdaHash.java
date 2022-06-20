package conjuntistas;
public class celdaHash {
	private Object elem;
	private int estado;
	public celdaHash(Object elem, int estado) {
		this.elem=elem;
		this.estado=estado;
	}
	public int getEstado() {
		return this.estado;
	}
	public Object getElem() {
		return this.elem;
	}
	public void setEstado(int unEstado) {
		this.estado=unEstado;
	}
	public void setElem(Object elem) {
		this.elem=elem;
	}
}
