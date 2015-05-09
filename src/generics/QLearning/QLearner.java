package generics.QLearning;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class QLearner {
    private HashMap<String, Double[]> Q = new HashMap<>();
    private double alpha, gamma;
    private int numActions, iteration, queueSize = 4;
    private QGame qGame;

    public QLearner(QGame qGame, int numActions, double alpha, double gamma) {
        this.qGame = qGame;
        this.numActions = numActions;
        this.alpha = alpha;
        this.gamma = gamma;
    }


    public void runIteration() {
        LinkedList<QHistory> prevStates = new LinkedList<>();
        iteration++;

        int moves = 0;
        while (!qGame.isFinished() && moves++ < qGame.getStepLimit()) {
            String currentState = qGame.getHash();

            int action = selectAction(currentState);
            double reward = qGame.updateGame(action);
            prevStates.add(new QHistory(currentState, action));

            if(prevStates.size() > queueSize) prevStates.pop();

            String nextState = qGame.getHash();
            double oldVal = Q.get(currentState)[action];
            double qMax = Q.containsKey(nextState) ? getMaxVal(Q.get(nextState)) : 0;
            double delta = reward + gamma * qMax - oldVal;
            double eligibilityDecay = 1;

            for(int i=prevStates.size()-1; i>=0; i--) {
                QHistory oldState = prevStates.get(i);
                oldVal = Q.get(oldState.getState())[oldState.getAction()];
                Q.get(oldState.getState())[oldState.getAction()] = oldVal + alpha * delta * eligibilityDecay;;
                eligibilityDecay *= 0.8;
            }
        }

        if(iteration %100 == 0) System.out.println("Iteration: " + iteration + " | " + qGame);
        qGame.reset();
    }


    public int selectAction(String state) {
        Double[] actions = Q.get(state);
        if(actions == null) {
            Double[] array = new Double[numActions];
            Arrays.fill(array, 0d);
            Q.put(state, array);
            return (int) (Math.random()*numActions);
        }

        int maxPos = 0;
        for(int i=1; i<actions.length; i++) {
            if(actions[maxPos] < actions[i]) maxPos = i;
        }

        if(actions[maxPos] == 0 || Math.random() < Math.exp(-iteration /500)) return (maxPos + (int) (Math.random() * (numActions-1))) % numActions;
        else return maxPos;
    }


    public int getAction(String state) {
        Double[] actions = Q.get(state);
        if(actions == null) return 4;

        int maxPos = 0;
        for(int i=1; i<actions.length; i++) {
            if(actions[maxPos] < actions[i]) maxPos = i;
        }

        return maxPos;
    }

    private static double getMaxVal(Double[] arr) {
        double maxVal = Double.MIN_VALUE;
        for (Double anArr : arr)
            if (maxVal < anArr) maxVal = anArr;

        return maxVal;
    }
}
