package generics.QLearning;

public interface QGame {
    void reset();

    boolean isFinished();

    double updateGame(int action);

    int getHash();
}
