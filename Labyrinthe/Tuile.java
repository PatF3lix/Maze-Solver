package Labyrinthe;

public class Tuile {
    private ArrayList<ArrayList<GraphNode<String>>> matrice;
    private boolean northOpenning = false;
    private boolean southOpenning = false;
    private boolean westOpenning = false;
    private boolean eastOpenning = false;
    private double difficulte = 0;
    private double nbMurs = 0;
    private double nbPath = 0;
    private double nbSorties = 0;
    String representationTile;

    public Tuile(int CombienParCombien, String representation) {
        this.matrice = new ArrayList<>(CombienParCombien);
        this.representationTile = representation;
        fillWithEmptyNodes();
        connectEachNode();
        fillNodes(representation);
        setDifficulte();
    }

    public Tuile(Tuile tile) {
        this.matrice = new ArrayList<>(TuileFactory.LARGEUR_TUILES);
        this.representationTile = tile.representationTile;
        fillWithEmptyNodes();
        connectEachNode();
        fillNodes(this.representationTile);
        setDifficulte();
    }

    // heuristique de difficulte d'une tuile
    public void setDifficulte() {
        this.difficulte = (this.nbMurs / this.nbPath) / nbSorties;
    }

    public double getDifficulte() {
        return this.difficulte;
    }

    public void fillWithEmptyNodes() {
        for (int i = 0; i < matrice.length(); i++) {
            ArrayList<GraphNode<String>> row = new ArrayList<>(matrice.length());
            for (int j = 0; j < row.length(); j++){
                row.add(new GraphNode<String>(""));
            }
            this.matrice.add(row);
        }
    }

    public void connectEachNode(){
        int longueurIteration = this.matrice.length() - 1;
        for (int rowIndex = 0; rowIndex <= longueurIteration; rowIndex++) {
            for (int colIndex = 0; colIndex <= longueurIteration; colIndex++) {
                // North neighbor
                if (rowIndex - 1 >= 0) {
                    matriceAt(rowIndex, colIndex).setNorth(matriceAt(rowIndex - 1, colIndex));
                }

                // South neighbor
                if (rowIndex + 1 <= longueurIteration) {
                    matriceAt(rowIndex, colIndex).setSouth(matriceAt(rowIndex + 1, colIndex));
                }

                // East neighbor
                if (colIndex + 1 <= longueurIteration) {
                    matriceAt(rowIndex, colIndex).setEast(matriceAt(rowIndex, colIndex + 1));
                }

                // West neighbor
                if (colIndex - 1 >= 0) {
                    matriceAt(rowIndex, colIndex).setWest(matriceAt(rowIndex, colIndex - 1));
                }
            }
        }
    }

    public void fillNodes(String representation) {
        String[] rows = representation.split("\n");
        for (int rowIndex = 0; rowIndex < this.matrice.length(); rowIndex++){
            char[] wallOrPath = rows[rowIndex].toCharArray();
            for (int colIndex = 0; colIndex < wallOrPath.length; colIndex++) {
                String wallPath = String.valueOf(wallOrPath[colIndex]);
                this.matrice.get(rowIndex).get(colIndex).setValue(wallPath);
                // add a check to see if openning when in right row + column, add it to right variabless
                // North openning
                boolean openning = (wallPath.equals(" "));
                if (openning && rowIndex == 0) {
                    this.northOpenning = true;
                    nbSorties += 1;
                }

                // South openning
                if (openning && rowIndex == this.matrice.length() - 1) {
                    this.southOpenning = true;
                    nbSorties += 1;
                }

                // West opennning
                if (openning && colIndex == 0) {
                    this.westOpenning = true;
                    nbSorties += 1;
                }

                // East opennning
                if (openning && colIndex == this.matrice.length() - 1) {
                    this.eastOpenning = true;
                    nbSorties += 1;
                }

                // nb of path and walls
                if (wallPath.equals(" ")){
                    nbPath += 1;
                }

                if (wallPath.equals("X")) {
                    nbMurs += 1;
                }
            }
        }
    }

    public GraphNode<String> matriceAt(int row, int col) {
        return this.matrice.get(row).get(col);
    }

    public ArrayList<ArrayList<GraphNode<String>>> getMatrice() {
        return matrice;
    }

    public void setMatrice(ArrayList<ArrayList<GraphNode<String>>> matrice) {
        this.matrice = matrice;
    }

    public String toString() {
        // StringBuilder str = new StringBuilder();
        // for (int rowIndex = 0; rowIndex < this.matrice.length(); rowIndex ++){
        //     for (int colIndex = 0; colIndex< this.matrice.length(); colIndex ++){
        //         str.append(matriceAt(rowIndex, colIndex).getValue());
        //     }
        //     if (rowIndex < this.matrice.length() - 1){
        //         str.append("\n");
        //     }
        // }

        // return str.toString();

        StringBuilder str = new StringBuilder();

        GraphNode<String> currentNode = this.matriceAt(0, 0);
        GraphNode<String> firstNodeNextRow = currentNode.getSouth();

        while (firstNodeNextRow != null) {
            firstNodeNextRow = currentNode.getSouth();
            while (currentNode != null) {
                str.append(currentNode.getValue());
                currentNode = currentNode.getEast();
            }
            str.append("\n");
            currentNode = firstNodeNextRow;
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }
    
    public boolean isNorthOpenning() {
        return northOpenning;
    }

    public void setNorthOpenning(boolean northOpenning) {
        this.northOpenning = northOpenning;
    }

    public boolean isSouthOpenning() {
        return southOpenning;
    }

    public void setSouthOpenning(boolean southOpenning) {
        this.southOpenning = southOpenning;
    }

    public boolean isWestOpenning() {
        return westOpenning;
    }

    public void setWestOpenning(boolean westOpenning) {
        this.westOpenning = westOpenning;
    }

    public boolean isEastOpenning() {
        return eastOpenning;
    }

    public void setEastOpenning(boolean eastOpenning) {
        this.eastOpenning = eastOpenning;
    }

    public String opennings(){
        StringBuilder str = new StringBuilder();
        str.append("North openning: " + this.northOpenning + "\n");
        str.append("South openning: " + this.southOpenning + "\n");
        str.append("West openning: " + this.westOpenning + "\n");
        str.append("East openning: " + this.eastOpenning);

        return str.toString();
    }

    public static void main(String[] args) {
        String representation = 
        "XXXX XXXX\nX       X\nX XXXXX X\nX   X   X\nX XXXXX X\nX   X   X\nXXXXXXX X\nX       X\nXXXX XXXX";
        Tuile exemple = new Tuile(9, representation);
        System.out.println(exemple);
        System.out.println(exemple.opennings());
        System.out.println(exemple.getDifficulte());
    }
}
