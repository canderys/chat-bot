package main.requests;

import java.util.Map;

import java.util.HashSet;
import java.util.HashMap;

import main.game.Side;
import main.statistics.HeroStatistics;
import synergy.SynergyCalculator;
import synergy.SynergySummator;
import main.game.DraftGame;
import main.bot.Bot;

import java.util.List;

public class RequestProcessor {
	
	private Map<String, Request> availableRequests;
	private List<HeroStatistics> heroStats;
	private Bot bot;

	
	private Map<Long, Pair<HashSet<HeroStatistics>, HashSet<HeroStatistics>>> usersHeroes = 
			new HashMap<Long, Pair<HashSet<HeroStatistics>, HashSet<HeroStatistics>>>();
	
	private Map<Long, Pair<Long, DraftGame>> currentGames = new HashMap<Long, Pair<Long, DraftGame>>();
	private long currentUserInQueue = -10000000;
	private boolean gameWasFound = false;
	private Map<Long, String> currentSides = new HashMap<Long, String>();
	
	public RequestProcessor(List<HeroStatistics> stat, Bot bot)
	{
		this.bot = bot;
		heroStats = stat;
		availableRequests = new HashMap<String, Request>();
		availableRequests.put("getstat", new HeroStatRequest());
		availableRequests.put("помошь", new HelpRequest());
		availableRequests.put("статистика", new AllStatRequest());
		availableRequests.put("совет", new HeroAdviceRequest());
		availableRequests.put("свет", new SideSelectRequest("radiant"));
		availableRequests.put("тьма", new SideSelectRequest("dire"));
		availableRequests.put("radiant", new HeroSelectRequest("radiant"));
		availableRequests.put("dire", new HeroSelectRequest("dire"));
		availableRequests.put("end", new HeroSelectEndingRequest());
		availableRequests.put("герои", new HeroListRequest());
		availableRequests.put("игра", new DraftGameRequest());
		availableRequests.put("выйти", new LeaveGameRequest());
		availableRequests.put("draft", new DraftHeroRequest());
	}
	
	public RequestResult getRequest(String request, Long chatId, int status)
	{
		if (!usersHeroes.containsKey(chatId))
			clearTeams(chatId);
		String currentRequest = request.toLowerCase();
		currentRequest = currentRequest.trim();
		while (currentRequest.indexOf("  ") != -1)
			currentRequest = currentRequest.replaceAll("  ", " ");
		if (status != 0)
		{
			switch (status)
			{
				case 1:
					if (currentRequest.equalsIgnoreCase("выйти"))
						return availableRequests.get("выйти").getRequestResult(currentRequest, heroStats, this, chatId);
					return availableRequests.get("draft").getRequestResult(currentRequest, heroStats, this, chatId);
				case 2:
					if (currentRequest.equalsIgnoreCase("результат"))
						return availableRequests.get("end").getRequestResult(currentRequest, heroStats, this, chatId);
					if (currentRequest.equalsIgnoreCase("свет") || currentRequest.equalsIgnoreCase("тьма"))
						return availableRequests.get(currentRequest).getRequestResult(currentRequest, heroStats, this, chatId);
					return availableRequests.get(currentSides.get(chatId)).getRequestResult(currentRequest, heroStats, this, chatId);
				case 3:
					return availableRequests.get("getstat").getRequestResult(currentRequest, heroStats, this, chatId);
			}
		}
		if (currentRequest.indexOf(' ') != -1)
		{
			String requestName = currentRequest.substring(0, currentRequest.indexOf(' '));
			if (availableRequests.containsKey(requestName))
			{
				return availableRequests.get(requestName).getRequestResult(currentRequest.substring(currentRequest.indexOf(' ') + 1), heroStats, this, chatId);
			}
			return new RequestResult("Неверный запрос, используйте <b>помощь</b>, чтобы показать доступные запросы.\n", RequestType.ERROR, chatId);
		}
		else
		{
			if (availableRequests.containsKey(currentRequest))
				return availableRequests.get(currentRequest).getRequestResult("", heroStats, this, chatId);
			return new RequestResult("Неверный запрос, используйте <b>помощь</b>, чтобы показать доступные запросы.\n", RequestType.ERROR, chatId);
		}
	}
	
	public void clearTeams(long chatId)
	{
		usersHeroes.put(chatId, new Pair<HashSet<HeroStatistics>, HashSet<HeroStatistics>>(new HashSet<HeroStatistics>(), new HashSet<HeroStatistics>()));
	}
	
	public boolean addHeroToTeam(String team, String hero, long chatId)
	{
		HeroStatistics heroStatistic = new HeroStatistics();
		HeroStatistics heroStat = heroStatistic.findHeroStatisticsWithName(hero, heroStats);
		if (heroStat == null)
			return false;
		if (team.equalsIgnoreCase("dire") && !usersHeroes.get(chatId).snd.contains(heroStat) && usersHeroes.get(chatId).snd.size() < 5)
		{
			usersHeroes.get(chatId).snd.add(heroStat);
			return true;
		}
		if (team.equalsIgnoreCase("radiant") && !usersHeroes.get(chatId).fst.contains(heroStat) && usersHeroes.get(chatId).fst.size() < 5)
		{
			usersHeroes.get(chatId).fst.add(heroStat);
			return true;
		}
		return false;
	}
	
	public String findBestHeroes(long chatId)
	{
		SynergyCalculator synergyCalculator = new SynergySummator();
		HeroStatistics radiantBestHero = synergyCalculator.findBestHero(heroStats, 
				usersHeroes.get(chatId).fst, usersHeroes.get(chatId).snd, Side.Radiant);
		HeroStatistics direBestHero = synergyCalculator.findBestHero(heroStats, 
				usersHeroes.get(chatId).fst, usersHeroes.get(chatId).snd, Side.Dire);
		return "Radiant pick: " + radiantBestHero.getName() + "\nDire pick: " + direBestHero.getName();
	}
	
	public boolean addUserToGameQueue(long chatId)
	{
		if (currentUserInQueue == -10000000)
		{
			currentUserInQueue = chatId;
			gameWasFound = false;
			return false;
		}
		DraftGame game = new DraftGame(heroStats);
		currentGames.put(currentUserInQueue, new Pair<Long, DraftGame>(chatId, game));
		currentGames.put(chatId, new Pair<Long, DraftGame>(currentUserInQueue, game));	
		bot.startDraftGame(new Pair<Long, Pair<Long, DraftGame>>(currentUserInQueue, currentGames.get(currentUserInQueue)));
		currentUserInQueue = -10000000;
		gameWasFound = true;
		return true;
	}
	
	public void clearGameQueue()
	{
		currentUserInQueue = -10000000;
	}
	
	public boolean setGameState(long chatId, String heroName)
	{
		if (currentGames.containsKey(chatId))
		{
			HeroStatistics stat = new HeroStatistics();
			return currentGames.get(chatId).snd.setState(stat.findHeroStatisticsWithName(heroName, heroStats));
		}
		return false;
	}
	
	public String getGameState(long chatId)
	{
		return currentGames.get(chatId).snd.getState(bot.getUserSide(chatId));
	}
	
	public long getAnotherPlayer(long chatId)
	{
		return currentGames.get(chatId).fst;
	}
	
	public boolean gameIsOver(long chatId)
	{
		return currentGames.get(chatId).snd.isEnd();
	}
	
	public void endGame(long chatId)
	{
		long anotherId = getAnotherPlayer(chatId);
		bot.endGame(chatId);
		currentGames.remove(chatId);
		currentGames.remove(anotherId);
	}
	
	public boolean isRightTurn(long chatId)
	{
		return currentGames.get(chatId).snd.getTurn() == bot.getUserSide(chatId);
	}
	
	public boolean isGameFound()
	{
		return gameWasFound;
	}
	
	public boolean userInQueue(long chatId)
	{
		return chatId == currentUserInQueue;
	}
	
	public boolean gameExists(long chatId)
	{
		return currentGames.containsKey(chatId);
	}
	
	public void setSide(long chatId, String side)
	{
		currentSides.put(chatId, side);
	}
}
