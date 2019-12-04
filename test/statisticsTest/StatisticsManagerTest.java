package statisticsTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.statistics.HeroStatistics;
import main.statistics.StatisticsManager;

class StatisticsManagerTest {
	
	private StatisticsManager statManager;
	@BeforeEach
	void setUp() {
		statManager = new StatisticsManager();
	}

	@Test
	void testCompleteAnotherFields() {
		HeroStatistics herostatAxe = new HeroStatistics();
		herostatAxe.setId(1);
		herostatAxe.setName("Axe");
		herostatAxe.setMathesAmount(100);
		herostatAxe.setWinsAmount(50);
		HeroStatistics herostatLuna = new HeroStatistics();
		herostatLuna.setId(10);
		herostatLuna.setName("Luna");
		herostatLuna.setMathesAmount(80);
		herostatLuna.setWinsAmount(30);
		List<HeroStatistics> heroStatistics = new ArrayList<HeroStatistics>();
		heroStatistics.add(herostatAxe);
		heroStatistics.add(herostatLuna);
		statManager.calculateOtherFields(heroStatistics);
		assertEquals(heroStatistics.get(0).getWinRate(), 50.0);
		assertEquals(heroStatistics.get(0).getPickRate(), 555,555555555);
		
	}

}
