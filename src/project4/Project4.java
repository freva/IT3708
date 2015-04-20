package project4;

import javax.swing.*;
import java.awt.*;

public class Project4 {
    public static final int CELL_SIZE = 40;

    public static void main(String[] args) {
        BeerTracker bt = new BeerTracker(BeerTracker.Scenario.NO_WRAP);
        bt.runSimulation();
    }


    public static void setUpGUI(BeerTracker flatland) {
        JFrame frame = new JFrame();
        frame.setTitle("Flatland");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(CELL_SIZE * Board.DIMENSION_X + frame.getInsets().left + frame.getInsets().right, BeerTracker.INFO_BOARD_HEIGHT + CELL_SIZE * Board.DIMENSION_Y + frame.getInsets().top + frame.getInsets().bottom);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.getContentPane().add(flatland);
        frame.setVisible(true);
    }
}
