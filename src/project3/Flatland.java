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
    private static Board[] boards;
    private Board board;
    private int boardNum;


    public Flatland() {
        setLayout(null);
        setBackground(null);
    }


    public void runSimulation(int numBoards, boolean isStatic) {
        int popSize = 200, probSize = 18;
        int[] structure = new int[]{6, 3};

        ArrayList<GenericGenoPhenom> init = generateInitialPopulation(popSize, probSize, structure);
        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(AdultSelection.MIXING, ParentSelection.TOURNAMENT, 400, 0.5, 0.9, 0.8, init);

        for(int generation=0; generation<50; generation++) {
            if(boards == null || !isStatic) {
                boards = new Board[numBoards];
                for(int i=0; i<numBoards; i++)
                    boards[i] = new Board(10, 1.0/3, 1.0/3);

                ArrayList<GenericGenoPhenom> adults = ea.getAdults();
                for(GenericGenoPhenom ggp: adults)
                    ((WeightNode) ggp).resetFitness();
            }

            ea.runGeneration();
            System.out.println(ea);
        }


        ANN ann = (ANN) ea.getBestNode().getPhenom();
        simulateBestChild(ann);
    }


    private void simulateBestChild(ANN ann) {
        Project3.setUpGUI(this);

        for(int j=0; j<boards.length; j++) {
            board = boards[j].getClone();
            boardNum = j+1;

            for (int i = 0; i < 60; i++) {
                ann.propagateInput(board.sense());
                double[] output = ann.getOutput();

                board.move(WeightNode.getBestMove(output));
                repaint();

                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static ArrayList<GenericGenoPhenom> generateInitialPopulation(int popSize, int probSize, int[] structure) {
        ArrayList<GenericGenoPhenom> init = new ArrayList<>();

        for(int i=0; i<popSize; i++) {
            Double[] weights = new Double[probSize];

            for(int j=0; j<probSize; j++)
                weights[j] = Math.random();

            init.add(new WeightNode(structure, weights, ActivationFunction.SIGMOID));
        }

        return init;
    }


    public static Board[] getBoards() {
        return boards;
    }


    public void paint(Graphics g) {
        super.paint(g);

        if(board == null) return;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", Font.PLAIN, 20));
        g.drawString("Board: " + boardNum + " | Moves: " + board.getNumberOfMoves() + " | Food: " + board.getFoodEaten() + " | Poison: " + board.getPoisonEaten(), 10, 22);
        for (int i=0; i<board.getSize(); i++) {
            for (int j=0; j<board.getSize(); j++) {
                if (board.getCell(i, j) != null)
                    board.getCell(i, j).draw(g, i*Project3.CELL_SIZE, INFO_BOARD_HEIGHT+j*Project3.CELL_SIZE);
            }
        }
    }
}
