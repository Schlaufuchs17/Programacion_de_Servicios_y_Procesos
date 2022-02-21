package Biblioteca_virtual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketCliente {

	public static void main(String[] args) {
		System.out.println("Cliente");
		Scanner lector=new Scanner(System.in);

		try {
			Socket c=new Socket();
			InetSocketAddress localhostS=new InetSocketAddress("localhost",2017);
			System.out.println("Esperando al servidor");
			c.connect(localhostS);
			System.out.println("Conexión establecida con el servidor");
			
			InputStream entrada=c.getInputStream();
			OutputStream salida=c.getOutputStream();
			
			String libro="";
			String opc;
			System.out.println("1: Buscar por Titulo");
			System.out.println("2: Buscar por ISBN");
			System.out.println("3: Buscar por Autor");
			System.out.println("4: Añadir Libro");
			System.out.println("5: Cerrar Servidor");
			opc=lector.nextLine();
			boolean sw=true;
			while(true){				
				switch (opc.toUpperCase()) {
				case "1":
				case "2":
				case "3":
					while(sw==true){
				if(!libro.equalsIgnoreCase("Fin")){
					System.out.println("Consultar libro; 'Fin' para cerrar conexión");
					libro=lector.nextLine();
					salida.write(libro.getBytes());
					byte[] mensaje=new byte[100];
					entrada.read(mensaje);
					System.out.println("Servidor responde: "+ new String(mensaje));
				}
				else{
					System.out.println("Ha decidido cerrar la conexión");
					lector.close();
					entrada.close();
					salida.close();
					c.close();
					System.out.println("La conexión esta cerrada");
					sw=false;
				}
				}
			break;
				case "L":
				while(sw==true){
				if(!libro.equalsIgnoreCase("Fin")){
					String lb="L";
					salida.write(lb.getBytes());
					byte[] mensajelb=new byte[100];
					System.out.println("Añadir libro; 'Fin' para cerrar conexión");
					System.out.println("1. Introduce Titulo");
					String isbn=lector.nextLine();
					System.out.println("2. Introduce ISBN");
					String titulo=lector.nextLine();
					System.out.println("3. Introduce Autor 1");
					String autor1=lector.nextLine();
					System.out.println("4. Introduce Autor 2");
					String autor2=lector.nextLine();
					System.out.println("5. Introduce Importe");
					String precio=lector.nextLine();
					
					salida.write(isbn.getBytes());
					salida.write(titulo.getBytes());
					salida.write(autor1.getBytes());
					salida.write(autor2.getBytes());
					salida.write(precio.getBytes());
					
					byte[] mensaje=new byte[100];
					entrada.read(mensaje);
					System.out.println("El servidor dice: "+ new String(mensaje));
				}
				else{
					System.out.println("Se ha cerrado la conexión");
					lector.close();
					entrada.close();
					salida.close();
					c.close();
					System.out.println("Conexion cerrada");
					sw=false;
				}
				}
			break;
			
			case "C":
				while(sw==true){
					System.out.println("Se ha cerrado la conexión");
					
					lector.close();
					entrada.close();
					salida.close();
					c.close();
					System.out.println("Conexion cerrada");
					sw=false;
					break;
				}
				}
				}
		}catch(UnknownHostException e) {
			System.out.println("No se puede establecer la conexión con el servidor);
			System.out.println(e.getMessage());
			}
			catch(IOException e) {
				System.out.println("Error de entrada");
				System.out.println(e.getMessage());
			}
		}
	}