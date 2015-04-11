package project1.gui;

import project1.simulation.objects.Boid;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Sliders extends JPanel implements ChangeListener {
    private JSlider separationWeight, alignmentWeight, cohesionWeight;

    public Sliders() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{170};
        gridBagLayout.rowHeights = new int[]{20, 40, 20, 20, 40, 20, 20, 40};
        setLayout(gridBagLayout);


        JLabel separationLabel = new JLabel("Separation weight", JLabel.CENTER);
        separationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc_separationLabel = new GridBagConstraints();
        gbc_separationLabel.fill = GridBagConstraints.BOTH;
        gbc_separationLabel.gridx = 0;
        gbc_separationLabel.gridy = 0;
        add(separationLabel, gbc_separationLabel);

        separationWeight = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        separationWeight.addChangeListener(this);
        separationWeight.setName("separation");
        separationWeight.setMajorTickSpacing(20);
        separationWeight.setMinorTickSpacing(5);
        separationWeight.setPaintTicks(true);
        separationWeight.setPaintLabels(true);
        GridBagConstraints gbc_separationWeight = new GridBagConstraints();
        gbc_separationWeight.fill = GridBagConstraints.BOTH;
        gbc_separationWeight.insets = new Insets(5, 5, 5, 5);
        gbc_separationWeight.gridx = 0;
        gbc_separationWeight.gridy = 1;
        add(separationWeight, gbc_separationWeight);


        JLabel alignmentLabel = new JLabel("Alignment weight", JLabel.CENTER);
        alignmentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc_alignmentLabel = new GridBagConstraints();
        gbc_alignmentLabel.fill = GridBagConstraints.BOTH;
        gbc_alignmentLabel.gridx = 0;
        gbc_alignmentLabel.gridy = 3;
        add(alignmentLabel, gbc_alignmentLabel);

        alignmentWeight = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        alignmentWeight.addChangeListener(this);
        alignmentWeight.setMajorTickSpacing(20);
        alignmentWeight.setMinorTickSpacing(5);
        alignmentWeight.setPaintTicks(true);
        alignmentWeight.setPaintLabels(true);
        GridBagConstraints gbc_alignmentWeight = new GridBagConstraints();
        gbc_alignmentWeight.fill = GridBagConstraints.BOTH;
        gbc_alignmentWeight.insets = new Insets(5, 5, 5, 5);
        gbc_alignmentWeight.gridx = 0;
        gbc_alignmentWeight.gridy = 4;
        add(alignmentWeight, gbc_alignmentWeight);


        JLabel cohesionLabel = new JLabel("Cohesion weight", JLabel.CENTER);
        cohesionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc_cohesionLabel = new GridBagConstraints();
        gbc_cohesionLabel.fill = GridBagConstraints.BOTH;
        gbc_cohesionLabel.gridx = 0;
        gbc_cohesionLabel.gridy = 6;
        add(cohesionLabel, gbc_cohesionLabel);

        cohesionWeight = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        cohesionWeight.addChangeListener(this);
        cohesionWeight.setMajorTickSpacing(20);
        cohesionWeight.setMinorTickSpacing(5);
        cohesionWeight.setPaintTicks(true);
        cohesionWeight.setPaintLabels(true);
        GridBagConstraints gbc_cohesionWeight = new GridBagConstraints();
        gbc_cohesionWeight.fill = GridBagConstraints.BOTH;
        gbc_cohesionWeight.insets = new Insets(5, 5, 5, 5);
        gbc_cohesionWeight.gridx = 0;
        gbc_cohesionWeight.gridy = 7;
        add(cohesionWeight, gbc_cohesionWeight);
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();

        if(slider == separationWeight) {
            Boid.setSeparationWeight(slider.getValue()/25.0);
        } else if(slider == alignmentWeight) {
            Boid.setAlignmentWeight(slider.getValue()/100.0);
        } else if(slider == cohesionWeight) {
            Boid.setCohesionWeight(slider.getValue()/1250.0);
        }
    }
}
