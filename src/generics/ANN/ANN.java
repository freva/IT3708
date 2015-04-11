package generics.ANN;


public class ANN {
    private Node[][] network;

    public ANN(int[] structure, ActivationFunction af) {
        network = new Node[structure.length][];

        for(int i=0; i<structure.length; i++) {
            network[i] = new Node[structure[i]];

            for(int j=0; j<structure[i]; j++) {
                network[i][j] = new Node(af);
            }
        }
    }


    public void propagateInput(double[] inputs){
        double[] currentOut = inputs.clone();

        for (Node[] layer : network) {
            double[] newLayer = new double[layer.length];
            for (int j=0; j<layer.length; j++) {

                layer[j].setInputs(currentOut);
                newLayer[j] = layer[j].getOutput();
            }
            currentOut = newLayer;
        }
    }
}