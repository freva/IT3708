package project3;

import generics.ANN.ANN;
import generics.ANN.ActivationFunction;
import generics.EA.GenericGenoPhenom;

public class WeightNode extends GenericGenoPhenom<Double[], ANN> {
    private ActivationFunction af;
    private Board[] boards;
    private int[] structure;
    private double fitness = Double.MIN_VALUE;

    public WeightNode(int[] structure, Double[] geno, ActivationFunction af, Board[] boards) {
        super(geno);
        this.af = af;
        this.boards = boards;
        this.structure = structure;
    }

    @Override
    public ANN developmentalMethod() {
        return new ANN(structure, convertGenoToPrimitive(), af);
    }

    @Override
    public GenericGenoPhenom<Double[], ANN> crossover(GenericGenoPhenom<Double[], ANN> other) {
        Double[] out = new Double[getGeno().length];
        int crossoverPoint = (int) (Math.random()*getGeno().length);

        System.arraycopy(getGeno(), 0, out, 0, crossoverPoint);
        System.arraycopy(other.getGeno(), crossoverPoint, out, crossoverPoint, getGeno().length - crossoverPoint);

        return new WeightNode(structure, out, af, boards);
    }

    @Override
    public GenericGenoPhenom<Double[], ANN> mutate() {
        Double[] out = getGeno().clone();

        int mutatePoint = (int) (Math.random()*out.length);
        out[mutatePoint] = Math.random();
        return new WeightNode(structure, out, af, boards);
    }

    @Override
    public double fitnessEvaluation() {
        if(fitness == Double.MIN_VALUE) {
            double sumFitness = 0;

            for(Board board : boards) {
                Board newBoard = board.getClone();

                for (int i = 0; i < 60; i++) {
                    getPhenom().propagateInput(newBoard.sense());
                    double[] output = getPhenom().getOutput();

                    newBoard.move(getBestMove(output));
                }

                sumFitness += newBoard.getBoardScore();
            }

            fitness = sumFitness/boards.length;
        }

        return fitness;
    }


    private double[] convertGenoToPrimitive() {
        double[] out = new double[getGeno().length];

        for(int i=0; i<getGeno().length; i++)
            out[i] = getGeno()[i];

        return out;
    }


    public static Board.Direction getBestMove(double[] arr) {
        int pos = 0;

        for(int i=1; i<arr.length; i++) {
            if(arr[i] > arr[pos])
                pos = i;
        }

        return Board.Direction.values()[pos];
    }
}
