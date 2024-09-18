package com.inders.dsa.datastructures.priorityqueue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndexedBinaryHeapTest {

    private static final int LOOPS = 100;
    private static final int MAX_SZ = 100;

    @Test
    public void testEmpty() {
        IndexedBinaryHeap<Integer> q = new IndexedBinaryHeap<>();
        assertEquals(0, q.size());
        assertTrue(q.isEmpty());
        assertNull(q.poll());
        assertNull(q.peek());
    }

    @Test
    public void testHeapProperty() {

        IndexedBinaryHeap<Integer> q = new IndexedBinaryHeap<>();
        Integer[] nums = {3, 2, 5, 6, 7, 9, 4, 8, 1};

        // Try manually creating heap
        for (int n : nums) {
            q.add(n);
        }
        for (int i = 1; i <= 9; i++) {
            assertEquals(i, q.poll());
        }

        q.clear();

        // Try heapify constructor
        q = new IndexedBinaryHeap<>(nums);
        for (int i = 1; i <= 9; i++) {
            assertEquals(i, q.poll());
        }
    }

    @Test
    public void testHeapify() {

        for (int i = 1; i < LOOPS; i++) {

            Integer[] lst = genRandArray(i);
            IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<>(lst);

            PriorityQueue<Integer> pq2 = new PriorityQueue<>(i);
            pq2.addAll(Arrays.asList(lst));

            assertTrue(pq.isMinHeap(0));
            while (!pq2.isEmpty()) {
                assertEquals(pq2.poll(), pq.poll());
            }
        }
    }

    @Test
    public void testClear() {

        IndexedBinaryHeap<String> q;
        String[] strs = {"aa", "bb", "cc", "dd", "ee"};
        q = new IndexedBinaryHeap<>(strs);
        q.clear();
        assertEquals(0, q.size());
        assertTrue(q.isEmpty());
    }

    @Test
    public void testContainment() {

        String[] strs = {"aa", "bb", "cc", "dd", "ee"};
        IndexedBinaryHeap<String> q = new IndexedBinaryHeap<>(strs);
        q.remove("aa");
        assertFalse(q.contains("aa"));
        q.remove("bb");
        assertFalse(q.contains("bb"));
        q.remove("cc");
        assertFalse(q.contains("cc"));
        q.remove("dd");
        assertFalse(q.contains("dd"));
        q.clear();
        assertFalse(q.contains("ee"));
    }

    @Test
    public void testContainmentRandomized() {

        for (int i = 0; i < LOOPS; i++) {

            List<Integer> randNums = genRandList(100);
            PriorityQueue<Integer> PQ = new PriorityQueue<>();
            IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<>();
            for (int j = 0; j < randNums.size(); j++) {
                pq.add(randNums.get(j));
                PQ.add(randNums.get(j));
            }

            for (int j = 0; j < randNums.size(); j++) {

                int randVal = randNums.get(j);
                assertEquals(PQ.contains(randVal), pq.contains(randVal));
                pq.remove(randVal);
                PQ.remove(randVal);
                assertEquals(PQ.contains(randVal), pq.contains(randVal));
            }
        }
    }

    public void sequentialRemoving(Integer[] in, Integer[] removeOrder) {

        assertEquals(removeOrder.length, in.length);

        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<>(in);
        PriorityQueue<Integer> PQ = new PriorityQueue<>();
        for (int value : in) PQ.offer(value);

        assertTrue(pq.isMinHeap(0));

        for (int i = 0; i < removeOrder.length; i++) {

            int elem = removeOrder[i];

            assertEquals(PQ.peek(), pq.peek());
            assertEquals(PQ.remove(elem), pq.remove(elem));
            assertEquals(PQ.size(), pq.size());
            assertTrue(pq.isMinHeap(0));
        }

        assertTrue(pq.isEmpty());
    }

    @Test
    public void testRemoving() {

        Integer[] in = {1, 2, 3, 4, 5, 6, 7};
        Integer[] removeOrder = {1, 3, 6, 4, 5, 7, 2};
        sequentialRemoving(in, removeOrder);

        in = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        removeOrder = new Integer[] {7, 4, 6, 10, 2, 5, 11, 3, 1, 8, 9};
        sequentialRemoving(in, removeOrder);

        in = new Integer[] {8, 1, 3, 3, 5, 3};
        removeOrder = new Integer[] {3, 3, 5, 8, 1, 3};
        sequentialRemoving(in, removeOrder);

        in = new Integer[] {7, 7, 3, 1, 1, 2};
        removeOrder = new Integer[] {2, 7, 1, 3, 7, 1};
        sequentialRemoving(in, removeOrder);

        in = new Integer[] {32, 66, 93, 42, 41, 91, 54, 64, 9, 35};
        removeOrder = new Integer[] {64, 93, 54, 41, 35, 9, 66, 42, 32, 91};
        sequentialRemoving(in, removeOrder);
    }

    @Test
    public void testRemovingDuplicates() {

        Integer[] in = new Integer[] {2, 7, 2, 11, 7, 13, 2};
        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<>(in);

        assertEquals(2, pq.peek());
        pq.add(3);

        assertEquals(2, pq.poll());
        assertEquals(2, pq.poll());
        assertEquals(2, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(7, pq.poll());
        assertEquals(7, pq.poll());
        assertEquals(11, pq.poll());
        assertEquals(13, pq.poll());
    }

    @Test
    public void testRandomizedPolling() {

        for (int i = 0; i < LOOPS; i++) {

            int sz = i;
            List<Integer> randNums = genRandList(sz);
            PriorityQueue<Integer> pq1 = new PriorityQueue<>();
            IndexedBinaryHeap<Integer> pq2 = new IndexedBinaryHeap<>();

            // Add all the elements to both priority queues
            for (Integer value : randNums) {
                pq1.offer(value);
                pq2.add(value);
            }

            while (!pq1.isEmpty()) {

                assertTrue(pq2.isMinHeap(0));
                assertEquals(pq2.size(), pq1.size());
                assertEquals(pq2.peek(), pq1.peek());
                assertEquals(pq2.contains(pq2.peek()), pq1.contains(pq1.peek()));

                Integer v1 = pq1.poll();
                Integer v2 = pq2.poll();

                assertEquals(v2, v1);
                assertEquals(pq2.peek(), pq1.peek());
                assertEquals(pq2.size(), pq1.size());
                assertTrue(pq2.isMinHeap(0));
            }
        }
    }

    @Test
    public void testRandomizedRemoving() {

        for (int i = 0; i < LOOPS; i++) {

            int sz = i;
            List<Integer> randNums = genRandList(sz);
            PriorityQueue<Integer> pq1 = new PriorityQueue<>();
            IndexedBinaryHeap<Integer> pq2 = new IndexedBinaryHeap<>();

            // Add all the elements to both priority queues
            for (Integer value : randNums) {
                pq1.offer(value);
                pq2.add(value);
            }

            Collections.shuffle(randNums);
            int index = 0;

            while (!pq1.isEmpty()) {

                int removeNum = randNums.get(index++);

                assertTrue(pq2.isMinHeap(0));
                assertEquals(pq2.size(), pq1.size());
                assertEquals(pq2.peek(), pq1.peek());
                pq1.remove(removeNum);
                pq2.remove(removeNum);
                assertEquals(pq2.peek(), pq1.peek());
                assertEquals(pq2.size(), pq1.size());
                assertTrue(pq2.isMinHeap(0));
            }
        }
    }

    @Test
    public void testPQReusability() {

        List<Integer> SZs = genUniqueRandList(LOOPS);

        PriorityQueue<Integer> PQ = new PriorityQueue<>();
        IndexedBinaryHeap<Integer> pq = new IndexedBinaryHeap<>();

        for (int sz : SZs) {

            pq.clear();
            PQ.clear();

            List<Integer> nums = genRandList(sz);
            for (int n : nums) {
                pq.add(n);
                PQ.add(n);
            }

            Collections.shuffle(nums);

            for (int i = 0; i < sz / 2; i++) {

                // Sometimes add a new number into the IndexedBinaryHeap
                if (0.25 < Math.random()) {
                    int randNum = (int) (Math.random() * 10000);
                    PQ.add(randNum);
                    pq.add(randNum);
                }

                int removeNum = nums.get(i);

                assertTrue(pq.isMinHeap(0));
                assertEquals(pq.size(), PQ.size());
                assertEquals(pq.peek(), PQ.peek());

                PQ.remove(removeNum);
                pq.remove(removeNum);

                assertEquals(pq.peek(), PQ.peek());
                assertEquals(pq.size(), PQ.size());
                assertTrue(pq.isMinHeap(0));
            }
        }
    }

    static Integer[] genRandArray(int sz) {
        Integer[] lst = new Integer[sz];
        for (int i = 0; i < sz; i++) {
            lst[i] = (int) (Math.random() * MAX_SZ);
        }
        return lst;
    }

    // Generate a list of random numbers
    static List<Integer> genRandList(int sz) {
        List<Integer> lst = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++) {
            lst.add((int) (Math.random() * MAX_SZ));
        }
        return lst;
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

