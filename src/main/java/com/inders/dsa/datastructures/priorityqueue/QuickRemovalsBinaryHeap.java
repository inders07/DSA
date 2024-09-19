package com.inders.dsa.datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class QuickRemovalsBinaryHeap<T extends Comparable<T>> implements Heap<T> {

    // list to store elements in the heap
    private List<T> heap;

    // hash map to store indexes for each element. This will make removals
    // from O(n) to O(log(n)) and O(1) containment check directly on the map.
    // this will cost us some additional linear memory O(n)
    private final Map<T, TreeSet<Integer>> indexMap = new HashMap<>();

    public QuickRemovalsBinaryHeap() {
        this(1);
    }

    // initialize heap with a certain capacity
    public QuickRemovalsBinaryHeap(int capacity) {
        heap = new ArrayList<>(capacity);
    }

    // construct a heap using heapify in O(n) linear time
    public QuickRemovalsBinaryHeap(T[] elements) {
        int capacity = elements.length;
        heap = new ArrayList<>(capacity);

        // add all elements to the heap
        for (int i = 0; i < capacity; i++) {
            heap.add(elements[i]);
            indexMapAdd(elements[i], i);
        }

        // heapify process : http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
        for (int i = Math.max(0, (capacity / 2) - 1); i >= 0; i--) {
            sink(i);
        }
    }

    public QuickRemovalsBinaryHeap(Collection<T> elements) {
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
        indexMap.clear();
    }

    // returns null if the heap is empty
    // returns root node/most priority node from the heap
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return heap.getFirst();
    }

    // returns null if the heap is empty
    // removes and returns root node/most priority node from the heap
    @Override
    public T poll() {
        return removeAt(0);
    }

    // adds element to the heap
    @Override
    public void add(T elem) {
        if (elem == null) {
            throw new IllegalArgumentException();
        }
        heap.add(elem);// add elem at the last of the list
        int lastIndex = size() - 1;
        indexMapAdd(elem, lastIndex); // and new elem index mapping to index map
        swim(lastIndex); // bubble up/swim the last node to it's correct place
    }

    // Test if an element is in heap, O(1)
    @Override
    public boolean contains(T elem) {
        if (elem == null) {
            return false;
        }
        return mapGet(elem) != null;
    }

    // remove top most element from the heap
    @Override
    public boolean remove(T elem) {
        Integer index = mapGet(elem);
        if (index != null) {
            removeAt(index);
            return true;
        }
        return false;
    }

    // remove value from a certain index O(log(n))
    private T removeAt(int i) {
        if (isEmpty()) {
            return null;
        }

        // store the node to be removed
        T removedNode = heap.get(i);

        int lastIndex = size() - 1;
        // swap this node with the last node
        swap(i, lastIndex);

        // remove the last node from the heap
        heap.remove(lastIndex);
        indexMapRemove(removedNode, lastIndex);

        // if node removed was last then simply return the value
        // no need to sink/swim
        if (i == lastIndex) {
            return removedNode;
        }

        T elem = heap.get(i);
        sink(i); // try sinking element

        if (heap.get(i).equals(elem)) {
            // if sinking did not work, try swimming
            swim(i);
        }

        return removedNode;
    }

    private void sink(int i) {
        int size = size();
        while (true) {
            int left = (2 * i) + 1; // left child
            int right = (2 * i) + 2; // right child

            int smallest = left; // assume that smallest value is left

            if (right < size && less(right, smallest)) {
                smallest = right; // check and assign is smallest is right
            }

            if (left >= size || less(i, smallest)) {
                // break out of the loop if current node is smallest
                // or we are out of bound of the tree
                break;
            }

            swap(smallest, i); // swap the smallest node with current node
            i = smallest; // update current node to smallest to traverse down the tree
        }
    }

    private void swim(int i) {
        // index of the next parent node in the tree
        int parent = (i - 1) / 2;

        while (i > 0 && less(i, parent)) {
            // while we are within the bounds of the tree
            // and parent is greater than current node
            swap(parent, i); // swap current and parent
            i = parent; // update current to parent to move up the tree
            parent = (i - 1) / 2; // update the parent to grab next parent
        }
    }

    private void swap(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);

        heap.set(i, node2);
        heap.set(j, node1);

        indexMapSwap(node1, node2, i, j);
    }

    // Tests if the value of node i <= node j
    // This method assumes i & j are valid indices, O(1)
    private boolean less(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        return node1.compareTo(node2) <= 0;
    }

    private void indexMapAdd(T elem, Integer index) {
        TreeSet<Integer> set = indexMap.get(elem);

        // New value being inserted in map
        if (set == null) {
            set = new TreeSet<>();
            set.add(index);
            indexMap.put(elem, set);
        } else {
            // Value already exists in map
            set.add(index);
        }
    }

    private void indexMapSwap(T elem1, T elem2, int elem1Idx, int elem2Idx) {
        final Set<Integer> set1 = indexMap.get(elem1);
        final Set<Integer> set2 = indexMap.get(elem2);

        set1.remove(elem1Idx);
        set2.remove(elem2Idx);

        set1.add(elem2Idx);
        set2.add(elem1Idx);
    }

    // Removes the index at a given element, O(log(n))
    private void indexMapRemove(T elem, int index) {
        final TreeSet<Integer> set = indexMap.get(elem);
        set.remove(index); // TreeSets take O(log(n)) removal time
        if (set.isEmpty()) {
            // remove the index set if this was the only index for this element
            indexMap.remove(elem);
        }
    }

    // Extract an index position for the given element
    // NOTE: If a value exists multiple times in the heap the highest
    // index is returned (this has arbitrarily been chosen)
    private Integer mapGet(T elem) {
        final TreeSet<Integer> set = indexMap.get(elem);
        return set != null ? set.last() : null;
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
