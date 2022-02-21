package Biblioteca_virtual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class prueba_cliente {
	public static final int PUERTO = 2017;
	public static final String IP_SERVER = "localhost";

	public static void main(String[] args) {

		System.out.println("        APLICACION CLIENTE         ");
		System.out.println("-----------------------------------");
		String isbn =null;
		
		String autor =null;
		String datos=null;
		String titulo =null;
		Object objeto=null;
		String precio =null;
		String nuevolibro=null;
		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);

		boolean continuar = true;
		System.out.println("NUMERO 1 CONSULTAR ISBN");
		System.out.println("NUMERO 2 CONSULTAR TITULO");
		System.out.println(" PARA SALIR PULSE CUALQUIER OTRO NUMERO ");
		try (Scanner sc = new Scanner(System.in);) {

			

			do {
				System.out.println("NUMERO 1 CONSULTAR ISBN");
				System.out.println("NUMERO 2 CONSULTAR TITULO");
				System.out.println("NUMERO 3 INSERTAR NUEVO LIBRO");
				System.out.println(" PARA SALIR PULSE 9 ");
				System.out.println("Introduzca OPCION");
				String opcion = sc.nextLine();
				
				switch (opcion) {
				
				case "1":
					System.out.println("INTRODUZCA ISBN PARA BUSCAR");
					String buscar =sc.nextLine();
					 datos= opcion + "-" + buscar;
					break;
					
					
				case "2":
					System.out.println("INTRODUZCA TITULO PARA BUSCAR");
					    buscar =sc.nextLine();
					 datos= opcion + "-" + buscar;
					break;
					
				case "3":
					System.out.println("INSERTE objeto");
					 objeto =sc.nextLine();
					System.out.println("INSERTE ISBN");
					 isbn =sc.nextLine();
					System.out.println("INSERTE AUTOR");
					 autor =sc.nextLine();
					System.out.println("INSERTE TITULO");
					 titulo =sc.nextLine();
					System.out.println("INSERTE PRECIO");
					 precio =sc.nextLine();
					 datos= opcion + "-" + objeto + "-" +  isbn + "-" + autor + "-" + titulo + "-" + precio;
					 break;
					 
				}
				
				
				Socket socket = new Socket();
				socket.connect(direccionServidor);
				System.out.println("CLIENTE: Esperando a que el servidor acepte la conexion");
				System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + " por el puerto " + PUERTO);

				PrintStream salida = new PrintStream(socket.getOutputStream());
				salida.println(datos);

				System.out.println("CLIENTE: Esperando al resultado del servidor...");
				InputStreamReader entrada = new InputStreamReader(socket.getInputStream());
				BufferedReader bf = new BufferedReader(entrada);
				String resultado = bf.readLine();
				System.out.println("CLIENTE: El resultado de la busqueda : " + resultado);

				System.out.println("CLIENTE: Quiere continuar? S/N");
				String sContinuar = sc.nextLine();
				if (sContinuar.equalsIgnoreCase("n")) {
					continuar = false;
				}
				socket.close();
			} while (continuar);

		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la direccion" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}

		System.out.println("CLIENTE: Fin del programa");
	}

}