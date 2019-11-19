package main.statistics;

import java.util.ArrayList;
import java.util.List;

public class HeroesDryadData {
	private List<PairHeroesData> with;
	public int matchCountWith;
	private List<PairHeroesData> vs;
	public int matchCountVs;

	public List<PairHeroesData> getSynergyListWith()
	{
		return new ArrayList<PairHeroesData>(with);
	}
	
	public List<PairHeroesData> getSynergyListVs()
	{
		return new ArrayList<PairHeroesData>(vs);
	}
	public HeroesDryadData()
	{
		with = new ArrayList<PairHeroesData>();
		vs =  new ArrayList<PairHeroesData>();
	}

}
