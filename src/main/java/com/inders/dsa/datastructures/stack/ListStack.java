package com.inders.dsa.datastructures.stack;


import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;

public final class ListStack<T> implements Iterable<T>, Stack<T> {

    private LinkedList<T> list = new LinkedList<>();

    public ListStack() {}

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void push(T elem) {
        list.addLast(elem);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.removeLast();
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.peekLast();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
