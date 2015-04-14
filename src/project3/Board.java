package project3;

import project3.cells.EmptyCell;
import project3.cells.Food;
import project3.cells.Agent;
import project3.cells.Poison;

public class Board {
    private static final double POISON_COST = -5, FOOD_COST = 1;

    private double scoreOffset, scoreWidth;
    private int agentX, agentY, foodEaten, poisonEaten, moves;
    private EmptyCell[][] board;
    private Agent agent;


    public Board(int dimension, double probPoison, double probFood) {
        int totalFood = 0, totalPoison = 0;
        board = new EmptyCell[dimension][dimension];

        for(int i=0; i<dimension; i++) {
            for(int j=0; j<dimension; j++) {
                if(Math.random() < probFood) {
                    board[i][j] = new Food();
                    totalFood++;
                } else if(Math.random() < probPoison) {
                    board[i][j] = new Poison();
                    totalPoison++;
                } else
                    board[i][j] = new EmptyCell();
            }
        }
        scoreOffset = -totalPoison*POISON_COST;
        scoreWidth = scoreOffset + totalFood*FOOD_COST;

        agentX = (int) (Math.random()*dimension);
        agentY = (int) (Math.random()*dimension);
        agent = new Agent();
        board[agentX][agentY] = agent;
    }


    private Board(EmptyCell[][] board, Agent agent, int agentX, int agentY, double scoreOffset, double scoreWidth) {
        this.board = board;
        this.agent = agent;
        this.agentX = agentX;
        this.agentY = agentY;
        this.scoreWidth = scoreWidth;
        this.scoreOffset = scoreOffset;
    }


    public void move(Direction direction) {
        switch (direction) {
            case UP:
                board[agentX][agentY] = new EmptyCell();
                agentX = (agentX + agent.getOrientation().getX() + getSize())%getSize();
                agentY = (agentY + agent.getOrientation().getY() + getSize())%getSize();

                EmptyCell targetCell = getCell(agentX, agentY);
                if(targetCell instanceof Food)
                    foodEaten++;
                else if(targetCell instanceof Poison)
                    poisonEaten++;

                board[agentX][agentY] = agent;
                moves++;
                break;

            case RIGHT:
            case LEFT:
                agent.turn(direction);
                move(Direction.UP);
                break;
        }

    }


    public double[] sense() {
        double[] sensing = new double[Direction.values().length * 2];

        Agent.Orientation agentOrientation = agent.getOrientation();
        for(int i=0; i<Direction.values().length; i++) {
            Agent.Orientation newOrientation = agentOrientation.turn(Direction.values()[i]);

            sensing[2*i] = getCell(agentX + newOrientation.getX(), agentY + newOrientation.getY()) instanceof Food ? 1 : 0;
            sensing[2*i + 1] = getCell(agentX + newOrientation.getX(), agentY + newOrientation.getY()) instanceof Poison ? 1 : 0;
        }

        return sensing;
    }


    public int getSize() {
        return board.length;
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

    public Board getClone() {
        EmptyCell[][] boardCopy = new EmptyCell[board.length][];
        for(int i = 0; i < board.length; i++)
            boardCopy[i] = board[i].clone();

        return new Board(boardCopy, agent.getClone(), agentX, agentY, scoreOffset, scoreWidth);
    }

    public double getBoardScore() {
        return (scoreOffset + FOOD_COST * foodEaten + POISON_COST * poisonEaten) / scoreWidth;
    }

    public EmptyCell getCell(int x, int y) {
        return board[(x+getSize()) % getSize()][(y+getSize()) % getSize()];
    }


    public enum Direction {
       UP, RIGHT, LEFT
    }
}
