package Labyrinthe;
import javax.swing.*;

import Algos.Astar;
import Algos.BreathFirstSearch;
import Algos.DepthFirstSearch;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * Source: https://www.guru99.com/java-swing-gui.html
 * Source: https://stackoverflow.com/questions/67609407/nested-for-loop-that-will-create-8x8-grid-with-jbutton-for-each-tile-for-game-of
 * User: Gilbert Le Blanc
 * Source: https://stackoverflow.com/questions/21879243/how-to-create-on-click-event-for-buttons-in-swing
 * User: alex2410
 * 
 */

public class TileGenerator implements Runnable {
    public JPanel tiles;
    public JFrame mainWindow;
    public int cbIndex = 0;
    public JComboBox<String> cbChoix = null;
    int nbNodesPerSide = 9;
    int largeurLabyrinthe = TuileFactory.LARGEUR_TUILES * TuileFactory.LARGEUR_TUILES;
    int hauteurNode = 20;
    Labyrinth labyrinthe;
    LinkedList<MyPair<Labyrinth>> labChoices = new LinkedList<>();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new TileGenerator());
    }

    @Override
    public void run(){
        fill_lab_choices();
        mainWindow = new JFrame("Tile generator");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        mainWindow.add(createMainView(), BorderLayout.CENTER);
        mainWindow.add(createMenu(), BorderLayout.NORTH);

        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    public void fill_lab_choices() {
        for (int i = 0; i < 10; i++) {
            Labyrinth un_labyrinthe = new Labyrinth();
            this.labChoices.add(new MyPair<Labyrinth>(un_labyrinthe.getDifficulte(), un_labyrinthe));
        }
        this.labChoices = DifficultySorter.sort(this.labChoices);
    }

    public Labyrinth get_lab_choice(int index) {
        return this.labChoices.get(index).getValue();
    }

    public void run(JPanel newMain) {
        mainWindow.getContentPane().removeAll();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainWindow.add(newMain, BorderLayout.CENTER);
        mainWindow.add(createMenu(), BorderLayout.NORTH);

        mainWindow.pack();
        mainWindow.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainWindow.setLocationByPlatform(true);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    private class newLabyrintheListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            run(labyrintheView(new Labyrinth()));
        }
    }

    private class wideListener implements ActionListener {
        private Labyrinth labyrinthe;

        public wideListener(Labyrinth labyrinthe) {
            this.labyrinthe = labyrinthe;
        }

        public void actionPerformed(ActionEvent e) {
            BreathFirstSearch.searchWide(this.labyrinthe.getStart());
            this.labyrinthe.getStart().getButton().setBackground(Color.BLUE);
            this.labyrinthe.getExit().getButton().setBackground(Color.RED);
            run(tiles);
        }
    }

    private class deepListener implements ActionListener {
        private Labyrinth labyrinthe;

        public deepListener(Labyrinth labyrinthe) {
            this.labyrinthe = labyrinthe;
        }

        public void actionPerformed(ActionEvent e) {
            DepthFirstSearch.searchDeep(this.labyrinthe.getStart());
            DepthFirstSearch.found = false;
            this.labyrinthe.getStart().getButton().setBackground(Color.BLUE);
            this.labyrinthe.getExit().getButton().setBackground(Color.RED);
            run(tiles);
        }
    }

    private class aStarListener implements ActionListener {
        private Labyrinth labyrinthe;

        public aStarListener(Labyrinth labyrinth) {
            this.labyrinthe = labyrinth;
        }

        public void actionPerformed(ActionEvent e) {
            Astar solver = new Astar(this.labyrinthe);
            solver.solve();
            this.labyrinthe.getExit().getButton().setBackground(Color.RED);
            this.labyrinthe.getStart().getButton().setBackground(Color.BLUE);
            run(tiles);
        }
    }

    private class resetPath implements ActionListener {
        private Labyrinth labyrinthe;
        
        public resetPath(Labyrinth labyrinthe) {
            this.labyrinthe = labyrinthe;
        }

        public void actionPerformed(ActionEvent e) {
            this.labyrinthe.resetPath();
            run(tiles);
        }
    }

    private class lab_choicer implements ActionListener {
        private Labyrinth labyrinthe;
        private JComboBox<String> cb1;
        private TileGenerator app;

        public lab_choicer(TileGenerator app, Labyrinth labyrinthe, JComboBox<String> box) {
            this.labyrinthe = labyrinthe;
            this.cb1 = box;
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.app.cbChoix.setSelectedIndex(cbChoix.getSelectedIndex());
            this.labyrinthe = get_lab_choice(cb1.getSelectedIndex());
            run(labyrintheView(this.labyrinthe));
        }
        
    }

    public String[] get_lab_names() {
        DecimalFormat df = new DecimalFormat("0.000");
        String lab_names[] = new String[this.labChoices.size];
        Node<MyPair<Labyrinth>> current = this.labChoices.begin();
        for (int i=0; i < this.labChoices.size; i++) {
            lab_names[i] = "LAB " + i + " " + df.format(current.getValue().getValue().getDifficulte());
            current = current.getNext();
        }
        return lab_names;
    }

    public JPanel createMenu() {
        JPanel menuPanel = new JPanel();
        JMenuBar menu = new JMenuBar();
        JMenu tilesMenu = new JMenu("Tiles");
        
        JMenu labyrintheMenu = new JMenu("Labyrinthe");
        JMenuItem newLabyrinthe = new JMenuItem("Generate new labyrinth");
        newLabyrinthe.addActionListener(new newLabyrintheListener());
        JMenuItem deptFirst = new JMenuItem("Solve with depth first");
        deptFirst.addActionListener(new deepListener(this.labyrinthe));
        JMenuItem breathFirst = new JMenuItem("Solve With breath first");
        breathFirst.addActionListener(new wideListener(this.labyrinthe));
        JMenuItem aStar = new JMenuItem("Solve with A*");
        aStar.addActionListener(new aStarListener(this.labyrinthe));
        JMenuItem resetPath = new JMenuItem("Reset path");
        resetPath.addActionListener(new resetPath(this.labyrinthe));

        labyrintheMenu.add(newLabyrinthe);
        labyrintheMenu.add(deptFirst);
        labyrintheMenu.add(breathFirst);
        labyrintheMenu.add(aStar);
        labyrintheMenu.add(resetPath);
        menu.add(labyrintheMenu);

        if (cbChoix == null) {
            cbChoix = new JComboBox<>(get_lab_names());
        }
        this.cbChoix.addActionListener(new lab_choicer(this, this.labyrinthe, this.cbChoix));
        menu.add(cbChoix);

        JMenuItem save = new JMenuItem("SAVE");
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder puzzlePiece = new StringBuilder();
                int compteur = 0;
                for (Component bouton : tiles.getComponents()) {
                    if (compteur % nbNodesPerSide == 0){
                        puzzlePiece.append("\n");
                    }
                    if (bouton.getBackground() == Color.BLACK){
                        puzzlePiece.append("X");
                        compteur += 1;
                        continue;
                    }

                    puzzlePiece.append(" ");
                    compteur += 1;
                }
                writeToFile(puzzlePiece.toString());
            }
            
        });

        JMenuItem reset = new JMenuItem("Reset tiles");
        reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Component bouton : tiles.getComponents()) {
                    bouton.setBackground(Color.WHITE);
                }
                
            }
            
        });

        tilesMenu.add(save);
        tilesMenu.add(reset);

        menu.add(tilesMenu);

        menuPanel.add(menu);

        return menuPanel;
    }

    public JPanel createMainView() {
        JPanel tileSelector = new JPanel(new GridLayout(nbNodesPerSide,nbNodesPerSide));
        tileSelector.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int index = 0; index < nbNodesPerSide*nbNodesPerSide; index++) {
            JButton button = new JButton();
            button.setBackground(Color.WHITE);
            button.setPreferredSize(new Dimension(hauteurNode, hauteurNode));
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (button.getBackground() != Color.BLACK) {
                        button.setBackground(Color.BLACK);
                        return;
                    }
                    button.setBackground(Color.WHITE);
                }
                
            });

            tileSelector.add(button);
        }
        this.tiles = tileSelector;
        return tileSelector;
    }

    public JPanel labyrintheView(Labyrinth un_Labyrinthe) {
        JPanel tileSelector = new JPanel(new GridLayout(this.largeurLabyrinthe,this.largeurLabyrinthe));
        tileSelector.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Labyrinth labyrinhe = un_Labyrinthe;
        this.labyrinthe = labyrinhe;
        GraphNode<String> currentNode = this.labyrinthe.topLeftNode;
        GraphNode<String> firstNodeNextRow = this.labyrinthe.topLeftNode.getSouth();

        while (firstNodeNextRow != null) {
            firstNodeNextRow = currentNode.getSouth();
            while (currentNode != null) {
                currentNode.setButton(new myButton(currentNode));
                currentNode.getButton().setPathOrWall();
                if (currentNode.isStart()) {
                    currentNode.getButton().setBackground(Color.BLUE);
                }

                if (currentNode.isExit()) {
                    currentNode.getButton().setBackground(Color.RED);
                }
                tileSelector.add(currentNode.getButton());
                currentNode = currentNode.getEast();
            }
            currentNode = firstNodeNextRow;
        }

        this.tiles = tileSelector;
        return tileSelector;
    }

    public class myButton extends JButton {
        GraphNode<String> node;
        public myButton(GraphNode<String> node) {
            super();
            this.node = node;
        }

        public void setPathOrWall() {
            if (this.node.getValue().equals(" ")) {
                this.setBackground(Color.WHITE);
            } else {
                this.setBackground(Color.BLACK);
            }
        }
    }

    /**
     * Source: https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
     * User: Kip
     * User: Dave Jarvis
     * @param puzzlePiece
     */
    public void writeToFile(String puzzlePiece) {
        try (
            FileWriter fw = new FileWriter("./ProjetContinu/semaine9/Labo3/pieces.txt", true); 
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            )
        {
            out.println(puzzlePiece);
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
