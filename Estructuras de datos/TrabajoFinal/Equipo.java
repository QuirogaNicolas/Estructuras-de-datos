package TrabajoFinal;

public class Equipo {
	private String nombreEquipo;
	private int puntajeExigido;
	private int puntajeTotal;
	private int codigoHabitacionActual;
	private int puntajeActual;
	public Equipo(String nomb, int puntajeEx, int puntajeAct, int puntajeTot, int habitacion) {
		this.nombreEquipo= nomb;
		this.puntajeExigido= puntajeEx;
		this.puntajeActual= puntajeAct; 
		this.puntajeTotal=puntajeTot;
		this.codigoHabitacionActual= habitacion;
	}
	public void setHabitacion(int nueva) {
		this.codigoHabitacionActual= nueva;
	}
	public void setPuntajeTotal(int nuevo) {
		this.puntajeTotal= nuevo;
	}
	public void setPuntajeExigido(int puntaje) {
		this.puntajeExigido= puntaje;
	}
	public void setPuntajeActual(int puntaje) {
		this.puntajeActual=puntaje;
	}
	public String getNombreEquipo() {
		return this.nombreEquipo;
	}
	public int getHabitacion() {
		return this.codigoHabitacionActual;
	}
	public int getPuntajeTotal() {
		return this.puntajeTotal;
	}
	public int getPuntajeExigido() {
		return this.puntajeExigido;
	}
	public int getPuntajeActual() {
		return this.puntajeActual;
	}
	public String toStringLargo() {
		return "Nombre del equipo: \""+this.nombreEquipo+"\". Puntaje exigido para salir: "+this.puntajeExigido+".\n"
				+ "Puntaje actual: "+this.puntajeActual+". Puntaje total: "+this.puntajeTotal+". habitacion actual: "+this.codigoHabitacionActual+".";
	}
	public String toString() {
		return "puntaje E: "+this.puntajeExigido+", puntaje A: "+this.puntajeActual+", puntaje T: "+this.puntajeTotal+" y habitacion: "+this.codigoHabitacionActual+"\n";
	}
}
