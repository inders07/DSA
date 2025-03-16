package com.inders.dsa.datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class BinaryHeap<T extends Comparable<T>> implements Heap<T> {

    // list to store elements in the heap
    private final List<T> heap;

    public BinaryHeap() {
        this(1);
    }

    // initialize heap with a certain capacity
    public BinaryHeap(int capacity) {
        heap = new ArrayList<>(capacity);
    }

    // construct a heap using heapify in O(n) linear time
    public BinaryHeap(T[] elements) {
        int capacity = elements.length;
        heap = new ArrayList<>(capacity);

        // add all elements to the heap
        heap.addAll(Arrays.asList(elements).subList(0, capacity));

        // heapify process : http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
        for (int i = Math.max(0, (capacity / 2) - 1); i >= 0; i--) {
            sink(i);
        }
    }

    public BinaryHeap(Collection<T> elements) {
        int capacity = elements.size();
        heap = new ArrayList<>(capacity);

        // add all elements to the heap
        heap.addAll(elements);

        // heapify process : http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
        for (int i = Math.max(0, (capacity / 2) - 1); i >= 0; i--) {
            sink(i);
        }
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        heap.clear();
    }

    // returns root element of the heap or
    // with the lowest priority in this priority queue
    // null is returned if heap is empty
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return heap.getFirst();
    }

    // removes and returns root element of the heap
    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        return removeAt(0);
    }

    @Override
    public boolean contains(T elem) {
        return heap.contains(elem);
    }

    // add an element to the heap
    @Override
    public void add(T elem) {
        if (elem == null) {
            throw new IllegalArgumentException();
        }
        heap.add(elem);
        swim(size() - 1);
    }

    // remove first occurrence of an element from the heap
    @Override
    public boolean remove(T elem) {
        if (elem == null) {
            return false;
        }

        for (int i = 0; i < size(); i++) {
            if (elem.equals(heap.get(i))) {
                removeAt(i);
                return true;
            }
        }

        return false;
    }

    private T removeAt(int i) {
        if (isEmpty()) {
            return null;
        }

        int lastIndex = size() - 1;
        T removedElem = heap.get(i);

        swap(i, lastIndex); // swap elem at i with last elem
        heap.remove(lastIndex); // clear the last index

        if (i == lastIndex) {
            return removedElem;
        }

        T elem = heap.get(i);
        sink(i); // try sinking
        if (heap.get(i).equals(elem)) {
            swim(i); // swim if sinking did not work
        }

        return removedElem;
    }

    private void swim(int k) {
        int parent = (k - 1) / 2;

        while (k > 0 && less(k, parent)) {
            swap(k, parent);
            k = parent;
            parent = (k - 1) / 2;
        }
    }

    private void sink(int k) {
        int size = size();
        while (true) {
            int left = 2 * k + 1;
            int right = 2 * k + 2;
            int smallest = left; // assume left node is the smallest

            // check if right is smaller
            if (right < size && less(right, smallest)) {
                smallest = right;
            }

            // break out of loop if out of bound of heap or k is smallest
            if (left >= size || less(k, smallest)) {
                break;
            }

            // Move down the tree with the smallest node
            swap(k, smallest);
            k = smallest;
        }
    }

    // Tests if the value of node i <= node j
    // This method assumes i & j are valid indices, O(1)
    private boolean less(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        return node1.compareTo(node2) <= 0;
    }

    // Swap two nodes. Assumes i & j are valid, O(1)
    private void swap(int i, int j) {
        T elem_i = heap.get(i);
        T elem_j = heap.get(j);

        heap.set(i, elem_j);
        heap.set(j, elem_i);
    }

    // recursively check if the min heap invariant
    // is valid for this heap
    public boolean isMinHeap(int k) {
        // If we are outside the bounds of the heap return true
        int heapSize = size();
        if (k >= heapSize) return true;

        int left = 2 * k + 1;
        int right = 2 * k + 2;

        // Make sure that the current node k is less than
        // both of its children left, and right if they exist
        // return false otherwise to indicate an invalid heap
        if (left < heapSize && !less(k, left)) return false;
        if (right < heapSize && !less(k, right)) return false;

        // Recurse on both children to make sure they're also valid heaps
        return isMinHeap(left) && isMinHeap(right);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
