package jp.ac.hiroshima_u.sci.math.saito.tinymt;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class TinyMT64Test {

    @Test
    public void testGetDefault() {
        TinyMT64 tiny = TinyMT64.getDefault(1);
        long[] output = new long[10];
        long[] c_data = { 0xd728944fa51f6257L, 0xefd23d979584d86eL, 0x1e3943ce0d830e11L, 
                0x8bffb401a9a2ce7eL, 0x7bdc760e5d5eb8daL, 0xb4d4b30850c1998dL, 
                0x42ea50d07ab9543aL, 0x9e68298a14b2e944L, 0xfc23531d52cd98b9L, 
                0x1848e4ac0a1240b9L };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextLong();
        }
        int weight = 65;
        int delta = 0;
        assertTrue(Arrays.equals(output, c_data));
        assertEquals(0, tiny.getId());
        assertEquals(delta, tiny.getDelta());
        assertEquals(weight, tiny.getWeight());
    }
 
    @Test
    public void testGetDefaultString() {
        TinyMT64 tiny = TinyMT64.getDefault("abcd");
        long[] output = new long[10];
        long[] c_data = { 0x988ff5fb3bc5e30bL, 0xcdf393cba27b3666L, 0x13d64a6901387d24L, 
                0x7c98b1e776a0270fL, 0x47503ac0e552f6a9L, 0x0541c7654f776a4dL, 
                0xa991086a713ce640L, 0xb6e245afa06decc7L, 0xaddb82cb02da2b8eL, 
                0x6f9acdb4fdc95087L };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextLong();
        }
        assertTrue(Arrays.equals(output, c_data));
    }
    
    @Test
    public void testGetDefaultSeedArray() {
        int[] seeds = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        TinyMT64 tiny = TinyMT64.getDefault(seeds);
        long[] output = new long[10];
        long[] c_data = { 0x5e70cdfe6bb4558dL, 0x386771af10f0e83eL, 0x78bbc9a7d60cc58aL, 
                0xf054e81c2f303b3fL, 0xf0050849fabb0bf0L, 0x2229a953f5d00bd5L, 
                0xdd1d9e468532ab6cL, 0xdfb0f129301cf0d5L, 0x70d305b658f4588eL, 
                0x31987df412b16835L };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextLong();
        }
        assertTrue(Arrays.equals(output, c_data));
    }

    @Test
    public void testGetDefaultArray() {
        TinyMT64 tiny[] = TinyMT64.getDefaultArray(5, 1L, 1);
        long[][] c_data = {
                { 0xd728944fa51f6257L, 0xefd23d979584d86eL, 0x1e3943ce0d830e11L, 
                  0x8bffb401a9a2ce7eL, 0x7bdc760e5d5eb8daL, 0xb4d4b30850c1998dL, 
                  0x42ea50d07ab9543aL, 0x9e68298a14b2e944L, 0xfc23531d52cd98b9L, 
                  0x1848e4ac0a1240b9L},
                { 0xf3253183c6f1773aL, 0xdde01e548ae636bfL, 0xec7cb783fd2608c8L, 
                  0xc7a35df5540b157eL, 0xf733fa531ff47909L, 0xcb6755206ec1ec18L, 
                  0x243769de6dadebb8L, 0x4b36dbde215590e9L, 0x94cce25c746bf9fcL, 
                  0x41a6f4b2420ea132L},
                { 0xb06382c3bcb38f5cL, 0xbd9988cd79a71663L, 0x353611f0fdcbcfdeL, 
                  0xfb2b7a2ddde5bea9L, 0xbd677db92be97bf8L, 0x329ac31ede542d31L, 
                  0xb2ce11aeb29d861dL, 0x4e879c4a84be3a3eL, 0x29f3eda788765d04L, 
                  0xd17e3173bcf9cbddL},
                { 0xd4a770c7539d4559L, 0xcdff1474a4c567b2L, 0x90018d44f802be93L, 
                  0x6e6fe69cd25b13f4L, 0xcc005900ef3ff0e1L, 0xc1f1a3e3121c9fdcL, 
                  0x5838598c1099761fL, 0x0e02744b8596405cL, 0xc324018e438890e3L, 
                  0x2b30adc275cf4c87L},
                { 0x9af3cfe462a4f979L, 0x2b0390e28bc27452L, 0x27d49bef44623024L, 
                  0xefae1630caac2c4eL, 0x2295b4a8d8e3e085L, 0x99454039689b7396L, 
                  0x1bdad525304a1d4eL, 0x2bfd8c1ef9f6e28dL, 0x1a68f0af48e2e38aL, 
                  0x404b4ba330b85480L} };
        long[] output = new long[10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < output.length; j++) {
                output[j] = tiny[i].nextLong();
            }
            assertTrue("line:" + i, Arrays.equals(output, c_data[i]));
        }
    }

    @Test
    public void testGetDefaultArrayString() {
        TinyMT64 tiny[] = TinyMT64.getDefaultArray(5, "tinyMT", 1);
        long[][] c_data = {
                { 0xec0867925f2f665dL, 0xae6bf069ab695726L, 0x39b6f70dff1d35ccL, 
                  0x7cfa530a51015956L, 0xd9254817bc34031aL, 0x7e42ba10b7b401b1L, 
                  0x35321e7a0428630bL, 0x7063802a801dd663L, 0x40a82b3a2fd7457aL, 
                  0x2eab7e9ec4a663deL},
                { 0xe3847858e5c20ef7L, 0xfe74bbe78c688a85L, 0x58377f09f3bc624fL, 
                  0x1dbfa8bf24aa6738L, 0x763fce2817abee79L, 0xfe54636784965e87L, 
                  0xe4b7b5296d2afb9bL, 0x39329fe9f3a43142L, 0xc283548d12dc08beL, 
                  0x4eb1e6214cd54eb6L},
                { 0x13ba5e22fecafd16L, 0xa7e15ab33f2234b3L, 0x5f44c6b6c6316b6fL, 
                  0x3190a2e31c03e019L, 0xa8291119be8a8f85L, 0xe6c39a47c70a57b0L, 
                  0x00b48ff88e580c82L, 0x3b6b4cfdba50d9a6L, 0x82ce540fa37deb4cL, 
                  0xba8f96d75c6ec9fdL},
                { 0x3acbde013c6154e7L, 0x13dbea1473b6507cL, 0x9065aec24b28b380L, 
                  0xeb35f02956c0b744L, 0xc784ea2311978cf4L, 0xd0847253dffecf12L, 
                  0x5cdc27214547ab0dL, 0x56912d25388cd87aL, 0x814fef920ffdc013L, 
                  0xa3648589c6803785L},
                { 0x912af3d5955164feL, 0xae49e6e3cad9b7afL, 0x1fcfdfc959d07515L, 
                  0xf47d151248956fd4L, 0xfd40c9eb86ddfdceL, 0x017daa91b3ff87d2L, 
                  0xfb95b6de4f46365dL, 0x880642894d13baa0L, 0x58b1386de1a00919L, 
                  0x99dc9cc618f1d0d6L}};
        long[] output = new long[10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < output.length; j++) {
                output[j] = tiny[i].nextLong();
            }
            assertTrue("line:" + i, Arrays.equals(output, c_data[i]));
        }
    }

    @Test
    public void testGetTinyMTArray() throws IOException {
        TinyMT64[] tiny = TinyMT64.getTinyMTArray(3, 1);
        long[][] c_data = {
                { 0x6fcd21fba3f6fb57L, 0xea60c3bb3d026591L, 
                  0xb5cabe6a77d72ce7L, 0x582ed743750b72d6L, 
                  0x14806cf8429f6de8L, 0x492799f0225d8ccaL, 
                  0x43766e8c99eb7660L, 0xd11af09580830601L, 
                  0x6f112a2830d5da5bL, 0x1736965121ab4540L},
                { 0xf6a173715a0a8789L, 0x5a106ea7fdb897f9L, 
                  0xe9547e119fb93c5fL, 0x366d85ae7ada861bL, 
                  0x8558e7e21f552d0aL, 0x941d0dca5e86b65dL, 
                  0xdcdee576166da1a0L, 0x7d5eb3bab44d9b9dL, 
                  0x8f123facd7c27d66L, 0xbe170ac3a1799707L},
                { 0xe8b7f4d2b4ad997cL, 0x099edf7b774353d1L, 
                  0xd565ebadb00a07d7L, 0x6f836bdec50af268L, 
                  0x6cb78bec1d66bed5L, 0x7f6efc2e687ce4efL, 
                  0xe35cda577f0279fdL, 0x7aff1333c6b3bddcL, 
                  0x513f719e5c79071eL, 0xeabd98dbbe2b164bL}};
        long[] output = new long[10];
        for (int i = 0; i < c_data.length; i++) {
            for (int j = 0; j < output.length; j++) {
                output[j] = tiny[i].nextLong();
            }
            assertTrue("line:" + i, Arrays.equals(output, c_data[i]));
        }
    }

    @Test
    public void testSeedArray() {
        TinyMT64 tiny = TinyMT64.getDefault(1);
        int[] ar = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        long[] output = new long[10];
        long[] c_data = {
                0x5e70cdfe6bb4558dL, 0x386771af10f0e83eL, 0x78bbc9a7d60cc58aL, 
                0xf054e81c2f303b3fL, 0xf0050849fabb0bf0L, 0x2229a953f5d00bd5L, 
                0xdd1d9e468532ab6cL, 0xdfb0f129301cf0d5L, 0x70d305b658f4588eL, 
                0x31987df412b16835L}; 
        tiny.setSeed(ar);
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextLong();
        }
        assertTrue(Arrays.equals(output, c_data));
    }

    @Test
    public void testSetSeedLong() {
        TinyMT64 tiny = TinyMT64.getDefault(100);
        long[] output = new long[10];
        long[] c_data = { 0x7e57d3aeb5e0e126L, 0xc8fe5b599d254059L, 
                0x706d9de38f795405L, 0x9a297bb5242c56edL, 
                0xb80821a9c29a77bbL, 0xcf2fd43eb3bb8844L, 
                0x911bee6cfdb407b4L, 0xe1a9da8c33ce06f9L, 
                0x6698dcf353232469L, 0x5f125459c8f6f803L};
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextLong();
        }
        assertTrue(Arrays.equals(output, c_data));
        tiny = TinyMT64.getDefault(0x100000010000000L);
        long[] c_data2 = { 0xf1a821db6b39590fL, 0xd7cfe590a8431095L, 
                0x712025707354368cL, 0x9727a1c388e2422dL, 
                0x2353d16d230fe1dcL, 0x216adb31cc61767fL, 
                0x8567de418511116eL, 0x57b99bc3139ad8d7L, 
                0xeaa956593ac3cf61L, 0xa4d8c79501e92672L, };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextLong();
        }
        assertTrue(Arrays.equals(output, c_data2));
    }
    
    @Test
    public void testSetSeedArray() {
        TinyMT64 tiny = TinyMT64.getDefault();
        long[] seeds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        tiny.setSeed(seeds);
        long[] output = new long[10];
        long[] c_data = {0xdc1843e3374552c8L, 0xcc5968a9950e2cfaL, 0x1d27e02f429d664bL, 
                0x5811d50b73fa7225L, 0xf22c9027bc9a6d15L, 0x77f081f721a20ca2L, 
                0x5201d3557aae58b9L, 0xf76caa1de0c6724bL, 0x1ff948f6faee1413L, 
                0xe0fb023631cbbe5eL};
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextLong();
        }
        assertTrue(Arrays.equals(output, c_data));
    }

    @Test
    public void testNextFloat() {
        TinyMT64 tiny = TinyMT64.getDefault();
        tiny.setSeed(1);
        float[] c_data = {0.840463f, 0.936802f, 
                0.118061f, 0.546870f, 
                0.483833f, 0.706371f, 
                0.261388f, 0.618777f, 
                0.984914f, 0.094862f,  };
        for (int i = 0; i < c_data.length; i++) {
            assertEquals(c_data[i], tiny.nextFloat(), 0.00001);
        }
    }
    
    @Test
    public void testNextDouble() {
        TinyMT64 tiny = TinyMT64.getDefault();
        tiny.setSeed(1);
        double[] c_data = {0.8404629416, 0.9368017669, 
                0.1180612925, 0.5468704704, 
                0.4838327203, 0.7063705344, 
                0.2613878736, 0.6187768900, 
                0.9849140116, 0.0948622627,   };
        for (int i = 0; i < c_data.length; i++) {
            assertEquals(c_data[i], tiny.nextDouble(), 0.00000001);
        }
    }

    @Test
    public void testNextInt() {
        TinyMT64 tiny = TinyMT64.getDefault();
        tiny.setSeed(1);
        int[] c_data = { 0xd728944f, 0xefd23d97, 
                0x1e3943ce, 0x8bffb401, 
                0x7bdc760e, 0xb4d4b308, 
                0x42ea50d0, 0x9e68298a, 
                0xfc23531d, 0x1848e4ac };
        for (int i = 0; i < c_data.length; i++) {
            assertEquals(c_data[i], tiny.nextInt());
        }
    }
}
