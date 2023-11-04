package Labyrinthe;
import java.util.Random;

public class PriorityQueue<T> {
    LinkedList<MyPair<T>> queue;

    public PriorityQueue() {
        this.queue = new LinkedList<>();
    }

    public PriorityQueue(LinkedList<MyPair<T>> queue) {
        this.queue = queue;
    }

    public boolean isEmpty() {
        return this.queue.size <= 0;
    }

    public T pop() {
        T temp = this.queue.begin().getValue().getValue();
        this.queue.removeFirst();
        return temp;
    }

    public boolean isIn(T value) {
        Node<MyPair<T>> current = this.queue.begin();
        boolean found = false;
        while (current != null) {
            T toCheck = current.getValue().getValue();
            if (toCheck.equals(value)) {
                found = true;
                break;
            }
            current = current.getNext();
        }

        return found;
    }

    public void remove(T value) {
        Node<MyPair<T>> current = this.queue.begin();
        // ma condition de sortie de la boucle netais jamais rencontree
        while (current != null) {
            if (current.getValue().getValue().equals(value)) {
                if (current.getNext() != null) {
                    current.getNext().setPrevious(current.getPrevious());
                }
                if (current.getPrevious() != null) {
                    current.getPrevious().setNext(current.getNext());
                }
                this.queue.size -= 1;
                break;
            }
            current = current.getNext();
        }
    }

    public void add(MyPair<T> pair) {
        this.queue.add(pair);
    }

    public void sortIt() {
        this.queue = sort(this.queue);
    }

    public LinkedList<MyPair<T>> sort(LinkedList<MyPair<T>> data) {
        // 2. Condition de sortie
        if (data.size <= 1) {
            return data;
        }

        // 3. Manipulations
        Node<MyPair<T>> pivot = data.end();

        LinkedList<MyPair<T>> petits = new LinkedList<>();
        LinkedList<MyPair<T>> grands = new LinkedList<>();

        grands.add(pivot.getValue());
        Node<MyPair<T>> current = data.begin();
        // condition por ne pas ajouter le pivot, il a deja ete placer.
        while (current.getNext() != null) {
            if (current.getValue().getKey() <= pivot.getValue().getKey()) {
                petits.add(current.getValue());
            } else {
                grands.add(current.getValue());
            }
            
            current = current.getNext();
        }

        // 4. Appels de recursions
        LinkedList<MyPair<T>> gauche = sort(petits);
        LinkedList<MyPair<T>> droite = sort(grands);

        return gauche.extend(droite);
    }

    public void test(){
        long startTime = System.currentTimeMillis();

        sortIt();

        long endTime = System.currentTimeMillis();

        System.out.println("Sorting executer en " + (endTime - startTime) + "ms");
    }

    public static void main(String[] args) {
        // 30000 super rapide, 300000 impossible.... Pourquoi?
        int nb_elements = 10;
        PriorityQueue<String> queue = new PriorityQueue<>();
        for (int i = 0; i < nb_elements; i++) {
            queue.add(new MyPair<String>(new Random().nextInt(500), "TEST"));
        }

        System.out.println(queue.queue);
        queue.test();
        System.out.println(queue.queue);
    }
}
