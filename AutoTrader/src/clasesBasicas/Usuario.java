package clasesBasicas;

import java.util.HashSet;
import java.util.UUID;

public class Usuario {
	/**
	 * Clase que crea un usuario
	 */

	private static final long serialVersionUID = -5971908660192096730L;
	private String nick, nombre, apellido, email, contrasenia;
	private int idusuarios;
	private UUID id;

	/**
	 * 
	 * @param nick        Nick del usuario
	 * @param nombre      Nombre del usuario
	 * @param apellido    Apellido del usuario
	 * @param email       Email del usuario
	 * @param contrasenia Contraseña del usuario
	 */
	public Usuario(int idusuarios, String nick, String nombre, String apellido, String email, String contrasenia) {
		super();
		this.idusuarios = idusuarios;
		this.nick = nick;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.contrasenia = contrasenia;

	}

	public Usuario() {

	}

	public int getIdusuarios() {
		return idusuarios;
	}

	public void setIdusuarios(int idusuarios) {
		this.idusuarios = idusuarios;
	}

	/**
	 *
	 * @return Devuelve el nick del usuario
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * 
	 * @param nick Introduce el nick del usuario
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * 
	 * @return Devuelve el nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * 
	 * @param nombre Introduce el nombre del usuario
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * @return Devuelve el apellido del usuario
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * 
	 * @param apellido Introduce el apellido del usuario
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * 
	 * @return Devuelve el email del usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email Introduce el email del usuario
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return Devuelve la contraseña cifrada del usuario
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * 
	 * @param contrasenia Introduce la contraseña del usuario
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Override
	public String toString() {
		return "Usuario [nick=" + nick + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
				+ ", contrasenia=" + contrasenia + ", idusuarios=" + idusuarios + "]";
	}

}
