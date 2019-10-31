package main.logic;

import java.util.List;

import main.console.Console;
import main.statistics.HeroStatistics;
import main.statistics.StatisticsManager;

public class Logic {

	public static void main(String[] args)
	{
		StatisticsManager statManager = new StatisticsManager();
		List<HeroStatistics> fullStat = statManager.getFullStat();
		Console.Print(statManager.getStrStat(fullStat));
		//Console.Print(StatisticsLoader.GetStatisticsByLink("https://api.stratz.com/api/v1/Hero/directory/detail"));
	}
}