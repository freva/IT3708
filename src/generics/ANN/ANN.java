package generics.ANN;

import java.util.Arrays;


public class ANN {
    protected ANNNode[][] network;

    public ANN(int[] structure, double[] weights, ActivationFunction af) {
        network = new ANNNode[structure.length-1][];

        for(int i=1, offset=0; i<structure.length; i++) {
            network[i-1] = new ANNNode[structure[i]];

            for(int j=0; j<structure[i]; j++, offset+=structure[i-1]) {
                network[i-1][j] = new ANNNode(af, Arrays.copyOfRange(weights, offset, offset+structure[i-1]));
            }
        }
    }


    public void propagateInput(double[] inputs){
        double[] currentOut = inputs.clone();

        for (ANNNode[] layer : network) {
            double[] newLayer = new double[layer.length];
            for (int j=0; j<layer.length; j++) {
                layer[j].setInputs(currentOut);
                newLayer[j] = layer[j].getOutput();
            }
            currentOut = newLayer;
        }
    }


    public double[] getOutput() {
        double[] out = new double[network[network.length-1].length];

        for(int i=0; i<network[network.length-1].length; i++) {
            out[i] = network[network.length-1][i].getOutput();
        }

        return out;
    }


    public String toString() {
        String out = "";

        for(int i=0; i<network.length; i++) {
            out += "Layer " + i + ": \n";
            for(int j=0; j<network[i].length; j++) {
                out += "\t" + network[i][j] + "\n";
            }

            out += "\n";
        }

        return out;
    }
}