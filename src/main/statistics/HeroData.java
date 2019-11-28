package main.statistics;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class HeroData implements Statistic
{
	private int id;
	private String displayName = "";
	
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return this.displayName;
	}
	public void setName(String name)
	{
		this.displayName = name;
	}
	
	public void fillParsedStat(List<HeroStatistics> updateStatistic) {
		getParsedStat(updateStatistic, getStringStat());
	}
	
	public void getParsedStat(List<HeroStatistics> updateStatistic, String stat) {
		boolean isEndStat = false;
		Gson g = new Gson();
		Type type = new TypeToken<Map<String, HeroData>>(){}.getType();
		Map<String, HeroData> heroes = g.fromJson(stat, type);
		Iterator<HeroStatistics> iterator = updateStatistic.iterator();
		for(String id : heroes.keySet())
		{
			HeroStatistics heroStatistics = new HeroStatistics();
			HeroData heroData = heroes.get(id);
			if(!isEndStat && iterator.hasNext())
				heroStatistics = iterator.next();
			else {
				isEndStat = true;
				updateStatistic.add(heroStatistics);
			}
			heroStatistics.setId(heroData.getId());
			heroStatistics.setName(heroData.getName());
		}
	}
	
	public String getStringStat() {
		String link = "https://api.stratz.com/api/v1/Hero";
		return StatisticsLoader.GetStatisticsByLink(link);
	}
}
