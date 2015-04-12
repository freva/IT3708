package generics.ANN;

public enum ActivationFunction {
    THRESHOLD, SIGMOID, HYPERBOLIC;

    private static double threshold = 0;
    public static void setThreshold(double threshold) {
        ActivationFunction.threshold = threshold;
    }


    public double compute(double value) {
        switch (this) {
            case THRESHOLD:
                return value >= threshold ? 1 :  0;

            case SIGMOID:
                return 1.0/(1 + Math.exp(-value));

            case HYPERBOLIC:
                return (Math.exp(2*value)-1)/(Math.exp(2*value)+1);

            default:
                return 0;
        }
    }
}
