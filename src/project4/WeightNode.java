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

        double mutateChance = out.length/5.0;
        for(int i=0; i<out.length; i++) {
            out[i] = getGeno()[i].clone();

            if(Math.random() < mutateChance) {
                int mutatePos = (int) (Math.random() * out[i].length);
                out[i][mutatePos] = BeerTracker.getRandomWeight(mutatePos);
            }
        }

        return new WeightNode(structure, out);
    }

    @Override
    public double fitnessEvaluation() {
        if(fitness == Double.MIN_VALUE) {
            Board newBoard = new Board();

            for (int i = 0; i < 600; i++) {
                getPhenom().propagateInput(newBoard.sense());
                double[] output = getPhenom().getOutput();

                newBoard.move(getBestMove(output));
                newBoard.tick();
            }

            fitness = newBoard.getBoardScore();
        }

        return fitness;
    }


    public void resetFitness() {
        fitness = Double.MIN_VALUE;
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
        //return Board.Action.values()[4 + (int) (Math.signum(arr[0]-0.5) * Math.exp(-arr[1]*1000)/0.2+0.001)];

        int pos = 0;

        for(int i=1; i<arr.length; i++) {
            if(arr[i] > arr[pos])
                pos = i;
        }

        switch (pos) {
            case 0:
                if (arr[pos] > 0.6)
                    return Board.Action.ONE_TO_LEFT;
                else if (arr[pos] > 0.37)
                    return Board.Action.TWO_TO_LEFT;
                else
                    return Board.Action.THREE_TO_LEFT;

            case 1:
                if (arr[pos] > 0.6)
                    return Board.Action.ONE_TO_RIGHT;
                else if (arr[pos] > 0.37)
                    return Board.Action.TWO_TO_RIGHT;
                else
                    return Board.Action.THREE_TO_RIGHT;

            case 2:
                return Board.Action.PULL;
            default:
                return Board.Action.NONE;
        }
    }
}
