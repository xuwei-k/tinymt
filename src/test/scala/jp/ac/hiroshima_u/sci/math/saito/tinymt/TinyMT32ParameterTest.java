package jp.ac.hiroshima_u.sci.math.saito.tinymt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class TinyMT32ParameterTest {

//	@Test
//	public void testTinyMT32Parameter() {
//		String characteristic ="a";
//		Polynomial pol = new Polynomial(characteristic, 16);
//		int id = 1;
//		int mat1 = 2;
//		int mat2 = 3;
//		int tmat = 4;
//		int weight = 5;
//		int delta = 6;
//		TinyMT32Parameter p = new TinyMT32Parameter(characteristic,
//				id, mat1, mat2,
//				tmat, weight, delta);
//		assertEquals(pol, p.getCharacteristic());
//		assertEquals(id, p.getId());
//		assertEquals(mat1, p.getMat1());
//		assertEquals(mat2, p.getMat2());
//		assertEquals(tmat, p.getTmat());
//		assertEquals(weight, p.getWeight());
//		assertEquals(delta, p.getDelta());
//	}

    @Test
    public void testGetThreadLocalParameter() {
        TinyMT32Parameter p0 = TinyMT32Parameter.getThreadLocalParameter(0);
        TinyMT32Parameter p1 = TinyMT32Parameter.getThreadLocalParameter(1);
        TinyMT32Parameter pm2 = TinyMT32Parameter.getThreadLocalParameter(-2);
        assertTrue(p0.getMat1() != p1.getMat1());
        assertTrue(p0.getMat1() != pm2.getMat1());
    }
	@Test
	public void testGetDefaultParameter() {
		TinyMT32Parameter p = TinyMT32Parameter.getDefaultParameter();
		//d8524022ed8dff4a8dcc50c798faba43,32,0,8f7011ee,fc78ff1f,3793fdff,63,0
		F2Polynomial pol = new F2Polynomial("d8524022ed8dff4a8dcc50c798faba43", 16);
		int id = 0;
		int mat1 = 0x8f7011ee;
		int mat2 = 0xfc78ff1f;
		int tmat = 0x3793fdff;
		int weight = 63;
		int delta = 0;
		assertEquals(pol, p.getCharacteristic());
		assertEquals(id, p.getId());
		assertEquals(mat1, p.getMat1());
        assertEquals(mat1, p.getMat1(1));
        assertEquals(0, p.getMat1(2));
		assertEquals(mat2, p.getMat2());
        assertEquals(mat2, p.getMat2(3));
        assertEquals(0, p.getMat2(4));
		assertEquals(tmat, p.getTmat());
        assertEquals(tmat, p.getTmat(5));
        assertEquals(0, p.getTmat(6));
        assertEquals(0x3f800000 | (tmat >>> 9), p.getTmatFloat(7));
        assertEquals(0x3f800000, p.getTmatFloat(8));
		assertEquals(weight, p.getWeight());
		assertEquals(delta, p.getDelta());
	}

	@Test
	public void testGetParameters() throws IOException {
		TinyMT32Parameter[] p = TinyMT32Parameter.getParameters(2);
		//8ee476cb10b7c7e20dd10725924e9877,32,0,877810ef,fc38ff0f,c7fb7fff,63,0
		//8331a00cb24d95a8e116e35435103213,32,0,837c106f,fc18ff07,eeb9bdff,51,0
		F2Polynomial[] pol = new F2Polynomial[]{
				new F2Polynomial("8ee476cb10b7c7e20dd10725924e9877", 16),
				new F2Polynomial("8331a00cb24d95a8e116e35435103213", 16)};
		int[] id = {0, 0};
		int[] mat1 = {0x877810ef, 0x837c106f};
		int[] mat2 = {0xfc38ff0f, 0xfc18ff07};
		int[] tmat = {0xc7fb7fff, 0xeeb9bdff};
		int[] weight = {63, 51};
		int[] delta = {0, 0};
		for (int i = 0; i < 2; i++) {
			assertEquals(pol[i], p[i].getCharacteristic());
			assertEquals(id[i], p[i].getId());
			assertEquals(mat1[i], p[i].getMat1());
			assertEquals(mat2[i], p[i].getMat2());
			assertEquals(tmat[i], p[i].getTmat());
			assertEquals(weight[i], p[i].getWeight());
			assertEquals(delta[i], p[i].getDelta());
		}
	}
	
    @Test
    public void testGetParametersStartCount() throws IOException {
        TinyMT32Parameter[] p = TinyMT32Parameter.getParameters(2, 2);
        //bc8ca81cb620b9610108b0fa2036f9ef,32,0,718e0e31,fb88fee3,11dbffff,57,0
        //97d57e00bc69e5ca2b9a5041d979eaff,32,0,50af0a15,fa80fea1,9ddc99ff,69,1
        F2Polynomial[] pol = new F2Polynomial[]{
                new F2Polynomial("bc8ca81cb620b9610108b0fa2036f9ef", 16),
                new F2Polynomial("97d57e00bc69e5ca2b9a5041d979eaff", 16)};
        int[] id = {0, 0};
        int[] mat1 = {0x718e0e31, 0x50af0a15};
        int[] mat2 = {0xfb88fee3, 0xfa80fea1};
        int[] tmat = {0x11dbffff, 0x9ddc99ff};
        int[] weight = {57, 69};
        int[] delta = {0, 1};
        for (int i = 0; i < 2; i++) {
            assertEquals(pol[i], p[i].getCharacteristic());
            assertEquals(id[i], p[i].getId());
            assertEquals(mat1[i], p[i].getMat1());
            assertEquals(mat2[i], p[i].getMat2());
            assertEquals(tmat[i], p[i].getTmat());
            assertEquals(weight[i], p[i].getWeight());
            assertEquals(delta[i], p[i].getDelta());
        }
    }
    
    @Test
    public void testGetParametersStartCountOver() throws IOException {
        TinyMT32Parameter[] tiny =  TinyMT32Parameter.getParameters(65537, 2);
        assertEquals(0, tiny.length);
        TinyMT32Parameter[] tiny2 =  TinyMT32Parameter.getParameters(65534, 10);
        assertEquals(1, tiny2.length);
    }
    
}
