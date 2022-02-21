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
	private static final String String = null;

	public static void main(String[] args) throws InterruptedException {
		System.out.println("      APLICACI�N DE SERVIDOR      ");
		System.out.println("----------------------------------");
		ArrayList<Libros> biblioteca = new ArrayList<>();

		Libros l1 = new Libros("A", "MACHADO", "l", "123");
		Libros l2 = new Libros("B", "PEDRO", "el perro de san roque", "500");
		Libros l3 = new Libros("C", "MARCOS", "viviendo en el campo", "345");
		Libros l4 = new Libros("D", "JESUS", "hoy comemos lentejas", "12");
		Libros l5 = new Libros("E", "OLGA", "así empezo todo", "45.98");
		Libros l6 = new Libros("E", "OLGA", "ghghghghghgh", "56");
		biblioteca.add(l1);
		biblioteca.add(l2);
		biblioteca.add(l3);
		biblioteca.add(l4);
		biblioteca.add(l5);
		biblioteca.add(l6);
		for (Libros v : biblioteca) {

			System.out.println(v.getISBN());
		InputStreamReader entrada = null;
		PrintStream salida = null;
		Socket socketAlCliente = null;

		InetSocketAddress direccion = new InetSocketAddress(PUERTO);
		try (ServerSocket serverSocket = new ServerSocket()) {
			serverSocket.bind(direccion);
			int peticion = 0;
			while (true) {

				System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);
				socketAlCliente = serverSocket.accept();
				System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");

				entrada = new InputStreamReader(socketAlCliente.getInputStream());
				BufferedReader bf = new BufferedReader(entrada);
				String stringRecibido = bf.readLine();
				System.out.println("SERVIDOR: Me ha llegado del cliente: " + stringRecibido);
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
							salida.println(resultado);
							
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
							salida.println(resultado);
							
						}
					}
					break;
				case "3":
					

					String a = operadores[2];
					String b = operadores[3];
					String c = operadores[4];
					String d = operadores[5];
					Object f = datos;

					biblioteca.add((Libros) (f = new Libros(a, b, c, d)));

					salida = new PrintStream(socketAlCliente.getOutputStream());
					salida.println(f.toString());
					break;
				}

				socketAlCliente.close();

			}
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error -> " + e);
			e.printStackTrace();
		}
	}
}