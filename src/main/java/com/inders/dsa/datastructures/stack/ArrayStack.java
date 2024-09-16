package com.inders.dsa.datastructures.stack;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;

public final class ArrayStack<T> implements Iterable<T>, Stack<T> {

    private int size;
    private int capacity;
    private Object[] data;

    public ArrayStack() {
        capacity = 16;
        data = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void push(T elem) {
        if (size == capacity) {
            increaseCapacity();
        }
        data[size++] = elem;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) data[--size];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) data[size - 1];
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = size;

            public boolean hasNext() {
                return index > 0;
            }

            public T next() {
                return (T) data[index--];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder(size).append("[");
            for (int i = size - 1; i > 0; i--) {
                sb.append(data[i]).append(", ");
            }
            return sb.append(data[0]).append("]").toString();
        }
    }

    private void increaseCapacity() {
        capacity *= 2;
        data = Arrays.copyOf(data, capacity);
    }
}
