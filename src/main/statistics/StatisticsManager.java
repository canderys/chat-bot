package main.statistics;

import java.util.List;
import java.util.Map;


public class StatisticsManager implements StatisticsGetter
{
	
	public List<HeroStatistics> getFullStat()
	{
		StatHandler statHandler = new StatHandler();
		List<StatInfo> processedStatistics = statHandler.getStatisticsForParse();
		return getFullStat(processedStatistics);
	}
	
	public List<HeroStatistics> getFullStat(List<StatInfo> processedStatistics)
	{
		List<HeroStatistics> finishedStat = null;
		for(StatInfo currentStat : processedStatistics )
		{
			String downloadStat = StatisticsLoader.GetStatisticsByLink(currentStat.link);
			StatisticsParser parser = new StatisticsParser();
			List<Map<String, String>> parsedStat = parser.ParseStatistic(downloadStat, currentStat.searchFields);
			finishedStat = parser.FillStat(parsedStat, currentStat.fieldsConformity, finishedStat);
		}
		calculateOtherFields(finishedStat);
		return finishedStat;
	}
	
	private void calculateOtherFields(List<HeroStatistics> finishedStat)
	{
		int allGamesCounter = 0;
		for(HeroStatistics record : finishedStat)
		{
			allGamesCounter+= record.matchesAmount;
		}
		for(HeroStatistics record : finishedStat)
		{	
			record.name = record.name.toLowerCase();
			record.allGames = allGamesCounter;
			record.pickRate = ((double)record.matchesAmount / allGamesCounter)*1000;
			record.winRate =  ((double)record.winsAmount / record.matchesAmount)*100;
		}
	}
	
	public String getStrStat(List<HeroStatistics> fullStat)
	{
		StringBuilder output = new StringBuilder();
		for(HeroStatistics record : fullStat)
		{
			output.append(record.ToString());
		}
		return output.toString();
	}
}
