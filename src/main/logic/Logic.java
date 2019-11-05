package main.logic;

import java.util.List;


import main.requests.RequestProcessor;
import main.console.Console;
import main.statistics.HeroStatistics;
import main.statistics.StatisticsManager;

public class Logic {

	public static void main(String[] args)
	{
		StatisticsManager statManager = new StatisticsManager();
		List<HeroStatistics> fullStat = statManager.getFullStat();
		RequestProcessor requestProcessor = new RequestProcessor(fullStat);
		Console.Print("Hello, I am StatBot. I can help you find Dota 2 heroes statistics. Use help to show available commands");
		while (true)
		{
			String request = Console.ReadLine();
			if (request.compareTo("exit") == 0)
				break;
			Console.Print(requestProcessor.GetRequest(request));
		}
		//Console.Print(statManager.getStrStat(fullStat));
		//Console.Print(StatisticsLoader.GetStatisticsByLink("https://api.stratz.com/api/v1/Hero/directory/detail"));
	}
}