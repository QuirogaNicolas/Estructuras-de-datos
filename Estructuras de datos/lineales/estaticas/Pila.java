package lineales.estaticas;

public class Pila {
	//atributos
	private static final int tamaño=10;
	private Object[] pila;
	private int tope;
	//constructores
	public Pila() {
		this.pila=new Object[tamaño];
		this.tope=-1;
	}
	//Propios del tipo
	public boolean apilar(Object x) {
		//este metodo apila un elemento de tipo Object a nuestra pila
		//inicializamos la variable exito en falso
		boolean exito=false;
		if(tope+1<tamaño) {
			//si todavia queda espacio en nuestra pila, entonces apilamos el elemento recibido 
			exito=true;
			this.tope++;
			this.pila[tope]=x;
		}
		return exito;
	}
	public boolean desapilar() {
		//este metodo desapila un elemento de la pila
		//inicializamos la variable exito en falso
		boolean exito=false;
		if(tope!=-1) {
			//si realmente es posible desapilar, entonces lo hacemos y a tope le restamos 1
			exito=true;
			this.pila[tope]=null;
			this.tope--;
		}
		return exito;
	}
	public Object obtenerTope() {
		//este modulo devuelve el elemento que se encuentre en el tope
		if(tope!=-1) {
			//si hay algun elemento en la pila lo devolvemos
			return pila[tope];
		}
		else {
			//si no hay ningun elemento devolvemos el valor null
			return null;
		}
	}
	public boolean esVacia() {
		//este modulo verifica si la pila analizada esta vacia o no
		//si esta vacia devuelve true y en el caso contrario devuelve false
		return tope==-1;
	}
	public void vaciar() {
		//este modulo vacia el arreglo de pila
		for(int i=tope; i>=0;i--) {
			//recorremos el arreglo dandole valores null a cada elemento para asi vaciarlo
			this.pila[i]=null;
		}
		//le asignamos -1 al tope ya que no hay ningun elemento 
		this.tope=-1;
	}
	public Pila clone() {
		//este modulo clona nuestra pila original
		//creamos la pila donde clonaremos la original
		Pila copia=new Pila();
		//le asignamos el mismo tope 
		copia.tope=this.tope;
		copia.pila=this.pila.clone();
		return copia;
	}
	public String toString() {
		//este modulo mostrara por pantalla todos los elementos de nuestra pila
		//creamos e inicializamos una variable donde guardaremos todos nuestros elementos
		String elementos="";
		if (tope!=-1) {
			//si la pila no esta vacia
			for(int i=0;i<=tope;i++) {
				//procedemos a recorrer toda la pila agregando los elementos a nuestra variable
				elementos=elementos+pila[i];
				if(i<tope) {
					//si el elemento actual no es el ultimo agregamos una coma para que sea mas legible
					elementos+=", ";
				}
			}
		}
		else {
			//si la pila esta vacia entoces guardaremos el mensaje "pila vacia" 
			elementos="pila vacia";
		}
		return elementos;
	}
}

