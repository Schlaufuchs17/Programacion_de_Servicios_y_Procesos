package controlador;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import coches.Coche;

public class Simetrica {
	public static boolean salir = false;
	public static int opcion; // Guardaremos la opcion del usuario
	static Scanner sn = new Scanner(System.in);
	static Scanner sc = new Scanner(System.in);
	static String datos;
	static String matricula;
	static String marca;
	static String modelo;
	static String precio;

	static byte[] bytesMensajeCifrado;
	static String mensajeCifrado;
	static String mensajeDescifrado;

	public static void SIME() throws IOException, Exception {

		System.out.println("Probando sistema de encriptacion con algoritmo AES");
		while (!salir) {
			// SOLO SE GUARDA 1 FRASE CADA VEZ
			System.out.println("1. Opcion 1: ENCRIPTACION DE FRASE ");
			System.out.println("2. Opcion 2: MOSTRAR FRASE ENCRIPTADA 'NO LEGIBLE' ");
			System.out.println("3. Opcion 3: MOSTRAR FRASE DESENCRIPTADA ");
			System.out.println("4. Opcion 4: ENCRIPTAR COCHE ");
			System.out.println("6. Salir ");
			System.out.println("Escribe una de las opciones");
			opcion = sn.nextInt();

			try {
				KeyGenerator generador = KeyGenerator.getInstance("AES");
			    
				SecretKey paloEspartano = generador.generateKey();
				Cipher cifrador = Cipher.getInstance("AES");
			

				switch (opcion) {
				case 1:
					 System.out.println( );
					System.err.println("*********** ENCRIPTACION DE FRASE  ***************");

					System.out.println("POR FAVOR INTRODUCE LA FRASE ");
					datos = sc.nextLine();
					byte[] bytesMensajeOriginal = datos.getBytes();
					cifrador.init(Cipher.ENCRYPT_MODE, paloEspartano);
					byte[] bytesMensajeCifrado = cifrador.doFinal(bytesMensajeOriginal);
					mensajeCifrado = new String(bytesMensajeCifrado);
					cifrador.init(Cipher.DECRYPT_MODE, paloEspartano);
					byte[] bytesMensajeDescifrado = cifrador.doFinal(bytesMensajeCifrado);

					mensajeDescifrado = new String(bytesMensajeDescifrado);
                     System.out.println();
					break;
				case 2:
					 System.out.println( );
					System.err.println("*********** MOSTRAR FRASE ENCRIPTADA ***************");

					System.out.println(" Mensaje Cifrado: " + mensajeCifrado);
                    System.out.println();
					break;

				case 3:// aqui muestro el mensaje desencriptado
					 System.out.println( );
					System.err.println("*********** MOSTRAR FRASE DESENCRIPTADA ***************");
					System.out.println(" Mensaje Descifrado: " + mensajeDescifrado);
                    System.out.println( );
					break;
				case 4:
					 System.out.println( );
					System.err.println("*********** ENCRIPTAR COCHE ***************");

					System.out.println("POR FAVOR INTRODUCE LA MATRICULA ");
					matricula = sc.nextLine();
					System.out.println("POR FAVOR INTRODUCE LA MARCA");
					marca = sc.nextLine();
					System.out.println("POR FAVOR INTRODUCE LA MODELO");
					modelo = sc.nextLine();
					System.out.println("POR FAVOR INTRODUCE EL PRECIO");
					precio = sc.nextLine();

					Coche coche = new Coche(matricula, marca, modelo, precio);
					// AQUI CIFRO EL COCHE
					KeyGenerator generador_clave_coche = KeyGenerator.getInstance("AES");
					SecretKey paloCoche = generador_clave_coche.generateKey();
					Cipher cifrador_coche = Cipher.getInstance("AES");
					cifrador_coche.init(Cipher.ENCRYPT_MODE, paloCoche);
					SealedObject so = new SealedObject(coche, cifrador_coche);
					System.out.println(" COCHE CIFRADO: " + so);
					// AQUI DESCIFRO EL COCHE
					cifrador_coche.init(Cipher.DECRYPT_MODE, paloCoche);
					Coche coche_descifrado = (Coche) so.getObject(cifrador_coche);
					System.out.println("COCHE DESCIFRADO : " + coche_descifrado);
                    System.out.println();
					break;

				case 6:
					 System.out.println( );
					System.err.println(" VOLVIENDO AL MENU PRINCIPAL  ");
					salir = true;
					break;
				default:
					System.out.println("Solo números entre 1 y 6");
				}
				// Simplificamos las excepciones capturando GeneralSecurityException
			} catch (GeneralSecurityException gse) {
				System.out.println("Algo ha fallado.." + gse.getMessage());
				gse.printStackTrace();
			}

		}

	}
}
