package generics.QLearning;

public interface QGame {
    public QGame getClone();

    public boolean isFinished();

    public double updateGame(int action);

    public String getHash();

    public int getStepLimit();
}
