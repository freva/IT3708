package project3;

import generics.ANN.ANN;
import generics.ANN.ActivationFunction;
import generics.EA.EvolutionaryAlgorithm;
import generics.EA.GenericGenoPhenom;
import generics.EA.selection.AdultSelection;
import generics.EA.selection.ParentSelection;

import javax.swing.*;
import java.util.ArrayList;


public class Project3 extends JPanel {
    public static final int BOARD_DIMENSION = 10, CELL_SIZE = 80;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Flatland");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(CELL_SIZE* BOARD_DIMENSION + frame.getInsets().left + frame.getInsets().right, CELL_SIZE* BOARD_DIMENSION + frame.getInsets().top + frame.getInsets().bottom);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Flatland flatland = new Flatland();
        frame.add(flatland);

        runSimulation(flatland);
    }


    private static void runSimulation(Flatland flatland) {
        Board board = new Board(BOARD_DIMENSION, 1.0/3, 1.0/3);

        int popSize = 200, probSize = 18;
        int[] structure = new int[]{6, 3};
        ActivationFunction.setThreshold(0.5);

        ArrayList<GenericGenoPhenom> init = generateInitialPopulation(popSize, probSize, structure, board);
        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(AdultSelection.MIXING, ParentSelection.TOURNAMENT, 1600, 0.5, 0.9, 0.8, init);
        for(int generation=0; generation<20; generation++) {
            ea.runGeneration();
            System.out.println(ea);

            ANN ann = (ANN) ea.getBestNode().getPhenom();
            simulateBestChild(ann, flatland, board.getClone());
        }
    }


    private static void simulateBestChild(ANN ann, Flatland flatland, Board board) {
        flatland.setBoard(board);
        flatland.repaint();

        for(int i=0; i<60; i++) {
            ann.propagateInput(board.sense());
            double[] output = ann.getOutput();
            int bestOut = WeightNode.getLargestIndex(output);

            board.move(Board.Direction.values()[bestOut]);
            flatland.repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static ArrayList<GenericGenoPhenom> generateInitialPopulation(int popSize, int probSize, int[] structure, Board board) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();

        for(int i=0; i<popSize; i++) {
            Double[] weights = new Double[probSize];

            for(int j=0; j<probSize; j++)
                weights[j] = Math.random();

            init.add(new WeightNode(structure, weights, ActivationFunction.SIGMOID, board));
        }

        return init;
    }
}
