package generics.ANN;

import java.util.Arrays;

public class Node {
    private ActivationFunction activationFunction;
    private double[] weights, inputs;
    private double biasWeight = 0;


    public Node(ActivationFunction activationFunction, double[] weights) {
        this.activationFunction = activationFunction;
        this.weights = weights;
    }


    public void setInputs(double newInput[]){
        inputs = newInput.clone();
    }


    public void setWeights(double newWeights[]){
        weights = newWeights.clone();
    }


    public void setBiasWeight(double biasW){
        this.biasWeight = biasW;
    }


    public double getOutput(){
        return activationFunction.compute(getNodeValue());
    }


    public double getNodeValue(){
        double sum = 0;

        for (int i=0; i<inputs.length; i++)
            sum += inputs[i] * weights[i];

        return sum + biasWeight;
    }


    public String toString() {
        return "Node: " + Arrays.toString(weights) + " " + Arrays.toString(inputs);
    }
}