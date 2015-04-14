package project4;

import javax.swing.*;
import java.awt.*;

public class BeerTracker extends JPanel {
    public static final int INFO_BOARD_HEIGHT = 25;
    private Board board = new Board();

    public BeerTracker() {
        setLayout(null);
        setBackground(null);
    }


    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", Font.PLAIN, 20));
        g.drawString("Tick: " + board.getNumberOfTicks() + " | Intercepted: " + board.getNumberOfIntercepted() + " | Avoided: " + board.getNumberOfAvoided(), 10, 22);

        board.draw(g, 0, INFO_BOARD_HEIGHT);
    }
}
