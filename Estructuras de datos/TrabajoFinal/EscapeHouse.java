package TrabajoFinal;
import Conjuntista.TablaDeBusqueda;
import Grafo.Grafo;
import Dominio.Desafio;
import Dominio.Equipo;
import Dominio.Habitacion;
import lineales.dinamicas.Lista;
import utiles.TecladoIn;
import lineales.dinamicas.Cola;
import java.util.HashMap;
import java.io.*;
import java.util.StringTokenizer;
public class EscapeHouse {
	private TablaDeBusqueda tablaHabitaciones;
	private TablaDeBusqueda tablaDesafios;
	private HashMap<String, Equipo> tablaEquipos;
	private HashMap<String, Lista> desafiosPorEquipo;
	private HashMap<String, Lista> puertasUsadas;
	private Grafo plano;

	public EscapeHouse() {
		this.tablaHabitaciones= new TablaDeBusqueda();
		this.tablaDesafios= new TablaDeBusqueda();
		this.plano= new Grafo();
		this.tablaEquipos= new HashMap<String, Equipo>();
		this.desafiosPorEquipo= new HashMap<String, Lista>();
		this.puertasUsadas= new HashMap<String, Lista>();
	}
	public boolean menu(String rutaArchivoLog, String direccion) {
		//Este metodo muestra por pantalla el menu principal 
		System.out.println("\nQue desea hacer? \n"
						+ "1- Realizar una carga inicial del sistema \n"
						+ "2- ABM de habitaciones, desafios y equipos \n"
						+ "3- Consultar sobre habitaciones \n"
						+ "4- Consultar sobre desafios \n"
						+ "5- Consultar sobre equipos participantes \n"
						+ "6- Consulta general (mostrar el sistema) \n"
						+ "7- Salir");
		int eleccion= TecladoIn.readLineInt();
		boolean salir= false;
		switch (eleccion) {
		case 1:
			//leer el archivo y cargar los contenidos
			carga(direccion, rutaArchivoLog);
			break; 
		case 2: 
			//ABM
			menuABM(rutaArchivoLog);
			break;
		case 3:
			//mostrar menu consultas de habitaciones
			menuHabitaciones();
			break; 
		case 4: 
			//mostrar menu consultas de desafios
			menuDesafios();
			break; 
		case 5: 
			//mostrar menu consultas de equipos
			menuEquipos();
			break; 
		case 6: 
			//mostar el sistema
			System.out.println(mostrarSistema());
			break; 
		case 7: 
			//dar las gracias por usar el sistema 
			finalizarPrograma(rutaArchivoLog);
			salir=true;
			break;
		default: 
			//mostrar que se ha elegido una opcion no dada
			System.out.println("usted a ingresado una opcion inexistente, vuelva a intentarlo");
			break;
		}
		return salir;
	}
	private void menuHabitaciones() {
		//Este metodo es el menu de habitaciones
		//Se elige sobre lo que se quiere trabajar y direccionamos 
		System.out.println("\nQue operacion desea realizar dentro de las habitaciones? \n"
							+"1- Mostrar una habitacion \n"
							+"2- Mostrar las habitaciones contiguas a una habitacion \n"
							+"3- Mostrar si es posible llegar de una habitacion a otra \n"
							+"4- Mostrar todas las formas de llegar de una habitacion a otra sin pasar por una determinada");
		
		int eleccion= TecladoIn.readLineInt();
		switch (eleccion) {
		case 1:
			mostrarHabitacion();
			break;
		case 2:
			habitacionesContiguas();
			break;
		case 3:
			esPosibleLlegar();
			break;
		case 4:	
			sinPasarPor();
			break;
		default:
			System.out.println("usted a ingresado una opcion inexistente");
			break;
		}	
		
	}
	private void menuDesafios() {
		//Este es el menu de desafios
		//Se elige sobre lo que queremos trabajar y direccionamos
		System.out.println("\nQue operacion desea realizar dentro de los desafios? \n"
				+"1- Mostrar un desafio \n"
				+"2- Mostrar los desafios resueltos por un equipo \n"
				+"3- Mostrar los desafios de un determinado tipo y que tenga un puntaje dentro de un rango determinado \n");
		
		int eleccion= TecladoIn.readLineInt();
		switch (eleccion) {
		case 1:
			mostrarDesafio();
			break;
		case 2:
			mostrarDesafiosResueltos();
			break; 
		case 3: 
			mostrarDesafiosTipo();
			break; 
		default:
			System.out.println("usted a ingresado una opcion inexistente");
			break;
		}
	}
	private void menuEquipos() {
		//Este es el menu de equipos 
		//Se elige sobre lo que queremos trabajar y direccionamos
		System.out.println("\nQue operacion desea realizar dentro de los equipos? \n"
				+"1- Mostrar un equipo \n"
				+"2- Jugar a un desafio con un equipo deseado \n"
				+"3- Pasar un equipo a otra habitacion \n"
				+"4- Verificar si un equipo dado puede salir del juego");
		
		int eleccion= TecladoIn.readLineInt();
		switch (eleccion) {
		case 1:
			mostrarInfoEquipo();
			break;
		case 2:
			jugarDesafio();
			break;
		case 3:
			pasarAHabitacion();
			break;
		case 4: 
			puedeSalir();
			break; 
		default: 
			System.out.println("usted a ingresado una opcion inexistente");
			break;
		}
	}
	private void menuABM(String rutaArchivoLog) {
		//Este es el menu de altas, bajas y modificaciones
		//Se elige lo que se quiere hacer y direccionamos
		System.out.println("\nQue desea hacer? \n"
						+ "1- Dar de baja una habitacion, equipo o desafio \n"
						+ "2- Dar de alta una habitacion, equipo o desafio \n"
						+ "3- Modificar una habitacion, equipo o desafio \n"
						+ "4- Agregar una puerta");
		int eleccion= TecladoIn.readLineInt();
		switch (eleccion) {
		case 1:
			bajas(rutaArchivoLog);
			break;
		case 2:
			altas(rutaArchivoLog);
			break;
		case 3:
			modificaciones(rutaArchivoLog);
			break;
		case 4:
			nuevaPuerta(rutaArchivoLog);
		default: 
			System.out.println("usted ha ingresado una opcion inexistente, lo direccionaremos al menu principal");
		}
		
	}
	public void escribir(String cadena, String direccion) {
		//Este metodo se encarga de escribir
		//Lo vamos a llamar cada vez que queramos registrar una accion en el archivo log
	        try {
	            PrintWriter writer = new PrintWriter(new FileWriter(direccion, true));
	            writer.println(cadena);
	            writer.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	public void carga(String direccion, String rutaArchivoLog) {
		//Este metodo se encarga de leer los datos del archivo de texto
		if(tablaHabitaciones.esVacio() && tablaDesafios.esVacio() && tablaEquipos.isEmpty() && desafiosPorEquipo.isEmpty() && plano.esVacio()) {
			//vamos a leer el archivo solo si las es
			BufferedReader elementos=null;
			try {
				elementos = new BufferedReader(new FileReader(direccion));
				String linea;
				StringTokenizer tokens;
				while((linea = elementos.readLine()) != null) {
					tokens= new StringTokenizer(linea, ";");
					cargaAux(tokens);
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if(elementos!=null) {
						elementos.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			//Mostramos un mensaje de exito si todo salio bien
			System.out.println("\nGenial, todo listo!");
			escribir("se realizo la carga de elementos. A continuacion mostraremos como han quedado las estructuras... \n"
					+ mostrarSistema(), rutaArchivoLog);
		}
		else {
			//Mostramos un mensaje de error si la carga ya fue realizada
			System.out.println("lo siento pero la carga inicial de elementos ya fue realizada. \n"
					+ "Si asi lo desea, usted puede agregar los elementos que desee seleccionando la opcion 2 del menu");
		}
	}
	private void cargaAux(StringTokenizer tokens) {
		//Este metodo recibe los tokens que contienen los datos
		String tipo = tokens.nextToken();
		switch (tipo) {
		//Segun el caracter que este al comienzo vamos a direccionar
		case "H":
			//H de habitacion
			nuevaHabitacion(tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
			break; 
		case "E":
			//E de equipo
			nuevoEquipo(tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
			break; 
		case "D":
			//D de desafio
			nuevoDesafio(tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
			break; 
		case "P":
			//P de profe espero le guste mi codigo
			nuevaPuerta(tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
			break;
		}
	}
	private void finalizarPrograma(String rutaArchivoLog) {
		//Cuando se quiera salir del programa vamos a escribir en el archivo log
		escribir("Ha finalizado el programa. Las estructuras quedaron de la siguiente manera... \n"
				+ mostrarSistema(), rutaArchivoLog);
		//Y tambien vamos a mostrar por pantalla el siguiente mensaje
		System.out.println("Gracias por usar EscapeHouse, lo espero pronto");
	}
	
	
	//CONSULTAS ABM ---> BAJAS
	
	
	private void bajas(String rutaArchivoLog) {
		//Este es el menu de bajas
		//Se elige que se quiere dar de baja y direccionamos
		System.out.println("Desea dar de baja un/una: \n"
				+ "1- Habitacion \n"
				+ "2- Desafio \n"
				+ "3- Equipo \n");
		int eleccion= TecladoIn.readLineInt();
		switch (eleccion) {
		case 1:
			bajaHabitacion(rutaArchivoLog);
			break; 
		case 2: 
			bajaDesafio(rutaArchivoLog);
			break; 
		case 3: 
			bajaEquipo(rutaArchivoLog);
			break; 
			default:
				System.out.println("usted ha ingresado una opcion inexistente, lo direccionaremos al menu principal");
		}
	}
	private void bajaHabitacion(String rutaArchivoLog) {
		//Este metodo se encarga de eliminar una habitacion
		//El caso de las habitaciones es un caso especial porque tengo que ver que no este ocupada
		//Por lo tanto me tengo que encargar de que el codigo ingresado sea valido para poder ver su estado
		boolean exito= false;
		String mensaje;
		//Solicitamos los datos
		System.out.println("ingrese el codigo de habitacion");
		Habitacion laHabitacion= (Habitacion)tablaHabitaciones.buscarElemento(TecladoIn.readInt());
		while(laHabitacion==null) {
			//Siempre que la habitacion no exista vamos a volver a solicitar los datos
			System.out.println("el codigo ingresado no pertenece a ninguna habitacion, por favor, ingrese otro");
			
			laHabitacion= (Habitacion)tablaHabitaciones.buscarElemento(TecladoIn.readInt());
		}
		mensaje="\nSe ha intentado remover la habitacion "+laHabitacion.getCodigo();
		if(laHabitacion.getEstado()==0) {
			//Si la habitacion no esta ocupada vamos a intentar eliminar
			exito= tablaHabitaciones.eliminar(laHabitacion.getCodigo());
			if(exito) {
				//Si tuvimos exito eliminando de la tabla de habitaciones proseguimos 
				exito= plano.eliminarVertice(laHabitacion.getCodigo());
				if(exito) {
					//Finalmente tuvimos exito si tambien se pudo eliminar del plano
					mensaje+=" y tuvimos exito!";
				}
				else {
					mensaje+=" pero no se elimino correctamente del plano";
				}
			}
			else {
				mensaje+=" pero no se elimino correctamente de la tabla";
			}
			
		}
		else {
			mensaje+=" pero esta ocupada";
		}
		//Mostramos por pantalla el mensaje, ya sea de error o de exito
		System.out.println(mensaje);
		//Tambien lo registramos en el archivo log
		escribir(mensaje, rutaArchivoLog);
	}
	private void bajaDesafio(String rutaArchivoLog) {
		//Este metodo se encarga de dar de baja un desafio
		//Pedimos que nos ingresen los datos
		System.out.println("ingrese el puntaje del desafio");
		Comparable puntaje= TecladoIn.readLineInt();
		//Asignamos a una variable boolean el resultado de intentar eliminar el desafio
		boolean exito= tablaDesafios.eliminar(puntaje);
		String mensaje;
		mensaje="\nSe ha intentado remover el desafio con puntaje "+puntaje;
		//Luego, segun el caso, mostraremos un mensaje de exito o error
		if(exito) {
			mensaje+=" y tuvimos exito!";
		}
		else {
			mensaje+=" pero hubo un error";
		}
		System.out.println(mensaje);
		//Tambien lo registraremos en el archivo log
		escribir(mensaje, rutaArchivoLog);
	}
	private void bajaEquipo(String rutaArchivoLog) {
		//Este metodo se encarga de dar de baja un Equipo
		//Pedimos que nos ingresen el nombre del equipo
		System.out.println("ingrese el nombre del equipo");
		String nombre= TecladoIn.readLine();
		//Asignamos a una variable boolean el resultado de intentar eliminar el equipo
		boolean exito= tablaEquipos.remove(nombre, tablaEquipos.get(nombre));
		String mensaje="\nSe ha intentado remover el equipo con nombre: \" "+nombre+"\"";
		//Luego, segun el caso, mostraremos un mensaje de exito o error
		if(exito) {
			mensaje+=" y tuvimos exito!";
		}
		else {
			mensaje+=" pero hubo un error";
		}
		//Tambien lo registraremos en el archivo log
		escribir(mensaje, rutaArchivoLog);
	}
	
	
	//CONSULTAS ABM ---> ALTAS
	
	
	private void altas(String rutaArchivoLog) {
		//Este es el menu de altas
		//Se elige que se quiere dar de alta y direccionamos segun el caso
		System.out.println("Desea dar de alta un/una: \n"
				+ "1- Habitacion \n"
				+ "2- Desafio \n"
				+ "3- Equipo \n");
		int eleccion= TecladoIn.readLineInt();
		switch (eleccion) {
		case 1:
			dataNuevaHabitacion(rutaArchivoLog);
			break; 
		case 2: 
			dataNuevoDesafio(rutaArchivoLog);
			
		case 3: 
			dataNuevoEquipo(rutaArchivoLog);
			break; 
			default:
				System.out.println("usted ha ingresado una opcion inexistente, lo direccionaremos al menu principal");
		}
	}
	private void dataNuevaHabitacion(String rutaArchivoLog) {
		//Este metodo se encarga de conseguir todos los datos para la creacion de una nueva habitacion
		//Solicitamos los datos
		System.out.println("ingrese el codigo de la habitacion");
		int codigo= TecladoIn.readLineInt();
		while(tablaHabitaciones.pertenece(codigo)) {
			//Siempre que el codigo ingresado ya este en uso vamos a pedir el reingreso del mismo
			System.out.println("el codigo ingresado ya esta en uso, por favor ingrese uno nuevo");
			codigo= TecladoIn.readLineInt();
		}
		System.out.println("ingrese el nombre de la habitacion");
		String nombre= TecladoIn.readLine();
		System.out.println("ingrese la planta en la que se encuentra");
		String planta= TecladoIn.readLine();
		System.out.println("ingrese los metros cuadrados");
		String metros= TecladoIn.readLine();
		System.out.println("ingrese true si la habitacion tiene salida y false en caso contrario");
		String salida= TecladoIn.readLine().toLowerCase();
		//Enviamos los datos al metodo correspondiente
		//Transformando el codigo a String para reutilizar codigo
		nuevaHabitacion(Integer.toString(codigo), nombre, planta, metros, salida);
		//Mostramos un mensaje de exito al haber agregado la habitacion
		System.out.println("\nSe agrego la habitacion exitosamente!");
		//Registramos en el archivo log
		escribir("se agrego la habitacion con codigo: "+codigo+"\".", rutaArchivoLog);
	}
	private void dataNuevoDesafio(String rutaArchivoLog) {
		//Este metodo se encarga de conseguir todos los datos para la creacion de un nuevo desafio
		//Solicitamos los datos
		System.out.println("ingrese el puntaje que tendra el nuevo desafio");
		int puntaje= TecladoIn.readLineInt();
		while(tablaDesafios.pertenece(puntaje)) {
			//Siempre que el puntaje ya este en uso, vamos a solicitar el reingreso del mismo
			System.out.println("el puntaje ingresado ya esta en uso, por favor ingrese uno nuevo");
			puntaje= TecladoIn.readLineInt();
		}
		System.out.println("ingrese el nombre del desafio");
		String nombre= TecladoIn.readLine();
		System.out.println("ingrese el tipo");
		String tipo= TecladoIn.readLine();
		//Enviamos los datos al metodo correspondiente
		//Transformando el puntaje a String para reutilizar codigo
		nuevoDesafio(Integer.toString(puntaje), nombre, tipo);
		//Mostramos un mensaje de exito al haber agregado el desafio
		System.out.println("\nSe agrego el desafio exitosamente!");
		//Registramos en el archivo log
		escribir("se agrego el desafio con puntaje: "+puntaje+"\".", rutaArchivoLog);
	}
	private void dataNuevoEquipo(String rutaArchivoLog) {
		//Este metodo se encarga de conseguir todos los datos para la creacion de un nuevo equipo
		//Solicitamos los datos
		System.out.println("ingrese el nombre del equipo");
		String nombre= TecladoIn.readLine();
		while(tablaEquipos.containsKey(nombre)) {
			//Siempre que el nombre ya este en uso, vamos a solicitar el reingreso del mismo
			System.out.println("el nombre ingresado ya esta en uso, por favor ingrese uno nuevo");
			nombre= TecladoIn.readLine();
		}
		System.out.println("ingrese el puntaje que debe conseguir para salir");
		String puntaje= TecladoIn.readLine();
		System.out.println("ingrese el puntaje acumulado del equipo");
		String acumulado= TecladoIn.readLine();
		System.out.println("ingrese el codigo de la habitacion en la cual se encuentra el equipo");
		int habitacion= TecladoIn.readLineInt();
		while(!tablaHabitaciones.pertenece(habitacion)) {
			//Verificamos que la habitacion exista
			//No podemos decir que un equipo esta en una habitacion inexistente
			System.out.println("la habitacion ingresada es inexistente, por favor ingrese una nueva");
			habitacion= TecladoIn.readLineInt();
		}
		System.out.println("ingrese el puntaje actual");
		String actual= TecladoIn.readLine();
		//Enviamos los datos al metodo correspondiente
		//Transformando el codigo de la habitacion a String para reutilizar codigo
		nuevoEquipo(nombre, puntaje, acumulado, Integer.toString(habitacion), actual);
		//Buscamos la habitacion y modificamos su estado sumandole un nuevo equipo
		Habitacion laHabitacion= (Habitacion)tablaHabitaciones.buscarElemento(habitacion);
		laHabitacion.setEstado(1);
		//Mostramos un mensaje de exito al haber agregado el desafio
		System.out.println("\nSe agrego el equipo exitosamente!");
		//Registramos en el arvhivo log
		escribir("se agrego el equipo con nombre: "+nombre+"\".", rutaArchivoLog);
	}
	private void nuevaPuerta(String rutaArchivoLog) {
		//Este metodo se encarga de conseguir todos los datos para la creacion de una nueva puerta
		//Solicitamos los datos
		boolean exito= false;
		System.out.println("ingrese el codigo de la habitacion de origen");
		int codigo1= TecladoIn.readInt();
		System.out.println("ingrese el codigo de la habitacion de destino");
		int codigo2= TecladoIn.readInt();
		Lista adyacentes= plano.listarAdyacentes(codigo1);
		if(adyacentes.localizar(codigo2)==-1) {
			//Si no existe un arco entre dichas habitaciones podemos crear la puerta
			System.out.println("Perfecto, podemos crear una puerta entre ambas habitaciones. \n"
					+ "Ingrese el puntaje que tendra dicha puerta");
			int puntaje= TecladoIn.readInt();
			plano.insertarArco(codigo1, codigo2, puntaje);
			exito= true;
		}
		//Mostramos mensajes de exito o error
		if(exito) {
			System.out.println("\n Se ha agregado la puerta con exito!");
		}
		else {
			System.out.println("\n Lo siento pero no se ha podido agregar la puerta con exito");
		}
		//Registramos en el archivo log
		escribir("Se agrego una puerta desde la habitacion: "+codigo1+" a la habitacion: "+codigo2+". La operacion tuvo exito: "+exito, rutaArchivoLog);
	}
	
	
	//MODIFICACIONES
	
	
	private void modificaciones(String rutaArchivoLog) {
		//Este es el menu de modificaciones
		//Se elige que se quiere modificar y direccionamos segun el caso
		System.out.println("Desea modificar los datos de un/una: \n"
				+ "1- Habitacion \n"
				+ "2- Desafio \n"
				+ "3- Equipo \n");
		int eleccion= TecladoIn.readLineInt();
		switch (eleccion) {
		case 1:
			modificacionHabitacion(rutaArchivoLog);
			break; 
		case 2: 
			modificacionDesafio(rutaArchivoLog);
			break;
		case 3: 
			modificacionEquipo(rutaArchivoLog);
			break; 
			default:
				System.out.println("usted ha ingresado una opcion inexistente, lo direccionaremos al menu principal");
		}	
	}
	private void modificacionHabitacion(String rutaArchivoLog) {
		//Este metodo se encarga de preguntar que deseamos modificar sobre una habitacion y realizar la modificacion
		//Solicitamos el codigo de la habitacion a modificar
		System.out.println("ingrese el codigo de la habitacion que quiere modificar");
		int eleccion= TecladoIn.readLineInt();
		Habitacion laHabitacion= (Habitacion)tablaHabitaciones.buscarElemento((Comparable)eleccion);
		while(laHabitacion== null) {
			//Siempre que no se haya encontrado la habitacion vamos a solicitar el reingreso del codigo
			System.out.println("no se ha encontrado ninguna habitacion con dicho codigo, por favor, ingrese otro");
			eleccion= TecladoIn.readLineInt();
			laHabitacion= (Habitacion)tablaHabitaciones.buscarElemento((Comparable)eleccion);
		}
		//Mostramos un mensaje para preguntar que se desea modificar
		System.out.println("Perfecto, ya encontramos la habitacion. \n" + "	¿Que le gustaria hacer con ella? \n"
				+ "1- Modificar su nombre \n" + "2- Modificar su planta \n"
				+ "3- Modificar sus metros cuadrados \n" + "4- Modificar si tiene salida al exterior");
		eleccion = TecladoIn.readLineInt();
		switch (eleccion) {
		//Segun lo que se desee realizar vamos a ir a un distinto case
		//Finalmente registraremos en el archivo log 
		case 1: 
			System.out.println("ingrese el nuevo nombre");
			laHabitacion.setNombre(TecladoIn.readLine());
			escribir("se modifico el nombre de la habitacion con codigo: "+laHabitacion.getCodigo()+". Ahora su nombre es: \""+laHabitacion.getNombre()+"\".", rutaArchivoLog);
			break; 
		case 2: 
			System.out.println("ingrese la nueva planta");
			laHabitacion.setPlanta(TecladoIn.readLineInt());
			escribir("se modifico la planta de la habitacion con codigo: "+laHabitacion.getCodigo()+". Ahora su planta es: "+laHabitacion.getPlanta()+".", rutaArchivoLog);
			break;
		case 3: 
			System.out.println("ingrese las nuevas medidas de la habitacion en metros cuadrados");
			laHabitacion.setMetros(TecladoIn.readLineDouble());
			escribir("se modifico el tamanio de la habitacion con codigo: "+laHabitacion.getCodigo()+". Ahora su tamanio es de: "+laHabitacion.getMetros()+"mts cuardrados.", rutaArchivoLog);
			break;
		case 4: 
			System.out.println("ingrese true/false si quiere que la habitacion tenga salida o no");
			laHabitacion.setSalida(TecladoIn.readLineBoolean());
			escribir("se modifico la salida de la habitacion con codigo: "+laHabitacion.getCodigo()+". Ahora tiene salida: "+laHabitacion.getSalida()+".", rutaArchivoLog);
		default:
			System.out.println("usted ha ingresado una opcion inexistente, lo direccionaremos al menu principal");
		}
	}
	private void modificacionDesafio(String rutaArchivoLog) {
		//Este metodo se encarga de preguntar que deseamos modificar sobre un desafio y realizar la modificacion
		//Solicitamos el puntaje del desafio a modificar
		System.out.println("ingrese el puntaje del desafio que quiere modificar");
		int eleccion= TecladoIn.readLineInt();
		Desafio elDesafio= (Desafio)tablaHabitaciones.buscarElemento((Comparable)eleccion);
		while(elDesafio== null) {
			//Siempre que no se haya encontrado el desafio vamos a solicitar el reingreso del puntaje
			System.out.println("no se ha encontrado ningun desafio con dicho puntaje, por favor, ingrese otro");
			eleccion= TecladoIn.readLineInt();
			elDesafio= (Desafio)tablaHabitaciones.buscarElemento((Comparable)eleccion);
		}
		//Mostramos un mensaje para preguntar que se desea modificar
		System.out.println("Perfecto, ya encontramos el desafio. \n" + "¿Que le gustaria hacer con el? \n"
				+ "1- Modificar su nombre \n" + "2- Modificar su tipo");
		eleccion = TecladoIn.readLineInt();
		switch (eleccion) {
		//Segun lo que se desee realizar vamos a ir a un distinto case
		//Finalmente registraremos en el archivo log 
		case 1: 
			System.out.println("ingrese el nuevo nombre");
			elDesafio.setNombre(TecladoIn.readLine());
			System.out.println("\nGenial, el nombre fue modificado con exito!");
			escribir("se modifico el nombre del desafio con puntaje: "+elDesafio.getPuntaje()+". Ahora su nombre es: \""+elDesafio.getNombre()+"\".", rutaArchivoLog);
			break; 
		case 2: 
			System.out.println("ingrese el nuevo tipo");
			elDesafio.setTipo(TecladoIn.readLine());
			System.out.println("\nGenial, el tipo fue modificado con exito!");
			escribir("se modifico el tipo del desafio con puntaje: "+elDesafio.getPuntaje()+". Ahora su tipo es: \""+elDesafio.getTipo()+"\".", rutaArchivoLog);
			break;
		default: 
			System.out.println("usted ha ingresado una opcion inexistente, lo direccionaremos al menu principal");		
		}
	}
	private void modificacionEquipo(String rutaArchivoLog) {
		//Este metodo se encarga de preguntar que deseamos modificar sobre un equipo y realizar la modificacion
		//Solicitamos el nombre del equipo a modificar
		System.out.println("ingrese el nombre del equipo que quiere modificar");
		//Pasamos el nombre a minusculas para que no se cometan errores de tipeo
		String opcion= TecladoIn.readLine().toLowerCase();
		Equipo elEquipo= tablaEquipos.get(opcion);
		while(elEquipo== null) {
			//Siempre que no se haya encontrado el equipo vamos a solicitar el reingreso del nombre
			System.out.println("no se ha encontrado ningun equipo con dicho nombre, por favor, ingrese otro");
			opcion= TecladoIn.readLine().toLowerCase();
			elEquipo= tablaEquipos.get(opcion);
		}
		//Mostramos un mensaje para preguntar que se desea modificar
		System.out.println("Perfecto, ya encontramos el equipo. \n" + "	¿Que le gustaria hacer con el? \n"
				+ "1- Modificar la habitacion donde se encuentra \n" + "2- Modificar su puntaje necesario para salir de la casa \n"
				+ "3- Modificar su puntaje total \n" + "4- Modificar su puntaje actual");
		int eleccion = TecladoIn.readLineInt();
		Comparable codigo;
		switch (eleccion) {
		//Segun lo que se desee realizar vamos a ir a un distinto case
		//Finalmente registraremos en el archivo log 
		case 1: 
			System.out.println("ingrese el codigo de la habitacion donde se encuentra");
			codigo= TecladoIn.readInt();
			while(!tablaHabitaciones.pertenece(codigo)) {
				System.out.println("no se ha encontrado ninguna habitacion con dicho codigo, por favor, ingrese otro");
				codigo= TecladoIn.readLineInt();
			}
			System.out.println("perfecto! encontramos la habitacion");
			elEquipo.setHabitacion(eleccion);
			escribir("se modifico la habitacion donde se encuentra el equipo: \" "+elEquipo.getNombreEquipo()+"\". Ahora se encuentra en la habitacion con codigo "+codigo+".", rutaArchivoLog);
			break; 
		case 2: 
			System.out.println("ingrese el nuevo puntaje necesario para salir");
			elEquipo.setPuntajeExigido(TecladoIn.readLineInt());
			escribir("se modifico el puntaje necesario para salir del equipo: \" "+elEquipo.getNombreEquipo()+"\". Ahora necesita "+elEquipo.getPuntajeExigido()+" puntos para salir.", rutaArchivoLog);
			break;
		case 3: 
			System.out.println("ingrese el nuevo puntaje total");
			elEquipo.setPuntajeTotal(TecladoIn.readLineInt());
			escribir("se modifico el puntaje total del equipo: \" "+elEquipo.getNombreEquipo()+"\". Ahora tiene "+elEquipo.getPuntajeTotal()+" puntos.", rutaArchivoLog);
			break;
		case 4: 
			System.out.println("ingrese el nuevo puntaje actual");
			elEquipo.setPuntajeActual(TecladoIn.readLineInt());
			escribir("se modifico el puntaje actual del equipo: \" "+elEquipo.getNombreEquipo()+"\". Ahora tiene "+elEquipo.getPuntajeActual()+" puntos en la habitacion actual.", rutaArchivoLog);
		}
	}

	
	//INSERCION DE NUEVOS ELEMENTOS
	
	
	private void nuevaHabitacion(String cod, String nombre, String plant, String met, String sal) {
		//Este metodo se encarga de registrar las nuevas habitaciones
		//Recibimos todos los datos por parametro
		int codigo= Integer.parseInt(cod);
		int planta= Integer.parseInt(plant);
		double metros= Double.parseDouble(met);
		boolean salida= Boolean.parseBoolean(sal);
		Habitacion nuevaHabitacion= new Habitacion(codigo, nombre, planta, metros, salida);
		//Insertamos la habitacion en la tabla de habitaciones y en el plano
		tablaHabitaciones.insertar(codigo, nuevaHabitacion);
		plano.insertarVertice(codigo);
	}
	private void nuevoEquipo(String nombre, String puntajeParaSalir, String puntajeAcumulado, String habitacion, String elPuntajeActual) {
		//Este metodo se encarga de registrar los nuevos equipos
		//Recibimos todos los datos por parametro
		int puntajeExigido= Integer.parseInt(puntajeParaSalir);
		int puntajeTotal= Integer.parseInt(puntajeAcumulado);
		int puntajeActual= Integer.parseInt(elPuntajeActual);
		int habitacionActual= Integer.parseInt(habitacion);
		//buscamos la habitacion en la cual se encuentra para modificar su estado ya que ahora estara ocupada
		Habitacion laHabitacion= (Habitacion)tablaHabitaciones.buscarElemento(habitacionActual);
		//Le sumamos uno a la habitacion puesto que tenemos un equipo mas dentro de ella
		laHabitacion.setEstado(1);
		Equipo nuevo= new Equipo(nombre, puntajeExigido, puntajeActual, puntajeTotal, habitacionActual);
		//En la tabla de equipos el nombre estara en minusculas para que sea mas facil buscarlo
		tablaEquipos.put(nombre.toLowerCase(), nuevo);
		desafiosPorEquipo.put(nombre.toLowerCase(), new Lista());
		puertasUsadas.put(nombre.toLowerCase(), new Lista());
	}
	private void nuevoDesafio(String punt, String nombre, String tipo) {
		//Este metodo se encarga de registrar los nuevos desafios
		//Recibimos todos los datos por parametro
		int puntaje= Integer.parseInt(punt);
		//Creamos el desafio
		Desafio nuevo= new Desafio(puntaje, nombre, tipo);
		//luego lo agregamos a la tabla de desafios
		tablaDesafios.insertar(puntaje, nuevo);
	}
	private void nuevaPuerta(String hab1, String hab2, String puntajeEx) {
		//Este metodo se encarga de registrar las puertas o arcos del plano
		//Recibimos todos los datos por parametro
		int codigoHabitacion1= Integer.parseInt(hab1);
		int codigoHabitacion2= Integer.parseInt(hab2);
		int puntajeExigido= Integer.parseInt(puntajeEx);
		//Luego agregamos el arco entre ambas habitaciones
		plano.insertarArco(codigoHabitacion1, codigoHabitacion2, puntajeExigido);
	}

	
	
	//MOSTRAMOS UN ELEMENTO DESEADO
	
	
	private void mostrarHabitacion() {
		//Este metodo se encarga de mostrar los datos correspondientes a una habitacion
		//Solicitamos que nos ingresen el codigo
		System.out.println("ingrese el codigo de la habitacion");
		int codigo= TecladoIn.readLineInt();
		Habitacion elemento= (Habitacion)tablaHabitaciones.buscarElemento(codigo);
		while(elemento==null) {
			//Mientras no hayamos encontrado dicho codigo 
			//Vamos a pedir que ingresen otro codigo
			System.out.println("el codigo ingresado no existe! Por favor, ingrese otro");
			codigo=TecladoIn.readLineInt();
			elemento= (Habitacion)tablaHabitaciones.buscarElemento(codigo);
		}
		//Mostramos por pantalla los datos
		System.out.println(elemento.toString());
	}
	private void mostrarDesafio() {
		//Este metodo se encarga de mostrar los datos correspondientes a un desafio
		//Solicitamos que nos ingresen el puntaje
		System.out.println("ingrese el puntaje del desafio");
		int puntaje= TecladoIn.readLineInt();
		Desafio elemento= (Desafio)tablaDesafios.buscarElemento(puntaje);
		while(elemento==null) {
			//Mientras no hayamos encontrado dicho puntaje
			//Vamos a pedir que ingresen otro puntaje
			System.out.println("el puntaje ingresado no existe! Por favor, ingrese otro");
			puntaje=TecladoIn.readLineInt();
			elemento= (Desafio)tablaDesafios.buscarElemento(puntaje);
		}
		//Mostramos por pantalla los datos
		System.out.println(elemento.toString());
	}
	private void mostrarInfoEquipo() {
		//Este metodo se encarga de mostrar los datos correspondientes a un equipo
		//Solicitamos que nos ingresen el nombre
		System.out.println("ingrese el nombre del equipo");
		String nombreDelEquipo= TecladoIn.readLine();
		Equipo elemento= tablaEquipos.get(nombreDelEquipo.toLowerCase());
		while(elemento==null) {
			//Mientras no hayamos encontrado dicho nombre
			//Vamos a pedir que ingresen otro nombre
			System.out.println("el nombre ingresado no existe! Por favor, ingrese otro");
			nombreDelEquipo=TecladoIn.readLine();
			elemento= tablaEquipos.get(nombreDelEquipo.toLowerCase());
		}
		//Mostramos por pantalla los datos
		System.out.println(elemento.toStringLargo());
	}
	
	
	//CONSULTAS PROPIAS SOBRE EQUIPOS
	
	
	public void jugarDesafio() {
		//Este metodo marca un desafio como jugado para cierto equipo
		//Solicitamos el nombre del equipo
		System.out.println("ingrese el nombre del equipo");
		String equipo= TecladoIn.readLine();
		Lista listaDesafios= desafiosPorEquipo.get(equipo.toLowerCase());
		while(listaDesafios==null) {
			//Mientras no hayamos encontrado la lista de desafios vamos a pedir otro nombre
			System.out.println("el nombre ingresado no existe! Por favor, ingrese otro");
			equipo=TecladoIn.readLine();
			listaDesafios= desafiosPorEquipo.get(equipo.toLowerCase());
		}
		//Solicitamos el puntaje del desafio a marcar como jugado
		System.out.println("ingrese el puntaje del desafio a marcar como jugado");
		Comparable puntaje= TecladoIn.readLineInt();
		Desafio elDesafio= (Desafio)tablaDesafios.buscarElemento(puntaje);
		boolean exito= false;
		while(elDesafio==null) {
			//Mientra no hayamos encontrado el desafio vamos a pedir otro puntaje
			System.out.println("el puntaje ingresado no corresponde a ningun desafio! Por favor, ingrese otro");
			elDesafio= (Desafio)tablaDesafios.buscarElemento(TecladoIn.readInt());
		}
		if(listaDesafios.localizar(elDesafio.getPuntaje())<0) {
			//Si el equipo no fue juegado todavia procedemos
			listaDesafios.insertar(puntaje,1);
			Equipo elEquipo= tablaEquipos.get(equipo.toLowerCase());
			elEquipo.setPuntajeActual(elEquipo.getPuntajeActual()+elDesafio.getPuntaje());
			elEquipo.setPuntajeTotal(elEquipo.getPuntajeTotal()+elDesafio.getPuntaje());
			exito= true;
		}	
		if(exito) {
			System.out.println("\nMarcamos el desafio como jugado!");
		}
		else {
			System.out.println("\nNo se ha podido marcar como jugado al desafio porque ya habia sido jugado antes");
		}
	}
	public void pasarAHabitacion() {
		//Dado un equipo eq y una habitación hab, verificar si es posible que el equipo eq pase a la habitación hab 
		//(considerando si es contigua a la actual y el puntaje acumulado es suficiente)
		//y en caso afirmativo actualizar los datos del equipo apropiadamente.
		boolean pudoPasar= false;
		//Solicitamos los datos para encontrar el equipo
		System.out.println("ingrese el nombre del equipo");
		String nombre= TecladoIn.readLine().toLowerCase();
		Equipo elEquipo= tablaEquipos.get(nombre);
		while(elEquipo==null) {
			//pedimos que ingrese un equipo valido
			System.out.println("el nombre ingresado no existe! Por favor, ingrese otro");
			nombre=TecladoIn.readLine().toLowerCase();
			elEquipo= tablaEquipos.get(nombre);
		}
		System.out.println("ingrese el codigo de la habitacion a la cual quiere mover el equipo");
		int codigo= TecladoIn.readLineInt();
		//Debemos verificar si ya estuvimos en dicha habitacion
		Lista contiguas= plano.listarAdyacentes(elEquipo.getHabitacion());
		if(elEquipo.getHabitacion()!=codigo) {
			//si la habitacion no es la misma en la cual nos encontramos
			if(contiguas.localizar(codigo)>=0) {
				//si la habitacion a la cual queremos pasar es contigua seguiremos operando
				int encontrado;
				boolean menor= false;
				Lista puertasYaUsadas=puertasUsadas.get(nombre);
				if(codigo<=elEquipo.getHabitacion()) {
					encontrado= puertasYaUsadas.localizar(codigo+""+elEquipo.getHabitacion());
					menor=true;
				}
				else {
					encontrado= puertasYaUsadas.localizar(elEquipo.getHabitacion()+""+codigo);
				}
				if(encontrado<0) {
					//si todavia no pasamos por esta puerta entonces tendremos que operar con todos los datos sobre los puntajes
					Object puntajeNecesario= plano.etiquetaArco(elEquipo.getHabitacion(), codigo);
					if(puntajeNecesario!= null) {
						if(elEquipo.getPuntajeActual()>=(int)puntajeNecesario) {
							Habitacion laHabitacion= (Habitacion)tablaHabitaciones.buscarElemento(elEquipo.getHabitacion());
							//Le restamos uno a el estado de la habitacion puesto que ahora tendra una habitacion menos en ella
							laHabitacion.setEstado(-1);
							elEquipo.setPuntajeActual(0);
							if(menor) {
								puertasYaUsadas.insertar(codigo+""+elEquipo.getHabitacion(), 1);
							}
							else {
								puertasYaUsadas.insertar(elEquipo.getHabitacion()+""+codigo,1);
							}
							elEquipo.setHabitacion(codigo);
							pudoPasar= true;
							laHabitacion= (Habitacion)tablaHabitaciones.buscarElemento(codigo);
							laHabitacion.setEstado(1);
						}
						else {
							System.out.println("\nEl equipo no ha podido pasar porque no le alcanza el puntaje");
						}
					}
					else {
						System.out.println("\nHubo un error debido a que la puerta por la que quiso pasar no tiene un valor asignado");
					}
				}
				else {
					//si ya pasamos 
					elEquipo.setHabitacion(codigo);
					pudoPasar= true;
				}
			}
			else {
				System.out.println("\nEl equipo no ha podido pasar porque la habitacion no es contigua");
			}
		}
		else {
			System.out.println("\nEl equipo no ha podido pasar porque ya se encuentra en la habitacion ingresada");
		}
		if(pudoPasar) {
			System.out.println("\nEl equipo ha podido pasar a la habitacion "+codigo);
		}
	}
	public void puedeSalir() {
		//Este metodo nos dice si un equipo puede salir o no
		//Teniendo en cuenta si tiene los puntos necesarios y si la habitacion tiene salida al exterior
		System.out.println("ingrese el nombre del equipo");
		String nombre= TecladoIn.readLine().toLowerCase();
		Equipo elEquipo= tablaEquipos.get(nombre);
		boolean puede=false, puedePorSalida=false;
		while(elEquipo==null) {
			//pedimos que ingrese un equipo valido
			System.out.println("el nombre ingresado no existe! Por favor, ingrese otro");
			nombre=TecladoIn.readLine().toLowerCase();
			elEquipo= tablaEquipos.get(nombre);
		}
		if(elEquipo.getPuntajeExigido()<=elEquipo.getPuntajeTotal()) {
			puede=true;
			Habitacion habitacionActual= (Habitacion)tablaHabitaciones.buscarElemento(elEquipo.getHabitacion());
			puedePorSalida= habitacionActual.getSalida();
		}
		if(puede && puedePorSalida) {
			System.out.println("\nEl equipo si puede salir");
		}
		else {
			if(puedePorSalida) {
				System.out.println("\nEl equipo no puede salir, le faltan "+(elEquipo.getPuntajeExigido()-elEquipo.getPuntajeTotal())+" puntos");
			}
			else {
				System.out.println("\nEl equipo no puede salir porque la habitacion en la que se encuentra no tiene salida");
			}
		}
	}
	
	
	//CONSULTAS PROPIAS SOBRE HABITACIONES
	
	
	public void habitacionesContiguas() {
		//Este metodo nos muestra una lista de todas las habitaciones contiguas a una ingresada por teclado
		//Solicitamos el codigo de la habitacion
		System.out.println("ingrese el codigo de la habitacion");
		int codigo=TecladoIn.readLineInt();
		Lista listado= plano.listarAdyacentes(codigo);
		Lista retorno= new Lista();
		int adyacente;
		int puntajeNecesario;
		while(!listado.esVacia()) {
			//Mientras que el listado de habitaciones contiguas no este vacio vamos a iterar
			adyacente= (int)listado.recuperar(1);
			//Vamos a obtener el valor o la etiqueta que hay entre la habitacion y cada contigua
			puntajeNecesario= (int)plano.etiquetaArco(codigo, adyacente);
			//Insertamos la informacion en una lista que llamaremos retorno
			retorno.insertar( "("+adyacente+", "+puntajeNecesario+")", 1);
			listado.eliminar(1);
		}
		//Mostramos el mensaje que corresponda
		if(retorno.esVacia()) {
			System.out.println("\nLa habitacion no tiene contiguas");
		}
		else {
			System.out.println("\nEstas son las habitaciones y los puntajes necesarios para pasar a ellas: "+retorno.toString());
		}
	}
	public void esPosibleLlegar() {
		//Este metodo nos dice si es posible llegar de una habitacion a otra
		//Solicitamos los datos
		System.out.println("ingrese el codigo de la habitacion de partida");
		int habitacion1=TecladoIn.readLineInt();
		System.out.println("ingrese el codigo de la habitacion destino");
		int habitacion2=TecladoIn.readLineInt();
		System.out.println("ingrese la cantidad de puntos acumulados");
		int puntaje=TecladoIn.readLineInt();
		boolean sePuede= false;
		if(habitacion1==habitacion2) {
			//Tenemos en cuenta el caso en el cual nos ingesaron las mismas habitaciones
			System.out.println("\nDado que las habitaciones ingresadas son las mismas, si es posible llegar");
		}
		else {
			if(tablaHabitaciones.pertenece(habitacion1) && tablaHabitaciones.pertenece(habitacion2) ) {
				//Chequeamos si ambas habitaciones existen para no realizar recorridos de mas 
				Lista camino= plano.caminoMasCorto(habitacion1, habitacion2);
				if(!camino.esVacia()) {
					//si existe un camino entre las habitaciones 
					//vamos a crear dos variables de tipo Object para ir recorriendo la lista del camino
					Object vertice1=null;
					Object vertice2= null;
					while(!camino.esVacia() && puntaje>=0) {
						//mientras que la lista que contiene el camino no este vacia y el puntaje restante sea mayor o igual que 0
						vertice1= camino.recuperar(1);
						camino.eliminar(1);
						vertice2= camino.recuperar(1);
						if(vertice2!= null) {
							//vamos a restar el puntaje que nos cuesta para pasar de habitacion a habitacion
							puntaje=puntaje - (int)plano.etiquetaArco(vertice1, vertice2);
						}
					}
					//finalmente, si el puntaje es menor que 0 significa que no es suficiente para llegar a la habitacion
					sePuede=puntaje>=0;
					//Mostramos el mensaje correspondiente
					if(sePuede) {
						System.out.println("\nExiste un camino que cumple con dicha condicion");
					}
					else {
						System.out.println("\nNo existe un camino que cumpla con la condicion del puntaje");
					}
				}
				else {
					System.out.println("\nNo existe camino entre dichas habitaciones");
				}
			}
			else {
				System.out.println("\nHa ingresado por lo menos una habitacion inexistente");
			}
		}
	}
	public void sinPasarPor() {
		//este metodo devuelve todos los caminos que vayan de la habitacion 1 a la habitacion 2 sin pasar por una determinada y que no requieran conseguir mas que un puntaje ingresado
		//Solicitamos los datos corespondientes
		System.out.println("ingrese el codigo de la habitacion de partida");
		int habitacion1=TecladoIn.readLineInt();
		System.out.println("ingrese el codigo de la habitacion destino");
		int habitacion2=TecladoIn.readLineInt();
		System.out.println("ingrese el codigo de la habitacion por la cual no quiere pasar");
		int habitacionProhibida=TecladoIn.readLineInt();
		System.out.println("ingrese la cantidad de puntos maximos");
		int puntajeMaximo=TecladoIn.readLineInt();
		//Obtenemos una lista con el posible camino entre ambas habitaciones
		Lista elCamino=plano.caminosSinMasPuntaje(habitacion1, habitacion2, habitacionProhibida, puntajeMaximo);
		//Mostramos el mensaje correspondiente
		if(elCamino.esVacia()) {
			System.out.println("\nNo existe un camino que reuna las condiciones solicitadas");
		}
		else {
			System.out.println("\nEl camino que mejor cumple las condiciones es: "+elCamino.toString());
		}
	}

	
	//CONSULTAS PROPIAS SOBRE DESAFIOS
	
	public void mostrarDesafiosResueltos() {
		//Este metodo nos muestra los desafios resueltos por un equipo dado
		//Solicitamos los datos correspondientes
		System.out.println("ingrese el equipo del cual quiere saber los desafios resueltos");
		String nombreEquipo= TecladoIn.readLine().toLowerCase();
		Lista listado=desafiosPorEquipo.get(nombreEquipo);
		while(listado==null) {
			//Si no encontramos el listado de desafios resueltos es porque no encontramos el equipo
			//En ese caso solicitamos los datos otra vez
			System.out.println("el nombre ingresado no existe! Por favor, ingrese otro");
			nombreEquipo=TecladoIn.readLine().toLowerCase();
			listado= desafiosPorEquipo.get(nombreEquipo);
		}
		//Mostramos por pantalla lo correspondiente-
		System.out.println("\nLos desafios resueltos son: "+listado.toString());
	}
	public void mostrarDesafiosTipo() {
		//Este metodo se encarga de mostrar los desafios de un determinado tipo 
		//Y que esten en un rango de puntaje dado
		//Solicitamos los datos correspondientes
		System.out.println("ingrese el puntaje minimo");
		int minimo= TecladoIn.readLineInt();
		System.out.println("ingrese el puntaje maximo");
		int maximo= TecladoIn.readLineInt();
		System.out.println("ingrese el tipo");
		String tipo= TecladoIn.readLine();
		Lista losDesafios= new Lista();
		//Obtenemos los desafios que cumplen con el rango de puntaje
		Cola postulantes= tablaDesafios.listarRango(minimo, maximo);
		while(!postulantes.esVacia()) {
			//Vamos a ir guardando los desafios que cumplan con el tipo deseado
			Object referenciaDesafio= postulantes.obtenerFrente();
			Desafio unDesafio= (Desafio)tablaDesafios.buscarElemento((Comparable)referenciaDesafio);
				if(unDesafio.getTipo().equalsIgnoreCase(tipo)) {
				losDesafios.insertar(unDesafio.getNombre(), 1);
			}
			postulantes.sacar();
		}
		//Mostramos por pantalla el mensaje que corresponda
		if(losDesafios.esVacia()) {
			System.out.println("\nNo hay desafios que cumplan con dichas condiciones");
		}
		else {
			System.out.println("\nLos desafios que cumplen las condiciones son: "+losDesafios.toString());
		}
	}
	
	
	//MOSTRAR EL SISTEMA
	
	public String mostrarSistema() {
		//Este metodo se encarga de mostrar como estan las estructuras
		String todasLasEstructuras="TABLA DE BUSQUEDA DE HABITACIONES: \n"
									+ tablaHabitaciones.toString()+"\n"
									+"TABLA DE BUSQUEDA DE DESAFIOS: \n"
									+ tablaDesafios.toString()+"\n"
									+"TABLA HASH DE EQUIPOS: \n"
									+ tablaEquipos.toString()+"\n"
									+"TABLA DE DESAFIOS POR EQUIPO: \n"
									+ desafiosPorEquipo.toString()+"\n"
									+"MAPA DE LA CASA: \n"
									+ plano.toString(); 
		return todasLasEstructuras;
	}
	
	
	//MENU PRINCIPAL
	
	
	public static void main(String[] args) {
		EscapeHouse nuevoEscapeHouse= new EscapeHouse();
		System.out.println("Hola, bienvenido a EscapeHouse! \n"
						+ "Como primer paso para iniciarte, voy a necesitar que ingreses una direccion de un archivo log. \n"
						+ "Con el podremos llevar cuenta de como va trabajando el programa ;)");
		//Un ejemplo de una direccion es "D:\\FACULTAD\\EDAT\\TrabajoFinal\\devolucion.txt"
		String rutaParaEscribir= TecladoIn.readLine();
		System.out.println("Por cierto! Tambien ingrese la direccion del archivo txt desde el cual desee realizar la carga");
		String rutaParaLeer= TecladoIn.readLine();
		boolean salir= false;
		while(!salir) {
			System.out.println("");
			salir= nuevoEscapeHouse.menu(rutaParaEscribir, rutaParaLeer);
		}
	    
	    }
}
