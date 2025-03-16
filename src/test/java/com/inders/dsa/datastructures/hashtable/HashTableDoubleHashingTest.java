package com.inders.dsa.datastructures.hashtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashTableDoubleHashingTest {

    static final Random RANDOM = new Random();
    static int LOOPS, MAX_SIZE, MAX_RAND_NUM;

    static {
        LOOPS = 500;
        MAX_SIZE = randInt(1, 750);
        MAX_RAND_NUM = randInt(1, 350);
    }

    HashTableDoubleHashing<DoubleHashingTestObject, Integer> map;

    @BeforeEach
    public void setup() {
        map = new HashTableDoubleHashing<>();
    }

    @Test
    public void testIllegalCreation1() {
        assertThrows(IllegalArgumentException.class, () -> new HashTableDoubleHashing<>(-3, 0.5));
    }

    @Test
    public void testIllegalCreation2() {
        assertThrows(IllegalArgumentException.class,
                () -> new HashTableDoubleHashing<>(5, Double.POSITIVE_INFINITY));
    }

    @Test
    public void testIllegalCreation3() {
        assertThrows(IllegalArgumentException.class, () -> new HashTableDoubleHashing<>(6, -0.5));
    }

    @Test
    public void testLegalCreation() {
        assertDoesNotThrow(() -> new HashTableQuadraticProbing<>(6, 0.9));
    }

    @Test
    public void testUpdatingValue() {
        DoubleHashingTestObject o1 = new DoubleHashingTestObject(1);
        DoubleHashingTestObject o5 = new DoubleHashingTestObject(5);
        DoubleHashingTestObject on7 = new DoubleHashingTestObject(-7);

        map.put(o1, 1);
        assertEquals(1, map.get(o1));

        map.put(o5, 5);
        assertEquals(5, map.get(o5));

        map.put(on7, -7);
        assertEquals(-7, map.get(on7));
    }

    @Test
    public void testIterator() {

        HashMap<DoubleHashingTestObject, DoubleHashingTestObject> jmap = new HashMap<>();
        HashTableDoubleHashing<DoubleHashingTestObject, DoubleHashingTestObject> mmap =
                new HashTableDoubleHashing<>();
        map = null; // Mark null to make sure is not used

        for (int loop = 0; loop < LOOPS; loop++) {

            mmap.clear();
            jmap.clear();
            assertTrue(mmap.isEmpty());

            List<DoubleHashingTestObject> rand_nums = genRandList(MAX_SIZE);
            for (DoubleHashingTestObject key : rand_nums) {
                assertEquals(jmap.put(key, key), mmap.put(key, key));
            }

            int count = 0;
            for (DoubleHashingTestObject key : mmap) {
                assertEquals(key, mmap.get(key));
                assertEquals(jmap.get(key), mmap.get(key));
                assertTrue(mmap.contains(key));
                assertTrue(rand_nums.contains(key));
                count++;
            }

            for (DoubleHashingTestObject key : jmap.keySet()) {
                assertEquals(key, mmap.get(key));
            }

            Set<DoubleHashingTestObject> set = new HashSet<>();
            set.addAll(rand_nums);

            // System.out.println(set.size() + " " + jmap.size() + " " + count);

            assertEquals(count, set.size());
            assertEquals(count, jmap.size());
        }
    }

    @Test
    public void testConcurrentModificationException() {
        assertThrows(ConcurrentModificationException.class,
                () -> {
                    // System.out.println("testConcurrentModificationException");
                    DoubleHashingTestObject o1 = new DoubleHashingTestObject(1);
                    DoubleHashingTestObject o2 = new DoubleHashingTestObject(2);
                    DoubleHashingTestObject o3 = new DoubleHashingTestObject(3);
                    DoubleHashingTestObject o4 = new DoubleHashingTestObject(4);
                    map.put(o1, 1);
                    map.put(o2, 1);
                    map.put(o3, 1);
                    for (DoubleHashingTestObject key : map) {
                        map.put(o4, 4);
                    }
                });
    }

    @Test
    public void testConcurrentModificationException2() {
        assertThrows(ConcurrentModificationException.class,
                () -> {
                    DoubleHashingTestObject o1 = new DoubleHashingTestObject(1);
                    DoubleHashingTestObject o2 = new DoubleHashingTestObject(2);
                    DoubleHashingTestObject o3 = new DoubleHashingTestObject(3);
                    map.put(o1, 1);
                    map.put(o2, 1);
                    map.put(o3, 1);
                    for (DoubleHashingTestObject key : map) {
                        map.remove(o2);
                    }
                });
    }

    @Test
    public void randomRemove() {

        HashTableDoubleHashing<DoubleHashingTestObject, Integer> map;

        for (int loop = 0; loop < LOOPS; loop++) {

            map = new HashTableDoubleHashing<>();
            map.clear();

            // Add some random values
            Set<DoubleHashingTestObject> keys_set = new HashSet<>();
            for (int i = 0; i < MAX_SIZE; i++) {
                int randomVal = randInt(-MAX_RAND_NUM, MAX_RAND_NUM);
                DoubleHashingTestObject obj = new DoubleHashingTestObject(randomVal);
                keys_set.add(obj);
                map.put(obj, 5);
            }

            assertEquals(keys_set.size(), map.size());

            List<DoubleHashingTestObject> keys = map.keys();
            for (DoubleHashingTestObject key : keys) {
                map.remove(key);
            }

            assertTrue(map.isEmpty());
        }
    }

    @Test
    public void removeTest() {

        HashTableDoubleHashing<DoubleHashingTestObject, Integer> map = new HashTableDoubleHashing<>(7);

        DoubleHashingTestObject o11 = new DoubleHashingTestObject(11);
        DoubleHashingTestObject o12 = new DoubleHashingTestObject(12);
        DoubleHashingTestObject o13 = new DoubleHashingTestObject(13);

        // Add three elements
        map.put(o11, 0);
        map.put(o12, 0);
        map.put(o13, 0);
        assertEquals(3, map.size());

        // Add ten more
        for (int i = 1; i <= 10; i++) {
            map.put(new DoubleHashingTestObject(i), 0);
        }
        assertEquals(13, map.size());

        // Remove ten
        for (int i = 1; i <= 10; i++) {
            map.remove(new DoubleHashingTestObject(i));
        }
        assertEquals(3, map.size());

        // remove three
        map.remove(o11);
        map.remove(o12);
        map.remove(o13);
        assertEquals(0, map.size());
    }

    @Test
    public void testRandomMapOperations() {

        HashMap<DoubleHashingTestObject, Integer> jmap = new HashMap<>();

        for (int loop = 0; loop < LOOPS; loop++) {

            map.clear();
            jmap.clear();
            assertEquals(map.size(), jmap.size());

            map = new HashTableDoubleHashing<>();

            final double probability1 = Math.random();
            final double probability2 = Math.random();

            List<DoubleHashingTestObject> nums = genRandList(MAX_SIZE);
            for (int i = 0; i < MAX_SIZE; i++) {

                double r = Math.random();

                DoubleHashingTestObject key = nums.get(i);
                int val = i;

                if (r < probability1) {
                    assertEquals(map.put(key, val), jmap.put(key, val));
                }

                assertEquals(map.get(key), jmap.get(key));
                assertEquals(map.contains(key), jmap.containsKey(key));
                assertEquals(map.size(), jmap.size());

                if (r > probability2) {
                    assertEquals(jmap.remove(key), map.remove(key));
                }

                assertEquals(map.get(key), jmap.get(key));
                assertEquals(map.contains(key), jmap.containsKey(key));
                assertEquals(map.size(), jmap.size());
            }
        }
    }

    @Test
    public void randomIteratorTests() {

        HashTableDoubleHashing<DoubleHashingTestObject, LinkedList<Integer>> m =
                new HashTableDoubleHashing<>();
        HashMap<DoubleHashingTestObject, LinkedList<Integer>> hm = new HashMap<>();

        for (int loop = 0; loop < LOOPS; loop++) {

            m.clear();
            hm.clear();
            assertEquals(0, m.size());

            int sz = randInt(1, MAX_SIZE);
            m = new HashTableDoubleHashing<>(sz);
            hm = new HashMap<>(sz);

            final double probability = Math.random();

            for (int i = 0; i < MAX_SIZE; i++) {

                int keyValue = randInt(0, MAX_SIZE - 1);
                DoubleHashingTestObject key = new DoubleHashingTestObject(keyValue);
                LinkedList<Integer> l1 = m.get(key);
                LinkedList<Integer> l2 = hm.get(key);

                if (l2 == null) {
                    l1 = new LinkedList<Integer>();
                    l2 = new LinkedList<Integer>();
                    m.put(key, l1);
                    hm.put(key, l2);
                }

                int randVal = randInt(-MAX_SIZE, MAX_SIZE);

                if (Math.random() < probability) {

                    l1.removeFirstOccurrence(randVal);
                    l2.removeFirstOccurrence(randVal);

                } else {

                    l1.add(randVal);
                    l2.add(randVal);
                }

                assertEquals(hm.size(), m.size());
                assertEquals(l2, l1);
            }
        }
    }

    static int randInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    // Generate a list of random numbers
    static List<DoubleHashingTestObject> genRandList(int sz) {

        List<DoubleHashingTestObject> lst = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++) {
            int randNum = randInt(-MAX_RAND_NUM, MAX_RAND_NUM);
            DoubleHashingTestObject obj = new DoubleHashingTestObject(randNum);
            lst.add(obj);
        }
        Collections.shuffle(lst);
        return lst;
    }
}
