package com.inders.dsa.datastructures.priorityqueue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndexedBinaryHeapTest {

    @Test
    public void testIllegalSizeOfNegativeOne() {
        assertThrows(IllegalArgumentException.class,
                () -> new IndexedBinaryHeap<String>(-1));
    }

    @Test
    public void testIllegalSizeOfZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new IndexedBinaryHeap<String>(0));
    }

    @Test
    public void testLegalSize() {
        new IndexedBinaryHeap<String>(1);
    }

    @Test
    public void testContainsValidKey() {
        IndexedBinaryHeap<String> pq = new IndexedBinaryHeap<String>(10);
        pq.insert(5, "abcdef");
        assertTrue(pq.contains(5));
    }

    @Test
    public void testContainsInvalidKey() {
        IndexedBinaryHeap<String> pq = new IndexedBinaryHeap<String>(10);
        pq.insert(5, "abcdef");
        assertFalse(pq.contains(3));
    }

    @Test
    public void testDuplicateKeys() {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    IndexedBinaryHeap<String> pq = new IndexedBinaryHeap<String>(10);
                    pq.insert(5, "abcdef");
                    pq.insert(5, "xyz");
                });
    }

    @Test
    public void testUpdateKeyValue() {
        IndexedBinaryHeap<String> pq = new IndexedBinaryHeap<String>(10);
        pq.insert(5, "abcdef");
        pq.update(5, "xyz");
        assertEquals("xyz", pq.valueOf(5));
    }

    @Test
    public void testTestDecreaseKey() {
        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<Integer>(10);
        pq.insert(3, 5);
        pq.decrease(3, 4);
        assertEquals(4, pq.valueOf(3));
    }

    @Test
    public void testTestDecreaseKeyNoUpdate() {
        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<Integer>(10);
        pq.insert(3, 5);
        pq.decrease(3, 6);
        assertEquals(5, pq.valueOf(3));
    }

    @Test
    public void testTestIncreaseKey() {
        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<Integer>(10);
        pq.insert(3, 5);
        pq.increase(3, 6);
        assertEquals(6, pq.valueOf(3));
    }

    @Test
    public void testTestIncreaseKeyNoUpdate() {
        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<Integer>(10);
        pq.insert(3, 5);
        pq.increase(3, 4);
        assertEquals(5, pq.valueOf(3));
    }

    @Test
    public void testPeekAndPollMinIndex() {
        // pairs[i][0] is the index
        // pairs[i][1] is the value
        Integer[][] pairs = {
                {4, 1},
                {7, 5},
                {1, 6},
                {5, 8},
                {3, 7},
                {6, 9},
                {8, 0},
                {2, 4},
                {9, 3},
                {0, 2}
        };
        sortPairsByValue(pairs);

        int n = pairs.length;
        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<Integer>(n);
        for (int i = 0; i < n; i++) {
            pq.insert(pairs[i][0], pairs[i][1]);
        }

        Integer minIndex;
        for (int i = 0; i < n; i++) {
            minIndex = pq.peekKeyIndex();
            assertEquals(pairs[i][0], minIndex);
            minIndex = pq.pollKeyIndex();
            assertEquals(pairs[i][0], minIndex);
        }
    }

    @Test
    public void testPeekAndPollMinValue() {
        // pairs[i][0] is the index
        // pairs[i][1] is the value
        Integer[][] pairs = {
                {4, 1},
                {7, 5},
                {1, 6},
                {5, 8},
                {3, 7},
                {6, 9},
                {8, 0},
                {2, 4},
                {9, 3},
                {0, 2}
        };
        sortPairsByValue(pairs);

        int n = pairs.length;
        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<Integer>(n);
        for (int i = 0; i < n; i++) {
            pq.insert(pairs[i][0], pairs[i][1]);
        }

        Integer minValue;
        for (int i = 0; i < n; i++) {
            assertEquals(pairs[i][1], pq.valueOf(pairs[i][0]));
            minValue = pq.peekValue();
            assertEquals(pairs[i][1], minValue);
            minValue = pq.pollValue();
            assertEquals(pairs[i][1], minValue);
        }
    }

    @Test
    public void testInsertionAndValueOf() {
        String[] names = {"jackie", "wilson", "catherine", "jason", "bobby", "sia"};
        IndexedBinaryHeap<String> pq = new IndexedBinaryHeap<String>(names.length);
        for (int i = 0; i < names.length; i++) {
            pq.insert(i, names[i]);
        }
        for (int i = 0; i < names.length; i++) {
            assertEquals(names[i], pq.valueOf(i));
        }
    }

    @Test
    public void testOperations() {
        int n = 7;
        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<Integer>(n);

        pq.insert(4, 4);
        assertTrue(pq.contains(4));
        assertEquals(4, pq.peekValue());
        assertEquals(4, pq.peekKeyIndex());
        pq.update(4, 8);
        assertEquals(8, pq.peekValue());
        assertEquals(4, pq.pollKeyIndex());
        assertFalse(pq.contains(4));
        pq.insert(3, 99);
        pq.insert(1, 101);
        pq.insert(2, 60);
        assertEquals(60, pq.peekValue());
        assertEquals(2, pq.peekKeyIndex());
        pq.increase(2, 150);
        assertEquals(99, pq.peekValue());
        assertEquals(3, pq.peekKeyIndex());
        pq.increase(3, 250);
        assertEquals(101, pq.peekValue());
        assertEquals(1, pq.peekKeyIndex());
        pq.decrease(3, -500);
        assertEquals(-500, pq.peekValue());
        assertEquals(3, pq.peekKeyIndex());
        assertTrue(pq.contains(3));
        pq.delete(3);
        assertFalse(pq.contains(3));
        assertEquals(101, pq.peekValue());
        assertEquals(1, pq.peekKeyIndex());
        assertEquals(101, pq.valueOf(1));
    }

    @Test
    public void testRandomInsertionsAndPolls() {
        for (int n = 1; n < 1500; n++) {
            int bound = 100000;
            int[] randomValues = genRandArray(n, -bound, +bound);
            IndexedBinaryHeap<Integer> pq1 = new IndexedBinaryHeap<Integer>(n);
            PriorityQueue<Integer> pq2 = new PriorityQueue<Integer>(n);

            final double p = Math.random();

            for (int i = 0; i < n; i++) {
                pq1.insert(i, randomValues[i]);
                pq2.add(randomValues[i]);

                if (Math.random() < p) {
                    if (!pq2.isEmpty()) {
                        assertEquals(pq2.poll(), pq1.pollValue());
                    }
                }

                assertEquals(pq2.size(), pq1.size());
                assertEquals(pq2.isEmpty(), pq1.isEmpty());
                if (!pq2.isEmpty()) {
                    assertEquals(pq2.peek(), pq1.peekValue());
                }
            }
        }
    }

    @Test
    public void testRandomInsertionsAndRemovals() {
        for (int n = 1; n < 500; n++) {
            List<Integer> indexes = genUniqueRandList(n);
            IndexedBinaryHeap<Integer> pq1 = new IndexedBinaryHeap<Integer>(n);
            PriorityQueue<Integer> pq2 = new PriorityQueue<Integer>(n);
            List<Integer> indexesToRemove = new ArrayList<>();

            final double p = Math.random();
            for (int i = 0; i < n; i++) {
                int ii = indexes.get(i);
                pq1.insert(ii, ii);
                pq2.add(ii);
                indexesToRemove.add(ii);
                assertTrue(pq1.isMinHeap());

                if (Math.random() < p) {
                    int itemsToRemove = (int) (Math.random() * 10);
                    while (itemsToRemove-- > 0 && indexesToRemove.size() > 0) {
                        int iii = (int) (Math.random() * indexesToRemove.size());
                        int indexToRemove = indexesToRemove.get(iii);
                        boolean contains1 = pq1.contains(indexToRemove);
                        boolean contains2 = pq2.contains(indexToRemove);
                        assertEquals(contains2, contains1);
                        assertTrue(pq1.isMinHeap());
                        if (contains2) {
                            pq1.delete(indexToRemove);
                            pq2.remove(indexToRemove);
                            indexesToRemove.remove(iii);
                        }
                        if (!pq2.isEmpty()) {
                            assertEquals(pq2.peek(), pq1.peekValue());
                        }
                    }
                }

                for (int index : indexesToRemove) {
                    assertTrue(pq2.contains(index)); // Sanity check.
                    assertTrue(pq1.contains(index));
                }

                assertEquals(pq2.size(), pq1.size());
                assertEquals(pq2.isEmpty(), pq1.isEmpty());
                if (!pq2.isEmpty()) {
                    assertEquals(pq2.peek(), pq1.peekValue());
                }
            }
        }
    }

    static int[] genRandArray(int n, int lo, int hi) {
        return new Random().ints(n, lo, hi).toArray();
    }

    static void sortPairsByValue(Integer[][] pairs) {
        Arrays.sort(
                pairs,
                new Comparator<Integer[]>() {
                    @Override
                    public int compare(Integer[] pair1, Integer[] pair2) {
                        return pair1[1] - pair2[1];
                    }
                });
    }

    // Generate a list of unique random numbers
    static List<Integer> genUniqueRandList(int sz) {
        List<Integer> lst = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++) {
            lst.add(i);
        }
        Collections.shuffle(lst);
        return lst;
    }
}
