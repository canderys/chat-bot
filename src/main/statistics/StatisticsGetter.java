package main.statistics;

import java.util.List;

public interface StatisticsGetter {
	public List<HeroStatistics> getFullStat();
	public String getStrStat(List<HeroStatistics> fullStat);
}
