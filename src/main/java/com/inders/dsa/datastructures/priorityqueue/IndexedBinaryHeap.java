package com.inders.dsa.datastructures.priorityqueue;

public class IndexedBinaryHeap<T extends Comparable<T>> extends IndexedDHeap<T> {
    public IndexedBinaryHeap(int maxNodes) {
        super(2, maxNodes);
    }
}
