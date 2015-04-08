package ANN.project3.cells;

public enum Direction {
    UP(0, 1), RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0);

    private int x, y;
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
