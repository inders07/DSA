package com.inders.dsa.datastructures.stack;

public interface Stack<T> {

    // returns number of elements in the stack
    int size();

    // returns if the stack is empty
    boolean isEmpty();

    // push the element to the stack
    void push(T elem);

    // pop the element from the stack
    T pop();

    // peek the element at the top of the stack
    T peek();
}
