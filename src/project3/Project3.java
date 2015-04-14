package project3;

import javax.swing.*;
import java.awt.*;

public class Project3 {
    public static final int BOARD_DIMENSION = 10, CELL_SIZE = 60;

    public static void main(String[] args) {
        new Flatland().runSimulation(Integer.parseInt(args[0]), args[1].equals("static"));
    }


    public static void setUpGUI(Flatland flatland) {
        JFrame frame = new JFrame();
        frame.setTitle("Flatland");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(CELL_SIZE * BOARD_DIMENSION + frame.getInsets().left + frame.getInsets().right, Flatland.INFO_BOARD_HEIGHT + CELL_SIZE * BOARD_DIMENSION + frame.getInsets().top + frame.getInsets().bottom);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.getContentPane().add(flatland);
        frame.setVisible(true);
    }
}
