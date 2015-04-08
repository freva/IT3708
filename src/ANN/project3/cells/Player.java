package ANN.project3.cells;

import ANN.project3.Project3;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Player extends EmptyCell {
    private Direction direction = Direction.UP;
    private double[][] dynCoords = {{0.5, 0.85}, {0.85, 0.15}, {0.5, 0.3}, {0.15, 0.15}};

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        super.draw(g, offsetX, offsetY);

        Graphics2D g2 = (Graphics2D) g;
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, dynCoords.length);
        polygon.moveTo(offsetX + dynCoords[0][0]* Project3.CELL_SIZE, offsetY + dynCoords[0][1]*Project3.CELL_SIZE);

        for (int i = 1; i < dynCoords.length; i++)
            polygon.lineTo(offsetX + dynCoords[i][0]*Project3.CELL_SIZE, offsetY + dynCoords[i][1]*Project3.CELL_SIZE);


        polygon.closePath();
        g2.setColor(Color.BLUE);
        g2.fill(polygon);
        g2.draw(polygon);
    }
}
