package Labyrinthe;

public class DifficultySorter {
    public static LinkedList<MyPair<Labyrinth>> sort(LinkedList<MyPair<Labyrinth>> data) {
        // 2. Condition de sortie
        if (data.size <= 1) {
            return data;
        }

        // 3. Manipulations
        Node<MyPair<Labyrinth>> pivot = data.end();

        LinkedList<MyPair<Labyrinth>> petits = new LinkedList<>();
        LinkedList<MyPair<Labyrinth>> grands = new LinkedList<>();

        grands.add(pivot.getValue());
        Node<MyPair<Labyrinth>> current = data.begin();
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
        LinkedList<MyPair<Labyrinth>> gauche = sort(petits);
        LinkedList<MyPair<Labyrinth>> droite = sort(grands);

        return gauche.extend(droite);
    }
}
