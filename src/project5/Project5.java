package project5;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Project5 {
    public static final int CELL_SIZE = 40;

    public static void main(String[] args) {
        args = new String[]{"5"};

        new Flatland(parseInput(args[0])).runSimulation();
    }


    public static void setUpGUI(Flatland flatland) {
        JFrame frame = new JFrame();
        frame.setTitle("Flatland");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(CELL_SIZE * Board.BOARD_DIMENSION_X + frame.getInsets().left + frame.getInsets().right, Flatland.INFO_BOARD_HEIGHT + CELL_SIZE * Board.BOARD_DIMENSION_Y + frame.getInsets().top + frame.getInsets().bottom);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.getContentPane().add(flatland);
        frame.setVisible(true);
    }


    private static ArrayList<Integer> parseInput(String filename) {
        ArrayList<Integer> values = new ArrayList<>();
        Path filePath = Paths.get("input/" + filename + ".txt");
        Scanner scanner;

        try {
            scanner = new Scanner(filePath);

            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                   values.add(scanner.nextInt());
                } else {
                    scanner.next();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return values;
    }
}
