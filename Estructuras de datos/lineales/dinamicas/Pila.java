package lineales.dinamicas;

import lineales.dinamicas.Nodo;

public class Pila {
	private Nodo tope;
	//metodo constructor
	public Pila() {
		this.tope=null;
	}
	//metodos propios del tipo
	public boolean apilar(Object elemento) {
		//este modulo apila nuevos elementos en nuestra pila dinamica
		//creamos un nuevo nodo y le asignamos los valores recibidos
		Nodo nuevo=new Nodo(elemento, this.tope);
		//el tope pasara a ser el nuevo elemento 
		this.tope=nuevo;
		//devolvemos siempre true ya que siempre se podran agregar nuevos elementos
		return true;
	}
	public boolean esVacia() {
		//este modulo retorna el valor booleano que resulta de comparar this.tope con null
		//si la pila esta vacia retornara un true, sino false
		return this.tope==null;
	}
	public String toString() {
		//este modulo muestra por pantalla el contenido de nuestra pila
		//creamos e inicializamos una variable que contendra a todos nuestros elementos
		String elementos="";
		if(tope==null) {
			//si la pila esta vacia entonces se mostrara el mensaje "pila vacia"
			elementos="pila vacia";
		}
		else {
			//si hay por lo menos un elemento creamos e inicializamos la variable aux
			Nodo aux=this.tope;
			elementos="[";
			while(aux!=null) {
				//mientras que aux haya un elemento no nulo en aux lo agregaremos a elementos
				elementos+=aux.getElemento().toString();
				//avanzamos un nodo en aux
				aux=aux.getEnlace();
				if(aux!=null) {
					//si el nuevo elemento no es nulo vamos a agregar una coma para que sea mas legible 
					elementos+=", ";
				}
			}
			//una vez agregados todos los elementos cerramos con un corchete
			elementos+="]";
		}
		return elementos;
	}
	public boolean desapilar() {
		//este modulo desapila elementos de nuestra pila siempre que sea posible 
		//creamos una variable de tipo boolean llamada exito que devolveremos mas tarde
		boolean exito=false;
		if(tope!=null) {
			//desapilamos puesto que hay por lo menos un elemento
			//y desapilamos cambiando el elemento en el tope por el elemento anterior
			//al no ser apuntado por nada, el nodo tope anterior sera eliminado por el garbage collector
			this.tope=tope.getEnlace();
			//suponiendo que tenga un solo elemento, entonces se le asignara el nodo que tiene el enlace(null)
			exito=true; 
		}
		return exito;
	}
	public void vaciar() {
		//este modulo vacia nuestra pila
		//para realizar dicha accion solamente hace falta asignarle null a nuestro tope
		//al no ser apuntados por nada, el resto de los nodos seran recolectados por el garbage collector
		this.tope=null;
	}
	public Object obtenerTope() {
		//este modulo retorna el elemento que se encuentre en el tope
		if(tope!=null) {
			//si la pila no esta vacia devolveremos el elemento en el tope
			return tope.getElemento();
		}
		else {
			//si la pila esta vacia devolvemos null
			return null;
		}
	}
	public Pila clone() {
		//este modulo crea una copia de la pila original 
		//creamos e inicializamos y una pila que sera nuestra "pila clon"
		Pila copia=new Pila();
		//creamos 3 nodos auxiliares que nos ayudaran a llevar a cabo la tarea
		Nodo nodo1, nodo2, nodo3;
		if(this.esVacia()==false) {
			//si la pila original no esta vacia, entonces procedemos a clonarla
			//le asignamos el tope a la copia
			nodo1=new Nodo(this.tope.getElemento(), null);
			copia.tope=nodo1;
			//al nodo2 le asignaremos el nodo siguiente al tope de la pila original pudiendo ser null
			nodo2=this.tope.getEnlace();
			while(nodo2!=null) {
				//mientras que haya otro nodo enlace en la pila original seguiremos agregando nodos en la copia
				//para no tener que crear un nodo dos veces, una en la linea 99 y otra vez en la linea 100, le asignamos los valores a nodo3
				nodo3=new Nodo(nodo2.getElemento(), null);
				nodo1.setEnlace(nodo3);
				nodo1=nodo3;
				nodo2=nodo2.getEnlace();
			}
		}
		return copia;
	}
}
