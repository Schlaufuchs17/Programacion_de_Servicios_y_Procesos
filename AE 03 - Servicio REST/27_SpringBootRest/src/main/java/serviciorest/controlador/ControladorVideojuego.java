package serviciorest.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import serviciorest.modelo.entidad.Videojuego;
import serviciorest.modelo.persistencia.DaoVideojuego;

@RestController
public class ControladorVideojuego {

	// Realizar inyecciones de dependencias de objetos dados de alta en Spring
	@Autowired
	private DaoVideojuego daoVideojuego;

	// GET PERSONA POR ID

	@GetMapping(path = "videojuegos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> getVideojuego(@PathVariable("id") int id) {
		System.out.println("Buscando videojuego con id: " + id);
		Videojuego p = daoVideojuego.get(id);
		if (p != null) {
			return new ResponseEntity<Videojuego>(p, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	// POST

	@PostMapping(path = "videojuegos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> altaJuego(@RequestBody Videojuego p) {
		if (daoVideojuego.add(p) == null) {
			System.out.println(" YA EXISTE UN NOMBRE IGUAL");
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		} else
			System.out.println("altaVideojuego: objeto juego: " + p);
		daoVideojuego.add(p);
		return new ResponseEntity<Videojuego>(p, HttpStatus.CREATED);// 201 CREATED
	}

	// GET LISTA PERSONAS

	@GetMapping(path = "videojuegos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Videojuego>> listarVideojuegos(
			@RequestParam(name = "nombre", required = false) String nombre) {
		List<Videojuego> listaJuegos = null;
		// Si no me viene el nombre, devolvemos toda la lista
		if (nombre == null) {
			System.out.println("ESTA ES LA LISTA DE VIDEOJUEGOS");
			listaJuegos = daoVideojuego.list();
		} else {
			System.out.println("Lista de videojuegos por nombre: " + nombre);
			listaJuegos = daoVideojuego.listByNombre(nombre);
		}
		System.out.println(listaJuegos);
		return new ResponseEntity<List<Videojuego>>(listaJuegos, HttpStatus.OK);
	}

	// PUT

	@PutMapping(path = "videojuegos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> modificarPersona(@PathVariable("id") int id, @RequestBody Videojuego p) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + p);
		p.setId(id);
		Videojuego pUpdate = daoVideojuego.update(p);
		if (pUpdate != null) {
			return new ResponseEntity<Videojuego>(HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	// DELETE

	@DeleteMapping(path = "videojuegos/{id}")
	public ResponseEntity<Videojuego> borrarJuego(@PathVariable("id") int id) {
		System.out.println("videojuego borrado con id: " + id);
		Videojuego p = daoVideojuego.delete(id);
		if (p != null) {
			return new ResponseEntity<Videojuego>(p, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
}
