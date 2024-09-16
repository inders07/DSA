package com.inders.dsa.datastructures.queue;

import java.util.Iterator;
import java.util.LinkedList;

public class ListQueue<T> implements Iterable<T>, Queue<T> {

    private final LinkedList<T> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue Empty");
        }
        return list.peekFirst();
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            throw new RuntimeException("Queue Empty");
        }
        return list.pollFirst();
    }

    @Override
    public void offer(T elem) {
        list.addLast(elem);
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
