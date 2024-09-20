package com.inders.dsa.datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class IndexedDHeap<T extends Comparable<T>> {

    // number of nodes in the heap
    private int size;

    // max number of nodes that this heap can have
    private final int n;

    // degree of each node of heap(number of children)
    private final int d;

    // starting index of child for each parent node
    private final int[] child;

    // parent node index for each node
    private final int[] parent;

    // position map(pm) which stores node index in heap for each ki(key index)
    private final int[] pm;

    // inverse map(im) which is inverse of pm and stores ki(key index) for each node index
    private final int[] im;

    // contains value for each key index(ki)
    private final Object[] values;

    public IndexedDHeap(int degree, int maxNodes) {
        if (maxNodes <= 0) {
            throw new IllegalArgumentException();
        }
        d = Math.max(2, degree);
        n = Math.max(d + 1, maxNodes);
        size = 0;

        child = new int[n];
        parent = new int[n];
        pm = new int[n];
        im = new int[n];
        values = new Object[n];

        for (int i = 0; i < n; i++) {
            child[i] = i * d + 1;
            parent[i] = (i - 1) / d;
            pm[i] = im[i] = -1;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(int ki) {
        isKeyInboundOrThrow(ki);
        return pm[ki] != -1; // -1 means there is no node corresponding to this ki
    }

    public int peekKeyIndex() {
        isNotEmptyOrThrow();
        return im[0];
    }

    public int pollKeyIndex() {
        int minKey = peekKeyIndex();
        delete(minKey);
        return minKey;
    }

    public T peekValue() {
        int minKey = peekKeyIndex();
        return (T) values[minKey];
    }

    public T pollValue() {
        int minKey = peekKeyIndex();
        return delete(minKey);
    }

    public void insert(int ki, T value) {
        if (contains(ki)) {
            throw new IllegalArgumentException("Key index " + ki + " already exists!");
        }
        valueNotNullOrThrow(value);
        pm[ki] = size;
        im[size] = ki;
        values[ki] = value;
        swim(size++);
    }

    public T valueOf(int ki) {
        keyExistsOrThrow(ki);
        return (T) values[ki];
    }

    public T update(int ki, T newValue) {
        keyExistsAndValueNotNullOrThrow(ki, newValue);

        final int i = pm[ki];
        final T oldValue = (T) values[ki];
        values[ki] = newValue;
        sink(i);
        swim(i);
        return oldValue;
    }

    public T delete(int ki) {
        keyExistsOrThrow(ki);
        final int i = pm[ki]; // node index for this key index
        swap(i, --size); // swap this node with the last node with size decrement
        sink(i);
        swim(i);
        T removedNode = (T) values[ki];
        values[ki] = null;
        pm[ki] = -1;
        im[size] = -1;
        return removedNode;
    }

    // Strictly decreases the value associated with 'ki' to 'value'
    public void decrease(int ki, T value) {
        keyExistsAndValueNotNullOrThrow(ki, value);
        if (less(value, values[ki])) {
            values[ki] = value;
            swim(pm[ki]);
        }
    }

    // Strictly increases the value associated with 'ki' to 'value'
    public void increase(int ki, T value) {
        keyExistsAndValueNotNullOrThrow(ki, value);
        if (less(values[ki], value)) {
            values[ki] = value;
            sink(pm[ki]);
        }
    }

    private void swim(int i) {
        while (less(i, parent[i])) {
            swap(parent[i], i);
            i = parent[i];
        }
    }

    // sink/bubble down node with index i to place at correct position
    private void sink(int i) {
        for (int j = minChild(i); j != -1;) {
            swap(j, i);
            i = j;
            j = minChild(i);
        }
    }

    // get the smallest child index for parent node i
    private int minChild(int i) {
        int index = -1;
        int from = child[i];
        int to = Math.min(size, from + d);

        for (int j = from; j < to; j++) {
            if (less(j, i)) {
                index = i = j;
            }
        }

        return index;
    }

    // returns true if the value of node i < node j
    private boolean less(int i, int j) {
        return ((Comparable<? super T>) values[im[i]]).compareTo((T) values[im[j]]) < 0;
    }

    // returns true if obj1 is less than obj2
    private boolean less(Object obj1, Object obj2) {
        return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
    }

    @Override
    public String toString() {
        List<Integer> lst = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            lst.add(im[i]);
        }
        return lst.toString();
    }

    // swap two nodes with index i and j
    private void swap(int i, int j) {
        pm[im[i]] = j;
        pm[im[j]] = i;

        int temp = im[i];
        im[i] = im[j];
        im[j] = temp;
    }

    private void keyExistsAndValueNotNullOrThrow(int ki, Object value) {
        keyExistsOrThrow(ki);
        valueNotNullOrThrow(value);
    }

    private void valueNotNullOrThrow(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
    }

    private void keyExistsOrThrow(int ki) {
        if (!contains(ki)) {
            throw new NoSuchElementException("Key index does not exist : " + ki);
        }
    }

    private void isNotEmptyOrThrow() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty!!");
        }
    }

    private void isKeyInboundOrThrow(int ki) {
        if (0 > ki || ki >= n) {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isMinHeap() {
        return isMinHeap(0);
    }

    private boolean isMinHeap(int i) {
        int from = child[i], to = Math.min(size, from + d);
        for (int j = from; j < to; j++) {
            if (!less(i, j)) return false;
            if (!isMinHeap(j)) return false;
        }
        return true;
    }
}
