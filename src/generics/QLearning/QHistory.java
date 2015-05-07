package generics.QLearning;

public class QHistory {
    private String state;
    private int action;
    private double reward;

    QHistory(String state, int action, double reward) {
        this.state = state;
        this.action = action;
        this.reward = reward;
    }

    public String getState() {
        return state;
    }

    public int getAction() {
        return action;
    }

    public double getReward() {
        return reward;
    }

    public String toString() {
        return "State: " + state + " | Action: " + action + " | Reward: " + reward;
    }
}
