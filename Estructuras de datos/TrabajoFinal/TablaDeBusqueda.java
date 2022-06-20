package TrabajoFinal;
import TrabajoFinal.NodoTabla;
import conjuntistas.NodoArbol;
//HACE FALTA LA LISTA PARA OTRA COSA ADEMAS DEL LISTAR RANGO???
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;
public class TablaDeBusqueda {
	private NodoTabla raiz;
	public TablaDeBusqueda() {
		this.raiz=null;
	}
	private int balance(NodoTabla n, NodoTabla padre) {
		//este metodo calcula el balance de un nodo
		int alturaIzq=-1, alturaDer=-1, balanceo=-1;
		if(n!=null) {
			//si el nodo es diferente de null
			if(n.getIzquierdo()!=null) {
				alturaIzq=n.getIzquierdo().getAltura();
			}
			if(n.getDerecho()!=null) {
				alturaDer=n.getDerecho().getAltura();
			}
		}
		balanceo=alturaIzq-alturaDer;
		if(balanceo== -2 || balanceo == 2) {
			reBalanceo(n, padre, balanceo);
		}
		return balanceo;
	}
	private void reBalanceo(NodoTabla n, NodoTabla padre, int balance) {
		int balanceHijo;
		if(balance<0) {
			//si la inclinacion del arbol es -2 miramos el balance del hijo derecho
			balanceHijo=balance(n.getDerecho(), n);
			if(balance+balanceHijo<=-2) {
				//rotacion simple a izquierda
				if(padre==null) {
					//si el padre es null significa que hay que setear la raiz del arbol
					this.raiz=rotarIzquierda(n);
				}
				else {
					padre.setDerecho(rotarIzquierda(n));
				}
			}
			else {
				//rotacion doble derecha-izquierda
				if(padre==null) {
					//doble rotacion
					this.raiz=rotarDerechaIzquierda(n);
				}
				else {
					padre.setDerecho(rotarDerechaIzquierda(n));
				}
			}
		}
		else {
			//sino miramos el balance del hijo izquierdo
			balanceHijo=balance(n.getIzquierdo(), n);
			if(balance+balanceHijo>=2) {
				//rotacion simple a derecha
				if(padre==null) {
					this.raiz=rotarDerecha(n);
				}
				else {
					padre.setDerecho(rotarDerecha(n));
				}
			}
			else {
				//rotacion doble izquierda-derecha
				if(padre==null) {
					this.raiz=rotarIzquierdaDerecha(n);
				}
				else {
					padre.setIzquierdo(rotarIzquierdaDerecha(n));
				}
			}
		}
	}
	private NodoTabla rotarDerecha(NodoTabla n) {
		NodoTabla hijoAux=n.getIzquierdo(), aux=hijoAux.getDerecho();
		hijoAux.setDerecho(n);
		n.setIzquierdo(aux);
		//recalculamos la altura de los nodos
		n.recalcularAltura();
		hijoAux.recalcularAltura();
		return hijoAux;
	}
	private NodoTabla rotarIzquierda(NodoTabla n) {
		NodoTabla hijoAux=n.getDerecho(), aux=hijoAux.getIzquierdo();
		hijoAux.setIzquierdo(n);
		n.setDerecho(aux);
		//recalculamos la altura de los nodos
		n.recalcularAltura();
		hijoAux.recalcularAltura();
		return hijoAux;
	}
	private NodoTabla rotarIzquierdaDerecha(NodoTabla n) {
		//revisar bien como funciona la rotacion doble
		n.setIzquierdo(rotarIzquierda(n.getIzquierdo()));
		return rotarDerecha(n);
	}
	private NodoTabla rotarDerechaIzquierda(NodoTabla n) {
		n.setDerecho(rotarDerecha(n.getDerecho()));
		return rotarIzquierda(n);
	}
	public boolean insertar(Comparable codigo, Object datos) {
		boolean exito=true;
		if(this.raiz==null) {
			this.raiz=new NodoTabla(codigo, datos);
		}
		else {
			exito=insertarRecursivo(this.raiz, null, codigo, datos);
			this.raiz.recalcularAltura();
			balance(this.raiz, null);
			this.raiz.recalcularAltura();
		}
		return exito;
	}
	private boolean insertarRecursivo(NodoTabla n, NodoTabla padre, Comparable codigo, Object datos ) {
		boolean exito=false;
		int balance, resultado=codigo.compareTo(n.getCodigo());
		if(resultado!=0) {
			if(resultado<0) {
				if(n.getIzquierdo()==null) {
					//si el nodo actual no tiene hijo izquierdo entonces lo vamos a agregar como su hijo
					n.setIzquierdo(new NodoTabla(codigo, datos));
					exito=true;
					//aca recalculamos la altura
					n.recalcularAltura();
				}
				else {
					//si el nodo si tiene hijo izquierdo entonces llamamos recursivamente
					exito=insertarRecursivo(n.getIzquierdo(), n, codigo, datos);
					n.recalcularAltura();
					//chequeamos el balance
					balance(n, padre);
					n.recalcularAltura();
				}
			}
			//creo que aca podria aplicar un else solamente y no otra condicion
			if(resultado>0) {
				if(n.getDerecho()==null) {
					n.setDerecho(new NodoTabla(codigo, datos));
					exito=true;
					n.recalcularAltura();
				}
				else {
					exito=insertarRecursivo(n.getDerecho(), n, codigo, datos);
					n.recalcularAltura();
					balance(n, padre);
					n.recalcularAltura();
				}
			}
		}
		return exito;
	}
	public boolean eliminar(Comparable codigo) {
		boolean exito=eliminarRecursivo(this.raiz, null, codigo);
		this.raiz.recalcularAltura();
		balance(this.raiz, null);
		this.raiz.recalcularAltura();
		return exito;
	}
	private boolean eliminarRecursivo(NodoTabla n, NodoTabla padre, Comparable codigo) {
		int balance;
		boolean exito=false;
		if(n!=null) {
			//si el nodo recibido no es null
			if(codigo.compareTo(n.getCodigo())==0) {
				//si el elemento del nodo actual es igual al elemento que queremos eliminar 
				exito=true;
				//dividimos en los casos
				if(n.getIzquierdo()==null && n.getDerecho()==null) {
					caso1(n, padre);
					
				}
				else {
					if(n.getIzquierdo()!=null && n.getDerecho()!=null) {
						caso3(n);
					}
					else {
						if(n.getIzquierdo()!=null) {
							caso2(n.getIzquierdo(), n, padre);
						}
						else {
							caso2(n.getDerecho(), n, padre);
						}
					}
				}
			}
			else {
				//sino bajaremos por el lado del arbol que corresponda
				if(codigo.compareTo(n.getCodigo())<0) {
					exito=eliminarRecursivo(n.getIzquierdo(), n, codigo);
				}
				else {
					exito=eliminarRecursivo(n.getDerecho(), n, codigo);
				}
				//creo que aca se hace el tema del chequeo
				n.recalcularAltura();
				//a la vuelta de la recursion chequeamos el balance
				balance=balance(n, padre);
				//POR QUE SE CHEQUEABA DOS VECES?
				n.recalcularAltura();
			}
		}
		return exito;
	}
	private void caso1(NodoTabla hijo, NodoTabla padre) {
		//este metodo recibe el nodo a eliminar y el hijo
		//en el caso uno el elemento a eliminar es una hoja 
		int resultado=padre.getCodigo().compareTo(hijo.getCodigo());
		if(resultado==0) {
			//tenemos en cuenta el caso especial en el cual la hoja es tambien la raiz
			this.raiz=null;
		}
		else {
			//si el elemento a eliminar no es la raiz
			//buscamos cual es el hijo a eliminar
			if(resultado>0) {
				//si el elemento a eliminar es el hijo izquierdo lo seteamos null
				padre.setIzquierdo(null);
			}
			else {
				//sino eliminamos el derecho
				padre.setDerecho(null);
			}
		}
	} 
	private void caso2(NodoTabla nieto, NodoTabla padre, NodoTabla abuelo) {
		//nos fijamos si el elemento a eliminar es hijo izquierdo o derecho
		int resultado=abuelo.getCodigo().compareTo(padre.getCodigo());
		if(resultado==0) {
			this.raiz=nieto;
		}
		else {
			if(resultado>0) {
				//procedemos a eliminar
				abuelo.setIzquierdo(nieto);
			}
			else {
				abuelo.setDerecho(nieto);
			}
		}
		
	}
	private void caso3(NodoTabla aEliminar) {
		Comparable aux;
		NodoTabla menorHijo=aEliminar.getDerecho(), padreMenorHijo=aEliminar;
		NodoTabla candidato=candidato(menorHijo, padreMenorHijo);
		aux=(Comparable)candidato.getCodigo();
		aEliminar.setCodigo(aux);
	}
	private NodoTabla candidato(NodoTabla n, NodoTabla padre) {
		NodoTabla retorno= null;
		if(n.getIzquierdo()!= null) {
			retorno= candidato(n.getIzquierdo(), n);
			n.recalcularAltura();
			balance(n, padre);
		}
		else {
			retorno= n;
			if(n.getDerecho()!= null) {
				caso2(n.getDerecho(), n, padre);
			}
			else {
				caso1(n, padre);
			}
		}
		return retorno;
	}
    
	public boolean pertenece(Comparable codigo) {
		return perteneceRecursivo(this.raiz, codigo);
	}
	private boolean perteneceRecursivo(NodoTabla n, Comparable codigo) {
		//inicializamos una variable en false
		boolean exito=false;
		if(n!=null) {
			//si el nodo en el cual nos encontramos no es null entonces procedemos, sino devolveriamo false
			if(codigo.compareTo(n.getCodigo())==0) {
				//si el elemento del nodo actual es igual al elemento que buscamos entonces devolveremos true
				exito=true;
			}
			else {
				//sino llamaremos recursivamente con el hijo izquierdo o derecho segun corresponda
				if(codigo.compareTo(n.getCodigo())<0) {
					exito=perteneceRecursivo(n.getIzquierdo(), codigo);
				}
				if(codigo.compareTo(n.getCodigo())>0) {
					exito=perteneceRecursivo(n.getDerecho(), codigo);
				}	
			}
		}
		return exito;
	}
	public Object buscarElemento(Comparable codigo) {
		return buscarElementoAux(this.raiz, codigo);
	}
	private Object buscarElementoAux(NodoTabla n, Comparable codigo) {
		//METODO ADICIONAL
		//este metodo se dedica a buscar un elemento y devolver sus datos
		//el elemento puede ser de tipo Habitacion o de tipo Desafio
		Object encontrado= null;
		if(n!=null) {
			//si el nodo en el cual nos encontramos no es null entonces procedemos, sino devolveriamo false
			if(codigo.compareTo(n.getCodigo())==0) {
				//si el elemento del nodo actual es igual al elemento que buscamos entonces devolveremos true
				encontrado=n.getEstructura();
			}
			else {
				//sino llamaremos recursivamente con el hijo izquierdo o derecho segun corresponda
				if(codigo.compareTo(n.getCodigo())<0) {
					encontrado=buscarElementoAux(n.getIzquierdo(), codigo);
				}
				if(codigo.compareTo(n.getCodigo())>0) {
					encontrado=buscarElementoAux(n.getDerecho(), codigo);
				}	
			}
		}
		return encontrado;
	}
	public Lista listar() {
        //este metodo devuelve una lista con los elementos en forma ordenada
        //creamos la lista que vamos a usar
        Lista lista = new Lista();
        //invocamos al metodo listarAux
        listarAux(this.raiz, lista, 1);
        return lista;
    }

    private int listarAux(NodoTabla nodo, Lista lista, int pos) {
        //este metodo privado y recursivo devuelve la posicion en la cual debemos insertar un elemento
        if (nodo != null) {
            //siempre que el nodo recibido sea distinto de null
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                //si llegamos a una hoja
                //vamos a insertarlo e incrementar pos
                lista.insertar(nodo.getCodigo(), pos);
                pos++;
            } else {
                //si no estamos en una hoja
                //seguimos recorriendo el arbol/subArbol por el lado izquierdo
                pos = listarAux(nodo.getIzquierdo(), lista, pos);
                //insertamos el nodo actual, que viene a ser el nodo padre
                lista.insertar(nodo.getCodigo(), pos);
                pos++;
                //incrementamos pos y repetimos el proceso con el lado derecho
                pos = listarAux(nodo.getDerecho(), lista, pos);
            }

        }
        return pos;
    }


	public boolean esVacio() {
		return this.raiz==null;
	}
	public Cola listarRango(Comparable elemMinimo, Comparable elemMaximo) {
		Cola listado=new Cola();
		listarRangoRecursivo(this.raiz, listado, elemMinimo, elemMaximo);
		return listado;
	}
	private void listarRangoRecursivo(NodoTabla n, Cola listado, Comparable min, Comparable max) {
		//no lo hice con el inOrden
		if(n!=null) {
			//mientras que el nodo actual no sea null
			if((min.compareTo(n.getCodigo())<0 || min.compareTo(n.getCodigo())==0) && (max.compareTo(n.getCodigo())>0 || max.compareTo(n.getCodigo())==0)) {
				//si el elemento se encuentra en el intervalo lo agregamos en la lista
				listado.poner(n.getCodigo());
			}
			if(min.compareTo(n.getCodigo())<0) {
				//si el elemento del nodo actual es mayor que el minimo del intervalo entonces visitaremos el hijo izquierdo de manera recursiva
				listarRangoRecursivo(n.getIzquierdo(), listado, min, max);
			}
			if(max.compareTo(n.getCodigo())>0) {
				//si el elemento del nodo actual es menor que ele maximo del intervalo entonces visitaremos el hijo derecho de manera recursiva
				listarRangoRecursivo(n.getDerecho(), listado, min, max);
			}
		}
	}
	public Object minimoElem() {
		return minimoElemRecursivo(this.raiz);
	}
	private Object minimoElemRecursivo(NodoTabla n) {
		//asignamos como minimo elemento al elemento del nodo en el que estamos posicionados
		Object minimo=n.getCodigo();
		if(n.getIzquierdo()!=null) {
			//si hay un hijo izquierdo significa que existe un menor elemento al actual 
			minimo=minimoElemRecursivo(n.getIzquierdo());
		}
		return minimo;
	}
	public Object maximoElem() {
		return maximoElemRecursivo(this.raiz);
	}
	private Object maximoElemRecursivo(NodoTabla n) {
		//asignamos como maximo al elemento del nodo en el que nos encontramos posicionados
		Object maximo=n.getCodigo();
		if(n.getDerecho()!=null) {
			//si hay un hijo derecho significa que existe un mayor elemento al actual
			maximo=maximoElemRecursivo(n.getDerecho());
		}
		return maximo;
	} 
	public String toString() {
		String retorno= "tabla vacia";
		if(this.raiz!= null) {
			retorno= toStringRecursivo(this.raiz);
		}
		return retorno;
	}
	private String toStringRecursivo(NodoTabla n) {
		String cadena="";
		if(n!= null) {
			cadena+= n.getAltura()+"| PADRE: "+n.getCodigo();
			if(n.getDerecho()!=null) {
				cadena+= "| DER: "+n.getDerecho().getCodigo();
			}
			else {
				cadena+="| DER: null";
			}
			if(n.getIzquierdo()!= null) {
				cadena+="| IZQ: "+ n.getIzquierdo().getCodigo()+"\n";
			}
			else {
				cadena+="| IZQ: null \n";
			}
			cadena+=toStringRecursivo(n.getDerecho());
			cadena+=toStringRecursivo(n.getIzquierdo());
		}
		return cadena;
	}
}
