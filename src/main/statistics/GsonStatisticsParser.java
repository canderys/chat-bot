package main.statistics;

import java.util.ArrayList;
import java.util.List;

public class GsonStatisticsParser implements StatisticsParser
{
	public List<HeroStatistics> getFullParsedStat(List<Statistic> statForParse)
	{
		List<HeroStatistics> fullStat = new ArrayList<HeroStatistics>();
		for(Statistic stat : statForParse)
		{
			stat.fillParsedStat(fullStat);
		}
		return fullStat;
	}
}
