package project5.cells;

import project5.Project5;

import java.awt.*;
import java.awt.geom.GeneralPath;

public enum Cells {
    AGENT, FOOD, POISON, EMPTY, START;

    private static final double circleSize = 0.7;

    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(offsetX + (int) (Project5.CELL_SIZE * 0.05), offsetY + (int) (Project5.CELL_SIZE * 0.05), (int) (Project5.CELL_SIZE * 0.9), (int) (Project5.CELL_SIZE * 0.9));

        switch (this) {
            case AGENT:
                drawShape(g, offsetX, offsetY, new double[]{0.25, 0.75, 0.75, 0.25}, Color.BLUE);
                break;

            case POISON:
                drawShape(g, offsetX, offsetY, new double[]{0.15, 0.5, 0.85, 0.5}, Color.RED);
                break;

            case FOOD:
                g.setColor(Color.GREEN);
                g.fillOval(offsetX + (int) (Project5.CELL_SIZE*0.15), offsetY + (int) (Project5.CELL_SIZE*0.15), (int) (circleSize* Project5.CELL_SIZE), (int) (circleSize*Project5.CELL_SIZE));
                break;

            case START:
                g.setColor(Color.CYAN);
                g.fillRect(offsetX + (int) (Project5.CELL_SIZE*0.05), offsetY + (int) (Project5.CELL_SIZE*0.05), (int) (Project5.CELL_SIZE*0.9), (int) (Project5.CELL_SIZE*0.9));
                break;
        }
    }


    private static void drawShape(Graphics g, int offsetX, int offsetY, double[] dynCoords, Color color) {
        Graphics2D g2 = (Graphics2D) g;
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, dynCoords.length);
        polygon.moveTo(offsetX + dynCoords[0]* Project5.CELL_SIZE, offsetY + dynCoords[dynCoords.length-1]*Project5.CELL_SIZE);

        for (int i = 1; i < dynCoords.length; i++)
            polygon.lineTo(offsetX + dynCoords[i]*Project5.CELL_SIZE, offsetY + dynCoords[i-1]*Project5.CELL_SIZE);

        polygon.closePath();
        g2.setColor(color);
        g2.fill(polygon);
        g2.draw(polygon);
    }
}
