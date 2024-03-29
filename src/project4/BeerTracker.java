package project4;

import generics.CTRNN.CTRNN;
import generics.EA.EvolutionaryAlgorithm;
import generics.EA.GenericGenoPhenom;
import generics.EA.selection.AdultSelection;
import generics.EA.selection.ParentSelection;
import project4.cells.Agent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BeerTracker extends JPanel {
    public static final int INFO_BOARD_HEIGHT = 25;
    private Board board = new Board();
    private static Scenario scenario;

    public BeerTracker(Scenario scenario) {
        setLayout(null);
        setBackground(null);

        BeerTracker.scenario = scenario;
    }


    public void runSimulation() {
        long startTime = System.currentTimeMillis();
        int popSize = 4000;

        ArrayList<GenericGenoPhenom> init = generateInitialPopulation(popSize, scenario.getStructure());
        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(AdultSelection.MIXING, ParentSelection.TOURNAMENT, popSize, 0.9, 0.9, 0.2, init);

        for(int i=0; i<100; i++) {
            ea.runGeneration();
            System.out.println(ea);

            ArrayList<GenericGenoPhenom> adults = ea.getAdults();
            for(GenericGenoPhenom ggp: adults)
                ((WeightNode) ggp).resetFitness();
        }


        System.out.println("Finished in: " + ((System.currentTimeMillis()-startTime)/1000) + "sec");
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

            init.add(new WeightNode(structure, weights));
        }

        return init;
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
        g.drawString("Tick: " + board.getNumberOfTicks() + " | Intercepted: " + board.getNumberOfIntercepted() +
                " | Avoided: " + board.getNumberOfAvoided() + " | Crashed: " + board.getNumberOfCrashes() +
                " | Misses: " + board.getNumberOfMisses(), 10, 22);

        board.draw(g, 0, INFO_BOARD_HEIGHT);
    }

    public static Scenario getScenario() {
        return scenario;
    }


    public enum Scenario {
        STANDARD(new int[]{5, 2, 2}, new double[]{1, 2, -1, -1}), PULL(new int[]{5, 2, 3}, new double[]{1, 1.25, -1.5, -1}), NO_WRAP(new int[]{7, 3, 2}, new double[]{1, 1.5, -1.25, -1.25});

        private int[] structure;
        private double[] rewards;
        Scenario(int[] structure, double[] rewards) {
            this.structure = structure;
            this.rewards = rewards;
        }

        public int[] getStructure() {
            return structure;
        }

        public double[] getRewards() {
            return rewards;
        }

        public int getNewPosition(int curPos, int relChange) {
            if(this == NO_WRAP) return Math.min(Math.max(curPos + relChange, 0), Board.DIMENSION_X-Agent.AGENT_LENGTH);
            else return (curPos + relChange + Board.DIMENSION_X)%Board.DIMENSION_X;
        }
    }
}
