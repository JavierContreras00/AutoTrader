package clasesBasicas;

import java.io.File;

import com.mysql.cj.jdbc.Blob;
import com.mysql.cj.result.BinaryStreamValueFactory;

public class Coche{

	/**
	 * Clase principal coche
	 */

	private String marca, modelo, carroceria, combustible, transmision;
	private int idvehiculos, anio, precio, potencia, nPlazas, nPuertas;
	private long kilometros;
	private File imagen;

	/**
	 * 
	 * @param marca       Marca del vehículo
	 * @param modelo      Modelo del vehículo
	 * @param carroceria  Carrocería del vehículo(cabrio, monovolumen...)
	 * @param combustible Tipo de combustible que utiliza
	 * @param anio        Anio de matriculación
	 * @param precio      Precio del vehículo
	 * @param kilometros  Kilómetros del vehículo
	 * @param potencia    Potencia del Vehículo( en cv)
	 * @param transmision Transmisión del vehículo(automático, manual...)
	 * @param nPuertas    Número de puertas incluido el maletero del vehículo
	 * @param nPlazas     Número de plazas incluido el conductor del vehículo
	 */
	public Coche(int idvehiculos, String marca, String modelo, String carroceria, String combustible, int anio,
			int precio, long kilometros, int potencia, String transmision, int nPlazas, int nPuertas, File imagen) {
		this.idvehiculos = idvehiculos;
		this.marca = marca;
		this.modelo = modelo;
		this.carroceria = carroceria;
		this.combustible = combustible;
		this.anio = anio;
		this.precio = precio;
		this.kilometros = kilometros;
		this.potencia = potencia;
		this.transmision = transmision;
		this.nPlazas = nPlazas;
		this.nPuertas = nPuertas;
		this.imagen = imagen;
	}

	public Coche() {

	}

	/**
	 * 
	 * @return Devuelve las puertes del vehículo
	 */
	public int getnPuertas() {
		return nPuertas;
	}

	/**
	 * 
	 * @param nPuertas Introduce las puertas del vehículo
	 */
	public void setnPuertas(int nPuertas) {
		this.nPuertas = nPuertas;
	}
	
	

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCarroceria() {
		return carroceria;
	}

	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	public String getTransmision() {
		return transmision;
	}

	public void setTransmision(String transmision) {
		this.transmision = transmision;
	}

	public int getIdvehiculos() {
		return idvehiculos;
	}

	public void setIdvehiculos(int idvehiculos) {
		this.idvehiculos = idvehiculos;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public int getnPlazas() {
		return nPlazas;
	}

	public void setnPlazas(int nPlazas) {
		this.nPlazas = nPlazas;
	}

	public long getKilometros() {
		return kilometros;
	}

	public void setKilometros(long kilometros) {
		this.kilometros = kilometros;
	}
	
	

	public File getImagen() {
		return imagen;
	}

	public void setImagen(File imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "Coche [marca=" + marca + ", modelo=" + modelo + ", carroceria=" + carroceria + ", combustible="
				+ combustible + ", transmision=" + transmision + ", idvehiculos=" + idvehiculos + ", anio=" + anio
				+ ", precio=" + precio + ", potencia=" + potencia + ", nPlazas=" + nPlazas + ", nPuertas=" + nPuertas
				+ ", kilometros=" + kilometros + "]";
	}

	

}
