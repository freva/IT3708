package generics.CTRNN;

import java.util.Arrays;

public class CTRNN {
    protected CTRNNNode[][] network;

    public CTRNN(int[] structure, double[][] weights) {
        network = new CTRNNNode[structure.length-1][];

        for(int i=1, offset=0; i<structure.length; i++) {
            network[i-1] = new CTRNNNode[structure[i]];

            for(int j=0; j<structure[i]; j++, offset++) {
                network[i-1][j] = new CTRNNNode(weights[offset]);
            }
        }
    }


    public void propagateInput(double[] inputs){
        double[] currentOut = inputs.clone();

        for (CTRNNNode[] layer : network) {
            double[] currentOutWithSelfOutputs = Arrays.copyOf(currentOut, currentOut.length+layer.length);
            for (int j=currentOut.length; j<currentOutWithSelfOutputs.length; j++)
                currentOutWithSelfOutputs[j] = layer[j-currentOut.length].getLastOutput();

            double[] newLayer = new double[layer.length];
            for (int j=0; j<layer.length; j++) {
                layer[j].setInputs(currentOutWithSelfOutputs);
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
            out += "\n\nLayer " + i + "\n";
            for(int j=0; j<network[i].length; j++) {
                out += "\n\nNode " + j + "\n" + network[i][j];
            }
        }

        return out;
    }
}
