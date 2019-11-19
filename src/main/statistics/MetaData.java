package main.statistics;

import java.util.List;

public class MetaData {
	public List<Integer> win;
	public List<Integer> pick;
	
	private int sumIntegers(List<Integer> list)
	{
		int sum = 0;
		for(int number : list)
		{
			sum+= number;
		}
		return sum;
	}
	
	public int getAllWins()
	{
		return this.sumIntegers(this.win);
	}
	
	public int getAllPicks()
	{
		return this.sumIntegers(this.pick);
	}
}
