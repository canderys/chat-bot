package main.requests;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

import main.console.Console;
import main.statistics.HeroStatistics;
import java.util.List;

public class RequestProcessor {
	
	private Map<String, Request> availableRequests;
	private List<HeroStatistics> heroStats;
	
	private HashSet<HeroStatistics> direTeam = new HashSet<HeroStatistics>();
	private HashSet<HeroStatistics> radiantTeam = new HashSet<HeroStatistics>();
	
	public RequestProcessor(List<HeroStatistics> stat)
	{
		heroStats = stat;
		availableRequests = new HashMap<String, Request>();
		availableRequests.put("getstat", new HeroStatRequest());
		availableRequests.put("help", new HelpRequest());
		availableRequests.put("getallstat", new AllStatRequest());
		availableRequests.put("advicehero", new HeroAdviceRequest());
		availableRequests.put("radiant", new HeroSelectRequest("radiant"));
		availableRequests.put("dire", new HeroSelectRequest("dire"));
		availableRequests.put("end", new HeroSelectEndingRequest());
		availableRequests.put("getherolist", new HeroListRequest());
	}
	
	public RequestResult GetRequest(String request)
	{
		String currentRequest = request.toLowerCase();
		currentRequest = currentRequest.trim();
		while (currentRequest.indexOf("  ") != -1)
			currentRequest = currentRequest.replaceAll("  ", " ");
		if (currentRequest.indexOf(' ') != -1)
		{
			String requestName = currentRequest.substring(0, currentRequest.indexOf(' '));
			if (availableRequests.containsKey(requestName))
				return availableRequests.get(requestName).GetRequestResult(currentRequest.substring(currentRequest.indexOf(' ') + 1), heroStats, this);
			return new RequestResult("Неверный запрос, напишите <b>help</b>, чтобы показать доступные запросы.\n", RequestType.ERROR);
		}
		else
		{
			if (availableRequests.containsKey(currentRequest))
				return availableRequests.get(currentRequest).GetRequestResult("", heroStats, this);
			return new RequestResult("Неверный запрос, напишите <b>help</b>, чтобы показать доступные запросы.\n", RequestType.ERROR);
		}
	}
	
	public void clearTeams()
	{
		direTeam.clear();
		radiantTeam.clear();
	}
	
	public boolean addHeroToTeam(String team, String hero)
	{
		HeroStatistics heroStat = findHeroStatisticsWithName(hero);
		if (heroStat == null)
			return false;
		if (team.equalsIgnoreCase("dire") && !direTeam.contains(heroStat) && direTeam.size() < 5)
		{
			direTeam.add(heroStat);
			return true;
		}
		if (team.equalsIgnoreCase("radiant") && !radiantTeam.contains(heroStat) && radiantTeam.size() < 5)
		{
			radiantTeam.add(heroStat);
			return true;
		}
		return false;
	}
	
	public String findBestHeroes()
	{
		HashSet<HeroStatistics> heroes = new HashSet<HeroStatistics>();
		for (int i = 0; i < heroStats.size(); ++i)
			heroes.add(findHeroStatisticsWithName(heroStats.get(i).getName()));
		for (HeroStatistics hero: direTeam)
			heroes.remove(hero);
		for (HeroStatistics hero: radiantTeam)
			heroes.remove(hero);
		HeroStatistics radiantBestHero = null;
		double radiantBestHeroSynergy = -10000;
		HeroStatistics direBestHero = null;
		double direBestHeroSynergy = -10000;
		for (HeroStatistics hero:heroes)
		{
			double synergyRadiant = 0;
			double synergyDire = 0;
			
			for (HeroStatistics anotherHero: radiantTeam)
			{
				synergyRadiant += hero.getSynergyByHeroWith(anotherHero.getId());
				synergyDire += hero.getSynergyByHeroVs(anotherHero.getId());
			}
			
			for (HeroStatistics anotherHero: direTeam)
			{
				synergyRadiant += hero.getSynergyByHeroVs(anotherHero.getId());
				synergyDire += hero.getSynergyByHeroWith(anotherHero.getId());
			}
			
			if (synergyRadiant > radiantBestHeroSynergy)
			{
				radiantBestHeroSynergy = synergyRadiant;
				radiantBestHero = hero;
			}
			
			if (synergyDire > direBestHeroSynergy)
			{
				direBestHeroSynergy = synergyDire;
				direBestHero = hero;
			}
		}
		return "Radiant pick: " + radiantBestHero.getName() + "\nDire pick: " + direBestHero.getName();
	}
	
	private HeroStatistics findHeroStatisticsWithName(String name)
	{
		for (int i = 0; i < heroStats.size(); ++i)
			if (heroStats.get(i).getName().equalsIgnoreCase(name))
				return heroStats.get(i);
		return null;
	}
}
