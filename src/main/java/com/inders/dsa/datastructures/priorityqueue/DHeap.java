package com.inders.dsa.datastructures.priorityqueue;

@SuppressWarnings("unchecked")
public class DHeap<T extends Comparable<T>> implements Heap<T> {

    private final T[] heap; // array to store heap elements
    private final int d; // degree of the DHeap
    private int size; // number of elements in the heap
    private final int[] child; // starting index of children of each node
    private final int[] parent; // parent index of each node

    public DHeap(int degree, int maxNodes) {
        d = Math.max(2, degree);
        // capacity of the static array used for heap
        int n = Math.max(degree + 1, maxNodes);

        heap = (T[]) new Comparable[n];
        child = new int[n];
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = (i - 1) / d;
            child[i] = i * d + 1;
        }
    }

    // Returns the number of elements currently present inside the heap
    @Override
    public int size() {
        return size;
    }

    // Returns true/false depending on whether the heap is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Clears all the elements from the heap
    @Override
    public void clear() {
        java.util.Arrays.fill(heap, null);
        size = 0;
    }

    @Override
    public void add(T elem) {
        if (elem == null) {
            throw new IllegalArgumentException();
        }
        heap[size] = elem;
        swim(size++);
    }

    // returns root element of the heap or
    // with the smallest priority in this priority queue
    // null is returned if heap is empty
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return heap[0];
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T root = heap[0];
        heap[0] = heap[--size];
        heap[size] = null;
        sink(0);
        return root;
    }

    @Override
    public boolean remove(T elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(T elem) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(elem)) {
                return true;
            }
        }
        return false;
    }

    // sink/bubble down the node at i to it's correct position
    private void sink(int i) {
        for (int j = minChild(i); j != -1;) {
            // loop will exit when current node is smaller than all its child
            // this happens when minChild returns -1
            swap(i, j);
            i = j;
            j = minChild(i);
        }
    }

    // swim/bubble up the node at i to it's correct position
    private void swim(int i) {
        while (less(i, parent[i])) {
            // the loop will exit either when parent is smaller
            // than current node or current node is root where
            // i and parent[i] are both zero and less will return false
            swap(i, parent[i]);
            i = parent[i];
        }
    }

    // swap elements at index i and j
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // for the parent node at i return the index of smallest child
    private int minChild(int i) {
        int index = -1; // assume that parent is least
        int from = child[i]; // first child of parent node at i
        int to = Math.min(size, from + d); // last child of parent node at i

        for (int j = from; j < to; j++) {
            if (less(j, i)) {
                index = i = j;
            }
        }

        return index;
    }

    // returns true if elem at index i is less than elem at index j
    private boolean less(int i, int j) {
        return heap[i].compareTo(heap[j]) < 0;
    }
}
