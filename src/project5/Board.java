package project5;

import generics.QLearning.QGame;
import project5.cells.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Board implements QGame {
    public static int BOARD_DIMENSION_X, BOARD_DIMENSION_Y;
    private static final double POISON_REWARD = -100, FOOD_REWARD = 1, FINISH_REWARD = 2;

    private int foodTotal, foodEaten, poisonEaten, moves;
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
                        foodTotal++;
                        break;
                }
            }
        }
    }

    public Board(EmptyCell[][] board, Agent agent, int foodTotal, int foodEaten, int poisonEaten) {
        this.board = board;
        this.agent = agent;
        this.foodTotal = foodTotal;
        this.foodEaten = foodEaten;
        this.poisonEaten = poisonEaten;
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

    @Override
    public QGame getClone() {
        EmptyCell[][] cells = new EmptyCell[board.length][];

        for(int i=0; i<board.length; i++) {
            cells[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return new Board(cells, agent.getClone(), foodTotal, foodEaten, poisonEaten);
    }

    @Override
    public boolean isFinished() {
        return foodTotal == foodEaten && board[agent.getX()][agent.getY()].isStart();
    }

    @Override
    public double updateGame(int action) {
        Direction direction = Direction.values()[action];
        int newX = (agent.getX() + direction.getVectorX() + BOARD_DIMENSION_X) % BOARD_DIMENSION_X;
        int newY = (agent.getY() + direction.getVectorY() + BOARD_DIMENSION_Y) % BOARD_DIMENSION_Y;

        moves++;
        EmptyCell targetCell = getCell(newX, newY);
        agent.setPosition(newX, newY);
        if(targetCell instanceof Food) {
            board[newX][newY] = new EmptyCell();
            foodEaten++;
            return FOOD_REWARD;
        } else if(targetCell instanceof Poison) {
            board[newX][newY] = new EmptyCell();
            poisonEaten++;
            return POISON_REWARD;
        } else  if(isFinished()) {
            return FINISH_REWARD;
        } else {
            return 0;
        }
    }

    public String getHash() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[i].length; j++) {
                EmptyCell targetCell = getCell(i, j);
                if(targetCell instanceof Food) sb.append("0");
                else if(targetCell instanceof Poison) sb.append("1");
                else if(targetCell instanceof Agent) sb.append("2");
                else sb.append("3");
            }
        }

        return sb.toString();
    }


    public String toString() {
        return "Moves: " + getNumberOfMoves() + " | Food: " + getFoodEaten() + " | Poison: " + getPoisonEaten() + " | " + agent.getX() + " " + agent.getY();
    }
}
