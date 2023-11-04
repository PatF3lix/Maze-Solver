package Labyrinthe;

public class MyPair<T> {
    private double key;
    private T value;

    public double getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public MyPair(double key, T value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("(" + key + ", " + value + ")");
        return str.toString();
    }
}
