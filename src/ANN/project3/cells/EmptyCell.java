package ANN.project3.cells;

import ANN.project3.Project3;

import java.awt.*;

public class EmptyCell {
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(offsetX + (int) (Project3.CELL_SIZE*0.05), offsetY + (int) (Project3.CELL_SIZE*0.05), (int) (Project3.CELL_SIZE*0.9), (int) (Project3.CELL_SIZE*0.9));
    }
}
