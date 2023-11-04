package Labyrinthe;

import java.awt.Color;
import java.util.Random;

public class Labyrinth {
    ArrayList<ArrayList<Tuile>> labyrinthe;
    public ArrayList<ArrayList<Tuile>> getLabyrinthe() {
        return labyrinthe;
    }

    int longLargeur = 9;
    private double difficulte = 0;
    GraphNode<String> topLeftNode;
    GraphNode<String> start;
    GraphNode<String> exit;

    public Labyrinth() {
        this.labyrinthe = new ArrayList<>(longLargeur);
        initialise();
        populate();
        connect();
        setVoisinage();
        startExit();
        this.topLeftNode = labyrintheAt(0, 0).matriceAt(0, 0);
        setDifficulte();
    }

    public void setDifficulte() {
        for (ArrayList<Tuile> row : this.labyrinthe) {
            for (Tuile tile : row) {
                this.difficulte += tile.getDifficulte();
            }
        }
        int nbTuiles = this.longLargeur * this.longLargeur;
        this.difficulte /= nbTuiles;
    }

    public double getDifficulte() {
        return this.difficulte;
    }

    public void initialise(){
        for (int rowIndex = 0; rowIndex < this.labyrinthe.length(); rowIndex ++) {
            this.labyrinthe.add(new ArrayList<Tuile>(longLargeur));
        }
    }

    public void populate() {
        ArrayList<Tuile> allTiles = TuileFactory.allTiles();
        Random rd = new Random();

        for (int rowIndex = 0; rowIndex < this.labyrinthe.length(); rowIndex ++) {
            for (int colIndex = 0; colIndex < this.labyrinthe.length(); colIndex ++) {
                // Le probleme etait que je prenait une reference a la meme tuile.
                // Pour eviter ce type de problemes il faut s'assurer que l'instance ajouter soit une instance unique
                // Ici la solution etait de faire un constructeur qui prenait une tuile pour avoir une nouvelle tuile unique
                Tuile addedTile = new Tuile(allTiles.get(rd.nextInt(allTiles.length())));
                setLabyrintheAt(rowIndex, colIndex, addedTile);
            }
        }
    }

    public void connect() {
        int longueurIteration = this.labyrinthe.length() - 1;
        for (int rowIndex = 0; rowIndex <= longueurIteration; rowIndex++) {
            for (int colIndex = 0; colIndex <= longueurIteration; colIndex++) {
                Tuile currentTile = labyrintheAt(rowIndex, colIndex);
                // North connection
                if (rowIndex - 1 >= 0) {
                    Tuile northTile = labyrintheAt(rowIndex - 1, colIndex);
                    merge(currentTile, northTile, "NORTH");
                }

                // South connection
                if (rowIndex + 1 <= longueurIteration) {
                    Tuile southTile = labyrintheAt(rowIndex + 1, colIndex);
                    merge(currentTile, southTile, "SOUTH");
                }

                // East connection
                if (colIndex + 1 <= longueurIteration) {
                    Tuile eastTile = labyrintheAt(rowIndex, colIndex + 1);
                    merge(currentTile, eastTile, "EAST");
                }

                // West connection
                if (colIndex - 1 >= 0) {
                    Tuile westTile = labyrintheAt(rowIndex, colIndex - 1);
                    merge(currentTile, westTile, "WEST");
                }
            }
        }
    }

    public void merge(Tuile tile1, Tuile tile2, String direction) {
        int tile1Row = 0;
        int tile1Col = 0;

        int tile2Row = 0;
        int tile2Col = 0;

        switch (direction) {
            case "NORTH":
                tile1Row = 0;
                tile2Row = tile2.getMatrice().length() - 1;

                for (int colIndex = 0; colIndex < tile1.getMatrice().length(); colIndex ++) {
                    tile1.matriceAt(tile1Row, colIndex).setNorth(tile2.matriceAt(tile2Row, colIndex));
                    tile2.matriceAt(tile2Row, colIndex).setSouth(tile1.matriceAt(tile1Row, colIndex));
                }
                break;

            case "SOUTH":
                tile1Row = tile1.getMatrice().length() - 1;
                tile2Row = 0;

                for (int colIndex = 0; colIndex < tile1.getMatrice().length(); colIndex ++) {
                    tile1.matriceAt(tile1Row, colIndex).setSouth(tile2.matriceAt(tile2Row, colIndex));
                    tile2.matriceAt(tile2Row, colIndex).setNorth(tile1.matriceAt(tile1Row, colIndex));
                }
                break;

            case "WEST":
                tile1Col = 0;
                tile2Col = tile2.getMatrice().length() - 1;

                for (int rowIndex = 0; rowIndex < tile1.getMatrice().length(); rowIndex ++) {
                    tile1.matriceAt(rowIndex, tile1Col).setWest(tile2.matriceAt(rowIndex, tile2Col));
                    tile2.matriceAt(rowIndex, tile2Col).setEast(tile1.matriceAt(rowIndex, tile1Col));
                }
                break;

            case "EAST":
                tile1Col = tile1.getMatrice().length() - 1;
                tile2Col = 0;

                for (int rowIndex = 0; rowIndex < tile1.getMatrice().length(); rowIndex ++) {
                    tile1.matriceAt(rowIndex, tile1Col).setEast(tile2.matriceAt(rowIndex, tile2Col));
                    tile2.matriceAt(rowIndex, tile2Col).setWest(tile1.matriceAt(rowIndex, tile1Col));
                }
                break;
    
            default:
                break;
        }
    }

    public void startExit() {
        // choisir la sortie et la fin
        // dans chaque tuile si il y a une sortie dans une direction ce node est le node a lindex (longueurCote // 2) de la colonne ou de la rangee
        // chiffre entre 0 et 3
        int cardinal = new Random().nextInt(3);
        this.setStart(setStartExit(cardinal));
        cardinal = new Random().nextInt(3);
        GraphNode<String> choixExit = null;
        while (choixExit == null) {
            cardinal = new Random().nextInt(3);
            choixExit = setStartExit(cardinal);
            if (choixExit.getSpecial() == GraphNode.START) {
                choixExit = null;
            }
        }
        this.setExit(choixExit);
        this.getStart().setSpecial(GraphNode.START);
        this.getExit().setSpecial(GraphNode.EXIT);

        // For testing purposes i manually set an entry and an exit.
        // this.setStart(this.labyrintheAt(5, 0).matriceAt(longLargeur / 2, 0));
        // this.setExit(this.labyrintheAt(5, longLargeur - 1).matriceAt(longLargeur/2, longLargeur - 1));

        // this.start.setSpecial(GraphNode.START);
        // this.exit.setSpecial(GraphNode.EXIT);
    }

    public GraphNode<String> setStartExit(int colRow) {
        int tileRow = 0;
        int tileCol = 0;
        if (colRow % 2 == 0) {
            Boolean gauche = new Random().nextBoolean();
            if (gauche) {
                tileCol = 0;
                tileRow = new Random().nextInt(longLargeur - 1);
                return this.labyrintheAt(tileRow, tileCol).matriceAt(longLargeur / 2, 0);
            } else {
                tileCol = longLargeur - 1;
                tileRow = new Random().nextInt(longLargeur - 1);
                return this.labyrintheAt(tileRow, tileCol).matriceAt(longLargeur / 2, longLargeur - 1);
            }
        } else {
            boolean haut = new Random().nextBoolean();
            if (haut) {
                tileRow = 0;
                tileCol = new Random().nextInt(longLargeur - 1);
                return this.labyrintheAt(tileRow, tileCol).matriceAt(0, longLargeur / 2);
            } else {
                tileRow = longLargeur - 1;
                tileCol = new Random().nextInt(longLargeur - 1);
                return this.labyrintheAt(tileRow, tileCol).matriceAt(longLargeur - 1, longLargeur / 2);
            }
        }
    }

    public void setLabyrintheAt(int row, int col, Tuile tile) {
        this.labyrinthe.get(row).set(col, tile);
    }

    public Tuile labyrintheAt(int row, int col) {
        return this.labyrinthe.get(row).get(col);
    }

    public String toString() {
        // String str = "";

        // GraphNode<String> currentNode = this.topLeftNode;
        // GraphNode<String> firstNodeNextRow = currentNode.getSouth();

        // while (firstNodeNextRow != null) {
        //     firstNodeNextRow = currentNode.getSouth();
        //     while (currentNode != null) {
        //         str += (currentNode.getValue());
        //         currentNode = currentNode.getEast();
        //     }
        //     str += ("\n");
        //     currentNode = firstNodeNextRow;
        // }

        // return str;

        return labyrintheAt(0, 0).toString();
    }

    public void resetPath() {
        for (ArrayList<Tuile> row : this.labyrinthe) {
            for (Tuile tile : row) {
                for (ArrayList<GraphNode<String>> tileRow : tile.getMatrice()) {
                    for (GraphNode<String> node : tileRow) {
                        if (!node.isWall()) {
                            node.getButton().setBackground(Color.WHITE);
                        }
                        if (node.isExit()) {
                            node.getButton().setBackground(Color.RED);
                        }
                        if (node.isStart()) {
                            node.getButton().setBackground(Color.BLUE);
                        }
                        node.setVisite(false);
                    }
                }
            }
        }
    }
    
    public void setVoisinage() {
        for (ArrayList<Tuile> row : this.labyrinthe) {
            for (Tuile tile : row) {
                for (ArrayList<GraphNode<String>> tileRow : tile.getMatrice()) {
                    for (GraphNode<String> node : tileRow) {
                        node.setVoisins();
                    }
                }
            }
        }
    }

    public GraphNode<String> getStart() {
        return start;
    }

    public void setStart(GraphNode<String> start) {
        this.start = start;
    }

    public GraphNode<String> getExit() {
        return exit;
    }

    public void setExit(GraphNode<String> exit) {
        this.exit = exit;
    }

    public static void main(String[] args) {
        Labyrinth test = new Labyrinth();
        System.out.println(test);
    }
}
