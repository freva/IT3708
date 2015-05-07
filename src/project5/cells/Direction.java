package project5.cells;

import project5.Project5;

import java.awt.*;
import java.awt.geom.AffineTransform;

public enum Direction {
    UP(0, 1), RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0), NONE(0, 0);

    private int vectorX, vectorY;
    Direction(int vectorX, int vectorY) {
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }

    public int getVectorY() {
        return vectorY;
    }

    public int getVectorX() {
        return vectorX;
    }

    public void drawDirection(Graphics g1, int x, int y) {
        int cellCenterX = x + Project5.CELL_SIZE/2, cellCenterY = y + Project5.CELL_SIZE/2;
        int cellOffsetX = (int) (vectorX*Project5.CELL_SIZE*0.3), cellOffsetY = (int) (vectorY*Project5.CELL_SIZE*0.3);

        drawArrow(g1, cellCenterX-cellOffsetX, cellCenterY-cellOffsetY, cellCenterX+cellOffsetX, cellCenterY+cellOffsetY);
    }

    public static void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        if(x1 == x2 && y1 == y2) return;
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        g.setColor(Color.BLACK);
        g.drawLine(0, 0, len, 0);

        int ARR_SIZE = 7;
        g.fillPolygon(new int[] {len, len- ARR_SIZE, len- ARR_SIZE, len}, new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }
}
