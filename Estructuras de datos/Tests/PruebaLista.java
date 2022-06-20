package Tests;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;
import lineales.dinamicas.Cola;
public class PruebaLista {
	public static Lista concatenar(Lista l1, Lista l2) {
		Lista concat=new Lista();
		int i=1;
		int j=1;
		while(i<=l1.longitud()){
			//agregamos todos los elementos de la primera lista
			concat.insertarElemento(l1.recuperar(i), i);
			i++;
		}
		while(j<=l2.longitud()) {
			//agregamos todos los elementos de la segunda lista
			concat.insertarElemento(l2.recuperar(j), i);
			i++;
			j++;
		}
		//mas eficiente con un for¿?
		return concat;
	}
	public static boolean comprobar(Lista l) {
		//inicializamos la variable verdadero en falso
		boolean verdadero=false;
		//creamos las estructuras auxiliares
		Cola auxCola=new Cola();
		Pila auxPila=new Pila();
		int i=1;
		//buscamos la primera aparicion del numero 0
		int j=l.localizar(0);
		//creamos la variable recuperacion para no tener que usar el metodo recuperar(pos) dos veces
		Object recuperacion;
		if(j!=-1) {
			//si la cadena cumple con la condicion de por lo menos tener un cero entonces
			verdadero=true;
			while(i<j) {
				//ponemos los elementos en las variables auxiliares
				recuperacion=l.recuperar(i);
				auxCola.poner(recuperacion);
				auxPila.apilar(recuperacion);
				i++;
			}
			//completamos el auxiliar lista con la forma que tiene que tener
			//poniendo los elementos de la cola 
			//y luego los de la pila para que esten invertidos
			while(verdadero && !auxCola.esVacia()) {
				i++;
				if(auxCola.obtenerFrente()!=l.recuperar(i)) {
					verdadero=false;
				}
				auxCola.sacar();
			}
			i++;
			if((int)l.recuperar(i)!=0) {
				verdadero=false;
			}
			while(verdadero && !auxPila.esVacia()) {
				i++;
				if(auxPila.obtenerTope()!=l.recuperar(i)) {
					verdadero=false;
				}
				auxPila.desapilar();
			}
		}
		return verdadero;
	}
	public static Lista invertir(Lista l) {
		Lista invertida=new Lista();
		int largo=l.longitud(), i=1;
		while(largo>=1) {
			invertida.insertarElemento(l.recuperar(largo), i);
			largo--;
			i++;
		}
		return invertida;
	}
	public static void main(String[] args){
		Lista l=new Lista();
		l.insertarElemento(1, 1);
		l.insertarElemento(2, 2);
		l.insertarElemento(3, 3);
		System.out.println(comprobar(l));
		System.out.println(invertir(l).toString());
	}
}
