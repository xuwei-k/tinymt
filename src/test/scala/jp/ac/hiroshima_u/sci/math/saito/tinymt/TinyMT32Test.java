package jp.ac.hiroshima_u.sci.math.saito.tinymt;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;

public class TinyMT32Test extends TestCase {

    @Test
    public void testGetDefault() {
        TinyMT32 tiny = TinyMT32.getDefault(1);
        int[] output = new int[10];
        int[] c_data = { 0x97b6d625, 0x3a86e2e1, 0xdd7305b1, 0x8e4ef1b0,
                0xd60a5515, 0xe3b751f6, 0x7e073136, 0x82e5df8b, 0xa5e6b3a8,
                0x2d91deed };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextInt();
        }
        assertTrue(Arrays.equals(output, c_data));
        int weight = 63;
        int delta = 0;
        assertEquals("d8524022ed8dff4a8dcc50c798faba43",
                tiny.getCharacteristic());
        assertEquals(0, tiny.getId());
        assertEquals(delta, tiny.getDelta());
        assertEquals(weight, tiny.getWeight());
    }

    @Test
    public void testGetDefaultString() {
        TinyMT32 tiny = TinyMT32.getDefault("abcd");
        int[] output = new int[10];
        int[] c_data = { 0x92264019, 0x3e048b87, 0x0618bd2a, 0x0b85252c,
                0xf720f342, 0x73ac4297, 0x20974815, 0x8ac7a201, 0x7443b188,
                0x0b7c6501 };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextInt();
        }
        assertTrue(Arrays.equals(output, c_data));
    }
    
    @Test
    public void testGetDefaultSeedArray() {
        int[] seeds = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        TinyMT32 tiny = TinyMT32.getDefault(seeds);
        int[] output = new int[10];
        int[] c_data = { 0x7c14361a, 0x41b450a8, 0xfecc94ae, 0xd19693cd,
                0x1d8b3ef7, 0xb5c88b3f, 0x45c79c71, 0xc8465811, 0x10fcf027,
                0x4e3317e5 };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextInt();
        }
        assertTrue(Arrays.equals(output, c_data));
    }

    @Test
    public void testGetDefaultArray() {
        TinyMT32 tiny[] = TinyMT32.getDefaultArray(5, 1L, 1L);
        int[][] c_data = {
                { 0x97b6d625, 0x3a86e2e1, 0xdd7305b1, 0x8e4ef1b0, 0xd60a5515,
                        0xe3b751f6, 0x7e073136, 0x82e5df8b, 0xa5e6b3a8,
                        0x2d91deed },
                { 0x069dd221, 0x139591eb, 0xb1bb01cb, 0xa59dfffc, 0x739f69a3,
                        0xa73eb87e, 0xe74ff5a4, 0x66add33a, 0x8058ebab,
                        0xd40b22ae },
                { 0x04788045, 0x4ff9f6ac, 0xa2320522, 0x7b54307f, 0x54d3cee9,
                        0xa6dd6ea1, 0xceaa10dc, 0x0ed65e8d, 0xe1457e91,
                        0x154a1ab8 },
                { 0xd026d1ca, 0xebce67c6, 0xdc706eda, 0xbc8007cb, 0xe2eb9899,
                        0x399992f3, 0x0ab705e6, 0x8b79b94b, 0x8a5c9316,
                        0x16cc039f },
                { 0xd15c9d16, 0xa767e149, 0xe9c73b4b, 0xaf69de00, 0x8319be3b,
                        0xecc79680, 0x8ab49653, 0x9bf1d861, 0x04c7dab6,
                        0xb0d5cf24 } };
        int[] output = new int[10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < output.length; j++) {
                output[j] = tiny[i].nextInt();
            }
            assertTrue("line:" + i, Arrays.equals(output, c_data[i]));
        }
    }

    @Test
    public void testGetDefaultArrayString() {
        TinyMT32 tiny[] = TinyMT32.getDefaultArray(5, "tinyMT", 1);
        int[][] c_data = {
                { 0xd0065635, 0x8d5e606e, 0x35292e8a, 0xf50272e0, 0x8fa1550b, 
                  0x6aaaed21, 0xb0159b78, 0x4adbe52d, 0x1ef7779f, 0xfea8cab6},
                { 0x98a8d0c4, 0xf0d0d812, 0xd00df952, 0x5f40c35c, 0x11cc9cab, 
                  0x8769ca59, 0xa06f3c6c, 0xe8285f96, 0x54005bb2, 0x483fec65},
                { 0x9835dd76, 0x82601445, 0xfd38ca2f, 0x4990fca2, 0x250a026c, 
                  0x20045548, 0x3d23916c, 0x63eec834, 0x104f5606, 0x8dd4ee2f},
                { 0x343a6834, 0x55e090a9, 0xb00c8b76, 0xc8c3738a, 0xeabdb64b, 
                  0x8ed5908f, 0x3f4393b6, 0xd57b28eb, 0x43725b82, 0x8e1c4a30},
                { 0x5c8e1f49, 0xbca358a9, 0x6189bbae, 0xe3e7eded, 0x6e2fa730, 
                  0xe1a38ddc, 0x4bbb4104, 0x478baaa4, 0x521237a7, 0xbfcd41ad} };
        int[] output = new int[10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < output.length; j++) {
                output[j] = tiny[i].nextInt();
            }
            assertTrue("line:" + i, Arrays.equals(output, c_data[i]));
        }
    }

    @Test
    public void testGetTinyMTArray() throws IOException {
        TinyMT32[] tiny = TinyMT32.getTinyMTArray(3, 1);
        int[][] c_data = {
                { 0xcaa912db, 0xa4f185e2, 0xf539b757, 0x3791a9b8, 0xb03509bf,
                        0x88a4100e, 0xd1072c63, 0x05a3c8e2, 0x89f1f44f,
                        0xc125e26e },
                { 0x70367341, 0x51658c92, 0x6091a2d9, 0x1b9465d7, 0xcb32c649,
                        0xf40459eb, 0x4d9d49e9, 0xe20a31c1, 0x5a58f47d,
                        0x2c508fda },
                { 0x457c166e, 0x59a81659, 0x666ce4ae, 0x86d92831, 0x2ccdb8cd,
                        0x2de09897, 0xb6ac8fe3, 0x5731a46a, 0xc6995e43,
                        0xa5685f6d }, };
        int[] output = new int[10];
        for (int i = 0; i < c_data.length; i++) {
            for (int j = 0; j < output.length; j++) {
                output[j] = tiny[i].nextInt();
            }
            assertTrue("line:" + i, Arrays.equals(output, c_data[i]));
        }
    }

    @Test
    public void testSeedArray() {
        TinyMT32 tiny = TinyMT32.getDefault(1);
        int[] ar = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] output = new int[10];
        int[] c_data = { 0x7c14361a, 0x41b450a8, 0xfecc94ae, 0xd19693cd,
                0x1d8b3ef7, 0xb5c88b3f, 0x45c79c71, 0xc8465811, 0x10fcf027,
                0x4e3317e5 };
        tiny.setSeed(ar);
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextInt();
        }
        assertTrue(Arrays.equals(output, c_data));
    }

    @Test
    public void testSetSeedLong() {
        TinyMT32 tiny = TinyMT32.getDefault(100);
        int[] output = new int[10];
        int[] c_data = { 0xeead8165, 0x8715ef2c, 0x23598315, 0x5facb14e,
                0x73e28fdf, 0x9826df55, 0x8536bb9c, 0x0c73d703, 0x7b174b03,
                0x7ddb6aaf };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextInt();
        }
        assertTrue(Arrays.equals(output, c_data));
        tiny = TinyMT32.getDefault(0x1000000010000000L);
        int[] c_data2 = { 0xfd520eb5, 0x6cdab063, 0x2877a46e, 0x5ed3fb03,
                0xc1bb07ea, 0xfb872239, 0xd2e2bade, 0xa41ea287, 0x263e62d3,
                0x94a5d5d1 };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextInt();
        }
        assertTrue(Arrays.equals(output, c_data2));
    }

    @Test
    public void testSetSeedArray() {
        TinyMT32 tiny = TinyMT32.getDefault();
        // 0x61, 0x62, 0x63, 0x64
        // e940c6b3 a06a11c1 e5d86c7a 14de6dbe 08f9afd1
        // e6c56aeb 809ba384 4db24dcf d0288300 f9566dd3
        tiny.setSeed("abcd");
        int[] output = new int[10];
        int[] c_data = { 0x92264019, 0x3e048b87, 0x0618bd2a, 0x0b85252c,
                0xf720f342, 0x73ac4297, 0x20974815, 0x8ac7a201, 0x7443b188,
                0x0b7c6501 };
        for (int i = 0; i < output.length; i++) {
            output[i] = tiny.nextInt();
        }
        assertTrue(Arrays.equals(output, c_data));
    }

    @Test
    public void testNextFloat() {
        TinyMT32 tiny = TinyMT32.getDefault();
        tiny.setSeed(1);
        float[] c_data = { 0.5926336050f, 0.2286207080f, 0.8650363684f,
                0.5558921099f, 0.8360951543f, 0.8895159960f, 0.4922972322f,
                0.5113201141f, 0.6480515003f, 0.1780070662f };
        for (int i = 0; i < c_data.length; i++) {
            assertEquals(c_data[i], tiny.nextFloat(), 0.00001);
        }
    }

    @Test
    public void testNextDouble() {
        TinyMT32 tiny = TinyMT32.getDefault();
        tiny.setSeed(1);
        double[] c_data = { 0.5926336137, 0.8650363501, 0.8360951594,
                0.4922972448, 0.6480514799, 0.1497518912, 0.2052537946,
                0.8592087870, 0.6802024140, 0.9528347664 };
        for (int i = 0; i < c_data.length; i++) {
            assertEquals(c_data[i], tiny.nextDouble(), 0.00000001);
        }
    }
    
    @Test
    public void testgetThreadLocal() {
        TinyMT32 tiny = TinyMT32.getThreadLocal(0);
        assertEquals("80227acb382d7b47f3714bd1223bedaf", tiny.getCharacteristic());
        tiny = TinyMT32.getThreadLocal(1);
        assertEquals("db46f27d546507bdf3445acd188fa8a3", tiny.getCharacteristic());
        tiny = TinyMT32.getThreadLocal(-1);
        assertEquals("db46f27d546507bdf3445acd188fa8a3", tiny.getCharacteristic());
    }

//    @Test
//    public void testGetTinyMTArrayOver() throws IOException {
//        TinyMT32[] tiny = TinyMT32.getTinyMTArray(65537, 1);
//        assertEquals(65535, tiny.length);
//    }

}
