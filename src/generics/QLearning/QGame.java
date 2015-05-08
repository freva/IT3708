package generics.QLearning;

public interface QGame {
    public void reset();

    public boolean isFinished();

    public double updateGame(int action);

    public String getHash();

    public int getStepLimit();
}
