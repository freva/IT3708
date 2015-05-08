package generics.QLearning;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class QLearner {
    private HashMap<String, Double[]> Q = new HashMap<>();
    private double alpha, gamma;
    private int numActions, queueSize = 20;
    private QGame original, last;

    public QLearner(QGame qGame, int numActions, double alpha, double gamma) {
        this.original = qGame;
        this.numActions = numActions;
        this.alpha = alpha;
        this.gamma = gamma;
    }


    public void runGeneration() {
        LinkedList<QHistory> prevStates = new LinkedList<>();
        last = original.getClone();

        int moves = 0;
        while (!last.isFinished() && moves++ < last.getStepLimit()) {
            String currentState = last.getHash();

            int action = selectAction(currentState);
            double reward = last.updateGame(action);
            prevStates.add(new QHistory(currentState, action, reward));

            if(prevStates.size() > queueSize) prevStates.pop();

            String nextState = last.getHash();
            for(int i=prevStates.size()-1; i>=0; i--) {
                updateQ(prevStates.get(i), nextState);
                nextState = prevStates.get(i).getState();
            }
        }
    }


    public QGame getLastGame() {
        return last;
    }


    public int selectAction(String state) {
        Double[] actions = Q.get(state);
        if(actions == null) {
            Double[] array = new Double[numActions];
            Arrays.fill(array, 10d);
            Q.put(state, array);
            return (int) (Math.random()*numActions);
        }

        int maxPos = 0;
        for(int i=1; i<actions.length; i++) {
            if(actions[maxPos] < actions[i]) maxPos = i;
        }

        if(Math.random() < 0.2) return (maxPos + (int) (Math.random() * (numActions-1))) % numActions;
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

    private void updateQ(QHistory oldState, String newState) {
        double oldVal = Q.get(oldState.getState())[oldState.getAction()];
        double qMax = Q.containsKey(newState) ? getMaxVal(Q.get(newState)) : 0;
        double newVal = oldVal + alpha * (oldState.getReward() + gamma * qMax - oldVal);
        Q.get(oldState.getState())[oldState.getAction()] = newVal;
    }


    private double getMaxVal(Double[] arr) {
        double maxVal = Double.MIN_VALUE;
        for (Double anArr : arr)
            if (maxVal < anArr) maxVal = anArr;

        return maxVal;
    }
}
