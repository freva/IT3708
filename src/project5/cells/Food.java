package project5.cells;

import project5.Project5;

import java.awt.*;

public class Food extends EmptyCell {
    private static final double circleSize = 0.7;

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        super.draw(g, offsetX, offsetY);

        g.setColor(Color.GREEN);
        g.fillOval(offsetX + (int) (Project5.CELL_SIZE*0.15), offsetY + (int) (Project5.CELL_SIZE*0.15), (int) (circleSize* Project5.CELL_SIZE), (int) (circleSize*Project5.CELL_SIZE));
    }
}
