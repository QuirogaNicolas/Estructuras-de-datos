package Tests;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Pila;
public class MixLineales {
	public static Cola generarOtraCola(Cola c) {
		//creamos dos estructuras de tipo Cola y otra de tipo Pila 
		Cola resultado=new Cola();
		Pila pila=new Pila();
		if(!c.esVacia()) {
			//si y solo si la cola recibida no esta vacia procederemos 
			//clonamos la estrucutura original para no modificarla
			Cola c1=c.clone();
			while(!c1.esVacia()) {
			//mientras que la cola no este vacia la recorreremos 
				while(!c1.esVacia() && (char)c1.obtenerFrente()!='$') {
					//vamos a recorrer elemento por elemento mientras que el caracter no sea un signo $
					//apilaremos letra por letra en nuestra pila auxiliar 
					pila.apilar((char)c1.obtenerFrente());
					//tambien iremos poniendo letra por letra en nuestra cola resultado
					resultado.poner((char)c1.obtenerFrente());
					c1.sacar();
				}
				//sacamos el $
				c1.sacar();
				while(!pila.esVacia()) {
					//mientras que la pila no este vacia 
					//iremos poniendo los elementos mediante obtenerTope() en nuestra cola resultado para asi guardarlos invertidos
					resultado.poner(pila.obtenerTope());
					//luego desapilaremos para no volver a poner el mismo elemento
					pila.desapilar();
				}
				if(!c1.esVacia()) {
					//si la cola clon no esta vacia agregaremos un signo peso para asi separar los elementos
				resultado.poner('$');
				}
			}
		}
		return resultado;
	}
	public static void main(String[] args) {
		Cola c1=new Cola();
		c1.poner('P');
		c1.poner('A');
		c1.poner('U');
		c1.poner('$');
		c1.poner('P');
		c1.poner('$');
		c1.poner('A');
		Cola c2=generarOtraCola(c1);
		System.out.println(c2.toString());
	}
}
