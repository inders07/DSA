package com.inders.dsa.datastructures.dynamicarray;

import java.util.Arrays;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class DynamicArray<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 1 << 3;

    private T[] arr;
    private int len = 0;
    private int capacity = 0;

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    public DynamicArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
        this.capacity = capacity;
        this.arr = (T[]) new Object[capacity];
    }

    public DynamicArray(T[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        this.arr = Arrays.copyOf(arr, arr.length);
        capacity = len = arr.length;
    }

    public int size() {
        return len;
    }

    public boolean isEmpty() {
        return len == 0;
    }

    public T get(int index) {
        validateIndex(index);
        return arr[index];
    }

    public void set(int index, T elem) {
        validateIndex(index);
        arr[index] = elem;
    }

    public void add(T elem) {
        if (capacity <= len + 1) {
            if (capacity == 0) {
                capacity = 1;
            } else {
                capacity *= 2;
            }
            arr = Arrays.copyOf(arr, capacity); // pads extra capacity with 0/null
        }
        arr[len++] = elem;
    }

    public T removeAt(int index) {
        validateIndex(index);
        T elem = arr[index];
        System.arraycopy(arr, index + 1, arr, index, len - index - 1);
        --len;
        --capacity;
        return elem;
    }

    public boolean remove(T elem) {
        for (int i = 0; i < len; i++) {
            if (arr[i].equals(elem)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    public void reverse() {
        for (int i = 0; i < len / 2; i++) {
            T temp = arr[i];
            arr[i] = arr[len - 1 - i];
            arr[len - 1 - i] = temp;
        }
    }

    public void sort() {
        Arrays.sort(arr, 0, len);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            int index = 0;

            public boolean hasNext() {
                return index < len;
            }

            public T next() {
                return arr[index++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        if (len == 0) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder(len).append("[");
            for (int i = 0; i < len - 1; i++) {
                sb.append(arr[i]).append(", ");
            }
            return sb.append(arr[len - 1]).append("]").toString();
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException("Out of bound index : " + index);
        }
    }

    public static void main(String[] args) {

        DynamicArray<Integer> arr = new DynamicArray<>(50);
        arr.add(23);
        arr.add(57);
        arr.add(13);
        arr.add(24);
        arr.add(9);
        arr.add(-10);

        arr.sort();

        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }

        Integer value = arr.removeAt(2);
        System.out.println(value);

        arr.reverse();
        arr.remove(-10);
        System.out.println(arr);
    }
}
