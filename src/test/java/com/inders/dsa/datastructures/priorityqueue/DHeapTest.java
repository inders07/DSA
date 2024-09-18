package com.inders.dsa.datastructures.priorityqueue;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DHeapTest {

    static final int LOOPS = 1000;
    static final int MAX_SZ = 100;

    @Test
    public void testEmpty() {
        DHeap<Integer> q = new DHeap<>(4, 0);
        assertEquals(0, q.size());
        assertTrue(q.isEmpty());
        assertNull(q.poll());
        assertNull(q.peek());
    }

    @Test
    public void testHeapProperty() {

        DHeap<Integer> q = new DHeap<>(3, 30);
        Integer[] nums = {3, 2, 5, 6, 7, 9, 4, 8, 1};

        // Try manually creating heap
        for (int n : nums) {
            q.add(n);
        }
        for (int i = 1; i <= 9; i++) {
            assertEquals(i, q.poll());
        }
    }

    @Test
    public void testPriorityQueueSizeParam() {
        for (int i = 1; i < LOOPS; i++) {

            Integer[] lst = genRandArray(i);

            DHeap<Integer> pq = new DHeap<>(i, lst.length);
            PriorityQueue<Integer> pq2 = new PriorityQueue<>(i);

            for (int x : lst) {
                pq2.add(x);
                pq.add(x);
            }
            while (!pq2.isEmpty()) {
                assertEquals(pq2.poll(), pq.poll());
            }
        }
    }

    @Test
    public void testPriorityRandomOperations() {
        for (int loop = 0; loop < LOOPS; loop++) {

            double p1 = Math.random();
            double p2 = Math.random();
            if (p2 < p1) {
                double tmp = p1;
                p1 = p2;
                p2 = tmp;
            }

            Integer[] ar = genRandArray(LOOPS);
            int d = 2 + (int) (Math.random() * 6);
            DHeap<Integer> pq = new DHeap<>(d, LOOPS);
            PriorityQueue<Integer> pq2 = new PriorityQueue<>(LOOPS);

            for (int i = 0; i < LOOPS; i++) {
                int e = ar[i];
                double r = Math.random();
                if (r <= p1) {
                    pq.add(e);
                    pq2.add(e);
                } else if (p1 < r && r <= p2) {
                    if (!pq2.isEmpty()) {
                        assertEquals(pq2.poll(), pq.poll());
                    }
                } else {
                    pq.clear();
                    pq2.clear();
                }
            }

            assertEquals(pq2.peek(), pq.peek());
        }
    }

    @Test
    public void testClear() {
        String[] strs = {"aa", "bb", "cc", "dd", "ee"};
        DHeap<String> q = new DHeap<>(2, strs.length);
        for (String s : strs) {
            q.add(s);
        }
        q.clear();
        assertEquals(0, q.size());
        assertTrue(q.isEmpty());
    }

    @Test
    public void testContainmentRandomized() {

        for (int i = 0; i < LOOPS; i++) {
            List <Integer> randNums = genRandList(100);
            PriorityQueue <Integer> PQ = new PriorityQueue<>();
            DHeap <Integer> pq = new DHeap<>(5, 100);
            for (Integer randNum : randNums) {
                pq.add(randNum);
                PQ.add(randNum);
            }

            for (int randVal : randNums) {
                assertEquals(PQ.contains(randVal), pq.contains(randVal));
            }
        }
    }

    @Test
    public void testRemovingDuplicates() {

        Integer[] in = new Integer[] {2, 7, 2, 11, 7, 13, 2};
        DHeap<Integer> pq = new DHeap<>(3, in.length + 1);

        for (Integer x : in) {
            pq.add(x);
        }
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
