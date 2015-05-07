package generics.QLearning;

import project5.Board;

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
        LinkedList<QHistory> prevStates = new LinkedList<>();
        last = original.getClone();

        int moves = 0;
        while (!last.isFinished() && moves++ < 2000) {
            //((Board) last).printBoard();

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

            /*System.out.println(prevStates.getLast());
            for (QHistory prevState : prevStates)
                System.out.print(Q.get(prevState.getState())[prevState.getAction()] + ", ");
            System.out.println(Arrays.toString(Q.get(currentState)) + "\n");*/
        }
/*
        for (QHistory prevState : prevStates)
            System.out.println(prevState);

        System.out.println();
        for(String key: Q.keySet())
            System.out.println(key + ": " + Arrays.toString(Q.get(key)));
        System.exit(0);*/
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
