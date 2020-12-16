package Java;

@SuppressWarnings("unchecked")
public class DynamicArray<T> implements Iterable<T> {
    
    private T[] arr;
    private int len;        // current length of array
    private int capacity;        // max capacity of array

    public DynamicArray() {
        this(16);
    }

    public DynamicArray(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal capacity : " + capacity);
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    public int size() {
        return len;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index) {
        if (index < 0 || index >= len) throw new IndexOutOfBoundsException();
        return arr[index];
    }

    public void set(int index, T val) {
        if (index < 0 || index >= len) throw new IndexOutOfBoundsException();
        arr[index] = val;
    }

    public void clear() {
        for (int i = 0; i < len; ++i) {
            arr[i] = null;
        }
        len = 0;
    }

    public void add(T val) {
        // resize if full
        if (len + 1 >= capacity) {
            if (capacity == 0) capacity = 1;
            else capacity *= 2;                 // double the size
            T[] new_arr = (T[]) new Object[capacity];   // create new arr
            for (int i = 0; i < len; i++) {
                new_arr[i] = arr[i];
            }
            arr = new_arr;
        }
        arr[len++] = val;
    }

    // removes and returns element at specified index
    public T removeAt(int index) {
        if (index < 0 || index >= len) throw new IndexOutOfBoundsException();
        T data = arr[index];
        T[] new_arr = (T[]) new Object[len - 1];
        for (int i = 0, j = 0; i < len; i++, j++) {
            if (i == index) j--;                        //skip over index to be removed
            else new_arr[j] = arr[i];                   // copy elements to new array
        }
        arr = new_arr;
        capacity = --len;
        return data;
    }

    public int indexOf(T obj) {
        for (int i = 0; i < len; i++) {
            if (obj == arr[i]) return i;
        }

        return -1;
    }

    public boolean remove(T obj) {
        int index = indexOf(obj);
        if (index == -1) return false;
        removeAt(index);
        return true;
    }

    public boolean contains(T obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < len;
            }

            @Override
            public T next() {
                return arr[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        if (len == 0) return "[]";
        else {
            StringBuilder sb = new StringBuilder(len).append("[");
            for (int i = 0; i < len - 1; i++) {
                sb.append(arr[i] + ", ");
            }
            return sb.append(arr[len - 1] + "]").toString();
        }
    }
}