package project5;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Project5 {
    public static final int CELL_SIZE = 30;

    public static void main(String[] args) {
        new Flatland(parseInput(args[0])).runSimulation();
    }


    public static void setUpGUI(Flatland flatland) {
        JSlider slider1 = new JSlider(JSlider.HORIZONTAL, 50, 2000, 300);
        slider1.setMajorTickSpacing(300);
        slider1.setMinorTickSpacing(150);
        slider1.setPaintLabels(true);
        slider1.setPaintTicks(true);
        slider1.setPreferredSize(new Dimension(300, 50));
        slider1.addChangeListener(flatland);

        JFrame frame = new JFrame();
        frame.setTitle("Flatland");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(Math.max(CELL_SIZE * Board.BOARD_DIMENSION_X + frame.getInsets().left + frame.getInsets().right, 300), 50 + Flatland.INFO_BOARD_HEIGHT + CELL_SIZE * Board.BOARD_DIMENSION_Y + frame.getInsets().top + frame.getInsets().bottom);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.getContentPane().add(flatland);
        frame.getContentPane().add(slider1, BorderLayout.NORTH);
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
