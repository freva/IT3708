package project5.cells;

import project5.Project5;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Agent extends EmptyCell {
    private double[] dynCoords = {0.25, 0.75, 0.75, 0.25};
    private int x, y;

    public Agent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        super.draw(g, offsetX, offsetY);

        Graphics2D g2 = (Graphics2D) g;
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, dynCoords.length);
        polygon.moveTo(offsetX + dynCoords[0]* Project5.CELL_SIZE, offsetY + dynCoords[dynCoords.length-1]*Project5.CELL_SIZE);

        for (int i = 1; i < dynCoords.length; i++)
            polygon.lineTo(offsetX + dynCoords[i]*Project5.CELL_SIZE, offsetY + dynCoords[i-1]*Project5.CELL_SIZE);


        polygon.closePath();
        g2.setColor(Color.BLUE);
        g2.fill(polygon);
        g2.draw(polygon);
    }
}
