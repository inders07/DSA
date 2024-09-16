package com.inders.dsa.datastructures.queue;

public interface Queue<T> {

    // returns the number of elements in the queue
    int size();

    // returns if the queue is empty
    boolean isEmpty();

    // returns the first element in the queue
    T peek();

    // removes and returns first element from the queue
    T poll();

    // adds element to the end of the queue
    void offer(T elem);
}
