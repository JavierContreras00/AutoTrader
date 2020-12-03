package clasesBasicas;

import java.util.ArrayList;

public class Marca {
	private String id;
	private String nombre;
	private ArrayList<String> modelos;

	/**
	 * Clase que permite vincular mediante un mapa , los dos ficheros de las marcas
	 * y los modelos en funcion de las marcas
	 * 
	 * @param id           identificador de la marca a leer desde el fichero
	 * @param nombrenombre de la marca
	 */
	public Marca(String id, String nombre) {
		super();
		modelos = new ArrayList<String>();
		this.id = id;
		this.nombre = nombre;
	}

	public void addModelo(String modelo) {
		modelos.add(modelo);
	}

	public ArrayList<String> getModelos() {
		return modelos;
	}

	public String getNombre() {
		return nombre;
	}

}