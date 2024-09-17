package com.inders.dsa.datastructures.queue;

import java.util.Iterator;

public final class ArrayQueue<T> implements Iterable<T>, Queue<T> {

    private int front;
    private int rear;
    private Object[] data;

    public ArrayQueue(int capacity) {
        data = new Object[capacity + 1];
        front = rear = 0;
    }

    @Override
    public int size() {
        return adjustIndex(rear + data.length - front, data.length);
    }

    @Override
    public boolean isEmpty() {
        return front == rear;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        front = adjustIndex(front, data.length);
        return (T) data[front];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T poll() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        front = adjustIndex(front, data.length);
        return (T) data[front++];
    }

    @Override
    public void offer(T elem) {
        if (isFull()) {
            throw new RuntimeException("Queue is full");
        }
        data[rear++] = elem;
        rear = adjustIndex(rear, data.length);
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    public boolean isFull() {
        return (front + data.length - rear) % data.length == 1;
    }

    private int adjustIndex(int index, int size) {
        return index >= size ? index - size : index;
    }
}
