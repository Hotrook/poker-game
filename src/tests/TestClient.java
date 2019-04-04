package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.Client;

public class TestClient {

	@Test
	public void testParseData(){
		String testString = "set active;1000;A";
		String[] result = Client.parseData(testString);
		assertEquals("set active", result[0]);
		assertEquals("1000", result[1]);
		assertEquals("A", result[2]);
		assertEquals(3,result.length);
	}
}
