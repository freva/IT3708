package generics.QLearning;


class QHistory {
    private int state, nextState;
    private int action;
    private double reward;

    QHistory(int state, int nextState, int action, double reward) {
        this.state = state;
        this.nextState = nextState;
        this.action = action;
        this.reward = reward;
    }


    public int getState() {
        return state;
    }

    public int getNextState() {
        return nextState;
    }

    public int getAction() {
        return action;
    }

    public double getReward() {
        return reward;
    }
}
