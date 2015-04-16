package generics.CTRNN;

import generics.ANN.ANNNode;
import generics.ANN.ActivationFunction;

import java.util.Arrays;

public class CTRNNNode extends ANNNode {
    private double internalState = 0, timeConstant, gain;


    public CTRNNNode(ActivationFunction activationFunction, double[] weights, double timeConstant, double gain) {
        super(activationFunction, weights);
        this.timeConstant = timeConstant;
        this.gain = gain;
    }


    @Override
    public void setInputs(double newInput[]){
        inputs = newInput.clone();
        internalState += 1/timeConstant * (-internalState + getNodeValue() + biasWeight);
    }

    @Override
    public double getOutput(){
        return activationFunction.compute(gain*getNodeValue());
    }
}