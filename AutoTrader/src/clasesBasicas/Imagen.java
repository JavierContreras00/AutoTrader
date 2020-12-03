package clasesBasicas;

import com.mysql.cj.jdbc.Blob;

public class Imagen{
	private int idImagen;
	private Blob imagen;

	public Imagen(int idImagen, Blob imagen) {
	
		this.idImagen = idImagen;
		this.imagen= imagen;
		// TODO Auto-generated constructor stub
	}
	
	public Imagen() {
		
	}
	public int getIdImagen() {
		return idImagen;
	}
	public void setIdImagen(int idImagen) {
		this.idImagen = idImagen;
	}
	public Blob getImagen() {
		return imagen;
	}
	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}
	@Override
	public String toString() {
		return "Imagen [idImagen=" + idImagen + ", imagen=" + imagen + "]";
	}
	
	
	
	
	
	
	

}
