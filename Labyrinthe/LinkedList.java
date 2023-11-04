package Labyrinthe;

public class LinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    public int size;

    public LinkedList() {
        this.first = null;
        this.last = null;
    }

    public LinkedList(LinkedList<T> collection) {
        Node<T> current = collection.begin();
        for (int i = 0; i < collection.size; i++) {
            this.add(current.getValue());
            current = current.getNext();
        }
    }

    public T get(int index) {
        Node<T> current = this.first;
        for (int i=0; i < index; i++) {
            current = current.getNext();
        }
        return current.getValue();
    }

    public void add(T element) {
        Node<T> elementN = new Node<>(element);
        elementN.setNext(null);
        elementN.setPrevious(this.last);

        if (this.first != null) {
            elementN.getPrevious().setNext(elementN);
        }
        
        this.last = elementN;

        if (this.first == null) {
            this.first = elementN;
        }

        size += 1;
    }

    public LinkedList<T> extend(LinkedList<T> collection) {
        LinkedList<T> extended = new LinkedList<>(this);
        Node<T> current = collection.begin();
        for (int i = 0; i < collection.size; i++) {
            extended.add(current.getValue());
            current = current.getNext();
        }
        return extended;
    }

    public LinkedList<T> slice(int nbElements) {
        LinkedList<T> retour = new LinkedList<>();
        Node<T> current_node = this.first;
        for (int i = 0 ; i < nbElements; i++) {
            retour.add(current_node.getValue());
            current_node = current_node.getNext();
        }

        return retour;
    }

    public Node<T> begin() {
        return this.first;
    }

    public void setFirst(Node<T> first) {
        Node<T> temp = null;
        if (this.first != null) {
            temp = this.first.getNext();
        }
        this.first = first;
        if (temp != null) {
            temp.setPrevious(this.first);
        }
        this.first.setNext(temp);        
    }

    public Node<T> end() {
        return this.last;
    }

    public void setLast(Node<T> last) {
        Node<T> temp = null;
        if (this.last != null) {
            temp = this.last.getPrevious();
        }
        this.last = last;
        if (temp != null) {
            temp.setNext(this.last);
        }
        this.last.setPrevious(temp);
    }

    public void removeFirst() {
        if (this.size > 0) {
            if (this.size == 1) {
                this.first = null;
                this.last = null;
                this.size -= 1;
                return;
            }
            this.first = this.first.getNext();
            if (this.first != null) {
                this.first.setPrevious(null);
            }
            this.size -= 1;
            return;
        }
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public String toString() {
        Node<T> current = this.first;
        StringBuilder str = new StringBuilder();
        str.append("[");
        while (current != null){
            str.append(current.getValue() + ",");
            current = current.getNext();
        }
        str.deleteCharAt(str.length() - 1);
        str.append("]");

        return str.toString();
    }

    public static void main(String[] args) {
        LinkedList<Integer> test = new LinkedList<>();
        test.add(10);
        test.add(20);
        test.add(45);
        System.out.println(test);

        LinkedList<Integer> test2 = new LinkedList<>();
        test2.add(15);
        test2.add(25);
        test2.add(54);
        System.out.println(test2);

        LinkedList<Integer> extend1 = test.extend(test2);
        LinkedList<Integer> extend2 = extend1.extend(test);
        LinkedList<Integer> extend3 = extend2.extend(test2);
        System.out.println(extend1);
        System.out.println(extend2);
        System.out.println(extend3);
    }
}
