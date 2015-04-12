package project3;

import project3.cells.EmptyCell;
import project3.cells.Food;
import project3.cells.Player;
import project3.cells.Poison;

public class Board {
    private static final double POISON_COST = -5, FOOD_COST = 1;

    private double scoreOffset, scoreWidth;
    private int playerX, playerY, foodEaten, poisonEaten, moves;
    private EmptyCell[][] board;
    private Player player;


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

        playerX = (int) (Math.random()*dimension);
        playerY = (int) (Math.random()*dimension);
        player = new Player();
        board[playerX][playerY] = player;
    }


    private Board(EmptyCell[][] board, Player player, int playerX, int playerY, double scoreOffset, double scoreWidth) {
        this.board = board;
        this.player = player;
        this.playerX = playerX;
        this.playerY = playerY;
        this.scoreWidth = scoreWidth;
        this.scoreOffset = scoreOffset;
    }


    public void move(Direction direction) {
        switch (direction) {
            case UP:
                board[playerX][playerY] = new EmptyCell();
                playerX = (playerX + player.getOrientation().getX() + getSize())%getSize();
                playerY = (playerY + player.getOrientation().getY() + getSize())%getSize();

                EmptyCell targetCell = getCell(playerX, playerY);
                if(targetCell instanceof Food)
                    foodEaten++;
                else if(targetCell instanceof Poison)
                    poisonEaten++;

                board[playerX][playerY] = player;
                moves++;
                break;

            case RIGHT:
            case LEFT:
                player.turn(direction);
                move(Direction.UP);
                break;
        }

    }


    public double[] sense() {
        double[] sensing = new double[Direction.values().length * 2];

        Player.Orientation playerOrientation = player.getOrientation();
        for(int i=0; i<Direction.values().length; i++) {
            Player.Orientation newOrientation = playerOrientation.turn(Direction.values()[i]);

            sensing[2*i] = getCell(playerX + newOrientation.getX(), playerY + newOrientation.getY()) instanceof Food ? 1 : 0;
            sensing[2*i + 1] = getCell(playerX + newOrientation.getX(), playerY + newOrientation.getY()) instanceof Poison ? 1 : 0;
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

        return new Board(boardCopy, player.getClone(), playerX, playerY, scoreOffset, scoreWidth);
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
