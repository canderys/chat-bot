package main.game;

public class PickInfo {
	private Side side;
	private Action action;
	
	public PickInfo(Side side, Action action)
	{
		this.side = side;
		this.action = action;
	}
	
	public Side getSide()
	{
		return side;
	}
	public Action getAction()
	{
		return action;
	}
		

}
