package main.game;

public interface Game<T> {
	public String getState(int id);
	public boolean setState(T message);
	public boolean isEnd();
	public int getTurn();
}
