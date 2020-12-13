package Test;
import static org.junit.Assert.*;

import org.junit.Test;

import ventanas.VentanaRegistrarse;

public class TestUsuario {
	@Test
	public void correoTest() {
		assertTrue(VentanaRegistrarse.comprobarCorreo("santiago.alfaro@opendeusto.es", false));
		assertFalse(VentanaRegistrarse.comprobarCorreo("santiago.alfaro@opendeusto.es", false));
	}

}
