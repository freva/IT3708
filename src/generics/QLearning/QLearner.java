package generics.QLearning;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class QLearner {
    private HashMap<String, Double[]> Q = new HashMap<>();
    private double alpha, gamma;
    private int numActions, queueSize = 10;
    private QGame original, last;

    public QLearner(QGame qGame, int numActions, double alpha, double gamma) {
        this.original = qGame;
        this.numActions = numActions;
        this.alpha = alpha;
        this.gamma = gamma;
    }


    public void runGeneration() {
        LinkedList<String> prevStates = new LinkedList<>();
        last = original.getClone();
        prevStates.add(last.getHash());

        int moves = 0;
        while (!last.isFinished() && moves++ < 1000) {
            int action = selectAction(prevStates.getLast());
            double reward = last.updateGame(action);
            prevStates.add(last.getHash());
            if(prevStates.size() > queueSize) prevStates.pop();

            for(int i= prevStates.size()-1; i>0; i--) {
                updateQ(prevStates.get(i - 1), prevStates.get(i), action, reward);
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
            Arrays.fill(array, 0d);
            Q.put(state, array);
            return (int) (Math.random()*numActions);
        }

        double sum = actions[0], target = Math.random();

        double[] cdf = new double[actions.length];
        for(int i=1; i<actions.length; i++) {
            cdf[i] = actions[i-1] + actions[i];
            sum += actions[i];
        }

        for(int i=0; i<cdf.length; i++)
            if(cdf[i]/sum > target) return i;
        return actions.length-1;
    }


    private void updateQ(String oldState, String newState, int action, double reward) {
        double oldVal = Q.get(oldState)[action];
        double qMax = Q.containsKey(newState) ? getMaxVal(Q.get(newState)) : 0;
        double newVal = oldVal + alpha * (reward + gamma * qMax - oldVal);
        Q.get(oldState)[action] = newVal;
    }


    private double getMaxVal(Double[] arr) {
        double maxVal = Double.MIN_VALUE;
        for (Double anArr : arr)
            if (maxVal < anArr) maxVal = anArr;

        return maxVal;
    }
}
