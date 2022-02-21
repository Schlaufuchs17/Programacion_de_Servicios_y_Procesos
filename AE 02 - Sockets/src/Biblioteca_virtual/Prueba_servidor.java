package Biblioteca_virtual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Prueba_servidor {

	public static final int PUERTO = 2017;

	public static void main(String[] args) throws InterruptedException {
		System.out.println("      APLICACION DE SERVIDOR      ");
		System.out.println("----------------------------------");

		// creo los libros de la biblioteca:
		ArrayList<Libros> biblioteca = new ArrayList<>();
		biblioteca.add(new Libros("A", "david", "titulo", "12"));
		biblioteca.add(new Libros("E", "OLGA", "así empezo todo", "45.98"));
		biblioteca.add(new Libros("F", "MACHADO", "l", "123"));
		biblioteca.add(new Libros("B", "PEDRO", "el perro de san roque", "500"));
		biblioteca.add(new Libros("C", "MARCOS", "viviendo en el campo", "345"));
		biblioteca.add(new Libros("D", "OLGA", "ghghghghghgh", "56"));

		// Entrada de datos. Es el canal de entrada del servidor, es decir, el canal por
		// el cual el cliente nos va a mandar la informacion.
		InputStreamReader entrada = null;

		// Salida de datos. Es el canal de salida del servidor, es decir, el canal por
		// el cual vamos a enviar informacion al cliente.

		PrintStream salida = null;

		// 1. El PrintStream del cliente es el InputStreamReader del servidor
		// 2. El PrintStream del servidor es el InputStreamReader del cliente

		Socket socketAlCliente = null;

		InetSocketAddress direccion = new InetSocketAddress(PUERTO);

		try (ServerSocket serverSocket = new ServerSocket()) {

			serverSocket.bind(direccion);

			int peticion = 0;// numero de peticones

			while (true) {

				System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);

				socketAlCliente = serverSocket.accept();
				System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");

				entrada = new InputStreamReader(socketAlCliente.getInputStream());
				BufferedReader bf = new BufferedReader(entrada);

				String stringRecibido = bf.readLine();

				// Hay que tener en cuenta que toda comunicacion entre cliente y servidor
				// esta en formato de cadena de texto
				System.out.println("SERVIDOR: Me ha llegado del cliente: " + stringRecibido);
				// Como sabemos que el cliente nos envia String, hacemos un split por "-"
				// para obtener la informacion.
				String[] operadores = stringRecibido.split("-");
				String opcion = operadores[0];
				String datos = operadores[1];

				switch (opcion) {

				case "1":

					String resultado = null;
					for (int x = 0; x < biblioteca.size(); x++) {
						Libros p = biblioteca.get(x);
						if (p.getISBN().equals(datos)) {
							resultado = p.toString();
							salida = new PrintStream(socketAlCliente.getOutputStream());
							salida.println("RESULTADO DE LA BUSQUEDA : " + resultado);

						}
					}
					break;
				case "2":

					resultado = null;
					for (int x = 0; x < biblioteca.size(); x++) {

						Libros p = biblioteca.get(x);
						if (p.getTitulo().equals(datos)) {
							resultado = p.toString();
							salida = new PrintStream(socketAlCliente.getOutputStream());
							salida.println("RESULTADO DE LA BUSQUEDA : " + resultado);

						}
					}
					break;

				case "3":

					ArrayList<String> Librosautores = new ArrayList<String>();

					for (Libros a : biblioteca) {
						if (a.getAutor().equals(datos))

							Librosautores.add(a.toString());

					}
					salida = new PrintStream(socketAlCliente.getOutputStream());
					salida.println(" LIBROS DEL AUTOR : " + Librosautores);
					break;

				case "5":

					String a = operadores[1];
					String b = operadores[2];
					String c = operadores[3];
					String d = operadores[4];

					biblioteca.add(new Libros(a, b, c, d));

					salida = new PrintStream(socketAlCliente.getOutputStream());
					salida.println(" NUEVO LIBRO INTRODUCIDO : " + biblioteca.get(biblioteca.size() - 1));
					break;
				}

				socketAlCliente.close();

			}

			// Mandamos el resultado al cliente

		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error -> " + e);
			e.printStackTrace();
		}
	}
}


