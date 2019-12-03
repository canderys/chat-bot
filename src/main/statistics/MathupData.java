package main.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.google.gson.Gson;

public class MathupData implements Statistic
{
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
	
	public void fillParsedStat(List<HeroStatistics> updateStatistic)
	{
		getParsedStat(updateStatistic, getStatList(updateStatistic));
	}

	public void getParsedStat(List<HeroStatistics> updateStatistic, List<String> stats) {
		boolean isEndStat = false;
		ListIterator<HeroStatistics> iterator = updateStatistic.listIterator();
		for(String stat : stats)
		{
			MathupData mathupDataHero = getMapchupStat(stat);
			HeroStatistics hero = new HeroStatistics();
			if(!isEndStat && iterator.hasNext())
				hero = iterator.next();
			else
			{
				isEndStat = true;
				updateStatistic.add(hero);
			}
			hero.setAdvantageList(mathupDataHero.getAdvantageStat().get(0));
			hero.setDisadvantageList(mathupDataHero.getDisadvantageStat().get(0));
		}
	}
	
	public List<String> getStatList(List<HeroStatistics> updateStatistic){
		List<String> stats = new ArrayList<String>();
		for(HeroStatistics hero : updateStatistic)
		{
			int id = hero.getId();
			String link = String.format("https://api.stratz.com/api/v1/Hero/%d/matchUp",id);
			StatisticsLoader loader = new StatisticsLoader(link);
			stats.add(loader.downloadStatistics());
		}
		return stats;
	}
	
	public MathupData getMapchupStat(String stat)
	{
		Gson g = new Gson();
		MathupData mathupData = g.fromJson(stat, MathupData.class);
		return mathupData;
	}
}
