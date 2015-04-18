package project4;

import generics.ANN.ActivationFunction;
import generics.CTRNN.CTRNN;
import generics.EA.EvolutionaryAlgorithm;
import generics.EA.GenericGenoPhenom;
import generics.EA.selection.AdultSelection;
import generics.EA.selection.ParentSelection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BeerTracker extends JPanel {
    public static final int INFO_BOARD_HEIGHT = 25;
    private static Board board = new Board();

    public BeerTracker() {
        setLayout(null);
        setBackground(null);
    }


    public void runSimulation() {
        int popSize = 200;
        int[] structure = new int[]{5, 18, 9};

        ArrayList<GenericGenoPhenom> init = generateInitialPopulation(popSize, structure);
        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(AdultSelection.MIXING, ParentSelection.TOURNAMENT, 400, 0.5, 0.9, 0.8, init);

        for(int generation=0; generation<50; generation++) {
            ea.runGeneration();
            System.out.println(ea);
        }


        CTRNN ctrnn = (CTRNN) ea.getBestNode().getPhenom();
        simulateBestChild(ctrnn);
    }


    private void simulateBestChild(CTRNN ctrnn) {
        Project4.setUpGUI(this);

        for (int i = 0; i < 600; i++) {
            ctrnn.propagateInput(board.sense());
            double[] output = ctrnn.getOutput();

            board.move(WeightNode.getBestMove(output));
            repaint();

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static ArrayList<GenericGenoPhenom> generateInitialPopulation(int popSize, int[] structure) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();

        for(int i=0; i<popSize; i++) {
            Double[][] weights = new Double[Arrays.stream(structure).sum()-structure[0]][];

            for(int j=1, pos=0; j<structure.length; j++) {
                for (int k = 0; k < structure[j]; k++, pos++) {
                    weights[pos] = new Double[structure[j-1] + 3];
                    for (int l = 0; l < structure[j - 1] + 3; l++)
                        weights[pos][l] = getRandomWeight(l);
                }
            }

            init.add(new WeightNode(structure, weights, ActivationFunction.SIGMOID));
        }

        return init;
    }


    public static Board getBoard() {
        return board.getClone();
    }

    public static double getRandomWeight(int pos) {
        switch (pos) {
            case 0:
                return 1 + Math.random();
            case 1:
                return 1 + 4*Math.random();
            case 2:
                return -10*Math.random();
            default:
                return 10*(Math.random()-0.5);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", Font.PLAIN, 20));
        g.drawString("Tick: " + board.getNumberOfTicks() + " | Intercepted: " + board.getNumberOfIntercepted() + " | Avoided: " + board.getNumberOfAvoided(), 10, 22);

        board.draw(g, 0, INFO_BOARD_HEIGHT);
    }
}
