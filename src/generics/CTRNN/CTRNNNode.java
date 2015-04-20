package generics.CTRNN;

import generics.ANN.ANNNode;
import generics.ANN.ActivationFunction;

import java.util.Arrays;

public class CTRNNNode extends ANNNode {
    private double internalState = 0, timeConstant, gain = 0;


    public CTRNNNode(double[] weights) {
        super(ActivationFunction.SIGMOID, Arrays.copyOfRange(weights, 3, weights.length));
        this.timeConstant = weights[0];
        this.gain = weights[1];
        this.setBiasWeight(weights[2]);
    }


    @Override
    public void setInputs(double newInput[]){
        inputs = newInput.clone();
        internalState += (-internalState + getNodeValue()) / timeConstant;
    }

    @Override
    public double getOutput(){
        return activationFunction.compute(gain*internalState);
    }

    public String toString() {
        String out = "";

        out += "Internal state: " + internalState;
        out += "\nGain: " + gain;
        out += "\nTime constant: " + timeConstant;
        out += "\nBias: " + biasWeight;

        out += "\nWeights: " + Arrays.toString(weights);
        out += "\nCurrent output: " + getOutput();
        return out;
    }
}