package main.statistics;

import java.lang.reflect.Field;
import java.util.List;

import main.console.Console;


public class HeroStatistics {
	private HeroData heroData;
	private double pickRate;
	private double winRate;
	private int matchesAmount;
	private int winsAmount;
	private int allGames;
	private HeroesDryadData advantage;
	private HeroesDryadData disadvantage;
	
	public HeroStatistics findHeroStatisticsWithName(String name, List<HeroStatistics> heroStats)
	{
		for (int i = 0; i < heroStats.size(); ++i) {
			if (heroStats.get(i).getName().equalsIgnoreCase(name))
				return heroStats.get(i);
		}
		return null;
	}
	public int getAllGames()
	{
		return this.allGames;
	}
	public void setAllGames(int allGames)
	{
		this.allGames = allGames;
	}
	public int getWinsAmount()
	{
		return this.winsAmount;
	}
	public void setWinsAmount(int winsAmount)
	{
		this.winsAmount = winsAmount;
	}
	public int getMathesAmount()
	{
		return this.matchesAmount;
	}
	public void setMathesAmount(int mathesAmount)
	{
		this.matchesAmount = mathesAmount;
	}
	
	public double getWinRate()
	{
		return this.winRate;
	}
	public void setWinRate(double winRate)
	{
		this.winRate = winRate;
	}
	public double getPickRate()
	{
		return this.pickRate;
	}
	public void setPickRate(double pickRate)
	{
		this.pickRate = pickRate;
	}
	
	public void setAdvantageList(HeroesDryadData advantage)
	{
		this.advantage = advantage;
	}
	public void setDisadvantageList(HeroesDryadData disadvantage)
	{
		this.disadvantage = disadvantage;
	}
	
	public double getSynergyByHeroWith(int id)
	{
		for(PairHeroesData heroPair : advantage.getSynergyListWith())
		{
			if(heroPair.getSecondtHeroId() == id)
				return heroPair.getSynergy();
		}
		for(PairHeroesData heroPair : disadvantage.getSynergyListWith())
		{
			if(heroPair.getSecondtHeroId() == id)
				return heroPair.getSynergy();
		}
		return 0;
	}
	
	public double getSynergyByHeroVs(int id)
	{
		for(PairHeroesData heroPair : advantage.getSynergyListVs())
		{
			if(heroPair.getSecondtHeroId() == id)
				return heroPair.getSynergy();
		}
		for(PairHeroesData heroPair : disadvantage.getSynergyListVs())
		{
			if(heroPair.getSecondtHeroId() == id)
				return heroPair.getSynergy();
		}
		return 0;
	}
	public void setId(int id)
	{
		this.heroData.setId(id);
	}
	public int getId()
	{
		return this.heroData.getId();
	}
	public String getName()
	{
		return this.heroData.getName();
	}
	public void setName(String name)
	{
		this.heroData.setName(name);
	}
	
	public HeroStatistics()
	{
		this.heroData = new HeroData();
		this.advantage = new HeroesDryadData();
		this.disadvantage = new HeroesDryadData();
	}
	
	public String toString()
	{
		String output = "";
		String separator = System.lineSeparator();
		output+= "id: " + this.getId() + separator;
		output+= "name: " + this.getName() + separator;
		output+= "wins: " + this.getWinsAmount() + separator;
		output+= "games: " + this.getMathesAmount() + separator;
		output+= "winrate: " + this.getWinRate() + separator;
		output+= "pickrate: " + this.getPickRate() + separator;
		return output;
	}
}
