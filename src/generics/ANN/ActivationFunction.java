package generics.ANN;

public enum ActivationFunction {
    THRESHOLD, SIGMOID, HYPERBOLIC;

    private static double threshold = 0;
    public void setThreshold(double threshold) {
        ActivationFunction.threshold = threshold;
    }


    public double compute(double value) {
        switch (this) {
            case THRESHOLD:
                return value >= threshold ? 1 :  -1;

            case SIGMOID:
                return 1.0/(1 + Math.exp(-value));

            case HYPERBOLIC:
                return 0;

            default:
                return 0;
        }
    }
}
