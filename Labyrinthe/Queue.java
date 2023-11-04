package Labyrinthe;

class IncorrectIndexException extends Exception {
    public IncorrectIndexException(String error) {
        super(error);
    }

    public IncorrectIndexException() {
        super();
    }
}

public class Queue<T> {
    Element<T> back;
    Element<T> front;
    int length = 0;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Element<T> getBack() {
        return back;
    }

    public void setBack(Element<T> back) {
        this.back = back;
    }

    public Element<T> getFront() {
        return front;
    }

    public void setFront(Element<T> front) {
        this.front = front;
    }

    public Queue() {
        back = null;
        front = null;
    }

    public Queue(T objet) {
        Element<T> element = new Element<T>(objet);
        front = element;
        back = element;
        length += 1;
    }

    public void add(T objet) {
        if (!empty()){
            Element<T> element = new Element<T>(objet);
            getBack().set_after(element);
            element.set_before(getBack());
            setBack(element);
            length += 1;
            return;
        }
        Element<T> element = new Element<T>(objet);
        setBack(element);
        setFront(element);
        length += 1;
    }

    public void add(int index, T objet) {
        try {
            if (!empty() && 0 <= index && index < length) {
                Element<T> toCheck = getFront();
                for (int i = 0; i < length; i++) {
                    if (i != index){
                        toCheck = toCheck.get_after();
                        continue;
                    }
                    Element<T> previous = toCheck.get_before();
                    Element<T> element = new Element<T>(objet);
                    previous.set_after(element);
                    toCheck.set_before(element);
                    toCheck = element;
                    length += 1;
                }
                return;
            }
            throw (new IncorrectIndexException("Entrer un index valide."));
        } catch (IncorrectIndexException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void clear() {
        length = 0;
        setBack(null);
        setFront(null);
    }

    public void remove() {
        if (!isOnly()) {
            getFront().get_after().set_before(null);
            setFront(getFront().get_after());
            length -= 1;
            return;
        }
        clear();
    }

    public void set(int index, T objet) {
        try {
            if (!empty() && 0 <= index && index < length) {
                Element<T> toCheck = getFront();
                for (int i = 0; i < length; i++) {
                    if (i != index){
                        toCheck = toCheck.get_after();
                        continue;
                    }
                    
                    toCheck.set_value(objet);
                    toCheck.set_after(toCheck.get_after());
                    toCheck.set_before(toCheck.get_before());
                    if (isBack(toCheck)){
                        setBack(toCheck);
                    }
                    if (isFront(toCheck)){
                        setFront(toCheck);
                    }
                    return;
                }
            }
            throw (new IncorrectIndexException("Entrer un index valide."));
        } catch (IncorrectIndexException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Element<T> get(int index) {
        try {
            if (!empty() && 0 <= index && index < length) {
                Element<T> toCheck = getFront();
                for (int i = 0; i < length; i++) {
                    if (i != index){
                        toCheck = toCheck.get_after();
                        continue;
                    }
                    
                    return toCheck;
                }
            }
            throw (new IncorrectIndexException("Entrer un index valide."));
        } catch (IncorrectIndexException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    private boolean empty() {
        return length == 0;
    }

    private boolean isOnly() {
        return length == 1;
    }

    private boolean isFront(Element<T> element) {
        return element == getFront();
    }

    private boolean isBack(Element<T> element) {
        return element == getBack();
    }

    public boolean isIn(T value) {
        Element<T> current = this.getFront();
        boolean found = false;
        while (current != null) {
            T toCheck = current.get_value();
            if (toCheck.equals(value)) {
                found = true;
                break;
            }
            current = current.get_after();
        }

        return found;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        Element<T> currentTask = this.front;
        for (int i = 0; i < this.length; i++) {
            if (i == this.length - 1) {
                str.append(currentTask.value);
                currentTask = currentTask.get_after();
                continue;
            }
            str.append(currentTask.value + ", ");
            currentTask = currentTask.get_after();
        }
        str.append("]");
        return str.toString();
    }

    public static void main(String[] args) {
        Queue<Integer> myQueue = new Queue<>();
        myQueue.add(45);
        myQueue.remove();
        System.out.println(myQueue.getFront());
        myQueue.add(30);
        myQueue.add(15);
        myQueue.set(0, 27);
        System.out.println(myQueue.get(0));
        System.out.println(myQueue);
        myQueue.remove();
        System.out.println(myQueue);
    }
}