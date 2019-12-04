package statisticsTest;

import java.util.List;
import main.statistics.HeroStatistics;
import main.statistics.Statistic;

public class TestStatistic implements Statistic
{
	public void fillParsedStat(List<HeroStatistics> updateStatistic) {
		updateStatistic.add(new HeroStatistics());
	}

}
