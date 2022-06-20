package conjuntistas;
import conjuntistas.NodoAvl;
import jerarquicas.NodoGen;
import lineales.dinamicas.Lista;
public class ArbolAvl {
	private NodoAvl raiz;
	public ArbolAvl() {
		this.raiz=null;
	}
	private int balance(NodoAvl n, NodoAvl padre) {
		//este metodo calcula el balance de un nodo
		int alturaIzq=-1, alturaDer=-1, balanceo=-1;
		if(n!=null) {
			//si el nodo es diferente de null
			if(n.getIzquierdo()!=null) {
				alturaIzq=n.getIzquierdo().getAltura();
			}
			if(n.getIzquierdo()!=null) {
				alturaDer=n.getDerecho().getAltura();
			}
		}
		balanceo=alturaIzq-alturaDer;
		if(balanceo%2==0) {
			reBalanceo(n, padre, balanceo);
		}
		return balanceo;
	}
	private void reBalanceo(NodoAvl n, NodoAvl padre, int balance) {
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
			if(balance>0) {
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
	}
	private NodoAvl rotarDerecha(NodoAvl n) {
		NodoAvl hijoAux=n.getIzquierdo(), aux=hijoAux.getDerecho();
		hijoAux.setDerecho(n);
		n.setIzquierdo(aux);
		//recalculamos la altura de los nodos
		hijoAux.recalcularAltura();
		n.recalcularAltura();
		return hijoAux;
	}
	private NodoAvl rotarIzquierda(NodoAvl n) {
		NodoAvl hijoAux=n.getDerecho(), aux=hijoAux.getIzquierdo();
		hijoAux.setIzquierdo(n);
		n.setDerecho(aux);
		//recalculamos la altura de los nodos
		hijoAux.recalcularAltura();
		n.recalcularAltura();
		return hijoAux;
	}
	private NodoAvl rotarIzquierdaDerecha(NodoAvl n) {
		//revisar bien como funciona la rotacion doble
		n.setIzquierdo(rotarIzquierda(n.getIzquierdo()));
		return rotarDerecha(n);
	}
	private NodoAvl rotarDerechaIzquierda(NodoAvl n) {
		n.setDerecho(rotarDerecha(n.getDerecho()));
		return rotarIzquierda(n);
	}
	public boolean insertar(Comparable elem) {
		boolean exito=true;
		if(this.raiz==null) {
			this.raiz=new NodoAvl(elem, null, null);
		}
		else {
			exito=insertarRecursivo(this.raiz, null, elem);
			this.raiz.recalcularAltura();
			balance(this.raiz, null);
			this.raiz.recalcularAltura();
		}
		return exito;
	}
	private boolean insertarRecursivo(NodoAvl n, NodoAvl padre,Comparable elem) {
		boolean exito=false;
		int balance, resultado=elem.compareTo(n.getElem());
		if(resultado!=0) {
			if(resultado<0) {
				if(n.getIzquierdo()==null) {
					//si el nodo actual no tiene hijo izquierdo entonces lo vamos a agregar como su hijo
					n.setIzquierdo(new NodoAvl(elem, null, null));
					exito=true;
					//aca recalculamos la altura
					n.recalcularAltura();
				}
				else {
					//si el nodo si tiene hijo izquierdo entonces llamamos recursivamente
					exito=insertarRecursivo(n.getIzquierdo(), n, elem);
					n.recalcularAltura();
					//chequeamos el balance
					//creo que el chequearBalanceo de ella hace cosas distintas
					balance(n, padre);
					n.recalcularAltura();
				}
			}
			//creo que aca podria aplicar un else solamente y no otra condicion
			if(resultado>0) {
				if(n.getDerecho()==null) {
					n.setDerecho(new NodoAvl(elem, null, null));
					exito=true;
					n.recalcularAltura();
				}
				else {
					exito=insertarRecursivo(n.getDerecho(), n, elem);
					n.recalcularAltura();
					balance(n, padre);
					n.recalcularAltura();
				}
			}
		}
		return exito;
	}
	public boolean eliminar(Comparable elem) {
		boolean exito=eliminarRecursivo(this.raiz, null, elem);
		this.raiz.recalcularAltura();
		balance(this.raiz, null);
		this.raiz.recalcularAltura();
		return exito;
	}
	private boolean eliminarRecursivo(NodoAvl n, NodoAvl padre, Comparable elem) {
		int balance;
		boolean exito=false;
		if(n!=null) {
			//si el nodo recibido no es null
			if(elem.compareTo(n.getElem())==0) {
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
				if(elem.compareTo(n.getElem())<0) {
					exito=eliminarRecursivo(n.getIzquierdo(), n, elem);
				}
				else {
					exito=eliminarRecursivo(n.getDerecho(), n, elem);
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
	private void caso1(NodoAvl hijo, NodoAvl padre) {
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
	private void caso2(NodoAvl nieto, NodoAvl padre, NodoAvl abuelo) {
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
	private void caso3(NodoAvl aEliminar) {
		Comparable aux;
		NodoAvl menorHijo=aEliminar.getDerecho(), padreMenorHijo=aEliminar;
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
	private boolean perteneceRecursivo(NodoAvl n, Comparable elem) {
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

    private int listarAux(NodoAvl nodo, Lista lista, int pos) {
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
	private void listarRangoRecursivo(NodoAvl n, Lista listado, Comparable min, Comparable max) {
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
	private Object minimoElemRecursivo(NodoAvl n) {
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
	private Object maximoElemRecursivo(NodoAvl n) {
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
	ArbolAvl nuevo=new ArbolAvl();
	nuevo.insertar(10);
	nuevo.insertar(12);
	nuevo.insertar(20);
	nuevo.insertar(25);
	nuevo.insertar(30);
	nuevo.insertar(35);
	//TENGO QUE HACER UN BUEN TESTER haciendo traza >:(
	System.out.println(nuevo.listar().toString());
	System.out.println("/////////////////////////");
	System.out.println(nuevo.listarRango(2, 16).toString());
	System.out.println("/////////////////////////");
	System.out.println("minimo elemento--------->"+nuevo.minimoElem());
	System.out.println("/////////////////////////");
	System.out.println("maximo elemento--------->"+nuevo.maximoElem());
	System.out.println("/////////////////////////");
	//hay problemas al querer eliminar el numero 45 con solo 10 y 12 en el arbol
	System.out.println("se encontro el elemento->");//nuevo.eliminar(45)
	System.out.println("/////////////////////////");
	System.out.println("probamos el eliminar:");
	//aca tambien hubo problemas en el eliminar
	System.out.println(nuevo.listar().toString());
	}
}
