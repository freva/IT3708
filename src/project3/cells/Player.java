package project3.cells;

import project3.Board;
import project3.Project3;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class Player extends EmptyCell {
    private Orientation orientation = Orientation.SOUTH;
    private double[][] dynCoords = {{0.5, 0.85}, {0.85, 0.15}, {0.5, 0.3}, {0.15, 0.15}};

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        super.draw(g, offsetX, offsetY);

        Graphics2D g2 = (Graphics2D) g;
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, dynCoords.length);
        polygon.moveTo(offsetX + dynCoords[0][0]*Project3.CELL_SIZE, offsetY + dynCoords[0][1]*Project3.CELL_SIZE);

        for (int i = 1; i < dynCoords.length; i++)
            polygon.lineTo(offsetX + dynCoords[i][0]*Project3.CELL_SIZE, offsetY + dynCoords[i][1]*Project3.CELL_SIZE);

        AffineTransform af = new AffineTransform();
        af.translate(offsetX + Project3.CELL_SIZE/2, offsetY + Project3.CELL_SIZE/2);
        af.rotate(orientation.angle());
        af.translate(-offsetX - Project3.CELL_SIZE/2, -offsetY - Project3.CELL_SIZE/2);
        polygon.transform(af);

        polygon.closePath();
        g2.setColor(Color.BLUE);
        g2.fill(polygon);
        g2.draw(polygon);
    }


    public Orientation getOrientation() {
        return orientation;
    }

    public void turn(Board.Direction direction) {
        orientation = orientation.turn(direction);
    }


    public enum Orientation {
        NORTH(0, -1, Math.PI), EAST(1, 0, Math.PI*1.5), SOUTH(0, 1, 0), WEST(-1, 0, Math.PI*0.5);

        private int x, y;
        private double angle;
        Orientation(int x, int y, double angle) {
            this.x = x;
            this.y = y;
            this.angle = angle;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double angle() {
            return angle;
        }


        public Orientation turn(Board.Direction direction) {
            switch (direction) {
                case RIGHT:
                    return Orientation.values()[(indexOf()+1)%Orientation.values().length];

                case LEFT:
                    return Orientation.values()[(indexOf()-1+Orientation.values().length)%Orientation.values().length];

                case UP:
                default:
                    return this;
            }
        }


        private int indexOf() {
            for(int i=0; i<Orientation.values().length; i++) {
                if(Orientation.values()[i] == this)
                    return i;
            }

            return -1;
        }
    }
}
