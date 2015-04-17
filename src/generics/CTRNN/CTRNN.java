package generics.CTRNN;

import generics.ANN.ANN;
import generics.ANN.ANNNode;
import generics.ANN.ActivationFunction;


public class CTRNN extends ANN {

    public CTRNN(int[] structure, double[][] weights, ActivationFunction af) {
        network = new CTRNNNode[structure.length-1][];

        for(int i=1, offset=0; i<structure.length; i++) {
            network[i-1] = new ANNNode[structure[i]];

            for(int j=0; j<structure[i]; j++, offset++) {
                network[i-1][j] = new ANNNode(af, weights[offset]);
            }
        }
    }
}
