package com.inders.dsa.datastructures.linkedlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkedListTest {
    private static final int LOOPS = 10000;
    private static final int TEST_SZ = 40;
    private static final int NUM_NULLS = TEST_SZ / 5;
    private static final int MAX_RAND_NUM = 250;

    DoublyLinkedList<Integer> list;

    @BeforeEach
    public void setup() {
        list = new DoublyLinkedList<>();
    }

    @Test
    public void testEmptyList() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testRemoveFirstOfEmpty() {
        assertThrows(NoSuchElementException.class, () -> list.removeFirst());
    }

    @Test
    public void testRemoveLastOfEmpty() {
        assertThrows(NoSuchElementException.class, () -> list.removeLast());
    }

    @Test
    public void testRemoveOfEmpty() {
        assertThrows(NoSuchElementException.class, () -> list.remove());
    }

    @Test
    public void testPeekFirstOfEmpty() {
        assertNull(list.peekFirst());
    }

    @Test
    public void testPeekLastOfEmpty() {
        assertNull(list.peekLast());
    }

    @Test
    public void testPeekOfEmpty() {
        assertNull(list.peek());
    }

    @Test
    public void testPollFirstOfEmpty() {
        assertNull(list.pollFirst());
    }

    @Test
    public void testPollLastOfEmpty() {
        assertNull(list.pollLast());
    }

    @Test
    public void testPollOfEmpty() {
        assertNull(list.poll());
    }

    @Test
    public void testContains() {
        list.addFirst(3);
        assertTrue(list.contains(3));
        assertFalse(list.contains(4));
        list.remove(3);
        assertFalse(list.contains(3));
        list.addFirst(null);
        assertTrue(list.contains(null));
        list.remove(null);
        assertFalse(list.contains(null));
    }

    @Test
    public void testAddFirst() {
        list.addFirst(3);
        assertEquals(1, list.size());
        list.addFirst(5);
        assertEquals(2, list.size());
    }

    @Test
    public void testAddLast() {
        list.addLast(3);
        assertEquals(1, list.size());
        list.addLast(5);
        assertEquals(2, list.size());
    }

    @Test
    public void testAdd() {
        list.add(3);
        assertEquals(1, list.size());
        list.add(5);
        assertEquals(2, list.size());
    }

    @Test
    public void testAddAt() throws Exception {
        list.addAt(0, 1);
        assertEquals(1, list.size());
        list.addAt(1, 2);
        assertEquals(2, list.size());
        list.addAt(1, 3);
        assertEquals(3, list.size());
        list.addAt(2, 4);
        assertEquals(4, list.size());
        list.addAt(1, 8);
        assertEquals(5, list.size());
    }

    @Test
    public void testRemoveFirst() {
        list.addFirst(3);
        assertEquals(3, list.removeFirst());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveLast() {
        list.addLast(4);
        assertEquals(4, list.removeLast());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemove() {
        list.addFirst(4);
        assertEquals(4, list.remove());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testPollFirst() {
        list.addFirst(3);
        assertEquals(3, list.pollFirst());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testPollLast() {
        list.addLast(4);
        assertEquals(4, list.pollLast());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testPoll() {
        list.addFirst(3);
        assertEquals(3, list.poll());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testPeekFirst() {
        list.addFirst(4);
        assertEquals(4, list.peekFirst());
        assertEquals(1, list.size());
    }

    @Test
    public void testPeekLast() {
        list.addLast(4);
        assertEquals(4, list.peekFirst());
        assertEquals(1, list.size());
    }

    @Test
    public void testPeek() {
        list.addFirst(4);
        assertEquals(4, list.peek());
        assertEquals(1, list.size());
    }

    @Test
    public void testPeeking() {
        // 5
        list.addFirst(5);
        assertEquals(5, list.peekFirst());
        assertEquals(5, list.peekLast());

        // 6 - 5
        list.addFirst(6);
        assertEquals(6, list.peekFirst());
        assertEquals(5, list.peekLast());

        // 7 - 6 - 5
        list.addFirst(7);
        assertEquals(7, list.peekFirst());
        assertEquals(5, list.peekLast());

        // 7 - 6 - 5 - 8
        list.addLast(8);
        assertEquals(7, list.peekFirst());
        assertEquals(8, list.peekLast());

        // 7 - 6 - 5
        list.removeLast();
        assertEquals(7, list.peekFirst());
        assertEquals(5, list.peekLast());

        // 7 - 6
        list.removeLast();
        assertEquals(7, list.peekFirst());
        assertEquals(6, list.peekLast());

        // 6
        list.removeFirst();
        assertEquals(6, list.peekFirst());
        assertEquals(6, list.peekLast());
    }

    @Test
    public void testRemoving() {
        DoublyLinkedList<String> strs = new DoublyLinkedList<>();
        strs.add("a");
        strs.add("b");
        strs.add("c");
        strs.add("d");
        strs.add("e");
        strs.add("f");
        strs.remove("b");
        strs.remove("a");
        strs.remove("d");
        strs.remove("e");
        strs.remove("c");
        strs.remove("f");
        assertEquals(0, strs.size());
    }

    @Test
    public void testRemoveAt() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.removeAt(0);
        list.removeAt(2);
        assertEquals(2, list.peekFirst());
        assertEquals(3, list.peekLast());
        list.removeAt(1);
        list.removeAt(0);
        assertEquals(0, list.size());
    }

    @Test
    public void testClear() {
        list.add(22);
        list.add(33);
        list.add(44);
        assertEquals(3, list.size());
        list.clear();
        assertEquals(0, list.size());
        list.add(22);
        list.add(33);
        list.add(44);
        assertEquals(3, list.size());
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    public void testRandomizedRemoving() {
        java.util.LinkedList<Integer> javaLinkedList = new java.util.LinkedList<>();
        for (int loops = 0; loops < LOOPS; loops++) {

            list.clear();
            javaLinkedList.clear();

            List<Integer> randNums = genRandList(TEST_SZ);
            for (Integer value : randNums) {
                javaLinkedList.add(value);
                list.add(value);
            }

            Collections.shuffle(randNums);

            for (int i = 0; i < randNums.size(); i++) {

                Integer rm_val = randNums.get(i);
                assertEquals(list.remove(rm_val), javaLinkedList.remove(rm_val));
                assertEquals(list.size(), javaLinkedList.size());

                java.util.Iterator<Integer> iter1 = javaLinkedList.iterator();
                java.util.Iterator<Integer> iter2 = list.iterator();
                while (iter1.hasNext()) assertEquals(iter2.next(), iter1.next());

                iter1 = javaLinkedList.iterator();
                iter2 = list.iterator();
                while (iter1.hasNext()) assertEquals(iter2.next(), iter1.next());
            }

            list.clear();
            javaLinkedList.clear();

            for (Integer value : randNums) {
                javaLinkedList.add(value);
                list.add(value);
            }

            // Try removing elements whether they exist
            for (int i = 0; i < randNums.size(); i++) {

                Integer rm_val = (int) (MAX_RAND_NUM * Math.random());
                assertEquals(list.remove(rm_val), javaLinkedList.remove(rm_val));
                assertEquals(list.size(), javaLinkedList.size());

                java.util.Iterator<Integer> iter1 = javaLinkedList.iterator();
                java.util.Iterator<Integer> iter2 = list.iterator();
                while (iter1.hasNext()) assertEquals(iter2.next(), iter1.next());
            }
        }
    }

    @Test
    public void testRandomizedRemoveAt() {
        java.util.LinkedList<Integer> javaLinkedList = new java.util.LinkedList<>();

        for (int loops = 0; loops < LOOPS; loops++) {

            list.clear();
            javaLinkedList.clear();

            List<Integer> randNums = genRandList(TEST_SZ);

            for (Integer value : randNums) {
                javaLinkedList.add(value);
                list.add(value);
            }

            for (int i = 0; i < randNums.size(); i++) {

                int rm_index = (int) (list.size() * Math.random());

                Integer num1 = javaLinkedList.remove(rm_index);
                Integer num2 = list.removeAt(rm_index);
                assertEquals(num2, num1);
                assertEquals(list.size(), javaLinkedList.size());

                java.util.Iterator<Integer> iter1 = javaLinkedList.iterator();
                java.util.Iterator<Integer> iter2 = list.iterator();
                while (iter1.hasNext()) assertEquals(iter2.next(), iter1.next());
            }
        }
    }

    @Test
    public void testRandomizedIndexOf() {
        java.util.LinkedList<Integer> javaLinkedList = new java.util.LinkedList<>();

        for (int loops = 0; loops < LOOPS; loops++) {

            javaLinkedList.clear();
            list.clear();

            List<Integer> randNums = genUniqueRandList(TEST_SZ);

            for (Integer value : randNums) {
                javaLinkedList.add(value);
                list.add(value);
            }

            Collections.shuffle(randNums);

            for (int i = 0; i < randNums.size(); i++) {
                Integer elem = randNums.get(i);
                Integer index1 = javaLinkedList.indexOf(elem);
                Integer index2 = list.indexOf(elem);

                assertEquals(index2, index1);
                assertEquals(list.size(), javaLinkedList.size());

                java.util.Iterator<Integer> iter1 = javaLinkedList.iterator();
                java.util.Iterator<Integer> iter2 = list.iterator();
                while (iter1.hasNext()) assertEquals(iter2.next(), iter1.next());
            }
        }
    }

    @Test
    public void testToString() {
        DoublyLinkedList<String> strs = new DoublyLinkedList<>();
        assertEquals("[  ]", strs.toString());
        strs.add("a");
        assertEquals("[ a ]", strs.toString());
        strs.add("b");
        assertEquals("[ a, b ]", strs.toString());
        strs.add("c");
        strs.add("d");
        strs.add("e");
        strs.add("f");
        assertEquals("[ a, b, c, d, e, f ]", strs.toString());
    }

    // Generate a list of random numbers
    static List<Integer> genRandList(int sz) {
        List<Integer> lst = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++) lst.add((int) (Math.random() * MAX_RAND_NUM));
        for (int i = 0; i < NUM_NULLS; i++) lst.add(null);
        Collections.shuffle(lst);
        return lst;
    }

    // Generate a list of unique random numbers
    static List<Integer> genUniqueRandList(int sz) {
        List<Integer> lst = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++) lst.add(i);
        for (int i = 0; i < NUM_NULLS; i++) lst.add(null);
        Collections.shuffle(lst);
        return lst;
    }
}
