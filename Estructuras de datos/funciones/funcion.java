package funciones;
public class funcion {
	public static int hash(Object x) {
		int posicion=0;
		if(true) {
			posicion=funcionPrimo((int)x);
		}
		else {
			posicion=funcionCadena((String) x);
		}
		return posicion;
	}
	private static int funcionPrimo(int x) {	
	//no se como hacer esta funcion
		return x%97;
	}
	private static int funcionCadena(String x) {
		int suma=0, i=0, largo=x.length();
		while(i<largo) {
			suma+=(int)x.charAt(i);
			i++;
		}
		return suma;
	}
	public static int reHash(Object objeto) {
		return (int) objeto/10;
	}
}
