package main.statistics;

import java.util.ArrayList;
import java.util.List;

public class MathupData {
	private List<HeroesDryadData> advantage;
	private List<HeroesDryadData> disadvantage;
	
	public MathupData()
	{
		advantage = new ArrayList<HeroesDryadData>();
		disadvantage = new ArrayList<HeroesDryadData>();
	}
	
	public List<HeroesDryadData> getAdvantageStat()
	{
		return this.advantage;
	}
	public List<HeroesDryadData> getDisadvantageStat()
	{
		return this.disadvantage;
	}
}
