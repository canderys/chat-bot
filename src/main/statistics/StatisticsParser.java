package main.statistics;

import java.util.List;

public interface StatisticsParser {
	List<HeroStatistics> getFullParsedStat(List<Statistic> stats);
}
