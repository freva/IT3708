package project5;

import project5.cells.*;

import java.util.ArrayList;

public class Board {
    public static int BOARD_DIMENSION_X, BOARD_DIMENSION_Y;
    private static final double POISON_COST = -2, FOOD_COST = 1;

    private int foodEaten, poisonEaten, moves;
    private EmptyCell[][] board;
    private Agent agent;


    public Board(ArrayList<Integer> values) {
        BOARD_DIMENSION_X = values.get(0);
        BOARD_DIMENSION_Y = values.get(1);

        board = new EmptyCell[BOARD_DIMENSION_X][BOARD_DIMENSION_Y];
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[i].length; j++) {
                switch (values.get(5+i + j*BOARD_DIMENSION_X)) {
                    case -2:
                        board[i][j] = new EmptyCell(true);
                        agent = new Agent(i, j);
                        break;

                    case -1:
                        board[i][j] = new Poison();
                        break;

                    case 0:
                        board[i][j] = new EmptyCell();
                        break;

                    default:
                        board[i][j] = new Food();
                        break;
                }
            }
        }
    }


    public int getFoodEaten() {
        return foodEaten;
    }

    public int getPoisonEaten() {
        return poisonEaten;
    }

    public int getNumberOfMoves() {
        return moves;
    }

    public EmptyCell getCell(int x, int y) {
        if((x+BOARD_DIMENSION_X) % BOARD_DIMENSION_X == agent.getX() && ((y+BOARD_DIMENSION_Y) % BOARD_DIMENSION_Y) == agent.getY()) return agent;
        return board[(x+BOARD_DIMENSION_X) % BOARD_DIMENSION_X][(y+BOARD_DIMENSION_Y) % BOARD_DIMENSION_Y];
    }
}
