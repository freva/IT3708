package boids;

import boids.gui.Sidebar;
import boids.simulation.Simulation;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final int APP_WIDTH = 980, APP_HEIGHT = 720;
    public static final int SIMULATION_WIDTH = 810, SIMULATION_HEIGHT = 720;


    public static void main(String[] args) {
        new Main();
        Simulation.getSimulation().run();
    }


    public Main() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{SIMULATION_WIDTH, APP_WIDTH-SIMULATION_WIDTH};
        gridBagLayout.rowHeights = new int[]{APP_HEIGHT};


        Simulation simulation = Simulation.getSimulation();
        GridBagConstraints gbc_simulation = new GridBagConstraints();
        gbc_simulation.fill = GridBagConstraints.BOTH;
        gbc_simulation.gridx = 0;
        gbc_simulation.gridy = 0;


        Sidebar sidebar = new Sidebar();
        GridBagConstraints gbc_sidebar = new GridBagConstraints();
        gbc_sidebar.fill = GridBagConstraints.BOTH;
        gbc_sidebar.gridx = 1;
        gbc_sidebar.gridy = 0;


        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Boids");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setSize(APP_WIDTH + mainFrame.getInsets().left + mainFrame.getInsets().right, APP_HEIGHT + mainFrame.getInsets().top + mainFrame.getInsets().bottom);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setFocusable(true);

        mainFrame.getContentPane().setLayout(gridBagLayout);
        mainFrame.getContentPane().add(sidebar, gbc_sidebar);
        mainFrame.getContentPane().add(simulation, gbc_simulation);
    }
}
