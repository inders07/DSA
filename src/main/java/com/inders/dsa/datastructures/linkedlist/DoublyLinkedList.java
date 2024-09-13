package com.inders.dsa.datastructures.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        Node<T> trav = head;
        while (trav != null) {
            Node<T> next = trav.next;
            trav.prev = trav.next = null;
            trav.data = null;
            trav = next;
        }
        head = tail = null;
        size = 0;
    }

    public void addFirst(T elem) {
        if (isEmpty()) {
            head = tail = new Node<>(elem, null, null);
        } else {
            head.prev = new Node<>(elem, null, head);
            head = head.prev;
        }
        size++;
    }

    public void addLast(T elem) {
        if (isEmpty()) {
            head = tail = new Node<>(elem, null, null);
        } else {
            tail.next = new Node<>(elem, tail, null);
            tail = tail.next;
        }
        size++;
    }

    public void add(T elem) {
        addLast(elem);
    }

    public void addAt(int index, T elem) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Out of bound index : " + index);
        }

        if (index == 0) {
            addFirst(elem);
            return;
        }

        if (index == size()) {
            addLast(elem);
            return;
        }

        Node<T> temp = head;
        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }

        Node<T> newNode = new Node<>(elem, temp.prev, temp);
        temp.next.prev = newNode;
        temp.next = newNode;

        size++;
    }

    public T peekFirst() {
        return isEmpty() ? null : head.data;
    }

    public T peekLast() {
        return isEmpty() ? null : tail.data;
    }

    public T peek() {
        return peekFirst();
    }

    public T pollFirst() {
        if (isEmpty()) {
            return null;
        }
        T data = head.data;
        head = head.next;
        size--;

        if (isEmpty()) {
            tail = null;
        } else {
            head.prev = null;
        }

        return data;
    }

    public T pollLast() {
        if (isEmpty()) {
            return null;
        }
        T data = tail.data;
        tail = tail.prev;
        size--;

        if (isEmpty()) {
            head = null;
        } else {
            tail.next = null;
        }

        return data;
    }

    public T poll() {
        return pollFirst();
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return pollFirst();
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return pollLast();
    }

    public T remove() {
        return removeFirst();
    }

    public T removeAt(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Out of bound index : " + index);
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }

        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }

        return remove(temp);
    }

    public boolean remove(Object obj) {
        Node<T> temp = head;

        if (obj == null) {
            for (; temp != null; temp = temp.next) {
                if (temp.data == null) {
                    remove(temp);
                    return true;
                }
            }
        } else {
            for (; temp != null; temp = temp.next) {
                if (obj.equals(temp.data)) {
                    remove(temp);
                    return true;
                }
            }
        }

        return false;
    }

    public int indexOf(Object obj) {
        int index = 0;
        Node<T> temp = head;

        if (obj == null) {
            for (; temp != null; temp = temp.next, index++) {
                if (temp.data == null) {
                    return index;
                }
            }
        } else {
            for (; temp != null; temp = temp.next, index++) {
                if (obj.equals(temp.data)) {
                   return index;
                }
            }
        }

        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node<T> trav = head;
        while (trav != null) {
            sb.append(trav.data);
            if (trav.next != null) {
                sb.append(", ");
            }
            trav = trav.next;
        }
        sb.append(" ]");
        return sb.toString();
    }

    private T remove(Node<T> node) {
        if (node.prev == null) {
            return removeFirst();
        }
        if (node.next == null) {
            return removeLast();
        }

        node.next.prev = node.prev;
        node.prev.next = node.next;

        T data = node.data;

        node.data = null;
        node = node.next = node.prev = null;

        --size;

        return data;
    }
}
