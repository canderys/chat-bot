package main.statistics;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import main.console.Console;

public class GsonStatisticsParser implements StatisticsParser
{

	
	public List<HeroStatistics> getHeroStat()
	{
		List<HeroStatistics> heroesStat = new ArrayList<HeroStatistics>();
		Gson g = new Gson();
		String link = "https://api.stratz.com/api/v1/Hero";
		String stat = StatisticsLoader.GetStatisticsByLink(link);
		Type type = new TypeToken<Map<String, HeroData>>(){}.getType();
		Map<String, HeroData> heroes = g.fromJson(stat, type);
		for(String id : heroes.keySet())
		{
			HeroData heroData = heroes.get(id);
			HeroStatistics heroStatistics = new HeroStatistics();
			heroStatistics.setId(heroData.getId());
			heroStatistics.setName(heroData.getName());
			heroesStat.add(heroStatistics);
		}
		return heroesStat;
		
	}
	public void getMetaStat(List<HeroStatistics> heroStat)
	{
		Gson g = new Gson();
		String link = "https://api.stratz.com/api/v1/Hero/metaTrend";
		String stat = StatisticsLoader.GetStatisticsByLink(link);
		Type type = new TypeToken<List<MetaData>>(){}.getType();
		List<MetaData> parsedMetaData = g.fromJson(stat, type);
		for(int i = 0;i < heroStat.size();i++)
		{
			HeroStatistics hero = heroStat.get(i);
			MetaData metaData = parsedMetaData.get(i);
			hero.setWinsAmount(metaData.getAllWins());
			hero.setMathesAmount(metaData.getAllPicks());
		}
	}
	
	public void getMapchupStatAllHeroes(List<HeroStatistics> heroStat)
	{
		for(HeroStatistics hero : heroStat)
		{
			MathupData mathupDataHero = getMapchupStat(hero.getId());
			hero.setAdvantageList(mathupDataHero.getAdvantageStat().get(0));
			hero.setDisadvantageList(mathupDataHero.getDisadvantageStat().get(0));
		}
	}
	
	public MathupData getMapchupStat(int id)
	{
		String link = String.format("https://api.stratz.com/api/v1/Hero/%d/matchUp",id);
		String stat = StatisticsLoader.GetStatisticsByLink(link);
		Gson g = new Gson();
		MathupData mathupData = g.fromJson(stat, MathupData.class);
		return mathupData;
	}
	
	public List<HeroStatistics> getParsedStat() {
		GsonStatisticsParser parser = new GsonStatisticsParser();
		List<HeroStatistics> heroStatistics = parser.getHeroStat();
		parser.getMetaStat(heroStatistics);
		parser.getMapchupStatAllHeroes(heroStatistics);
		return heroStatistics;
	}
}
