package ANN.project3;

import ANN.project3.cells.EmptyCell;
import ANN.project3.cells.Food;
import ANN.project3.cells.Player;
import ANN.project3.cells.Poison;

public class Board {
    private int playerX, playerY, foodCounter, poisonCounter;
    private EmptyCell[][] board;
    private Player player;


    public Board(int dimension, double probPoison, double probFood) {
        board = new EmptyCell[dimension][dimension];

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

        playerX = (int) (Math.random()*dimension);
        playerY = (int) (Math.random()*dimension);
        player = new Player();
        board[playerX][playerY] = player;
    }


    public void move(Direction direction) {
        switch (direction) {
            case UP:
                board[playerX][playerY] = new EmptyCell();
                playerX = (playerX + player.getOrientation().getX() + getSize())%getSize();
                playerY = (playerY + player.getOrientation().getY() + getSize())%getSize();

                EmptyCell targetCell = getCell(playerX, playerY);
                if(targetCell instanceof Food)
                    foodCounter++;
                else if(targetCell instanceof Poison)
                    poisonCounter++;

                board[playerX][playerY] = player;
                break;

            case RIGHT:
                player.turnRight();
                break;

            case LEFT:
                player.turnLeft();
                break;
        }

    }


    public int getSize() {
        return board.length;
    }


    public EmptyCell getCell(int x, int y) {
        return board[x][y];
    }



    public enum Direction {
       UP, RIGHT, LEFT, NONE;
    }
}
