package Tests;
import lineales.dinamicas.Lista;
public class TestLista {
	public static void main(String[] args) {
		Lista l=new Lista();
		l.insertarElemento(1, 1);
		l.insertarElemento(2, 2);
		l.insertarElemento(3, 3);
		l.insertarElemento(1, 4);
		l.insertarElemento(5, 5);
		l.insertarElemento(6, 6);
		l.eliminarApariciones(1)
		;
		System.out.println("eliminamos el 3----------------------------------------------------->"+l.toString());
		System.out.println("esperamos 1,2,3,4,5,6----------------------------------------------->"+l.toString());
		System.out.println("preguntamos si esta vacia, esperamos: false------------------------->"+l.esVacia());
		System.out.println("clonamos la lista y agregamos algunos elementos nuevos");
		Lista l2=l.clon();
		l2.insertarElemento(7, 1);
		l2.insertarElemento(8, 6);
		System.out.println("mostramos lista clon, esperamos: 7,1,2,3,4,5,6,8--------------------->"+l2.toString());
		System.out.println("longitud lista original, esperamos: 6-------------------------------->"+l.longitud());
		System.out.println("longitud lista clon, esperamos: 8------------------------------------>"+l2.longitud());
		System.out.println("recuperamos elemento, esperamos: 2----------------------------------->"+l2.recuperar(3));
		System.out.println("localizamos elemento, esperamos: 8----------------------------------->"+l2.localizar(8));
		System.out.println("localizamos elemento que no existe, esperamos: -1-------------------->"+l2.localizar(9));
		l2.eliminar(1);
		l2.eliminar(7);
		System.out.println("eliminamos elementos, esperamos: 1,2,3,4,5,6------------------------->"+l2.toString());
		l2.vaciar();
		System.out.println("preguntamos si esta vacia, esperamos: true--------------------------->"+l2.esVacia());
		System.out.println("mostramos lista vacia, esperamos: lista vacia------------------------>"+l2.toString());
		System.out.println("eliminamos en lista vacia, esperamos: false-------------------------->"+l2.eliminar(1));
		l2.insertarElemento(2, 1);
		System.out.println("eliminamos con un solo elemento, esperamos: true--------------------->"+l2.eliminar(1));
		
		//eliminar con un solo elemento en la lista
	}
}
