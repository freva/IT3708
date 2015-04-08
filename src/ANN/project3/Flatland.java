package ANN.project3;

import ANN.project3.cells.Cell;
import ANN.project3.cells.Food;
import ANN.project3.cells.Poison;

public class Flatland {
    Cell[][] board;

    public Flatland(int dimension, double probPoison, double probFood) {
        board = new Cell[dimension][dimension];

        for(int i=0; i<dimension; i++) {
            for(int j=0; j<dimension; j++) {
                if(probFood < Math.random())
                    board[i][j] = new Food(i, j);
                else if(probPoison < Math.random())
                    board[i][j] = new Poison(i, j);
            }
        }
    }

    public Flatland(Cell[][] board) {
        this.board = board;
    }
}
