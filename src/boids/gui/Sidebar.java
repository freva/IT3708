package boids.gui;

import boids.simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sidebar extends JPanel implements ActionListener {
    private JButton btnAddObstacle, btnRemoveObstacles, btnAddPredator, btnRemovePredators;

    public Sidebar() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{180};
        gridBagLayout.rowHeights = new int[]{40, 220, 40, 40, 40, 40, 160};
        setBackground(Color.gray);
        setLayout(gridBagLayout);

        JLabel lblTitle = new JLabel("Boids");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblTitle.setForeground(Color.WHITE);
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.fill = GridBagConstraints.BOTH;
        gbc_lblTitle.insets = new Insets(5, 5, 5, 5);
        gbc_lblTitle.gridx = 0;
        gbc_lblTitle.gridy = 0;
        add(lblTitle, gbc_lblTitle);


        Sliders sliders = new Sliders();
        GridBagConstraints gbc_sliders = new GridBagConstraints();
        gbc_sliders.fill = GridBagConstraints.BOTH;
        gbc_sliders.insets = new Insets(5, 5, 20, 5);
        gbc_sliders.gridx = 0;
        gbc_sliders.gridy = 1;
        add(sliders, gbc_sliders);


        btnAddObstacle = new JButton("Add obstacle");
        btnAddObstacle.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnAddObstacle.addActionListener(this);
        GridBagConstraints gbc_btnAddObstacle = new GridBagConstraints();
        gbc_btnAddObstacle.fill = GridBagConstraints.BOTH;
        gbc_btnAddObstacle.insets = new Insets(20, 5, 5, 5);
        gbc_btnAddObstacle.gridx = 0;
        gbc_btnAddObstacle.gridy = 2;
        add(btnAddObstacle, gbc_btnAddObstacle);

        btnRemoveObstacles = new JButton("Remove obstacles");
        btnRemoveObstacles.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnRemoveObstacles.addActionListener(this);
        GridBagConstraints gbc_btnRemoveObstacles = new GridBagConstraints();
        gbc_btnRemoveObstacles.fill = GridBagConstraints.BOTH;
        gbc_btnRemoveObstacles.insets = new Insets(5, 5, 20, 5);
        gbc_btnRemoveObstacles.gridx = 0;
        gbc_btnRemoveObstacles.gridy = 3;
        add(btnRemoveObstacles, gbc_btnRemoveObstacles);


        btnAddPredator = new JButton("Add predator");
        btnAddPredator.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnAddPredator.addActionListener(this);
        GridBagConstraints gbc_btnAddPredator = new GridBagConstraints();
        gbc_btnAddPredator.fill = GridBagConstraints.BOTH;
        gbc_btnAddPredator.insets = new Insets(20, 5, 5, 5);
        gbc_btnAddPredator.gridx = 0;
        gbc_btnAddPredator.gridy = 4;
        add(btnAddPredator, gbc_btnAddPredator);

        btnRemovePredators = new JButton("Remove predators");
        btnRemovePredators.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnRemovePredators.addActionListener(this);
        GridBagConstraints gbc_btnRemovePredators = new GridBagConstraints();
        gbc_btnRemovePredators.fill = GridBagConstraints.BOTH;
        gbc_btnRemovePredators.insets = new Insets(5, 5, 5, 5);
        gbc_btnRemovePredators.gridx = 0;
        gbc_btnRemovePredators.gridy = 5;
        add(btnRemovePredators, gbc_btnRemovePredators);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if(button == btnAddObstacle) {
            Simulation.getSimulation().addObstacle();
        } else if(button == btnRemoveObstacles) {
            Simulation.getSimulation().removeObstacles();
        } else if(button == btnAddPredator) {
            Simulation.getSimulation().addPredator();
        } else if(button == btnRemovePredators) {
            Simulation.getSimulation().removePredators();
        }
    }
}
