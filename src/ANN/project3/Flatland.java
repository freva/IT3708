package ANN.project3;

import ANN.project3.cells.EmptyCell;
import ANN.project3.cells.Food;
import ANN.project3.cells.Player;
import ANN.project3.cells.Poison;

import javax.swing.*;
import java.awt.*;

public class Flatland extends JPanel {
    private EmptyCell[][] board;


    public Flatland(EmptyCell[][] board) {
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        this.board = board;
    }

    public Flatland(int dimension, double probPoison, double probFood) {
        this(generateBoard(dimension, probPoison, probFood));
    }


    public static EmptyCell[][] generateBoard(int dimension, double probPoison, double probFood) {
        EmptyCell[][] board = new EmptyCell[dimension][dimension];

        for(int i=0; i<dimension; i++) {
            for(int j=0; j<dimension; j++) {
                if(Math.random() < probFood)
                    board[i][j] = new Food();
                else if(Math.random() < probPoison)
                    board[i][j] = new Poison();
                else
                    board[i][j] = new EmptyCell();
            }
        }

        board[(int) (Math.random()*dimension)][(int) (Math.random()*dimension)] = new Player();

        return board;
    }


    public void paint(Graphics g) {
        super.paint(g);
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board.length; j++) {
                if (board[i][j] != null)
                    board[i][j].draw(g, i*Project3.CELL_SIZE, j*Project3.CELL_SIZE);
            }
        }
    }
}
