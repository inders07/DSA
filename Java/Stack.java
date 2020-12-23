package Java;

public class Stack <T> implements Iterable <T> {

    private java.util.LinkedList<T> list = new java.util.LinkedList<T>();

    // create an empty stack
    public Stack () { }

    // create a stack with one item
    public Stack (T item) {
        push(item);
    }

    public int size () {
        return list.size();
    }

    public boolean isEmpty () {
        return size() == 0;
    }

    // push item onto stack
    public void push (T item) {
        list.addLast(item);
    }

    // pop item from stack
    // throw exception if stack empty
    public T pop () {
        if (isEmpty()) throw new java.util.EmptyStackException();
        return list.removeLast();
    }

    // peek top element
    // throw exceptions if empty stack
    public T peek () {
        if (isEmpty()) throw new java.util.EmptyStackException();
        return list.peekLast();
    }

    // allows user to iterate through stack using iterator
    @Override public java.util.Iterator <T> iterator () {
        return list.iterator();
    }
}