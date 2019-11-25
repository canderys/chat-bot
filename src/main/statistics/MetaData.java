package main.statistics;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class MetaData implements Statistic
{
	public List<Integer> win;
	public List<Integer> pick;
	
	private int sumIntegers(List<Integer> list)
	{
		int sum = 0;
		if(list != null) {
			for(Integer number : list)
			{
				if(number != null)
					sum+= number;
			}
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

	public void fillParsedStat(List<HeroStatistics> updateStatistic) {
		getParsedStat(updateStatistic, getStringStat());
	}

	public void getParsedStat(List<HeroStatistics> updateStatistic, String stat) {
		boolean isEndStat = false;
		Iterator<HeroStatistics> statIterator = updateStatistic.iterator();
		Gson g = new Gson();
		Type type = new TypeToken<List<MetaData>>(){}.getType();
		List<MetaData> parsedMetaData = g.fromJson(stat, type);
		for(int i = 0;i < parsedMetaData.size();i++)
		{
			HeroStatistics hero = new HeroStatistics();
			if(!isEndStat && statIterator.hasNext())
				hero = statIterator.next();
			else 
			{
				isEndStat = true;
				updateStatistic.add(hero);
			}
			MetaData metaData = parsedMetaData.get(i);
			hero.setWinsAmount(metaData.getAllWins());
			hero.setMathesAmount(metaData.getAllPicks());
		}
		
	}

	public String getStringStat() {
		String link = "https://api.stratz.com/api/v1/Hero/metaTrend";
		return StatisticsLoader.GetStatisticsByLink(link);
	}
}
