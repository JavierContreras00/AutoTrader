package Test;
import static org.junit.Assert.*;

import org.junit.Test;

import clasesBasicas.Usuario;
import ventanas.VentanaRegistrarse;

public class TestUsuario {
	
	
	@Test
	public void testId() {
		Usuario u = new Usuario(4343, "4345", "javier", "contreras", "javicoa@opendesuto.es", "434");
		assertEquals(4343, u.getIdusuarios());	
	}
	
	
	@Test
	public void testNombre() {
		Usuario u = new Usuario(4343, "4345", "javier", "contreras", "javicoa@opendesuto.es", "434");
		assertEquals("javier", u.getNombre());	
	}

	@Test
	public void testApellido() {
		Usuario u = new Usuario(4343, "4345", "javier", "contreras", "javicoa@opendesuto.es", "434");
		assertEquals("contreras", u.getApellido());	
	}
	
	@Test
	public void testContrasenia() {
		Usuario u = new Usuario(4343, "4345", "javier", "contreras", "javicoa@opendesuto.es", "434");
		assertEquals("434", u.getContrasenia());	
	}
	
	
	
	
}
