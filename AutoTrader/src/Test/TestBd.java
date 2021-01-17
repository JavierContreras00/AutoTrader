package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestBd {

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void testBd() {
		
		
		SqlConnection con = new SqlConnection();
		
	}
	
	
	
	
	@Test
	public void testDriverManager() {
	    SqlConnection conClient = new SqlConnection();
	    assertEquals(conClient.getConnection(),...);
	    or
	    assertTrue(conClient.getConnection(),...);
	}

}
