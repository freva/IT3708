package project3;

import generics.ANN.ANN;
import generics.ANN.ActivationFunction;
import generics.EA.EvolutionaryAlgorithm;
import generics.EA.GenericGenoPhenom;
import generics.EA.selection.AdultSelection;
import generics.EA.selection.ParentSelection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Flatland extends JPanel {
    public static final int INFO_BOARD_HEIGHT = 25;
    private EvolutionaryAlgorithm ea;
    private Board board;


    public Flatland() {
        setBackground(Color.DARK_GRAY);
        setLayout(null);
    }


    public void runSimulation(int boardSize) {
        Board simulationBoard = new Board(boardSize, 1.0/3, 1.0/3);

        int popSize = 200, probSize = 18;
        int[] structure = new int[]{6, 3};
        ActivationFunction.setThreshold(0.5);

        ArrayList<GenericGenoPhenom> init = generateInitialPopulation(popSize, probSize, structure, simulationBoard);
        ea = new EvolutionaryAlgorithm(AdultSelection.MIXING, ParentSelection.TOURNAMENT, 200, 0.5, 0.9, 0.8, init);
        for(int generation=0; generation<20; generation++) {
            board = simulationBoard.getClone();
            ea.runGeneration();
            System.out.println(ea);

            ANN ann = (ANN) ea.getBestNode().getPhenom();
            simulateBestChild(ann);
        }
    }


    private void simulateBestChild(ANN ann) {
        for(int i=0; i<60; i++) {
            ann.propagateInput(board.sense());
            double[] output = ann.getOutput();

            board.move(WeightNode.getBestMove(output));
            repaint();

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


    public void paint(Graphics g) {
        super.paint(g);

        if(board == null || ea == null) return;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", Font.PLAIN, 20));
        g.drawString("Generation: " + ea.getGeneration() + " | Moves: " + board.getNumberOfMoves() + " | Food: " + board.getFoodEaten() + " | Poison: " + board.getPoisonEaten(), 10, 22);
        for (int i=0; i<board.getSize(); i++) {
            for (int j=0; j<board.getSize(); j++) {
                if (board.getCell(i, j) != null)
                    board.getCell(i, j).draw(g, i*Project3.CELL_SIZE, INFO_BOARD_HEIGHT+j*Project3.CELL_SIZE);
            }
        }
    }
}
