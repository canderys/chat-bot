package main.requests;

import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import main.statistics.HeroStatistics;
import java.util.List;

public class RequestProcessor {
	
	private Map<String, Request> availableRequests;
	private List<HeroStatistics> heroStats;
	
	private List<HeroStatistics> direTeam;
	private List<HeroStatistics> radiantTeam;
	
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
			return new RequestResult("Incorrect request. Type help to show available requests.\n", RequestType.ERROR);
		}
		else
		{
			if (availableRequests.containsKey(currentRequest))
				return availableRequests.get(currentRequest).GetRequestResult("", heroStats, this);
			return new RequestResult("Incorrect request. Type help to show available requests.\n", RequestType.ERROR);
		}
	}
	
	public void clearTeams()
	{
		direTeam.clear();
		radiantTeam.clear();
	}
	
	public void addHeroToTeam(String team, String hero)
	{
		if (team.equalsIgnoreCase("dire") && !direTeam.contains(hero) && direTeam.size() < 5)
		{
			direTeam.add(findHeroStatisticsWithName(hero));
			return;
		}
		if (team.equalsIgnoreCase("radiant") && !radiantTeam.contains(hero) && radiantTeam.size() < 5)
		{
			radiantTeam.add(findHeroStatisticsWithName(hero));
			return;
		}
	}
	
	public String findBestHeroes()
	{
		HashSet<HeroStatistics> heroes = new HashSet<HeroStatistics>();
		for (int i = 0; i < heroStats.size(); ++i)
			heroes.add(findHeroStatisticsWithName(heroStats.get(i).getName()));
		for (int i = 0; i < direTeam.size(); ++i)
			heroes.remove(direTeam.get(i));
		for (int i = 0; i < direTeam.size(); ++i)
			heroes.remove(radiantTeam.get(i));
		HeroStatistics radiantBestHero = null;
		double radiantBestHeroSynergy = -10000;
		HeroStatistics direBestHero = null;
		double direBestHeroSynergy = -10000;
		for (HeroStatistics hero:heroes)
		{
			double synergyRadiant = 0;
			double synergyDire = 0;
			
			for (int i = 0; i < radiantTeam.size(); ++i)
			{
				synergyRadiant += hero.getSynergyByHeroWith(radiantTeam.get(i).getId());
				synergyDire += hero.getSynergyByHeroVs(radiantTeam.get(i).getId());
			}
			
			for (int i = 0; i < direTeam.size(); ++i)
			{
				synergyRadiant += hero.getSynergyByHeroVs(direTeam.get(i).getId());
				synergyDire += hero.getSynergyByHeroWith(direTeam.get(i).getId());
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
