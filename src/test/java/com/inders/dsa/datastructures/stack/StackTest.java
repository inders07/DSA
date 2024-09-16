package com.inders.dsa.datastructures.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StackTest {

    ListStack<Integer> stack = new ListStack<>();

    @Test
    public void testEmptyStack() {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    public void testPopOnEmpty() {
        assertThrows(Exception.class, () -> stack.pop());
    }

    @Test
    public void testPeekOnEmpty() {
        assertThrows(Exception.class, () -> stack.peek());
    }

    @Test
    public void testPush() {
        stack.push(2);
        assertEquals(1, stack.size());
    }

    @Test
    public void testPeek() {
        stack.push(2);
        assertEquals(2, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testPop() {
        stack.push(2);
        assertEquals(2, stack.pop());
        assertEquals(0, stack.size());
    }

    @Test
    public void testExhaustively() {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
        stack.push(2);
        assertEquals(2, stack.size());
        assertEquals(2, stack.peek());
        assertEquals(2, stack.size());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.size());
        assertEquals(1, stack.peek());
        assertEquals(1, stack.size());
        assertEquals(1, stack.pop());
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }
}
