package Biblioteca_virtual;

//ESTA ES LA CLASE LIBROS PARA CREARLOS.
public class Libros {

	public String ISBN;
	public String Autor;
	public String Titulo;
	public String Precio;

	public Libros(String iSBN, String autor, String titulo, String precio) {
		super();
		ISBN = iSBN;
		Autor = autor;
		Titulo = titulo;
		Precio = precio;
	}

	@Override
	public String toString() {
		return "Libro: [ISBN=" + ISBN + ", Autor=" + Autor + ", Titulo=" + Titulo + ", Precio=" + Precio + "]";
	}

	public String getISBN() {
		return ISBN;
	}

	public String getTitulo() {
		return Titulo;
	}

	public String getAutor() {
		return Autor;
	}

}
