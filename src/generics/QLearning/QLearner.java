package generics.QLearning;

import project5.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class QLearner {
    private static final int numActions = 4, queueSize = 2;
    private HashMap<String, Double[]> Q = new HashMap<>();
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
        probFactor = numIterations/10.0;

        for(iteration=0; iteration<numIterations; iteration++) {
            LinkedList<QHistory> prevStates = new LinkedList<>();

            while (!qGame.isFinished()) {
                String currentState = qGame.getHash();
                int action = selectAction(currentState);
                double reward = qGame.updateGame(action);
                String nextState = qGame.getHash();

                prevStates.add(new QHistory(currentState, nextState, action, reward));
                if (prevStates.size() > queueSize) prevStates.pop();

                double qMax = Q.containsKey(nextState) ? getMaxVal(Q.get(nextState)) : 0;
                double delta = reward + gamma * qMax - Q.get(currentState)[action];

                updateQ(prevStates, delta);
            }

            if (iteration % 100 == 0) System.out.println("Iteration: " + iteration + " | " + qGame);
            if (iteration>=4900) results[iteration-4900] = ((Board) qGame).getNumberOfMoves();

            qGame.reset();
        }

        System.out.println("Avg: " + Arrays.stream(results).average().getAsDouble() + " | Min: " + Arrays.stream(results).min().getAsInt());
    }


    private void updateQ(LinkedList<QHistory> previousStates, double delta) {
        double eligibilityDecay  = 1;

        for (int i = previousStates.size() - 1; i >= 0; i--) {
            double reward = previousStates.get(i).getReward();
            String nextKey = previousStates.get(i).getNextState();
            int index = previousStates.get(i).getAction();
            Double[] actions = Q.get(previousStates.get(i).getState());

            double oldValue = actions[index];
            double qMax = Q.containsKey(nextKey) ? getMaxVal(Q.get(nextKey)) : 0;
            double newValue = oldValue + alpha * (reward + gamma * qMax - oldValue);
            //double newValue = oldValue + alpha*delta*eligibilityDecay;
            actions[index] = newValue;
            eligibilityDecay *= lambda;
        }
    }


    public int selectAction(String state) {
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


    public int getBestAction(String key){
        Double[] actions = Q.get(key);
        if(actions == null) return 4;

        ArrayList<Integer> best = new ArrayList<>();
        double highest = Double.NEGATIVE_INFINITY;

        for(int i = 0; i < numActions; i++){
            if(actions[i] > highest){
                highest = actions[i];
                best.clear();
                best.add(i);
            } else if(actions[i] == highest){
                best.add(i);
            }
        }

        if(best.size() == 1) return best.get(0);
        else return best.get((int) (Math.random()*best.size()));
    }

    private static double getMaxVal(Double[] arr) {
        double maxVal = Double.MIN_VALUE;
        for (Double anArr : arr)
            if (maxVal < anArr) maxVal = anArr;

        return maxVal;
    }
}
