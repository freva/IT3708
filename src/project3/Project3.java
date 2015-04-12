package project3;

import javax.swing.*;

public class Project3 extends JPanel {
    public static final int BOARD_DIMENSION = 10, CELL_SIZE = 80;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Flatland");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(CELL_SIZE* BOARD_DIMENSION + frame.getInsets().left + frame.getInsets().right, Flatland.INFO_BOARD_HEIGHT + CELL_SIZE* BOARD_DIMENSION + frame.getInsets().top + frame.getInsets().bottom);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Flatland flatland = new Flatland();
        frame.add(flatland);

        flatland.runSimulation(BOARD_DIMENSION);
    }
}
