package serviciorest.cliente;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Videojuego;

import serviciorest.cliente.servicio.ServicioProxyVideojuego;

@SpringBootApplication
public class Application implements CommandLineRunner {

	// Primero inyectaremos todos los objetos que necesitamos para
	// acceder a nuestro ServicioRest, el ServicioProxyPersona y el
	// ServicioProxyMensaje
	@Autowired
	private ServicioProxyVideojuego spp;



	// Tambien necesitaremos acceder al contexto de Spring para parar
	// la aplicacion, ya que esta app al ser una aplicacion web se
	// lanzará en un tomcat. De esta manera le decimos a Spring que
	// nos inyecte su propio contexto.
	@Autowired
	private ApplicationContext context;

	// En este metodo daremos de alta un objeto de tipo RestTemplate que sera
	// el objeto mas importante de esta aplicacion. Sera usado por los
	// objetos ServicioProxy para hacer las peticiones HTTP a nuestro
	// servicio REST. Como no podemos anotar la clase RestTemplate porque
	// no la hemos creado nosotros, usaremos la anotacion @Bean para decirle
	// a Spring que ejecute este metodo y meta el objeto devuelto dentro
	// del contexto de Spring con ID "restTemplate" (el nombre del metodo)

	public boolean salir = false;
	public static int opcion; // Guardaremos la opcion del usuario
	static Scanner sn = new Scanner(System.in);
	static Scanner sc = new Scanner(System.in);
	String datos;
	int nota;
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// Metodo main que lanza la aplicacion
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(Application.class, args);

		// Notese que como este metodo es estatico no podemos acceder
		// a los metodos dinamicos de la clase, como el "spp" o "spm"
		// Para solucionar esto, haremos que nuestra clase implemente
		// "CommandLineRunner" e implementaremos el metodo "run"
		// Cuando se acabe de arrancar el contexto, se llamara automaticamente
		// al metodo run

	}

	// Este metodo es dinamico por la tanto ya podemos acceder a los atributos
	// dinamicos
	@Override
	public void run(String... args) throws Exception {
		System.out.println("****** Arrancando el cliente REST ******");
		
		while (!salir) {

			System.out.println("1. Opcion 1: NUEVO VIDEOJUEGO ");
			System.out.println("2. Opcion 2: BUSCAR VIDEOJUEGO POR ID ");
			System.out.println("3. Opcion 3: MODIFICAR VIDEOJUEGO POR ID ");
			System.out.println("4. Opcion 4: LISTAR TODOS LOS  VIDEOJUEGOS ");
			System.out.println("5. Opcion 5: BORRAR VIDEOJUEGO POR ID ");
			System.out.println("6. Salir ");

			System.out.println("Escribe una de las opciones");
			opcion = sn.nextInt();
			switch (opcion) {
			case 1:
				System.out.println("Has seleccionado la opcion 1");
				System.out.println("*********** ALTA VIDEOJUEGO ***************");
				Videojuego videojuego = new Videojuego();
				System.out.println("POR FAVOR INTRODUCE EL NOMBRE");
				datos= sc.next();
				videojuego.setNombre(datos);
				System.out.println("POR FAVOR INTRODUCE LA COMPAÑIA");
				datos= sc.next();
				videojuego.setCompañia(datos);
				System.out.println("POR FAVOR INTRODUCE LA NOTA");
				nota = sn.nextInt();
				videojuego.setNota(nota);

				Videojuego pAlta = spp.alta(videojuego);
				System.out.println(" Videojuego dado de alta " + pAlta);
				break;
			case 2:
				System.out.println("Has seleccionado la opcion 2");
				System.out.println("************  BUSCAR Videojuego POR ID ***************");
				int id;
				System.out.println("POR FAVOR INTRODUCE EL ID DEL VIDEOJUEGO");
				 id= sn.nextInt();
				
				 videojuego = spp.obtener(id);
				System.out.println(" videojuego con id : " + id + " es " + videojuego);

				break;
			case 3:
				System.out.println("Has seleccionado la opcion 3");
				System.out.println("********* MODIFICAR VIDEOJUEGO POR ID*************");
				System.out.println("POR FAVOR INTRODUCE EL ID DEL VIDEOJUEGO");
				 id= sn.nextInt();
				Videojuego pModificar = new Videojuego();
				pModificar.setId(id);
				System.out.println("POR FAVOR INTRODUCE EL NOMBRE");
				datos= sc.next();
				pModificar.setNombre(datos);
				System.out.println("POR FAVOR INTRODUCE LA COMPAÑIA");
				datos= sc.next();
				pModificar.setCompañia(datos);
				System.out.println("POR FAVOR INTRODUCE LA NOTA");
				nota = sn.nextInt();
				pModificar.setNota(nota);
				boolean modificado = spp.modificar(pModificar);
				System.out.println(" VIDEOJUEGO MODIFICADO? " + modificado);

				
				break;
			case 4:
				System.out.println("Has seleccionado la opcion 4");
				System.out.println("********** LISTAR VIDEOJUEGO ***************");
				List<Videojuego> listarjuegos = spp.listar(null);
				// Recorremos la lista y la imprimimos con funciones lambda
				// Tambien podriamos haber usado un for-each clasico de java
				listarjuegos.forEach((v) -> System.out.println(v));

				break;
			case 5:
				System.out.println("Has seleccionado la opcion 5");
				System.out.println("*********** BORRAR VIDEOJUEGO POR ID ****************** ");
				System.out.println("POR FAVOR INTRODUCE EL ID DEL VIDEOJUEGO");
				
				id= sn.nextInt();
				boolean borrada = spp.borrar(id);
				System.out.println(" Videojuego con " + id + "?" + borrada);

				
				break;
			case 6:
				System.out.println("Has seleccionado la opcion 6");
				System.out.println(" PROGRAMA FINALIZADO  ");
				salir = true;
				break;
			default:
				System.out.println("Solo números entre 1 y 6");
			}
		}

		// Mandamos parar nuestra aplicacion Spring Boot
		pararAplicacion();

	}

	public void pararAplicacion() {
		// Esta aplicacion levanta un servidor web, por lo que tenemos que darle
		// la orden de pararlo cuando acabemos. Para ello usamos el metodo exit,
		// de la clase SpringApplication, que necesita el contexto de Spring y
		// un objeto que implemente la interfaz ExitCodeGenerator.
		// Podemos usar la funcion lambda "() -> 0" para simplificar

		SpringApplication.exit(context, () -> 0);

		// Podriamos hacerlo tambien de una manera clasica, es decir, creando
		// la clase anonima a partir de la interfaz.
		// El codigo de abajo sería equivalente al de arriba
		// (pero mucho más largo)
		/*
		 * SpringApplication.exit(context, new ExitCodeGenerator() {
		 * 
		 * @Override public int getExitCode() { return 0; } });
		 */
	}
}
