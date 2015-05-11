package generics.QLearning;


class QHistory {
    private String state, nextState;
    private int action;
    private double reward;

    QHistory(String state, String nextState, int action, double reward) {
        this.state = state;
        this.nextState = nextState;
        this.action = action;
        this.reward = reward;
    }


    public String getState() {
        return state;
    }

    public String getNextState() {
        return nextState;
    }

    public int getAction() {
        return action;
    }

    public double getReward() {
        return reward;
    }
}
