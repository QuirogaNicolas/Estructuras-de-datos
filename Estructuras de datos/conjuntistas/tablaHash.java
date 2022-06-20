package conjuntistas;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Nodo;
import funciones.funcion;
public class tablaHash {
	//tabla hash abierto
	private int tamanio=100;
	private Nodo [] tabla;
	private int cant;
	
	public tablaHash() {
		tabla=new Nodo[tamanio];
		cant=0;
	}
	public boolean insertar(Object elem) {
		//este metodo inserta un elemento dado en la tabla hash
		//lo primero que hacemos es calcular la posicion donde deberia ir el elemento
		int pos=funcion.hash(elem)%this.tamanio;
		//creamos un auxiliar que sera el nodo que hay en la posicion del arreglo donde debe ir el nuevo 
		Nodo aux=this.tabla[pos];
		//seteamos una variable en false porque suponemos que no lo encontramos todavia
		boolean encontrado=false;
		while(!encontrado && aux!=null) {
			//mientras que no se haya encontrado el elemento en la tabla y aux no sea nulo 
			//tenemos lo de que aux no sea nulo porque, si lo es, y encontrado todavia es false significa que no hay mas elementos por comparar en la coleccion de nodos
			encontrado=aux.getElem().equals(elem);
			aux=aux.getEnlace();
		}
		if(!encontrado) {
			//si el elemento no se encontro, entonces procedemos a agregarlo
			//lo agregamos al principio de la lista de elementos
			this.tabla[pos]=new Nodo(elem, this.tabla[pos]);
			//sumamos uno a la cantidad de elementos porque habremos agregado uno nuevo
			this.cant++;
		}
		return !encontrado;
	}
	public boolean eliminar(Object elem) {
		int pos=funcion.hash(elem)%this.tamanio;
		boolean encontrado=false;
		Nodo aux=this.tabla[pos];
		if(aux!=null) {
			//lo hice de esta forma porque tengo que guardar el nodo anterior al que queremos guardar
			//para luego poder modificarle su enlace
			encontrado=aux.getElem().equals(elem);
			while(!encontrado && aux!=null) {
				encontrado=aux.getEnlace().getElem().equals(elem);
				if(!encontrado) {
					aux=aux.getEnlace();
				}
			}
		}
		if(encontrado) {
			aux.setEnlace(aux.getEnlace().getEnlace());
			cant--;
		}
		return encontrado;
	}
	public boolean esVacia() {
		return this.cant==0;
	}
	public boolean pertenece(Object elem) {
		int pos=funcion.hash(elem)%this.tamanio;
		Nodo aux=this.tabla[pos];
		boolean encontrado=false;
		while(!encontrado && aux!=null) {
			encontrado=aux.getElem().equals(elem);
			aux=aux.getEnlace();
		}
		return encontrado;
	}
	public Lista listar() {
		int cont=1, pos=0;
		Lista listado=new Lista();
		while(pos<this.tamanio) {
			Nodo aux=this.tabla[pos];
			while(aux!=null) {
				listado.insertar(aux.getElem(), cont);
				aux=aux.getEnlace();
				cont++;
			}
			pos++;
		}
		return listado;
	}
}

