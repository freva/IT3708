package project5;

import generics.QLearning.QGame;
import project5.cells.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Board implements QGame {
    public static int BOARD_DIMENSION_X, BOARD_DIMENSION_Y;
    private static final double POISON_REWARD = -10, FOOD_REWARD = 2, FINISH_REWARD = 10;

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

    public String getHash(boolean withAgent) {
        StringBuilder sb = new StringBuilder();

        for (EmptyCell[] aBoard : board) {
            for (EmptyCell targetCell : aBoard) {
                if (targetCell instanceof Food) sb.append("O");
                else if (targetCell instanceof Poison) sb.append("X");
                else if (targetCell instanceof Agent) sb.append("?");
                else sb.append(".");
            }
        }

        if(withAgent) return sb.append(agent.getX()).append(agent.getY()).toString();
        else return sb.toString();
    }

    public String getHash() {
        return getHash(true);
    }


    public String toString() {
        return "Moves: " + getNumberOfMoves() + " | Food: " + getFoodEaten() + " | Poison: " + getPoisonEaten() + " | " + agent.getX() + " " + agent.getY();
    }


    public void printBoard() {
        for(int y=0; y<BOARD_DIMENSION_Y; y++) {
            for (int x=0; x<BOARD_DIMENSION_X; x++) {
                EmptyCell targetCell = getCell(x, y);
                if (targetCell instanceof Food) System.out.print("O");
                else if (targetCell instanceof Poison) System.out.print("X");
                else if (targetCell instanceof Agent) System.out.print("?");
                else System.out.print(".");
            }
            System.out.println();
        }
    }
}
