package Labyrinthe;
public class Element<T> {
    Element<T> before;
    Element<T> after;
    T value;


    public Element(T value) {
        super();
        this.value = value;
        this.before = null;
        this.after = null;
    }

    public Element(T value, Element<T> before, Element<T> after) {
        super();
        this.value = value;
        this.before = before;
        this.after = after;
    }


    public Element<T> get_before() {
        return this.before;
    }

    public void set_before(Element<T> element) {
        this.before = element;
    }

    public Element<T> get_after() {
        return this.after;
    }

    public void set_after(Element<T> element) {
        this.after = element;
    }

    public T get_value() {
        return this.value;
    }

    public void set_value(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.get_value().toString();
    }

    public static void main(String[] args) {
        Element<Integer> my_element = new Element<Integer>(15);
        System.out.println(my_element.get_before());
        System.out.println(my_element.get_value());
    }
}
