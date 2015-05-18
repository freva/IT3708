package generics.QLearning;

import project5.Board;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;


public class QLearner {
    private static final int numActions = 4, queueSize = 10;
    private HashMap<Integer, Double[]> Q = new HashMap<>();
    private double alpha, gamma, lambda, probFactor;
    private QGame qGame;
    private int iteration;
    private int[] results = new int[100];

    public QLearner(QGame qGame, double alpha, double gamma, double lambda) {
        this.qGame = qGame;
        this.alpha = alpha;
        this.gamma = gamma;
        this.lambda = lambda;
    }


    public void train(int numIterations) {
        probFactor = numIterations/6.0;

        for(iteration=0; iteration<numIterations; iteration++) {
            LinkedList<QHistory> prevStates = new LinkedList<>();

            while (!qGame.isFinished()) {
                int currentState = qGame.getHash();
                int action = selectAction(currentState);
                double reward = qGame.updateGame(action);
                int nextState = qGame.getHash();

                prevStates.add(new QHistory(currentState, nextState, action, reward));
                if (prevStates.size() > queueSize) prevStates.pop();

                updateQ(prevStates);
            }

            results[iteration%100] = ((Board) qGame).getNumberOfMoves();
            if (iteration % 100 == 99) System.out.println("Iteration: " + (iteration+1) + " | Avg: " + Arrays.stream(results).average().getAsDouble() + " | Min: " + Arrays.stream(results).min().getAsInt());

            qGame.reset();
        }
    }


    private void updateQ(LinkedList<QHistory> previousStates) {
        double eligibilityDecay = 1;

        for (QHistory state: previousStates) {
            double reward = state.getReward();
            int nextKey = state.getNextState();
            int index = state.getAction();
            Double[] actions = Q.get(state.getState());

            double oldValue = actions[index];
            double qMax = Q.containsKey(nextKey) ? getMaxVal(Q.get(nextKey)) : 0;

            actions[index] = oldValue + alpha * (reward + gamma * qMax - oldValue) * eligibilityDecay;
            eligibilityDecay *= lambda;
        }
    }


    public int selectAction(int state) {
        Double[] actions = Q.get(state);
        if(actions == null) {
            Double[] array = new Double[numActions];
            Arrays.fill(array, 0d);
            Q.put(state, array);
            return (int) (Math.random()*numActions);
        }

        int maxPos = getBestAction(state);
        if(Math.random() < Math.exp(-1 -iteration /probFactor)) return (maxPos + (int) (Math.random() * (numActions-1))) % numActions;
        else return maxPos;
    }


    public int getBestAction(int key){
        Double[] actions = Q.get(key);
        if(actions == null) return 4;

        int index = 0;
        for(int i = 1; i < numActions; i++){
            if(actions[i] > actions[index]){
                index = i;
            } else if(actions[i].equals(actions[index])){
                if(Math.random() < 0.5) index = i;
            }
        }

        return index;
    }

    private static double getMaxVal(Double[] arr) {
        double maxVal = Double.MIN_VALUE;
        for (Double anArr : arr)
            if (maxVal < anArr) maxVal = anArr;

        return maxVal;
    }
}
