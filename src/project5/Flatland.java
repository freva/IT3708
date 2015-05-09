package project5;

import generics.QLearning.QLearner;
import project5.cells.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Flatland extends JPanel {
    public static final int INFO_BOARD_HEIGHT = 25;
    private Board board;
    private QLearner qLearner;


    public Flatland(ArrayList<Integer> cells) {
        setLayout(null);
        setBackground(null);

        board = new Board(cells);
    }


    public void runSimulation() {
        qLearner = new QLearner(board, Direction.values().length-1, 0.9d, 0.7d);

        for(int i=0; i<5000; i++) {
            qLearner.runIteration();
        }

        simulateBestResult();
    }


    private void simulateBestResult() {
        Project5.setUpGUI(this);
        do {
            board.updateGame(qLearner.getAction(board.getHash()));

            repaint();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(!board.isFinished());
    }


    public void paint(Graphics g) {
        super.paint(g);

        if(board == null) return;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", Font.PLAIN, 20));
        g.drawString(board.toString(), 10, 22);
        for (int i=0; i<Board.BOARD_DIMENSION_X; i++) {
            for (int j=0; j<Board.BOARD_DIMENSION_Y; j++) {
                Direction direction = Direction.values()[qLearner.getAction(board.getHash(false) + i + j)];
                board.getCell(i, j).setDirection(direction);
                board.getCell(i, j).draw(g, i*Project5.CELL_SIZE, INFO_BOARD_HEIGHT+j*Project5.CELL_SIZE);
            }
        }
    }
}
