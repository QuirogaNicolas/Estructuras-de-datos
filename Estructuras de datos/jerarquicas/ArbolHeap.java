package jerarquicas;
public class ArbolHeap {
	//esta es la implementacion de el arbol heap
private int tamanio=10;
private Comparable[] heap;
private int ultimo=0;

	public ArbolHeap() {
		this.heap=new Comparable[tamanio];
		ultimo=0;
	}
	public boolean insertar(Comparable nuevoElem) {
		boolean exito=false;
		if(ultimo+1<tamanio) {
			exito=true;
			heap[ultimo+1]=nuevoElem;
			//hacemos subir hasta donde se deba el elemento nuevo ingresado
			hacerSubir(ultimo+1);
			ultimo++;
		}
		return exito;
	}
	private void hacerSubir(int posHijo) {
		int posPadre;
		Comparable aux=this.heap[posHijo];
		boolean salir=false;
		while(!salir) {
			//mientras salir sea falso
				if(posHijo>1) {
					//si la posicion del hijo es mayor a 1
					//caso contrario la posicion del padre seria 0 y en ese lugar del arreglo no tenemos nada
					//calculamos la posicion del padre
					posPadre=Math.toIntExact(posHijo/2);
					if(this.heap[posHijo].compareTo(this.heap[posPadre])<0) {
						//si el hijo es menor que el padre los intercambiamos
						this.heap[posHijo]=this.heap[posPadre];
						this.heap[posPadre]=aux;
						posHijo=posPadre;
					}
					else {
						//si esto no pasa entonces salimos
						salir=true;
					}
				}
				else {
					//si no se verifica entonces salimos
					salir=true;
				}
				
		}
	}
	private void hacerBajar(int posPadre) {
		int posHijo;
		Comparable aux=this.heap[posPadre];
		boolean salir=false;
		while(!salir) {
			posHijo=posPadre*2;
			if(posHijo<=this.ultimo) {
				if(posHijo<this.ultimo) {
					if(this.heap[posHijo+1].compareTo(this.heap[posHijo])<0) {
						posHijo++;
					}
				}
				if(this.heap[posHijo].compareTo(aux)<0) {
					this.heap[posPadre]=this.heap[posHijo];
					this.heap[posHijo]=aux;
					posPadre=posHijo;
				}
				else {
					salir=true;
				}
			}
			else {
				salir=true;
			}
		}
	}
	public boolean eliminarCima() {
		boolean exito=false;
		if(this.ultimo>=1) {
			this.heap[1]=this.heap[ultimo];
			this.heap[ultimo]=null;
			ultimo--;
			hacerBajar(1);
			exito=true;
		}
		return exito;
	}
	public boolean esVacio() {
		return ultimo==0;
	}
	public Comparable recuperarCima() {
		return this.heap[1];
	}
	public String toStringPobre() {
		int i=1;
		String lugares="";
		while(i<=ultimo) {
			lugares+="\n"+"Nodo: "+this.heap[i]+" |";
			if((i*2)<=ultimo) {
				lugares+=" hijo izquierdo: "+this.heap[i*2];
				if((1*2+1)<=ultimo) {
					lugares+=" | hijo derecho: "+this.heap[i*2+1];
				}
				else {
					lugares+=" | hijo derecho: -";
				}
			}
			else {
				lugares+=" hijo izquierdo: - | hijo derecho: -";
			}
			i++;
		}
		return lugares;
	}
	public static void main(String[] args) {
		ArbolHeap nuevo=new ArbolHeap();
		System.out.println("es vacio----------------------->"+nuevo.esVacio()+" retorna true");
		nuevo.insertar(10);
		nuevo.insertar(12);
		nuevo.insertar(15);
		nuevo.insertar(21);
		nuevo.insertar(45);
		nuevo.insertar(19);
		nuevo.insertar(8);
		System.out.println("hacemos un toString------------>"+nuevo.toStringPobre());
		System.out.println("///////////////////////////////////////////////////////");
		System.out.println("retorna la cima---------------->"+nuevo.recuperarCima());
	}
}
