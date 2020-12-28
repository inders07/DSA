package Java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BinaryHeap <T extends Comparable <T>> {
    private List <T> heap = null;

    public BinaryHeap () {
        this(1);
    }

    public BinaryHeap (int size) {
        heap = new ArrayList<>(size);
    }

    public BinaryHeap (T [] elems) {
        int heapSize = elems.length;
        heap = new ArrayList<>(heapSize);

        for (int i = 0; i < heapSize; i++) {
            heap.add(elems[i]);
        }

        for (int i = Math.max(0, heapSize / 2 - 1); i >= 0; i--) {
            sink(i);
        }
    }

    public BinaryHeap (Collection<T> elems) {
        int heapSize = elems.size();
        heap = new ArrayList<>(heapSize);

        heap.addAll(elems);

        for (int i = Math.max(0, heapSize / 2 - 1); i >= 0; i--) {
            sink(i);
        }
    }

    public boolean isEmpty () {
        return size() == 0;
    }

    public int size () {
        return heap.size();
    }

    public void clear () {
        heap.clear();
    }

    public T peek () {
        if (isEmpty()) return null;
        return heap.get(0);
    }

    public T poll () {
        return removeAt(0);
    }

    public boolean contains (T elem) {
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i).equals(elem)) return true;
        }
        return false;
    }

    public void add (T elem) {
        if (elem == null) throw new IllegalArgumentException();

        heap.add(elem);
        swim(heap.size() - 1);
    }

    private boolean less (int i , int j) {
        T obj1 = heap.get(i);
        T obj2 = heap.get(j);
        return obj1.compareTo(obj2) <= 0;
    }

    private void swim (int i) {
        int parent = (i - 1) / 2;

        while (i > 0 && less(i, parent)) {
            swap(i, parent);
            i = parent;
            parent = (i - 1) / 2;
        }
    }

    private void sink (int i) {
        int heapSize = size();
        while (true) {
            int left = i * 2 + 1;
            int right = i * 2 + 2;
            int smaller = left;
            if (right < heapSize && less(right, left)) smaller = right;

            if (left >= heapSize || less(i, left)) break;

            swap(smaller, i);
            i = smaller;
        }
    }

    private void swap (int i, int j) {
        T elem_i = heap.get(i);
        T elem_j = heap.get(j);

        heap.set(i, elem_j);
        heap.set(j, elem_i);
    }

    public boolean remove (T elem) {
        if (elem == null) return false;

        for (int i = 0; i < size(); i++) {
            if (heap.get(i).equals(elem)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    private T removeAt (int i) {
        if (isEmpty()) return null;

        int indexOfLastElement = size() - 1;
        T removed_data = heap.get(i);
        swap(i, indexOfLastElement);

        heap.remove(indexOfLastElement);

        if (i == indexOfLastElement) return removed_data;

        T elem = heap.get(i);

        sink(i);
        if (heap.get(i).equals(elem)) swim(i);
        return removed_data;
    }

    public boolean isMinHeap (int i) {
        int heapSize = size();
        if (i >= heapSize) return false;

        int left = i * 2 + 1;
        int right = i * 2 + 2;

        if (left < heapSize && !less(i, left)) return false;
        if (right < heapSize && !less(i, right)) return false;

        return isMinHeap(left) && isMinHeap(right);
    }

    @Override public String toString () {
        return heap.toString();
    }
}
