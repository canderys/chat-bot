package statisticsTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.statistics.*;

import main.statistics.StatisticsManager;

class StatisticsManagerTest {

	@Test
	void CountHeroesTest() {
		StatisticsManager manager = new StatisticsManager();
		List<HeroStatistics> fullStat =  manager.getFullStat();
		Assert.assertEquals(fullStat.size(), 117);
	}
}
