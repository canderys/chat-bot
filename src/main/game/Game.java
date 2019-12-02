package main.game;

public interface Game<T> {
	public String getState();
	public boolean setState(T message);
	public boolean isEnd();
}
