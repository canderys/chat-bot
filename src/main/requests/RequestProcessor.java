package main.requests;

import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import main.game.Side;
import main.statistics.HeroStatistics;
import synergy.SynergyCalculator;
import synergy.SynergySummator;

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
	
	public RequestResult getRequest(String request)
	{
		String currentRequest = request.toLowerCase();
		currentRequest = currentRequest.trim();
		while (currentRequest.indexOf("  ") != -1)
			currentRequest = currentRequest.replaceAll("  ", " ");
		if (currentRequest.indexOf(' ') != -1)
		{
			String requestName = currentRequest.substring(0, currentRequest.indexOf(' '));
			if (availableRequests.containsKey(requestName))
				return availableRequests.get(requestName).getRequestResult(currentRequest.substring(currentRequest.indexOf(' ') + 1), heroStats, this);
			return new RequestResult("Неверный запрос, напишите <b>help</b>, чтобы показать доступные запросы.\n", RequestType.ERROR);
		}
		else
		{
			if (availableRequests.containsKey(currentRequest))
				return availableRequests.get(currentRequest).getRequestResult("", heroStats, this);
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
		HeroStatistics heroStatistic = new HeroStatistics();
		HeroStatistics heroStat = heroStatistic.findHeroStatisticsWithName(hero, heroStats);
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
		SynergyCalculator synergyCalculator = new SynergySummator();
		HeroStatistics radiantBestHero = synergyCalculator.findBestHero(heroStats, radiantTeam, direTeam, Side.Radiant);
		HeroStatistics direBestHero = synergyCalculator.findBestHero(heroStats, radiantTeam, direTeam, Side.Dire);
		return "Radiant pick: " + radiantBestHero.getName() + "\nDire pick: " + direBestHero.getName();
	}
}
