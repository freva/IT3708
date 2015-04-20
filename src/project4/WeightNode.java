package project4;

import generics.CTRNN.CTRNN;
import generics.EA.GenericGenoPhenom;


public class WeightNode extends GenericGenoPhenom<Double[][], CTRNN> {
    private int[] structure;
    private double fitness = Double.MIN_VALUE;

    public WeightNode(int[] structure, Double[][] geno) {
        super(geno);
        this.structure = structure;
    }

    @Override
    public CTRNN developmentalMethod() {
        return new CTRNN(structure, convertGenoToPrimitive());
    }

    @Override
    public GenericGenoPhenom<Double[][], CTRNN> crossover(GenericGenoPhenom<Double[][], CTRNN> other) {
        Double[][] out = new Double[getGeno().length][];
        int crossoverPoint = (int) (Math.random()*getGeno().length);

        for(int i=0; i<crossoverPoint; i++)
            out[i] = getGeno()[i].clone();

        for(int i=crossoverPoint; i<getGeno().length; i++)
            out[i] = other.getGeno()[i].clone();


        return new WeightNode(structure, out);
    }

    @Override
    public GenericGenoPhenom<Double[][], CTRNN> mutate() {
        Double[][] out = new Double[getGeno().length][];

        int mutateNode = (int) (Math.random()*out.length);
        for(int i=0; i<out.length; i++) {
            out[i] = getGeno()[i].clone();

            if(i == mutateNode) {
                int mutatePos = (int) (Math.random() * out[i].length);
                out[i][mutatePos] = BeerTracker.getRandomWeight(mutatePos);
            }
        }

        return new WeightNode(structure, out);
    }

    @Override
    public double fitnessEvaluation() {
        if(fitness == Double.MIN_VALUE) {
            Board newBoard = BeerTracker.getBoard();

            for (int i = 0; i < 600; i++) {
                getPhenom().propagateInput(newBoard.sense());
                double[] output = getPhenom().getOutput();

                newBoard.move(getBestMove(output));
            }

            fitness = newBoard.getBoardScore();
        }

        return fitness;
    }


    private double[][] convertGenoToPrimitive() {
        double[][] out = new double[getGeno().length][];

        for(int i=0; i<getGeno().length; i++) {
            out[i] = new double[getGeno()[i].length];
            for (int j = 0; j < getGeno()[i].length; j++)
                out[i][j] = getGeno()[i][j];
        }


        return out;
    }


    public static Board.Action getBestMove(double[] arr) {
        if(arr[0] < arr[1]) {
            return Board.Action.getRightMoves()[((int) (arr[1]/(arr[0]+arr[1])/0.25-0.00001))];
        } else {
            return Board.Action.getLeftMoves()[((int) (arr[0]/(arr[0]+arr[1])/0.25-0.00001))];
        }
    }
}
