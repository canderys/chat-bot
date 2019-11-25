package main.statistics;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import main.console.Console;

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
