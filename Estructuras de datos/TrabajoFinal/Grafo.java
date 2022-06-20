package TrabajoFinal;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;
public class Grafo {
	private NodoVertice inicio;
	public Grafo() {
		this.inicio=null;
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
		NodoVertice aux= this.inicio;
		while(aux!= null &&  !aux.getElem().equals(nuevo)) {
			aux=aux.getSig();
		}
		return aux;
	}
	public boolean insertarArco(Object desde, Object hasta, Object etiqueta) {
		boolean exito= false;
		NodoVertice nodoDesde= buscarVertice(desde);
		NodoVertice nodoHasta= buscarVertice(hasta);
		if(nodoDesde!=null && nodoHasta!= null) {
			//creo que esta bien esta implementacion
			nodoDesde.setAd(new NodoAdyacente(nodoHasta, nodoDesde.getAdy(), etiqueta));
			nodoHasta.setAd(new NodoAdyacente(nodoDesde, nodoHasta.getAdy(), etiqueta));
			exito=true;
		}
		return exito;
	}
	public boolean eliminarVertice(Object aEliminar) {
		boolean exito=true;
		NodoVertice encontrado= this.inicio;
		NodoVertice anterior=this.inicio;
		if(encontrado!=null && !encontrado.getElem().equals(aEliminar)) {
			encontrado=encontrado.getSig();
			while(encontrado!=null && !encontrado.getElem().equals(aEliminar)) {
				encontrado=encontrado.getSig();
				anterior=anterior.getSig();
			}
		}
		//si encontramos el nodo que buscabamos
		if(encontrado!=null) {
			if(encontrado==this.inicio) {
				//si el vertice es el de inicio entonces simplemente seteamos el inicio del grafo
				this.inicio=encontrado.getSig();
			}
			else {
				//si el vertice no es el de inicio
				anterior.setSig(encontrado.getSig());
			}
			eliminarArcos(encontrado.getAdy(), aEliminar);
			exito=true;
		}
		return exito;
	}
	private void eliminarArcos(NodoAdyacente n, Object aEliminar) {
		//este metodo elimina los arcos recursivamente
		if(n!= null) {
			//siempre que el nodo con el cual llamamos sea distinto de null
			//vamos a llamar recursivamente con el nodoAdyacente siguiente
			if(n.getAdy()!=null) {
				eliminarArcos(n.getAdy(), aEliminar);
			}
			//una vez volvamos
			//vamos a crear una variable que va a iterar en los arcos del otro nodo vertice
			NodoAdyacente aux=n.getVertice().getAdy(), aux2 = n.getVertice().getAdy();
			boolean exito= false;
			if(aux.getVertice().getElem().equals(aEliminar)) {
				n.getVertice().setAd(aux.getAdy());
			}
			else {
				aux=aux.getAdy();
				while(aux!=null && !exito) {
					//siempre que no se haya encontrado el arco buscado
					if(aux.getVertice().getElem().equals(aEliminar)) {
						//sino seteamos el anterior adyacente al siguiente y asi lo desconectamos
						aux2.setAdy(aux.getAdy());
						exito= true;
					}
					aux=aux.getAdy();
					aux2=aux2.getAdy();
				}
			}
		}

	}
	public boolean existeVertice(Object aBuscar) {
		NodoVertice aux=buscarVertice(aBuscar);
		return aux!=null;
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
	public boolean existeArco(Object origen, Object destino) {
		NodoVertice vertice=buscarVertice(origen);
		boolean existe=false;
		if(vertice!=null) {
		
			NodoAdyacente aux=vertice.getAdy();
			while(!existe && aux!=null) {
	
				if(aux.getVertice().getElem().equals(destino)) {
					existe=true;
				}
				aux=aux.getAdy();
			}
		}
		
		return existe;
	}
	public Object etiquetaArco(Object origen, Object destino) {
		//este metodo retorna la etiqueta de un determinado arco
		NodoVertice vertice=buscarVertice(origen);
		boolean existe=false;
		Object etiqueta=null;
		if(vertice!=null) {
			NodoAdyacente aux= vertice.getAdy();
			while(!existe && aux!=null) {
			if(aux.getVertice().getElem().equals(destino)) {
				existe=true;
				etiqueta= aux.getEtiqueta();
			}
			aux=aux.getAdy();
		}
		}
	
		return etiqueta;
	}
	public Lista listarAdyacentes(Object origen) {
		Lista listado= new Lista();
		NodoVertice elNodo= buscarVertice(origen);
		if(elNodo!=null) {
			NodoAdyacente adyacentes= elNodo.getAdy();
			while(adyacentes!=null) {
				listado.insertar(adyacentes.getVertice().getElem(), 1);
				adyacentes= adyacentes.getAdy();
			}
		}
		return listado;
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
					if(visitados.localizar(aux.getVertice().getElem())<0) {
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
		NodoVertice aux=this.buscarVertice(origen);
		return 	cortoRecursivo(aux, destino, camino, visitados, 0);
	}
	private Lista cortoRecursivo(NodoVertice n, Object destino, Lista camino, Lista visitados, int caminoMinimo) {
		int actual;
		if(n!= null) {
			actual= visitados.longitud();
			visitados.insertar(n.getElem(), actual+1);
			if(n.getElem().equals(destino)) {
				if(caminoMinimo==0 || actual<caminoMinimo) {
					camino=visitados.clone();
					caminoMinimo=actual;
				}
			}
			else {
				NodoAdyacente aux=n.getAdy();
				while(aux!=null) {
					 if(visitados.localizar(aux.getVertice().getElem())<0){
						 if(caminoMinimo==0 || caminoMinimo>visitados.longitud()) {
							 camino=cortoRecursivo(aux.getVertice(), destino, camino, visitados, caminoMinimo);
							 caminoMinimo=camino.longitud();
							 visitados.eliminar(visitados.longitud());
						 }
	                    }
	                    aux= aux.getAdy();
				 }
			}
		}
		return camino;
	}
	public Lista caminoMasLargo(Object origen, Object destino) {
		Lista camino=new Lista();
		Lista visitados=new Lista();
		NodoVertice aux=this.buscarVertice(origen);
		return largoRecursivo(aux, destino, camino, visitados, 0);
	}
	private Lista largoRecursivo(NodoVertice n, Object destino, Lista camino, Lista visitados, int caminoMaximo) {
		int actual;
		if(n!= null) {
			actual= visitados.longitud();
			visitados.insertar(n.getElem(), actual+1);
			if(n.getElem().equals(destino)) {
				if(caminoMaximo==0 || caminoMaximo<actual) {
					camino= visitados.clone();
					caminoMaximo= actual;
				}
			}
			else {
				NodoAdyacente aux=n.getAdy();
				while(aux!=null) {
					 if(visitados.localizar(aux.getVertice().getElem())<0){
	                        camino=largoRecursivo(aux.getVertice(), destino, camino, visitados, caminoMaximo);
	                        caminoMaximo=camino.longitud();
	                        visitados.eliminar(visitados.longitud());
	                    }
	                    aux= aux.getAdy();
				 }
			}
		}
		return camino;
	}
	public Grafo clone() {
		Grafo clon= new Grafo();
		NodoVertice auxVertice, clonVertice;
		if(this.inicio != null) {
			clon.inicio= new NodoVertice(this.inicio.getElem(), null, null);
			clonVertice= clon.inicio;
			auxVertice= this.inicio.getSig();
			while(auxVertice != null) {
				//primero copiamos todos los vertices
				clonVertice.setSig(new NodoVertice(auxVertice.getElem(), null, null));
				auxVertice= auxVertice.getSig();
				clonVertice= clonVertice.getSig();
			}
			clonVertice= clon.inicio;
			auxVertice= this.inicio;
			while(auxVertice != null) {
				//ahora copiamos los arcos
				if(auxVertice.getAdy()!=null) {
					clonVertice.setAd(new NodoAdyacente(auxVertice.getAdy().getVertice(), null, auxVertice.getAdy().getEtiqueta()));
					cloneArcos(auxVertice.getAdy().getAdy(), clonVertice.getAdy());
				}
				auxVertice= auxVertice.getSig();
				clonVertice= clonVertice.getSig();
			}
		}
		return clon;
	}
	private void cloneArcos(NodoAdyacente n, NodoAdyacente m) {
		//por un hecho de simplificar y modularizar hice este metodo 
		//tranquilamente lo puede haber hecho en el metodo clone()
		while(n!= null) {
			m.setAdy(new NodoAdyacente(n.getVertice(), null, n.getEtiqueta()));
			n= n.getAdy();
			m= m.getAdy();
		}
	}
	public Lista todosLosCaminos(Object origen, Object destino) {
		Lista caminos=new Lista();
		Lista visitados=new Lista();
		NodoVertice aux=this.buscarVertice(origen);
		return losCaminosRecursivos(aux, destino, caminos, visitados, null);
	}
	private Lista losCaminosRecursivos(NodoVertice n, Object destino, Lista caminos, Lista visitados, Object prohibido) {
		//este metodo sirve tanto para listar todos los caminos posibles entre dos vertices como para listar los caminos posibles sin pasar por uno determinado
		//lo hice todo en uno asi ahorraba codigo, tal vez no sea lo ideal PREGUNTAR
		NodoAdyacente aux;
		if(n!=null) {
			visitados.insertar(n.getElem(), visitados.longitud()+1);
			if(n.getElem().equals(destino)) {	
				caminos.insertar(visitados.clone(), caminos.longitud()+1);
			}
			else {
				aux= n.getAdy();
				while(aux != null) {
					//lo hago para que no se mantenga "tanto" registro de los visitados y se puedan hacer caminos nuevos
					if(visitados.localizar(aux.getVertice().getElem())<0 && !aux.getVertice().getElem().equals(prohibido)) {
						losCaminosRecursivos(aux.getVertice(), destino, caminos, visitados, prohibido);
						visitados.eliminar(visitados.longitud());
					}
					aux= aux.getAdy();

				}
			}	
		}
		return caminos;
	}
	public String toString (){
		NodoVertice aux=this.inicio;
		String cadena= "plano vacio";
		if(aux!=null) {
			cadena="VERTICES	(ADYACENTE, ETIQUETA) \n";
			while (aux!=null){
				cadena+= ""+aux.getElem() +"-->		";
				NodoAdyacente ad=aux.getAdy();
				while (ad!=null){
					cadena+= " ("+ad.getVertice().getElem() +", "+ad.getEtiqueta()+ ")";
					ad=ad.getAdy();
					if(ad!=null) {
						cadena+=", ";
					}
				}
				cadena+="\n";
				aux=aux.getSig();
			}
		}
		return cadena;
	}
	public Lista caminosSin(Object origen, Object destino, Object sin) {
		Lista caminos=new Lista();
		Lista visitados=new Lista();
		NodoVertice aux=this.buscarVertice(origen);
		return losCaminosRecursivos(aux, destino, caminos, visitados, sin);
	}
	public Lista caminosSinMasPuntaje(Object origen, Object destino, Object sin, int puntajeMaximo) {
		//tal vez puedo modificar el metodo losCaminos para que tambien busque caminos que no se pasen de un puntajeMaximo
		Lista caminos=new Lista();
		Lista visitados=new Lista();
		NodoVertice aux=this.buscarVertice(origen);
		return chequearCaminos(aux, destino, caminos, visitados, sin, puntajeMaximo);
	}
	private Lista chequearCaminos(NodoVertice n, Object destino, Lista caminos, Lista visitados, Object prohibido, int puntaje) {
		NodoAdyacente aux;
		System.out.println(puntaje);
		if(n!=null) {
			visitados.insertar(n.getElem(), visitados.longitud()+1);
			System.out.println("PRUEBA---->"+visitados.toString());
			if(n.getElem().equals(destino)) {	
				
				//esta bien agregar una lista a una lista???
				caminos.insertar(visitados.clone(), caminos.longitud()+1);
			}
			else {
				aux= n.getAdy();
				while(aux != null) {
					//lo hago para que no se mantenga "tanto" registro de los visitados y se puedan hacer caminos nuevos
					if(!aux.getVertice().getElem().equals(prohibido) && visitados.localizar(aux.getVertice().getElem())<0) {
						if(puntaje-(int)aux.getEtiqueta()>=0) {
							chequearCaminos(aux.getVertice(), destino, caminos, visitados, prohibido, puntaje-(int)aux.getEtiqueta());
							if(!visitados.recuperar(visitados.longitud()).equals(n.getElem())) {
								visitados.eliminar(visitados.longitud());
							}
						}
						
					}
					aux= aux.getAdy();
				}
			}	
		}
		return caminos;
	}
}
