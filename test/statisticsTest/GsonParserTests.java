package statisticsTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import main.statistics.GsonStatisticsParser;
import main.statistics.HeroStatistics;
import main.statistics.StatisticsParser;

class GsonParserTests {
	@Test
	void testFillStat() {
		StatisticsParser parser = new GsonStatisticsParser();
		List<HeroStatistics> parsedStat = parser.getFullParsedStat(List.of(new TestStatistic(), new TestStatistic()));
		assertEquals(parsedStat.size(), 2);
	}

}
