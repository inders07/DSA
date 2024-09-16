package com.inders.dsa.datastructures.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueueTest {

    private ListQueue<Integer> queue = new ListQueue<>();

    @Test
    public void testEmptyQueue() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void testPollOnEmpty() {
        assertThrows(Exception.class, () -> queue.poll());
    }

    @Test
    public void testPeekOnEmpty() {
        assertThrows(Exception.class, () -> queue.peek());
    }

    @Test
    public void testOffer() {
        queue.offer(2);
        assertEquals(1, queue.size());
    }

    @Test
    public void testPeek() {
        queue.offer(2);
        assertEquals(2, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    public void testPoll() {
        queue.offer(2);
        assertEquals(2, queue.poll());
        assertEquals(0, queue.size());
    }

    @Test
    public void testExhaustively() {
        assertTrue(queue.isEmpty());
        queue.offer(1);
        assertFalse(queue.isEmpty());
        queue.offer(2);
        assertEquals(2, queue.size());
        assertEquals(1, queue.peek());
        assertEquals(2, queue.size());
        assertEquals(1, queue.poll());
        assertEquals(1, queue.size());
        assertEquals(2, queue.peek());
        assertEquals(1, queue.size());
        assertEquals(2, queue.poll());
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }
}
