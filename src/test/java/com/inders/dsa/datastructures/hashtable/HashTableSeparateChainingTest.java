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

public class HashTableSeparateChainingTest {

    // You can set the hash value of this object to be whatever you want
    // This makes it great for testing special cases.
    static class HashObject {
        final int hash, data;

        public HashObject(int hash, int data) {
            this.hash = hash;
            this.data = data;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            else if (o instanceof HashObject) {
                HashObject ho = (HashObject) o;
                return hashCode() == ho.hashCode() && data == ho.data;
            } else {
                return false;
            }
        }
    }

    static final Random RANDOM = new Random();
    static int LOOPS, MAX_SIZE, MAX_RAND_NUM;

    static {
        LOOPS = 500;
        MAX_SIZE = randInt(1, 750);
        MAX_RAND_NUM = randInt(1, 350);
    }

    HashTableSeparateChaining<Integer, Integer> map;

    @BeforeEach
    public void setup() {
        map = new HashTableSeparateChaining<>();
    }

    @Test
    public void testIllegalCreation1() {
        assertThrows(IllegalArgumentException.class,
                () -> new HashTableSeparateChaining<>(-3, 0.5));
    }

    @Test
    public void testIllegalCreation2() {
        assertThrows(IllegalArgumentException.class,
                () -> new HashTableSeparateChaining<>(5, Double.POSITIVE_INFINITY));
    }

    @Test
    public void testIllegalCreation3() {
        assertThrows(IllegalArgumentException.class,
                () -> new HashTableSeparateChaining<>(6, -0.5));
    }

    @Test
    public void testLegalCreation() {
        assertDoesNotThrow(() -> new HashTableSeparateChaining<>(6, 0.9));
    }

    @Test
    public void testUpdatingValue() {

        map.put(1, 1);
        assertEquals(1, map.get(1));

        map.put(1, 5);
        assertEquals(5, map.get(1));

        map.put(1, -7);
        assertEquals(-7, map.get(1));
    }

    @Test
    public void testIterator() {

        HashMap<Integer, Integer> map2 = new HashMap<>();

        for (int loop = 0; loop < LOOPS; loop++) {

            map.clear();
            map2.clear();
            assertTrue(map.isEmpty());

            map = new HashTableSeparateChaining<>();

            List<Integer> rand_nums = genRandList(MAX_SIZE);
            for (Integer key : rand_nums) {
                assertEquals(map2.put(key, key), map.put(key, key));
            }

            int count = 0;
            for (Integer key : map) {
                assertEquals(key, map.get(key));
                assertEquals(map2.get(key), map.get(key));
                assertTrue(map.contains(key));
                assertTrue(rand_nums.contains(key));
                count++;
            }

            for (Integer key : map2.keySet()) {
                assertEquals(key, map.get(key));
            }

            Set<Integer> set = new HashSet<>();
            for (int n : rand_nums) {
                set.add(n);
            }

            assertEquals(count, set.size());
            assertEquals(count, map2.size());
        }
    }

    @Test
    public void testConcurrentModificationException() {
        assertThrows(ConcurrentModificationException.class,
                () -> {
                    map.put(1, 1);
                    map.put(2, 1);
                    map.put(3, 1);
                    for (Integer key : map) {
                        map.put(4, 4);
                    }
                });
    }

    @Test
    public void testConcurrentModificationException2() {
        assertThrows(ConcurrentModificationException.class,
                () -> {
                    map.put(1, 1);
                    map.put(2, 1);
                    map.put(3, 1);
                    for (Integer key : map) {
                        map.remove(2);
                    }
                });
    }

    @Test
    public void randomRemove() {

        HashTableSeparateChaining<Integer, Integer> map;

        for (int loop = 0; loop < LOOPS; loop++) {

            map = new HashTableSeparateChaining<>();
            map.clear();

            // Add some random values
            Set<Integer> keys_set = new HashSet<>();
            for (int i = 0; i < MAX_SIZE; i++) {
                int randomVal = randInt(-MAX_RAND_NUM, MAX_RAND_NUM);
                keys_set.add(randomVal);
                map.put(randomVal, 5);
            }

            assertEquals(keys_set.size(), map.size());

            List<Integer> keys = map.keys();
            for (Integer key : keys) {
                map.remove(key);
            }

            assertTrue(map.isEmpty());
        }
    }

    @Test
    public void removeTest() {

        HashTableSeparateChaining<Integer, Integer> map = new HashTableSeparateChaining<>(7);

        // Add three elements
        map.put(11, 0);
        map.put(12, 0);
        map.put(13, 0);
        assertEquals(3, map.size());

        // Add ten more
        for (int i = 1; i <= 10; i++) {
            map.put(i, 0);
        }
        assertEquals(13, map.size());

        // Remove ten
        for (int i = 1; i <= 10; i++) {
            map.remove(i);
        }
        assertEquals(3, map.size());

        // remove three
        map.remove(11);
        map.remove(12);
        map.remove(13);
        assertEquals(0, map.size());
    }

    @Test
    public void removeTestComplex1() {

        HashTableSeparateChaining<HashObject, Integer> map = new HashTableSeparateChaining<>();

        HashObject o1 = new HashObject(88, 1);
        HashObject o2 = new HashObject(88, 2);
        HashObject o3 = new HashObject(88, 3);
        HashObject o4 = new HashObject(88, 4);

        map.put(o1, 111);
        map.put(o2, 111);
        map.put(o3, 111);
        map.put(o4, 111);

        map.remove(o2);
        map.remove(o3);
        map.remove(o1);
        map.remove(o4);

        assertEquals(0, map.size());
    }

    @Test
    public void testRandomMapOperations() {

        HashMap<Integer, Integer> jmap = new HashMap<>();

        for (int loop = 0; loop < LOOPS; loop++) {

            map.clear();
            jmap.clear();
            assertEquals(map.size(), jmap.size());

            map = new HashTableSeparateChaining<>();

            final double probability1 = Math.random();
            final double probability2 = Math.random();

            List<Integer> nums = genRandList(MAX_SIZE);
            for (int i = 0; i < MAX_SIZE; i++) {

                double r = Math.random();

                int key = nums.get(i);
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

        HashTableSeparateChaining<Integer, LinkedList<Integer>> m = new HashTableSeparateChaining<>();
        HashMap<Integer, LinkedList<Integer>> hm = new HashMap<>();

        for (int loop = 0; loop < LOOPS; loop++) {

            m.clear();
            hm.clear();
            assertEquals(hm.size(), m.size());

            int sz = randInt(1, MAX_SIZE);
            m = new HashTableSeparateChaining<>(sz);
            hm = new HashMap<>(sz);

            final double probability = Math.random();

            for (int i = 0; i < MAX_SIZE; i++) {

                int index = randInt(0, MAX_SIZE - 1);
                LinkedList<Integer> l1 = m.get(index);
                LinkedList<Integer> l2 = hm.get(index);

                if (l2 == null) {
                    l1 = new LinkedList<Integer>();
                    l2 = new LinkedList<Integer>();
                    m.put(index, l1);
                    hm.put(index, l2);
                }

                int rand_val = randInt(-MAX_SIZE, MAX_SIZE);

                if (Math.random() < probability) {

                    l1.removeFirstOccurrence(rand_val);
                    l2.removeFirstOccurrence(rand_val);

                } else {

                    l1.add(rand_val);
                    l2.add(rand_val);
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
    static List<Integer> genRandList(int sz) {

        List<Integer> lst = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++) {
            lst.add(randInt(-MAX_RAND_NUM, MAX_RAND_NUM));
        }
        Collections.shuffle(lst);
        return lst;
    }
}
