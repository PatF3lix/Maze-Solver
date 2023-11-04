package Labyrinthe;

public class Node<T> {
    private Node<T> previous = null;
    private Node<T> next = null;
    private T value = null;


    public Node<T> getPrevious() {
        return previous;
    }


    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }


    public Node<T> getNext() {
        return next;
    }


    public void setNext(Node<T> next) {
        this.next = next;
    }


    public T getValue() {
        return value;
    }


    public void setValue(T value) {
        this.value = value;
    }


    public Node(T value) {
        this.value = value;
    }
}
