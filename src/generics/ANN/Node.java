package generics.ANN;

public class Node {
    private ActivationFunction activationFunction;
    private double weights[], inputs[];
    private double biasWeight = 0, biasInput = 0;

    public Node(ActivationFunction activationFunction){
        this.activationFunction = activationFunction;
    }


    public void setInputs(boolean newInput[]){
        inputs = new double[newInput.length];

        for (int i=0; i<newInput.length; i++) {
            inputs[i] = newInput[i] ? 1:0;
        }
    }


    public void setInputs(double newInput[]){
        inputs = newInput.clone();
    }


    public void setWeights(double newWeights[]){
        weights = newWeights.clone();
    }


    public void setBiasInput(double biasIn){
        this.biasInput = biasIn;
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
            sum += inputs[i]* weights[i];

        return sum + biasInput * biasWeight;
    }
}