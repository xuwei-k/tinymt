package jp.ac.hiroshima_u.sci.math.saito.tinymt;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

public class ThreadLocalRandomTest {
    private final static String[] characteristic = {
            "80227acb382d7b47f3714bd1223bedaf",
            "db46f27d546507bdf3445acd188fa8a3",
            "e1c47f40863c844be54fc078750562ef",
            "d1346cadec1fbc329d1fe2283a577b77",
            "c7487f9b2e8f8aaa231ac4b22b14db9b",
            "b0d7d986ce26326dbb6b0fccda28bdbb",
            "c0edefb49baf0424fd235ce48e8d26fb",
            "b24aa41c9eba05c4ffa8fc0e90438c37",
            "a6aca2283f3e12ed69818bd95c35d00b",
            "a86f5f29166785b075c431b027044287",
            "e570ac318b37d9caa71ba4b99bad6a3b",
            "d227f7cbfb9408765dd7da7d51790a13",
            "833689351c2ed91b5f56d10bde4b5197",
            "dd68340e3a7a7b8d993c6f412b125ca7",
            "9898245c4fccabd1617bb16fff089643",
            "e04a99bcc12b07a661440bef54402207"
    };
    private final static String[] save = new String[characteristic.length];
    private static HashMap<Long, int[]> map = new HashMap<Long, int[]>();

    @Test
    public void testCurrent() throws InterruptedException {
        Thread[] t = new Thread[20];
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread() {
                public void run() {
                    TinyMT32 tiny = ThreadLocalRandom.currentTinyMT();
                    long threadId = Thread.currentThread().getId();
                    save[(int)(threadId % characteristic.length)] = tiny.getCharacteristic();
                }
            };
            t[i].start();            
        }
        for (int i = 0; i < t.length; i++) {
            t[i].join();
        }
        for (int i = 0; i < characteristic.length; i++) {
            assertEquals(characteristic[i], save[i]);
        }
    }
    
    public void testCurrent2() throws InterruptedException {
        Thread[] t = new Thread[10];
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread() {
                public void run() {
                    Random r = ThreadLocalRandom.current();
                    long threadId = Thread.currentThread().getId();
                    int[] array = new int[10];
                    for (int i = 0; i < array.length; i++) {
                        array[i] = r.nextInt();
                    }
                    map.put(threadId, array);
                }
            };
            t[i].start();            
        }
        for (int i = 0; i < t.length; i++) {
            t[i].join();
        }
        int[] pre = new int[10];
        for (int[] array : map.values()) {
            assertFalse(Arrays.equals(pre, array));
        }
    }
}
