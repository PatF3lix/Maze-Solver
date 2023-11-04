package Labyrinthe;

import Labyrinthe.TileGenerator.myButton;

/**
 * * GraphNodes
    * cost (G)
    * heuristique (H)
    * priorite (F)
    * parent
    * special (start (1) ou exit (0))
    * valeur
    * voisins
    * visite = True or False
 * @param value
 */
public class GraphNode<T> extends Node<T> {
    public static int START = 1;
    public static int EXIT = 0;
    
    private int cost = 0;
    private int heuristique = 0;
    private double priorite = Double.POSITIVE_INFINITY;
    private GraphNode<T> parent;
    private int special = -1;
    private GraphNode<T> north = null;
    private GraphNode<T> west = null;
    private GraphNode<T> south = null;
    private GraphNode<T> east= null;
    private ArrayList<GraphNode<T>> voisins;
    private boolean visite = false;

    private myButton button;

    public myButton getButton() {
        return button;
    }

    public void setButton(myButton button) {
        this.button = button;
    }

    public GraphNode(T value) {
        super(value);
        this.voisins = new ArrayList<>(4);
    }

    public void setVoisins() {
        this.voisins.add(this.east);
        this.voisins.add(this.west);
        this.voisins.add(this.south);
        this.voisins.add(this.north);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHeuristique() {
        return heuristique;
    }

    public void setHeuristique(int heuristique) {
        this.heuristique = heuristique;
    }

    public double getPriorite() {
        return priorite;
    }

    public void setPriorite(Double priorite) {
        this.priorite = priorite;
    }

    public GraphNode<T> getParent() {
        return parent;
    }

    public void setParent(GraphNode<T> parent) {
        this.parent = parent;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public GraphNode<T> getNorth() {
        return this.north;
    }

    public void setNorth(GraphNode<T> north) {
        this.north = north;
    }

    public GraphNode<T> getWest() {
        return this.west;
    }

    public void setWest(GraphNode<T> west) {
        this.west = west;
    }

    public GraphNode<T> getSouth() {
        return this.south;
    }

    public void setSouth(GraphNode<T> south) {
        this.south = south;
    }

    public GraphNode<T> getEast() {
        return this.east;
    }

    public void setEast(GraphNode<T> east) {
        this.east = east;
    }

    public ArrayList<GraphNode<T>> getVoisins() {
        return voisins;
    }
    
    public boolean isVisite() {
        return visite;
    }

    public void setVisite(boolean visite) {
        this.visite = visite;
    }

    public boolean isWall() {
        return this.getValue().equals("X");
    }

    public boolean isStart() {
        return this.special == GraphNode.START;
    }

    public boolean isExit() {
        return this.special == GraphNode.EXIT;
    }

    public int distanceDeDroite(){
        GraphNode<T> temp = this;
        int retour = 0;
        while (temp != null) {
            retour += 1;
            temp = temp.east;
        }

        return retour;
    }

    public int distanceDeGauche(){
        GraphNode<T> temp = this;
        int retour = 0;
        while (temp != null) {
            retour += 1;
            temp = temp.west;
        }

        return retour;
    }

    public int distanceDuHaut(){
        GraphNode<T> temp = this;
        int retour = 0;
        while (temp != null) {
            retour += 1;
            temp = temp.north;
        }

        return retour;
    }

    public int distanceDuBas(){
        GraphNode<T> temp = this;
        int retour = 0;
        while (temp != null) {
            retour += 1;
            temp = temp.south;
        }

        return retour;
    }
}
