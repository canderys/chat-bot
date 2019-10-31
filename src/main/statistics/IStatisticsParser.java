package main.statistics;

import java.util.List;
import java.util.Map;

public interface IStatisticsParser {
	public List<Map<String, String>> ParseStatistic(String stats, List<String> fields);
	public List<HeroStatistics> FillStat(List<Map<String, String>> parsedStat, 
			Map<String, String> heroStatFieldsDict,
			List<HeroStatistics> completeStat);
}
