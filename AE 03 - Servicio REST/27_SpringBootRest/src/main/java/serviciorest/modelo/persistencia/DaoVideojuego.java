package serviciorest.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import serviciorest.modelo.entidad.Videojuego;

//Damos de alta un objeto dentro del contexto Spring.
@Component
public class DaoVideojuego {

	public List<Videojuego> listaJuegos;
	public int id;

	/**
	 * Cuando se cree el objeto dentro del contexto de Spring, se ejecutara su
	 * constructor, que creara los videojuegos y los metera en una lista para que
	 * puedan ser consumidas por nuestros clientes
	 * 
	 * //Se crea la lista de videojuegos
	 */
	public DaoVideojuego() {
		System.out.println("DaoVideojuegos -> Creando la lista de videojugos!");
		listaJuegos = new ArrayList<Videojuego>();
		Videojuego p1 = new Videojuego(id++, "Bloodborne", "From Software", 91);// ID: 0
		Videojuego p2 = new Videojuego(id++, "Red Dead Redemption 2", "Rockstar", 95);// ID: 1
		Videojuego p3 = new Videojuego(id++, "Warcraft 2 Tides of Darkness", "Blizzard Entertainment", 85);// ID: 2
		Videojuego p4 = new Videojuego(id++, "Doom", "ID Software", 82);// ID:3
		Videojuego p5 = new Videojuego(id++, "Silent Hill 2", "Konami", 87);// ID:4
		listaJuegos.add(p1);
		listaJuegos.add(p2);
		listaJuegos.add(p3);
		listaJuegos.add(p4);
		listaJuegos.add(p5);
	}

	/**
	 * Devuelve un videojuego a partir de su posicion del array
	 * 
	 * @param posicion la posicion del arrya que buscamos
	 * @return el videojuego que ocupe en la posicion del array, null en caso de que no
	 *         exista o se haya ido fuera de rango del array
	 */
	public Videojuego get(int posicion) {
		try {
			return listaJuegos.get(posicion);
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("Videojuego fuera de rango");
			return null;
		}
	}

	/**
	 * Metodo que devuelve el videojuego del array
	 * 
	 * @return una lista con todas los videojuegos del array
	 */
	public List<Videojuego> list() {
		return listaJuegos;
	}

	/**
	 * Metodo que introduce un videojuego al final de la lista
	 * 
	 * @param p el videojuego que queremos introducir
	 */
	public String add(Videojuego p) {

		for (Videojuego x : listaJuegos) {
			if (x.getNombre().equalsIgnoreCase(p.getNombre())) {
				return null;
			}

		}
		p.setId(id++);
		listaJuegos.add(p);
		return "añadido";
	}

	/**
	 * Borramos un videojuego de una posicion del array
	 * 
	 * @param posicion la posicion a borrar
	 * @return devolvemos el videojuego que hemos quitado del array, o null en caso de
	 *         que no exista.
	 */
	public Videojuego delete(int posicion) {
		try {
			return listaJuegos.remove(posicion);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("delete -> Videojuego fuera de rango");
			return null;
		}
	}

	/**
	 * Metodo que modifica un videojuego de una posicion del array
	 * 
	 * @param p contiene todos los datos que queremos modificar, pero p.getId()
	 *          contiene la posicion del array que queremos eliminar
	 * @return el videojuego modificado en caso de que exista, null en caso contrario
	 */
	public Videojuego update(Videojuego p) {
		try {
			Videojuego pAux = listaJuegos.get(p.getId());
			pAux.setNombre(p.getNombre());
			pAux.setCompañia(p.getCompañia());
			pAux.setNota(p.getNota());

			return pAux;
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("update -> Videojuego fuera de rango");
			return null;
		}
	}

	/**
	 * Metodo que devuelve todas los videojuegos por nombre. Como puede haber varios
	 * videojuegos con el mismo nombre, tengo que devolver una lista con todas
	 * los encontrados
	 * 
	 * @param nombre representa el nombre por el que vamos a hacer la busqueda
	 * @return una lista con los videojuegos que coincidan en el nombre. La lista
	 *         estará vacia en caso de que no hay coincidencias
	 */
	public List<Videojuego> listByNombre(String nombre) {
		List<Videojuego> listajuegoAux = new ArrayList<Videojuego>();
		for (Videojuego p : listaJuegos) {
			if (p.getNombre().equalsIgnoreCase(nombre)) {// contains()
				listajuegoAux.add(p);
			}
		}
		return listajuegoAux;
	}
}
