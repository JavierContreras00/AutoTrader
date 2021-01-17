package Test;
import static org.junit.Assert.*;

import org.junit.Test;

import ventanas.VentanaRegistrarse;

public class TestUsuario {
	@Test
	public void correoTest() {
		assertTrue(VentanaRegistrarse.comprobarCorreo("santiago.alfaro2000@gmail.com", false));
		assertFalse(VentanaRegistrarse.comprobarCorreo("santiago.alfaro2000@gmail.com", false));
	}

}
