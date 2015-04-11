package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;


public class Flatland extends JPanel implements KeyListener {
    private Board board;


    public Flatland(Board board) {
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        this.board = board;
    }

    public Flatland(int dimension, double probPoison, double probFood) {
        this(new Board(dimension, probPoison, probFood));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP:
                board.move(Board.Direction.UP);
                break;
            case KeyEvent.VK_LEFT:
                board.move(Board.Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT :
                board.move(Board.Direction.RIGHT);
                break;
        }
        repaint();
        System.out.println(Arrays.toString(board.sense()));
    }

    @Override
    public void keyReleased(KeyEvent e) {

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
