package Labyrinthe;

import java.util.Iterator;

// J'ai utiliser les meme termes que la classe officiel mais pas le code.
// https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html

public class ArrayList<T> implements Iterable<T>{
    private T [] list;
    private int size = 0;
    private int lastIndex;

    /**
     * Returns the number of elements in this list.
     * @return int
     */
    public int size() {
        return size;
    }

    public int length(){
        return this.list.length;
    }

    /**
     * Return the underlying list of an arraylist
     * @return T[]
     */
    public T[] getList() {
        return list;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index
     * @return
     */
    public T get(int index) {
        return this.list[index];
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @param index
     * @param element
     */
    public void set(int index, T element) {
        try {
            if (this.list[index] == null) {
                this.size += 1;
            }

            this.list[index] = element;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayList () {
        this.list = (T[]) new Object [10];
    }

    
    /**
     * Constructs an empty list with the specified initial capacity.
     * @param initialCapaciy (int)
     */
    public ArrayList (int initialCapaciy) {
        try {
            this.list = (T[]) new Object [initialCapaciy];
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    /**
     * Constructs a list containing the elements of the specified collection, in the order they are returned by the collection's iterator.
     * @param list (ArrayList)
     * 
     * @throws NullPointerException - if the specified collection is null
     */
    public ArrayList (ArrayList<T> collection) {
        try {
            this.list = (T[]) new Object [collection.size];
            for (T object : collection.getList()) {
                if (object != null) {
                    this.add(object);
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    /**
     * Appends the specified element to the end of this list.
     * @param element
     */
    public void add(T element) {
        if (this.list.length == this.lastIndex){
            T[] temp = this.list;
            T[] newList = (T[]) new Object [this.list.length + 10];
            for (int i = 0; i < temp.length; i++) {
                newList[i] = temp [i];
            }
            newList[this.lastIndex] = element;
            this.size += 1;
            this.lastIndex += 1;
            this.list = newList;
            return;
        }
        this.list[lastIndex] = element;
        lastIndex += 1;
        this.size += 1;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * @param index
     * @param element
     */
    public void add(int index, T element) {
        if(this.list[index] == null) {
            this.size += 1;
        }

        this.list[index] = element;
        if (index > this.lastIndex){
            this.lastIndex = index + 1;
        }

        if (index == this.lastIndex) {
            this.lastIndex += 1;
        }
        
    }

    /**
     * Removes the element at the specified position in this list.
     * @param index
     */
    public void remove(int index){
        this.set(index, null);
        this.size -= 1;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     * @param element
     */
    public void remove(T element) {
        for (int i = 0; i < this.list.length; i++) {
            if(this.list[i] == element) {
                this.list[i] = null;
                this.size -= 1;
                break;
            }
        }
    }


    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        for (int i = 0; i < this.list.length; i++) {
            this.list[i] = null;
        }
        this.size = 0;
        this.lastIndex = 0;
    }

    public ArrayList<T> slice(int start, int end) {
        ArrayList<T> returnListe = new ArrayList<>(0);

        for (int i = start; i <= end; i++) {
            returnListe.add(this.get(i));
        }

        return new ArrayList<>(returnListe);
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        int counter = 0;
        for (T element : this.list) {
            if (counter == this.list.length - 1) {
                str.append(element);
                break;
            }
            str.append(element + ",");
            counter += 1;
        }
        str.append("]");
        return str.toString();
    }

    // Pour cette classe j'ai utiliser le code suivant
    /**
     * Source: https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
     * User: Tombart
     * Utilisation: rendre la classe iterable.
     */
    class thisIterator implements Iterator<T> {
        private int index = 0;

        public boolean hasNext() {
            return index < size();
        }

        public T next() {
            return get(index++);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new thisIterator();
    }

    public static void main(String[] args) {
        ArrayList<Integer> liste = new ArrayList<>();
        liste.add(15);
        liste.add(30);
        liste.add(45);
        System.out.println(liste);

        liste.remove(2);
        liste.remove(Integer.valueOf(15));


        ArrayList<Integer> newList = new ArrayList<>(liste);
        System.out.println(newList);

        newList.clear();
        newList.add(69);
        newList.add(70);
        newList.add(90);
        newList.remove(Integer.valueOf(70));

        newList.add(10, 50);
        newList.add(42);
        newList.add(56);

        newList.add(4, 78);
        newList.add(67);

        System.out.println(newList);
    }
}
