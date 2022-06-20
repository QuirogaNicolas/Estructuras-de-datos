package grafos;
import grafos.NodoVertice;
import grafos.NodoAdyacente;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;
public class Grafo {
	private NodoVertice inicio= null;
	public Grafo() {
		this.inicio=new NodoVertice(null, null, null);
	}
	public boolean insertarVertice(Object nuevo) {
		boolean exito= false;
		NodoVertice aux= buscarVertice(nuevo);
		if(aux== null) {
			this.inicio= new NodoVertice(nuevo, inicio, null); 
			exito= true;
		}
		return exito;
	}
	private NodoVertice buscarVertice(Object nuevo) {
		boolean exito= false;
		NodoVertice retorno=this.inicio;
		while(retorno != null && !exito) {
			if( retorno.getElem().equals(nuevo)) {
				exito=true;
			}
		}
		return retorno;
	}
	public boolean insertarVertice(Object desde, Object hasta) {
		boolean exito= false;
		NodoVertice nodoDesde= buscarVertice(desde);
		NodoVertice nodoHasta= buscarVertice(hasta);
		if(nodoDesde!=null && nodoHasta!= null) {
			//creo que esta bien esta implementacion
			nodoDesde.setAd(new NodoAdyacente(nodoHasta, nodoDesde.getAdy()));
			nodoHasta.setAd(new NodoAdyacente(nodoDesde, nodoHasta.getAdy()));
			exito=true;
		}
		return exito;
	}
	public boolean eliminarVertice(Object aEliminar) {
		boolean exito=true;
		NodoVertice encontrado=buscarVertice(aEliminar);
		if(encontrado!=null) {
			if(encontrado==this.inicio) {
				//si el vertice es el de inicio entonces simplemente seteamos el inicio del grafo
				this.inicio=encontrado.getSig();
			}
			else {
				//si el vertice no es el de inicio
				//buscamos el vertice anterior y seteamos su vertice siguiente para desconectar el vertice a eliminar y que se lo lleve el garbage collector
				NodoVertice anterior=buscarVerticeAnterior(aEliminar);
				anterior.setSig(encontrado.getSig());
			}
			eliminarArcos(encontrado.getAdy());
			exito=true;
		}
		return exito;
	}
	private void eliminarArcos(NodoAdyacente n) {
		//este metodo elimina los arcos recursivamente
		if(n!= null) {
			//siempre que el nodo con el cual llamamos sea distinto de null
			//vamos a llamar recursivamente con el nodoAddyacente siguiente
			eliminarArcos(n.getAdy());
			//una vez volvamos 
			//vamos a crear una variable que va a iterar en los arcos del otro nodo vertice
			NodoAdyacente aux=n.getVertice().getAdy(), aux2 = n.getVertice().getAdy();
			boolean exito= false;
			while(aux!=null && !exito) {
				//siempre que no se haya encontrado el arco buscado
				if(aux.getVertice().getElem().equals(n.getVertice().getElem())) {
					if(aux==aux.getVertice().getAdy()) {
						//si el adyacente es el primero de la lista de adyacentes entonces seteamos el primer adyacente al vertice  
						aux.getVertice().setAd(aux.getAdy());
					}
					else {
						//sino seteamos el anterior adyacente al siguiente y asi lo desconectamos
						aux2.setAdy(aux.getAdy());
					}
				}
			}
			
		}
		
	}
	private NodoVertice buscarVerticeAnterior(Object elem) {
		//este metodo busca el vertice anterior a un elemento dado
		boolean exito=false;
		NodoVertice aux=this.inicio;
		while(!exito) {
			if(aux.getSig()!=null && aux.getSig().equals(elem)) {
				exito=true;
			}
			else {
				aux=aux.getSig();
			}
		}
		return aux;
	}
	public boolean existeVertice(Object aBuscar) {
		NodoVertice aux=buscarVertice(aBuscar);
		return aux==null;
	}
	public boolean esVacio() {
		return this.inicio==null;
	} 
	public boolean eliminarArcos(NodoVertice origen, NodoVertice destino) {
		//este metodo se encarga de borrar los arcos en un grafo 
		//vamos a llamar para eliminar primero el arco que va desde el origen hacia el destino
		boolean exito=eliminarArco(origen, destino);
		if(exito) {
			//si la enterior tarea concluyo con exito entonces vamos a hacer lo mismo pero con el arco que va desde el destino al origen
			exito=eliminarArco(destino, origen);
		}
		return exito;
	}
	private boolean eliminarArco(NodoVertice origen, NodoVertice destino) {
		//este metodo busca un arco y lo elimina, en caso de poder eliminarlo devuelve true	
		boolean exito=false;
		if(origen.getAdy().getVertice()!=destino) {
			NodoAdyacente aux= origen.getAdy();
			while(aux.getAdy()!=null && aux.getAdy().getVertice()!=destino) {
				//buscamos el arco dentro de los nodos adyacentes
				aux.getAdy();
			}
			if(aux.getAdy().getVertice()==destino) {
				aux.setAdy(aux.getAdy().getAdy());
				exito=true;
			}
		}
		else {
			NodoVertice aux=origen;
			aux.setAd(aux.getAdy().getAdy());
			exito=true;
		}
		return exito;
	}
	public boolean existeArco(NodoVertice origen, NodoVertice destino) {
		NodoAdyacente aux=origen.getAdy();
		boolean existe=false;
		while(!existe && aux!=null) {
			if(aux.getVertice()==destino) {
				existe=true;
			}
			aux=aux.getAdy();
		}
		return existe;
	}
	public Lista listadoEnProfundidad() {
		//este metodo va a ir recorriendo todos los vertices en orden
		//creamos una variable de tipo lista para llevar cuenta de cuales son los vertices por los cuales ya pasamos
		Lista visitados=new Lista();
		NodoVertice aux= this.inicio;
		while(aux!=null) {
			//mientras que aux sea diferente a null
			if(visitados.localizar(aux.getElem())<0) {
				//si el vertice aux no se encuentra en la lista de visitados 
				//procedemos a recorrer sus adyacentes
				listarEnProfundidadRecursivo(aux, visitados);
			}
			//pasamos al siguiente vertice en la lista
			aux=aux.getSig();
		}
		return visitados;
	}
	private void listarEnProfundidadRecursivo(NodoVertice n, Lista visitados) {
		//este metodo recorre los vertices adyacentes a un nodo dado
		if(n!=null) {
			//si el vertice en el cual nos encontramos es diferente de null entonces procedemos
			//lo agregamos a la lista de visitados 
			visitados.insertar(n.getElem(), visitados.longitud()+1);
			//creamos una variable aux para recorrer todos sus adyacentes
			NodoAdyacente aux=n.getAdy();
			while(aux!=null) {
				//mientras aux no sea null, es decir, tenga un adyacente
				if(visitados.localizar(aux.getVertice().getElem())<0) {
					//si el vertice del adyacente no esta en la lista de los recorridos entonces vamos a llamar recursivamente con el
					listarEnProfundidadRecursivo(aux.getVertice(), visitados);
				}
				//finalmente obtendremos el siguiente adyacente
				aux=aux.getAdy();
			}
		}
	}
	public boolean existeCamino(Object origen, Object destino) {
		boolean exito=false;
		NodoVertice aux=this.inicio;
		NodoVertice auxOr=null;
		NodoVertice auxDe=null;
		while((auxOr==null || auxDe==null) && aux!=null) {
			if(aux.getElem().equals(origen)) {
				auxOr=aux;
			}
			if(aux.getElem().equals(destino)) {
				auxDe=aux;
			}
			aux=aux.getSig();
		}
		if(auxOr!=null && auxDe!=null ) {
			Lista visitados=new Lista();
			exito=existeCaminoRecursivo(auxOr, destino, visitados);
		}
		return exito;			
	}
	private boolean existeCaminoRecursivo(NodoVertice actual, Object destino, Lista visitados) {
		boolean llegamos=false;
		if(actual!=null) {
			//si el vertice en el cual nos encontramos es distinto de null y este esta conectado a otros vertices procederemos
			if(actual.getElem().equals(destino)) {
				//si el vertice del nodo adyacente es igual al nodo destino asignaremos true a la variable de llegada
				llegamos=true;
			}
			else {
				//si no llegamos entonces vamos a llamar recursivamente con el siguiente en la lista de adyacentes
				visitados.insertar(actual.getElem(), visitados.longitud()+1);
				NodoAdyacente aux=actual.getAdy();
				while(aux!=null && !llegamos) {
					if(visitados.localizar(aux)<0) {
						llegamos=existeCaminoRecursivo(aux.getVertice(), destino, visitados);
					}
					aux=aux.getAdy();
				}
			}
		}
		return llegamos;
	}
	public Lista listarEnAnchura() {
		//este metodo recorre el grafo de ancho
		//creamos una lista de visitados para llevar cuenta de cuales vertices ya fueron recorridos
		Lista visitados=new Lista();
		//creamos una varible para recorrer los vertices
		NodoVertice aux=this.inicio;
		while(aux!=null) {
			//mientras hayan vertices por recorrer
			//llamamos al metodo anchuraDesde para recorrer los adyacentes a el
			anchuraDesde(aux, visitados);
			//vamos al siguiente vertice 
			aux=aux.getSig();
		}
		return visitados;
	}
	private void anchuraDesde(NodoVertice n, Lista visitados) {
		//este metodo recorre los vertices adyacentes a partir de un vertice dado
		//creamos una cola para tener cuales son los adyacentes siguientes a recorrer
		Cola cola= new Cola();
		//agregamos el nodo actual a la lista de visitados
		visitados.insertar(n.getElem(), visitados.longitud()+1);
		//tambien lo ponemos en la cola
		cola.poner(n);
		//creamos una variable aux para recorrer los vertices adyacentes
		NodoVertice aux;
		//creamos una variable para recorrer los vertices adyacentes
		NodoAdyacente arcos;
		while(!cola.esVacia()) {
			//mientras la cola no este vacia 
			//obtenemos el frente y lo asignamos a aux
			aux=(NodoVertice)cola.obtenerFrente();
			//sacamos de la cola 
			cola.sacar();
			//asignamos el primer adyacente de aux a arcos
			arcos=aux.getAdy();
			while(arcos!=null) {
				//mientras hayan nodos adyacentes
				if(visitados.localizar(arcos.getVertice().getElem())<0) {
					//si el vertice del arco todavia no fue visitado
					//lo agregamos a la lista y a la cola
					visitados.insertar(arcos.getVertice(), visitados.longitud()+1);
					cola.poner(arcos.getVertice());
				}
			}
		}
	}
	public Lista caminoMasCorto(Object origen, Object destino) {
		Lista camino=new Lista();
		Lista visitados=new Lista();
		NodoVertice aux=this.inicio;
		NodoVertice auxOr=null;
		NodoVertice auxDe=null;
		while((auxOr==null || auxDe==null) && aux!=null) {
			if(aux.getElem().equals(origen)) {
				auxOr=aux;
			}
			if(aux.getElem().equals(destino)) {
				auxDe=aux;
			}
			aux=aux.getSig();
		}
		if(auxOr!=null && auxDe!=null ) {
			//si existen los vertices de los cuales queremos el camino mas corto
			//llamamos al metodo existeCaminoRecursivo
			cortoRecursivo(auxOr, destino, camino, visitados);
		}
		return camino;	
	}
	private boolean cortoRecursivo(NodoVertice n, Object destino, Lista camino, Lista visitados) {
		//FALTA TERMINAR
		//creamos una variable de tipo boolean para indicar si llegamos o no
		boolean llegamos=true;
		if(n!=null) {
			//si el vertice en el que nos encontramos es distinto de null, vamos a seguir con el procedimiento
			 if(n.getElem().equals(destino)) {
				 //si el elemento del vertice actual es igual la destino 
				 //indicamos que llegamos
				 llegamos=true;
			 }
			 else {
				 //sino llegamos vamos a insertar el vertice en los visitados
				 visitados.insertar(n.getElem(), visitados.longitud()+1);
				 //vamos a crear una variable para recorrer los adyacentes
				 NodoAdyacente aux=n.getAdy();
				 //ESTO ESTA DE PRUEBA
				 //vamos a guardar la lista de visitados hasta el momento
				 Lista reespaldo=visitados.clone();
				 //HASTA ACA
				 while(aux!=null) {
					 //mientras haya adyacentes
					 //vamos a llamar recursivamente y verificar si se llego
					 llegamos=cortoRecursivo(aux.getVertice(), destino, camino, visitados);
					 //para el metodo de caminoMasLargo() solamente habria que cambiar la condicion de este if que sigue
					 if(llegamos && (camino.longitud()==0 || visitados.longitud()<camino.longitud())) { 
						//si llegamos y el recorrido hecho es mas corto que el camino guardado o directamente no hay ningun camino guardado
						//al nuevo camino lo vamos a setear haciendo un clon de los vertices visitados
						camino=visitados.clone();
						//vamos a poner en false la variable de llegamos para que en la proxima iteracion no vuelva a entrar en este if
						llegamos=false;
					 }
					 //vamos a asignar a visitados la lista de reespaldo para que no guarde en camino vertices que hizo en otro recorrido
					 visitados=reespaldo.clone();
					 //pasamos al siguiente adyacente
					 aux=aux.getAdy();
				 }
			 }
		 }
		 return llegamos;
	}
	
}
