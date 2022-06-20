package conjuntistas;
import conjuntistas.NodoArbol;
import lineales.dinamicas.Lista;
public class ArbolBB {
	private NodoArbol raiz;
	public void ArbolBB() {
		this.raiz=null;
	}
	public boolean insertar(Comparable elem) {
		boolean exito=true;
		if(this.raiz==null) {
			this.raiz=new NodoArbol(elem, null, null);
		}
		else {
			exito=insertarRecursivo(this.raiz, elem);
		}
		return exito;
	}
	private boolean insertarRecursivo(NodoArbol n, Comparable elem) {
		boolean exito=false;
		if(elem.compareTo(n.getElem())!=0) {
			if(elem.compareTo(n.getElem())<0) {
				if(n.getIzquierdo()==null) {
					n.setIzquierdo(new NodoArbol(elem, null, null));
					exito=true;
				}
				else {
					exito=insertarRecursivo(n.getIzquierdo(), elem);
				}
			}
			if(elem.compareTo(n.getElem())>0) {
				if(n.getDerecho()==null) {
					n.setDerecho(new NodoArbol(elem, null, null));
					exito=true;
				}
				else {
					exito=insertarRecursivo(n.getDerecho(), elem);
				}
			}
		}
		return exito;
	}
	public boolean eliminar(Comparable elem) {
		return eliminarRecursivo(this.raiz, this.raiz, elem);
	}
	private boolean eliminarRecursivo(NodoArbol n, NodoArbol m, Comparable elem) {
		boolean exito=false;
		if(n!=null) {
			//si el nodo recibido no es null
			if(elem.compareTo(n.getElem())==0) {
				//si el elemento del nodo actual es igual al elemento que queremos eliminar 
				exito=true;
				//dividimos en los casos
				if(n.getIzquierdo()==null && n.getDerecho()==null) {
					caso1(n, m);
					
				}
				else {
					if(n.getIzquierdo()!=null && n.getDerecho()!=null) {
						caso3(n);
					}
					else {
						if(n.getIzquierdo()!=null) {
							caso2(n.getIzquierdo(), n, m);
						}
						else {
							caso2(n.getDerecho(), n, m);
						}
					}
				}
			}
			else {
				//sino bajaremos por el lado del arbol que corresponda
				if(elem.compareTo(n.getElem())<0) {
					exito=eliminarRecursivo(n.getIzquierdo(), n, elem);
				}
				else {
					exito=eliminarRecursivo(n.getDerecho(), n, elem);
				}
			}
		}
		return exito;
	}
	private void caso1(NodoArbol hijo, NodoArbol padre) {
		//este metodo recibe el nodo a eliminar y el hijo
		//en el caso uno el elemento a eliminar es una hoja 
		int resultado=padre.getElem().compareTo(hijo.getElem());
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
	private void caso2(NodoArbol nieto, NodoArbol padre, NodoArbol abuelo) {
		//nos fijamos si el elemento a eliminar es hijo izquierdo o derecho
		int resultado=abuelo.getElem().compareTo(padre.getElem());
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
	private void caso3(NodoArbol aEliminar) {
		Comparable aux;
		NodoArbol menorHijo=aEliminar.getDerecho(), padreMenorHijo=aEliminar;
		if(padreMenorHijo.getDerecho().getIzquierdo()!=null) {
			//agregue este if porque me parecio necesario
			//solo se me ocurrio pensarlo asi
			//si el hijo derecho no es el minimo hijo derecho eso significa que tenemos que buscarlo
			//actualizamos el padre
			padreMenorHijo=padreMenorHijo.getDerecho();
			while(padreMenorHijo.getIzquierdo().getIzquierdo()!=null) {
			//mientras que el hijo izquierdo del hijo izquierdo no sea null vamos a seguir iterando
			padreMenorHijo=padreMenorHijo.getIzquierdo();
			}
			//finalmente el menor hijo izquierdo es el hijo izquierdo del padre
			menorHijo=padreMenorHijo.getIzquierdo();
		}
		aux=(Comparable)menorHijo.getElem();
		if(menorHijo.getDerecho()!=null) {
			//si el menor hijo derecho tiene un hijo entonces procederemos con el caso de eliminacion 2
			caso2(menorHijo.getDerecho(), menorHijo, padreMenorHijo);
		}
		else {
			//sino usamos el metodo de elminacion 1
			caso1(menorHijo, padreMenorHijo);
		}
		aEliminar.setElem(aux);
	}
    
	public boolean pertenece(Comparable elem) {
		return perteneceRecursivo(this.raiz, elem);
	}
	private boolean perteneceRecursivo(NodoArbol n, Comparable elem) {
		//inicializamos una variable en false
		boolean exito=false;
		if(n!=null) {
			//si el nodo en el cual nos encontramos no es null entonces procedemos, sino devolveriamo false
			if(elem.compareTo(n.getElem())==0) {
				//si el elemento del nodo actual es igual al elemento que buscamos entonces devolveremos true
				exito=true;
			}
			else {
				//sino llamaremos recursivamente con el hijo izquierdo o derecho segun corresponda
				if(elem.compareTo(n.getElem())<0) {
					exito=perteneceRecursivo(n.getIzquierdo(), elem);
				}
				if(elem.compareTo(n.getElem())>0) {
					exito=perteneceRecursivo(n.getDerecho(), elem);
				}	
			}
		}
		return exito;
	}
	private boolean perteneceAux(NodoArbol nodo, Comparable elem) {
        boolean exito = false;
        int resultado = elem.compareTo(nodo.getElem());
        //si son iguales encontramos el elemento
        if (resultado == 0) {
            exito = true;
        } else {
            //si el elemento a buscado es menor al elemento que se encuentra en el nodo bajamos a la izquierda
            if (resultado < 0) {
                //si llegamos a la hoja y no lo encontro no pertenece al arbol
                if (nodo.getIzquierdo() == null) {
                    exito = false;
                } else {
                    //si tiene hijo izq llama recursivamente con el 
                    exito = perteneceAux(nodo.getIzquierdo(), elem);
                }
            } //si el elemento buscado es mayor que el elemento que se encuentra en el nodo bajamos a la derecha
            else {
                //si llegamos a la hoja y no lo encontro no pertenece al arbol
                if (nodo.getDerecho() == null) {
                    exito = false;
                } else {
                    exito = perteneceAux(nodo.getDerecho(), elem);
                }

            }

        }
        return exito;
    }


    public Lista listar() {
        //este metodo devuelve una lista con los elementos en forma ordenada
        //creamos la lista que vamos a usar
        Lista lista = new Lista();
        //invocamos al metodo listarAux
        listarAux(this.raiz, lista, 1);
        return lista;
    }

    private int listarAux(NodoArbol nodo, Lista lista, int pos) {
        //este metodo privado y recursivo devuelve la posicion en la cual debemos insertar un elemento
        if (nodo != null) {
            //siempre que el nodo recibido sea distinto de null
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                //si llegamos a una hoja
                //vamos a insertarlo e incrementar pos
                lista.insertar(nodo.getElem(), pos);
                pos++;
            } else {
                //si no estamos en una hoja
                //seguimos recorriendo el arbol/subArbol por el lado izquierdo
                pos = listarAux(nodo.getIzquierdo(), lista, pos);
                //insertamos el nodo actual, que viene a ser el nodo padre
                lista.insertar(nodo.getElem(), pos);
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
	public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
		Lista listado=new Lista();
		listarRangoRecursivo(this.raiz, listado, elemMinimo, elemMaximo);
		return listado;
	}
	private void listarRangoRecursivo(NodoArbol n, Lista listado, Comparable min, Comparable max) {
		//no lo hice con el inOrden
		if(n!=null) {
			//mientras que el nodo actual no sea null
			if((min.compareTo(n.getElem())<0 || min.compareTo(n.getElem())==0) && (max.compareTo(n.getElem())>0 || max.compareTo(n.getElem())==0)) {
				//si el elemento se encuentra en el intervalo lo agregamos en la lista
				listado.insertar(n.getElem(), 1);
			}
			if(min.compareTo(n.getElem())<0) {
				//si el elemento del nodo actual es mayor que el minimo del intervalo entonces visitaremos el hijo izquierdo de manera recursiva
				listarRangoRecursivo(n.getIzquierdo(), listado, min, max);
			}
			if(max.compareTo(n.getElem())>0) {
				//si el elemento del nodo actual es menor que ele maximo del intervalo entonces visitaremos el hijo derecho de manera recursiva
				listarRangoRecursivo(n.getDerecho(), listado, min, max);
			}
		}
	}
	public Object minimoElem() {
		return minimoElemRecursivo(this.raiz);
	}
	private Object minimoElemRecursivo(NodoArbol n) {
		//asignamos como minimo elemento al elemento del nodo en el que estamos posicionados
		Object minimo=n.getElem();
		if(n.getIzquierdo()!=null) {
			//si hay un hijo izquierdo significa que existe un menor elemento al actual 
			minimo=minimoElemRecursivo(n.getIzquierdo());
		}
		return minimo;
	}
	public Object maximoElem() {
		return maximoElemRecursivo(this.raiz);
	}
	private Object maximoElemRecursivo(NodoArbol n) {
		//asignamos como maximo al elemento del nodo en el que nos encontramos posicionados
		Object maximo=n.getElem();
		if(n.getDerecho()!=null) {
			//si hay un hijo derecho significa que existe un mayor elemento al actual
			maximo=maximoElemRecursivo(n.getDerecho());
		}
		return maximo;
	}
	public static void main(String[] args) {
		//hice algunas pruebas
	ArbolBB nuevo=new ArbolBB();
	nuevo.insertar(20);
	nuevo.insertar(14);
	nuevo.insertar(26);
	nuevo.insertar(22);
	nuevo.insertar(11);
	nuevo.insertar(16);
	nuevo.insertar(30);
	nuevo.insertar(29);
	
	System.out.println(nuevo.listar().toString());
	System.out.println("/////////////////////////");
	System.out.println(nuevo.listarRango(2, 16).toString());
	System.out.println("/////////////////////////");
	System.out.println("minimo elemento--------->"+nuevo.minimoElem());
	System.out.println("/////////////////////////");
	System.out.println("maximo elemento--------->"+nuevo.maximoElem());
	System.out.println("/////////////////////////");
	System.out.println("se encontro el elemento->"+nuevo.eliminar(45));
	System.out.println("/////////////////////////");
	System.out.println("probamos el eliminar:");
	nuevo.eliminar(29);
	System.out.println(nuevo.listar().toString());
	}
}
