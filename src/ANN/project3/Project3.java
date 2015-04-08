package ANN.project3;

import javax.swing.*;

public class Project3 extends JPanel {
    public static final int BOARD_DIMENSION = 10, CELL_SIZE = 80;

    public static void main(String[] args) {
        JFrame game = new JFrame();
        game.setTitle("2048");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setResizable(false);
        game.pack();
        game.setSize(CELL_SIZE* BOARD_DIMENSION + game.getInsets().left + game.getInsets().right, CELL_SIZE* BOARD_DIMENSION + game.getInsets().top + game.getInsets().bottom);

        Flatland p = new Flatland(BOARD_DIMENSION, 1.0/3, 1.0/3);
        game.add(p);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
