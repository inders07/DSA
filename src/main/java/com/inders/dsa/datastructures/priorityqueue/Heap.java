package com.inders.dsa.datastructures.priorityqueue;

public interface Heap<T extends Comparable<T>> {

    int size();

    boolean isEmpty();

    void clear();

    void add(T elem);

    T peek();

    T poll();

    boolean remove(T elem);

    boolean contains(T elem);
}
