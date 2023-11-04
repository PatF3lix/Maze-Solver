package Labyrinthe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TuileFactory {
    public static int LARGEUR_TUILES = 9;

    public static ArrayList<Tuile> allTiles() {
        ArrayList<Tuile> listeTuiles = new ArrayList<>();
        BufferedReader reader;

        /**
         * Source: https://www.digitalocean.com/community/tutorials/java-read-file-line-by-line
         */
		try {
			reader = new BufferedReader(new FileReader("./ProjetContinu/semaine9/Labo3/pieces.txt"));
			String line = reader.readLine();
            StringBuilder tileRepresentation = new StringBuilder();
			while (line != null) {
                if (line.equals("")) {
                    listeTuiles.add(new Tuile(TuileFactory.LARGEUR_TUILES, tileRepresentation.toString()));
                    tileRepresentation = new StringBuilder();
                    line = reader.readLine();
                    continue;
                }
				tileRepresentation.append(line + "\n");
				// read next line
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return new ArrayList<Tuile>(listeTuiles);
    }

    /**
     * Choices: NORTH - SOUTH - WEST - EAST
     * @param cardinalDirection
     * @return
     */
    public static ArrayList<Tuile> cardinalTiles(String cardinalDirection) {
        boolean condition = false;
        ArrayList<Tuile> listeTuiles = TuileFactory.allTiles();
        ArrayList<Tuile> listeTuilesCardinales = new ArrayList<>();

        for (Tuile tile : listeTuiles) {
            switch (cardinalDirection) {
                case "NORTH":
                    condition = tile.isNorthOpenning();
                    break;

                case "SOUTH":
                    condition = tile.isSouthOpenning();
                    break;

                case "WEST":
                    condition = tile.isWestOpenning();
                    break;

                case "EAST":
                    condition = tile.isEastOpenning();
                    break;
        
                default:
                    break;
            }

            if (condition) {
                listeTuilesCardinales.add(tile);
            }
        }

        return new ArrayList<Tuile>(listeTuilesCardinales);
    }

    public static void main(String[] args) {
        ArrayList<Tuile> cardinalTiles = TuileFactory.allTiles();
        for (Tuile tile: cardinalTiles){
            System.out.println(tile + "\n");
        }
    }
}
