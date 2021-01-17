package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import clasesBasicas.Coche;
import clasesBasicas.Usuario;

class TestCoche {
	

	@Test
	public void testId() {
		Coche c = new Coche(23, "Ferrari", "Enzo", " ", "gasolina", 543, 1000000, 5345.53, 543, " ", 74, 7644, imagen);
	//	assertEquals(4343, u.getIdusuarios());	
	}

}
