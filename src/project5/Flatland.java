package project5;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Flatland extends JPanel {
    public static final int INFO_BOARD_HEIGHT = 25;
    private Board board;


    public Flatland(ArrayList<Integer> cells) {
        setLayout(null);
        setBackground(null);

        board = new Board(cells);
    }


    public void paint(Graphics g) {
        super.paint(g);

        if(board == null) return;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", Font.PLAIN, 20));
        g.drawString("Moves: " + board.getNumberOfMoves() + " | Food: " + board.getFoodEaten() + " | Poison: " + board.getPoisonEaten(), 10, 22);
        for (int i=0; i<Board.BOARD_DIMENSION_X; i++) {
            for (int j=0; j<Board.BOARD_DIMENSION_Y; j++) {
                board.getCell(i, j).draw(g, i*Project5.CELL_SIZE, INFO_BOARD_HEIGHT+j*Project5.CELL_SIZE);
            }
        }
    }
}
