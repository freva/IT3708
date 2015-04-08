package ANN.project3.cells;

public abstract class Cell {
    private int x = 0, y = 0;

    public Cell(int x, int y) {
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
