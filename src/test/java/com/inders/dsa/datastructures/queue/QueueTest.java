package com.inders.dsa.datastructures.queue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueueTest {

    private static List<Queue<Integer>> inputs() {
        List<Queue<Integer>> queues = new ArrayList<>();
        queues.add(new ListQueue<>());
        queues.add(new ArrayQueue<>(16));
        return queues;
    }

    @ParameterizedTest
    @MethodSource("inputs")
    public void testEmptyQueue(Queue<Integer> queue) {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @ParameterizedTest
    @MethodSource("inputs")
    public void testPollOnEmpty(Queue<Integer> queue) {
        assertThrows(Exception.class, () -> queue.poll());
    }

    @ParameterizedTest
    @MethodSource("inputs")
    public void testPeekOnEmpty(Queue<Integer> queue) {
        assertThrows(Exception.class, () -> queue.peek());
    }

    @ParameterizedTest
    @MethodSource("inputs")
    public void testOffer(Queue<Integer> queue) {
        queue.offer(2);
        assertEquals(1, queue.size());
    }

    @ParameterizedTest
    @MethodSource("inputs")
    public void testPeek(Queue<Integer> queue) {
        queue.offer(2);
        assertEquals(2, queue.peek());
        assertEquals(1, queue.size());
    }

    @ParameterizedTest
    @MethodSource("inputs")
    public void testPoll(Queue<Integer> queue) {
        queue.offer(2);
        assertEquals(2, queue.poll());
        assertEquals(0, queue.size());
    }

    @ParameterizedTest
    @MethodSource("inputs")
    public void testExhaustively(Queue<Integer> queue) {
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
