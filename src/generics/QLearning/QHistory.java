package generics.QLearning;

public class QHistory {
    private String state;
    private int action;

    QHistory(String state, int action) {
        this.state = state;
        this.action = action;
    }

    public String getState() {
        return state;
    }

    public int getAction() {
        return action;
    }

    public String toString() {
        return "State: " + state + " | Action: " + action;
    }
}
