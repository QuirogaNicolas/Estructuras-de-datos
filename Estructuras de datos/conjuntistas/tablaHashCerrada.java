package conjuntistas;
import conjuntistas.celdaHash;
import funciones.funcion;
public class tablaHashCerrada {
	private int tamanio=10;
	private celdaHash[] tabla;
	private int cant;
	private int VACIO=0;
	private int OCUPADO=1;
	private int BORRADO=-1;
	//PARA QUE SIRVE LE VACIO???
	public tablaHashCerrada() {
		tabla= new celdaHash[tamanio];
		cant=0;
	}
	public boolean pertenece(Object buscado) {
		int pos=funcion.hash(buscado)%this.tamanio;
		int incremento=funcion.reHash(buscado)%this.tamanio;
		int intento=1;
		boolean encontrado=false;
		while(!encontrado && intento<this.tamanio && this.tabla[pos]!=null) {
			if(this.tabla[pos].getEstado()==OCUPADO) {
				encontrado=this.tabla[pos].getElem()==buscado;
			}
			pos=(pos+intento*incremento)%tamanio;
			intento++;
		}
		return encontrado;
	}
	public boolean insertar(Object nuevo) {
		int pos=nuevo.hashCode()%this.tamanio;
		int incremento=funcion.reHash(nuevo)%this.tamanio;
		int intento=1;
		boolean exito=false, fin=false;
		
		while(!fin && !exito && intento<this.tamanio) {
			//mientras que no hagamos mas intentos que el tamanio del arreglo o hasta que encontremos un lugar vacio
			if(this.tabla[pos]==null) {				
				this.tabla[pos]=new celdaHash(nuevo, OCUPADO);
				exito=true;
				cant++;
			}
			else {
				if(this.tabla[pos].getEstado()!=OCUPADO) {					
					this.tabla[pos]=new celdaHash(nuevo, OCUPADO);
					exito=true;
					cant++;
				}
				else {
					if(this.tabla[pos].getElem().equals(nuevo)) {						
						fin=true;
					}	
				}
			}
			pos = (pos + incremento * intento) % this.tamanio;
	    	intento++;	
		}
		return exito;
	}
	public boolean eliminar(Object buscado) {
		//calculamos la posicion inicial e incremento
		int pos=funcion.hash(buscado)%this.tamanio;
		int incremento=funcion.reHash(buscado)%this.tamanio;
		boolean encontrado=false;
		int intento=1;
		while(!encontrado && intento<this.tamanio && this.tabla[pos]!=null) {
			//vamos a buscar mientras que 
			//no se haya encontrado el elemento a buscar - hayamos hecho menos intentos que lugares en la tabla - el lugar que le corresponde no este vacio
			//(esto ultimo porque si el lugar que le corresponde esta vacio entonces ya podemos dar por hecho que el elemento no esta en la tabla)
			if(this.tabla[pos].getEstado()==OCUPADO) {
				//si el lugar de la tabla en el que nos encontramos esta ocupado
				//nos fijamos si es el elemento buscado
				encontrado=this.tabla[pos].getElem()==buscado;
				if(encontrado) {
					//si encontramos el elemento
					this.tabla[pos].setElem(null);
					this.tabla[pos].setEstado(BORRADO);
					this.cant--;
				}
			}
			pos=(pos+intento*incremento)%this.tamanio;
			intento++;
		}
		return encontrado;
	}
	  public String toString (){
		  String cadena="[";
	       
	        for(int i=0;i<this.tamanio;i++){
	        	
	            if (this.tabla[i]!=null){
	                cadena+=this.tabla[i].getElem();
	            }
	            else {
	            	 cadena+="null";
	            }
	            if (i!=this.tamanio-1){
	                    cadena+=", ";
	            }
	        }
	        cadena+="]";
	        return cadena;
	    }
	public static void main(String[] args) {
		tablaHashCerrada nuevo=new tablaHashCerrada();
		nuevo.insertar(20);
		System.out.println(nuevo.insertar(20));
		nuevo.insertar(34);
		nuevo.insertar(42);
		nuevo.insertar(69);
		nuevo.insertar(52);
		System.out.println(nuevo.pertenece(6));
		System.out.println(nuevo.toString());
	}
}

