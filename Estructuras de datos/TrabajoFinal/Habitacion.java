package TrabajoFinal;

public class Habitacion {
	private int codigo;
	private String nombre;
	private int planta;
	private double metrosCuadrados;
	private boolean salidaAlExterior;
	private int estaOcupada;
	public Habitacion(int cod, String nom, int plant, double metros, boolean salida) {
		this.codigo=cod;
		this.nombre= nom;
		this.planta=plant;
		this.metrosCuadrados= metros;
		this.salidaAlExterior=salida;
		this.estaOcupada=0;
	}
	public int getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public int getPlanta() {
		return planta;
	}
	public double getMetros() {
		return metrosCuadrados;
	}
	public boolean getSalida() {
		return salidaAlExterior;
	}
	public int getEstado() {
		return estaOcupada;
	}
	public void setNombre(String nomb) {
		this.nombre= nomb;
	}
	public void setPlanta(int plant) {
		this.planta= plant;
	}
	public void setMetros(double metros) {
		this.metrosCuadrados= metros;
	}
	public void setSalida(boolean salida) {
		this.salidaAlExterior= salida;
	}
	public void setEstado(int ocupada) {
		this.estaOcupada+= ocupada;
	}
	public String toString() {
		 return "codigo de habitacion: "+this.codigo+". Nombre de la habitacion: "+this.nombre+". \n"
				+ "Planta: "+ this.planta+". Metros cuadrados: "+this.metrosCuadrados+". Tiene salida la exterior: "+this.salidaAlExterior;
	}
}
