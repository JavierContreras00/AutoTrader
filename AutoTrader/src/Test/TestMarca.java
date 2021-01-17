package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import clasesBasicas.Marca;
import clasesBasicas.Usuario;

class TestMarca {

	@Test
	void testNombre() {
		Marca m = new Marca("12A", "Ferrari");
		assertEquals("Ferrari", m.getNombre());	
	}

	
	
	
	
	
	
}
