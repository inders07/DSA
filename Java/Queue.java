package Java;

public class Queue <T> implements Iterable <T> {
    
    private java.util.LinkedList <T> list = new java.util.LinkedList <T> ();

    public Queue () {};

    // initiate queue with first element
    public Queue (T elem) {
        add(elem);
    }

    public int size () {
        return list.size();
    }

    public boolean isEmpty () {
        return size() == 0;
    }

    // add element to queue
    public void add (T elem) {
        list.addLast(elem);
    }

    // remove element from queue
    public T remove () {
        if (isEmpty()) throw new RuntimeException("Queue empty");
        return list.removeFirst();
    }

    // peek first element
    public T peek () {
        if (isEmpty()) throw new RuntimeException("Queue empty");
        return list.peekFirst();
    }

    // return iterator to traverse queue
    @Override public java.util.Iterator <T> iterator () {
        return list.iterator();
    }
}
