package jp.ac.hiroshima_u.sci.math.saito.tinymt;

import java.math.BigInteger;

import junit.framework.TestCase;

import org.junit.Test;

public class PolynomialTest extends TestCase {

	@Test
	public void testPolynomialString() {
		assertEquals(new F2Polynomial("1101").toString(),"1101");
	}

	@Test
	public void testPolynomialStringInt() {
		assertEquals(new F2Polynomial("f", 16).toString(), "1111");
	}

	@Test
	public void testDegree() {
		assertEquals(new F2Polynomial("100").degree(), 2);
		assertEquals(new F2Polynomial("1").degree(), 0);
		assertEquals(new F2Polynomial("0").degree(), -1);
	}

	@Test
	public void testAdd() {
		assertEquals(new F2Polynomial("1101").add(new F2Polynomial("110")),
				new F2Polynomial("1011"));
	}

	@Test
	public void testMul() {
		assertEquals(new F2Polynomial("110"),
				new F2Polynomial("11").mul(new F2Polynomial("10")));
        assertEquals(new F2Polynomial("1110"),
                new F2Polynomial("10").mul(new F2Polynomial("111")));
	}

	@Test
	public void testMod() {
		assertEquals(new F2Polynomial("1011").mod(new F2Polynomial("11")),
				new F2Polynomial("1"));
	}

	@Test
	public void testPower() {
		assertEquals(new F2Polynomial("10").power(new BigInteger("3")),
				new F2Polynomial("1000"));
	}

	@Test
	public void testPowerMod() {
		assertEquals(new F2Polynomial("10").powerMod(new BigInteger("3"), new F2Polynomial("11")),
				new F2Polynomial("1"));
	}
	
	@Test
	public void testGetCoefficient() {
	    assertEquals(1, new F2Polynomial("10").getCoefficient(1));
        assertEquals(0, new F2Polynomial("10").getCoefficient(0));
	}

	@Test
	public void testToString() {
	    assertEquals("101", new F2Polynomial("101").toString());
        assertEquals("5", new F2Polynomial("101").toString(16));
	}
	
	@Test
	public void testHashCode() {
	    assertEquals(new BigInteger("1101", 2).hashCode(), new F2Polynomial("1101").hashCode());
	}
	
	@Test
	public void testEquals() {
	    assertTrue(new F2Polynomial("11010").equals(new F2Polynomial("11010")));
        assertTrue(! new F2Polynomial("11010").equals(new F2Polynomial("11011")));
        assertTrue(! new F2Polynomial("11010").equals(new BigInteger("11010",2)));
        F2Polynomial p = new F2Polynomial("101");
        assertTrue(p.equals(p));
	}
}
