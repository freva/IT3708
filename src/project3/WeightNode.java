package project3;

import generics.ANN.ANN;
import generics.ANN.ActivationFunction;
import generics.EA.GenericGenoPhenom;

public class WeightNode extends GenericGenoPhenom<Double[], ANN> {
    public static final double POISON_COST = -5, FOOD_COST = 1;

    private ActivationFunction af;
    private Board board;
    private int[] structure;
    private double fitness = Double.MIN_VALUE;

    public WeightNode(int[] structure, Double[] geno, ActivationFunction af, Board board) {
        super(geno);
        this.af = af;
        this.board = board;
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

        return new WeightNode(structure, out, af, board);
    }

    @Override
    public GenericGenoPhenom<Double[], ANN> mutate() {
        Double[] out = getGeno().clone();

        int mutatePoint = (int) (Math.random()*out.length);
        out[mutatePoint] = Math.random();
        return new WeightNode(structure, out, af, board);
    }

    @Override
    public double fitnessEvaluation() {
        if(fitness == Double.MIN_VALUE) {
            Board newBoard = board.getClone();

            for (int i = 0; i < 60; i++) {
                getPhenom().propagateInput(newBoard.sense());
                double[] output = getPhenom().getOutput();
                int bestOut = getLargestIndex(output);

                newBoard.move(Board.Direction.values()[bestOut]);
            }

            fitness = POISON_COST * newBoard.getPoisonCounter() + FOOD_COST * newBoard.getFoodCounter();
        }

        return fitness;
    }


    private double[] convertGenoToPrimitive() {
        double[] out = new double[getGeno().length];

        for(int i=0; i<getGeno().length; i++)
            out[i] = getGeno()[i];

        return out;
    }


    public static int getLargestIndex(double[] arr) {
        int pos = 0;

        for(int i=1; i<arr.length; i++) {
            if(arr[i] > arr[pos])
                pos = i;
        }

        return pos;
    }
}
