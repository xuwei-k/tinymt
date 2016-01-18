package jp.ac.hiroshima_u.sci.math.saito.tinymt;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TinyMT64ParameterTest {

    @Test
    public void testGetDefaultParameter() {
        TinyMT64Parameter p = TinyMT64Parameter.getDefaultParameter();
        //945e0ad4a30ec19432dfa9d5959e5d5d,64,0,fa051f40,ffd0fff4,58d02ffeffbfffbc,65,0
        F2Polynomial pol = new F2Polynomial("945e0ad4a30ec19432dfa9d5959e5d5d", 16);
        int id = 0;
        long mat1 = 0xfa051f40L;
        long mat2 = 0xffd0fff4L;
        long tmat = 0x58d02ffeffbfffbcL;
        int weight = 65;
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
        assertEquals(0x3ff0000000000000L | (tmat >>> 12), p.getTmatDouble(7));
        assertEquals(0x3ff0000000000000L, p.getTmatDouble(8));
        assertEquals(weight, p.getWeight());
        assertEquals(delta, p.getDelta());
    }

    @Test
    public void testGetParameters() throws IOException {
        TinyMT64Parameter[] p = TinyMT64Parameter.getParameters(2);
        //c8a646f09f334156a307970051b83461,64,0,daa51b54,fed47fb5,a853e7ffeffefffe,55,0
        //c6d63a1d259b079d5c056853ebd36cb7,64,0,c0bf1817,fe047f81,7fc75ff6ffffffbc,67,0
        F2Polynomial[] pol = new F2Polynomial[]{
                new F2Polynomial("c8a646f09f334156a307970051b83461", 16),
                new F2Polynomial("c6d63a1d259b079d5c056853ebd36cb7", 16)};
        int[] id = {0, 0};
        long[] mat1 = {0xdaa51b54L, 0xc0bf1817L};
        long[] mat2 = {0xfed47fb5L, 0xfe047f81L};
        long[] tmat = {0xa853e7ffeffefffeL, 0x7fc75ff6ffffffbcL};
        int[] weight = {55, 67};
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
        TinyMT64Parameter[] p = TinyMT64Parameter.getParameters(2, 2);
        //9f0678a5b7b90af5e7cb430d1e7c272b,64,0,c03f1807,fe00ff80,14727d7fff7f7ffe,69,0
        //c488d75651a5baac842c1dae4f85e6f7,64,0,443b0887,fa247e89,f0d0e77bef7fdffa,65,0
        F2Polynomial[] pol = new F2Polynomial[]{
                new F2Polynomial("9f0678a5b7b90af5e7cb430d1e7c272b", 16),
                new F2Polynomial("c488d75651a5baac842c1dae4f85e6f7", 16)};
        int[] id = {0, 0};
        long[] mat1 = {0xc03f1807L, 0x443b0887L};
        long[] mat2 = {0xfe00ff80L, 0xfa247e89L};
        long[] tmat = {0x14727d7fff7f7ffeL, 0xf0d0e77bef7fdffaL};
        int[] weight = {69, 65};
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
    public void testGetParametersStartCountOver() throws IOException {
        TinyMT64Parameter[] tiny =  TinyMT64Parameter.getParameters(65537, 2);
        assertEquals(0, tiny.length);
        TinyMT64Parameter[] tiny2 =  TinyMT64Parameter.getParameters(65534, 10);
        assertEquals(1, tiny2.length);
    }
    

}
