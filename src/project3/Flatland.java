package project3;

import javax.swing.*;
import java.awt.*;

public class Flatland extends JPanel {
    private Board board;


    public Flatland(Board board) {
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        this.board = board;
    }


    public Flatland(int dimension, double probPoison, double probFood) {
        this(new Board(dimension, probPoison, probFood));
    }


    public void paint(Graphics g) {
        super.paint(g);
        for (int i=0; i<board.getSize(); i++) {
            for (int j=0; j<board.getSize(); j++) {
                if (board.getCell(i, j) != null)
                    board.getCell(i, j).draw(g, i*Project3.CELL_SIZE, j*Project3.CELL_SIZE);
            }
        }
    }
}
