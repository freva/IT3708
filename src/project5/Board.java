package project5;

import generics.QLearning.QGame;
import project5.cells.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Board implements QGame {
    public static int BOARD_DIMENSION_X, BOARD_DIMENSION_Y;
    private static final double POISON_REWARD = -100, EMPTY_REWARD = -0.05, FOOD_REWARD = 10, FINISH_REWARD = 100;

    private int foodTotal, foodEaten, poisonEaten, moves, startX, startY, agentX, agentY;
    private Cells[][] original, board;
    private String hash;


    public Board(ArrayList<Integer> values) {
        BOARD_DIMENSION_X = values.get(0);
        BOARD_DIMENSION_Y = values.get(1);

        original = new Cells[BOARD_DIMENSION_X][BOARD_DIMENSION_Y];
        for(int i=0; i<original.length; i++) {
            for(int j=0; j<original[i].length; j++) {
                switch (values.get(5+i + j*BOARD_DIMENSION_X)) {
                    case -2:
                        original[i][j] = Cells.START;
                        startX = i;
                        startY = j;
                        agentX = i;
                        agentY = j;
                        break;

                    case -1:
                        original[i][j] = Cells.POISON;
                        break;

                    case 0:
                        original[i][j] = Cells.EMPTY;
                        break;

                    default:
                        original[i][j] = Cells.FOOD;
                        foodTotal++;
                        break;
                }
            }
        }

        reset();
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

    public Cells getCell(int x, int y) {
        if((x+BOARD_DIMENSION_X) % BOARD_DIMENSION_X == agentX && ((y+BOARD_DIMENSION_Y) % BOARD_DIMENSION_Y) == agentY) return Cells.AGENT;
        return board[(x+BOARD_DIMENSION_X) % BOARD_DIMENSION_X][(y+BOARD_DIMENSION_Y) % BOARD_DIMENSION_Y];
    }

    @Override
    public void reset() {
        board = new Cells[original.length][];

        for(int i=0; i<board.length; i++)
            board[i] = Arrays.copyOf(original[i], original[i].length);

        poisonEaten = 0;
        foodEaten = 0;
        moves = 0;
        agentX = startX;
        agentY = startY;
        updateCacheHash();
    }

    @Override
    public boolean isFinished() {
        return (foodTotal == foodEaten && board[agentX][agentY] == Cells.START) || moves > getStepLimit();
    }

    @Override
    public double updateGame(int action) {
        Direction direction = Direction.values()[action];
        int newX = (agentX + direction.getVectorX() + BOARD_DIMENSION_X) % BOARD_DIMENSION_X;
        int newY = (agentY + direction.getVectorY() + BOARD_DIMENSION_Y) % BOARD_DIMENSION_Y;

        moves++;
        Cells targetCell = getCell(newX, newY);
        agentX = newX;
        agentY = newY;
        if(targetCell == Cells.FOOD) {
            board[newX][newY] = Cells.EMPTY;
            foodEaten++;
            updateCacheHash();
            return FOOD_REWARD;
        } else if(targetCell == Cells.POISON) {
            board[newX][newY] = Cells.EMPTY;
            poisonEaten++;
            return POISON_REWARD;
        } else if(isFinished()) return FINISH_REWARD;
        else return EMPTY_REWARD;
    }

    public String getHash(int x, int y) {
        return hash + x + y;
    }

    public String getHash() {
        return getHash(agentX, agentY);
    }


    private void updateCacheHash() {
        hash = "";
        for (int i = 0; i < BOARD_DIMENSION_X; i++) {
            for (int j = 0; j < BOARD_DIMENSION_Y; j++) {
                if (board[i][j] == Cells.FOOD) {
                    hash += i + j;
                }
            }
        }
    }


    public String toString() {
        return "Moves: " + getNumberOfMoves() + " | Food: " + getFoodEaten() + " | Poison: " + getPoisonEaten();
    }


    private int getStepLimit() {
        return BOARD_DIMENSION_X * BOARD_DIMENSION_Y * 4;
    }
}
