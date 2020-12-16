package Java;

public class DoublyLinkedList <T> implements Iterable <T> {
    
    private int size = 0;
    private Node <T> head = null;
    private Node <T> tail = null;

    // interal Node class for list nodes
    private class Node <T> {
        public T data;
        public Node <T> prev, next;
        public Node(T data, Node <T> prev, Node <T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
        @Override public String toString() {
            return data.toString();
        }
    }

    // clear the list
    public void clear() {
        Node <T> trav = head;
        while (trav != null) {
            Node <T> next = trav.next;
            trav.data = null;
            trav.next = null;
            trav.prev = null;
            trav = next;
        }
        head = tail = trav = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // append element
    public void add(T elem) {
        addLast(elem);
    }

    // add element at head
    public void addFirst(T elem) {
        // empty list
        if (isEmpty()) {
            head = tail = new Node <T> (elem, null, null);
        } else {
            head.prev = new Node <T> (elem, null, head);
            head = head.prev;
        }
        ++size;
    }

    // add at tail
    public void addLast(T elem) {
        // empty list
        if (isEmpty()) {
            head = tail = new Node <T> (elem, null, null);
        } else {
            tail.next = new Node <T> (elem, tail, null);
            tail = tail.next;
        }
        ++size;
    }

    // return first element value if exists
    public T peekFirst() {
        // throw exception if empty list
        if (isEmpty()) throw new RuntimeException("Empty list");
        return head.data;
    }

    // return last element if exists
    public T peekLast() {
        // throw exception if empty list
        if (isEmpty()) throw new RuntimeException("Empty list");
        return tail.data;
    }

    // remove first node and return its value
    public T removeFirst() {
        // can't remove from empty list
        if (isEmpty()) throw new RuntimeException("Empty list");
        // store first element
        T data = head.data;
        head = head.next;
        --size;
        
        // empty list
        if (isEmpty()) tail = null;
        else head.prev = null;

        return data;
    }

    // remove the last node and returns it's value
    public T removeLast() {
        // can't remove from empty list
        if (isEmpty()) throw new RuntimeException("Empty list");
        // store last element
        T data = tail.data;
        tail = tail.prev;
        --size;

        // empty list
        if (isEmpty()) head = null;
        else tail.next = null;

        return data;
    }

    // internal method to remove a node
    private T remove(Node <T> node) {
        // if node is at head or tail
        if (node.prev == null) return removeFirst();
        if (node.next == null) return removeLast();

        // skip over the node
        node.next.prev = node.prev;
        node.prev.next = node.next;

        // store data
        T data = node.data;

        // memory cleanup
        node.data = null;
        node = node.prev = node.next = null;
        --size;

        return data;
    }

    // remove node at a particular index
    public T removeAt(int index) {
        // check validity of index
        if (index < 0 || index >= size) throw new IllegalArgumentException();

        int i;
        Node <T> trav;
        // search from the front
        if (index < size / 2) {
            for (i = 0, trav = head; i != index; i++) {
                trav = trav.next;
            }
        } else {
            for (i = size - 1, trav = tail; i != index; i--) {
                trav = trav.prev;
            }
        }

        return remove(trav);
    }

    // remove a node with a particular value
    public boolean remove(Object obj) {

        // if obj is null
        if (obj == null) {
            for (Node <T> trav = head; trav != null; trav = trav.next) {
                if (trav.data == null) {
                    remove(trav);
                    return true;
                }
            }
        } else {
            for (Node <T> trav = head; trav != null; trav = trav.next) {
                if (obj.equals(trav.data)) {
                    remove(trav);
                    return true;
                }
            }
        }

        return false;
    }

    // find index of a particular value in list
    public int indexOf(Object obj) {
        int index = 0;
        Node <T> trav;
        // support for null value
        if (obj == null) {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (trav.data == null) return index;
            }
        } else {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (obj.equals(trav.data)) return index;
            }
        }

        return -1;
    }

    // check if a value exists in list
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override public java.util.Iterator <T> iterator () {
        return new java.util.Iterator <T> () {
            private Node <T> trav = head;

            @Override public boolean hasNext() {
                return trav != null;
            }

            @Override public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    @Override public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node <T> trav = head;
        while (trav.next != null) {
            sb.append(trav.data + ", ");
        }
        sb.append(trav.data + " ]");
        return sb.toString();
    }

}
