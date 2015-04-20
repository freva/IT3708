package generics.CTRNN;

import generics.ANN.ANNNode;
import generics.ANN.ActivationFunction;

import java.util.Arrays;

public class CTRNNNode extends ANNNode {
    private double internalState = 0, timeConstant, gain;


    public CTRNNNode(double[] weights) {
        super(ActivationFunction.SIGMOID, Arrays.copyOfRange(weights, 3, weights.length));
        this.timeConstant = weights[0];
        this.gain = weights[1];
        this.setBiasWeight(weights[2]);
    }


    @Override
    public void setInputs(double newInput[]){
        inputs = newInput.clone();
        internalState += 1/timeConstant * (-internalState + getNodeValue() + biasWeight);
    }

    @Override
    public double getOutput(){
        return activationFunction.compute(gain*internalState);
    }
}