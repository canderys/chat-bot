package synergyTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.console.Console;
import main.game.Side;
import main.statistics.HeroStatistics;
import main.statistics.StatisticsGetter;
import main.statistics.StatisticsManager;
import synergy.SynergyCalculator;
import synergy.SynergySummator;

class TestSynergySummator {
	private HeroStatistics heroStatObject = new HeroStatistics();
	private SynergyCalculator synergyCalculator;
	private StatisticsGetter statGetter;
	List<HeroStatistics> heroStat;
	@BeforeEach
	void setUp() throws Exception {
		heroStatObject = new HeroStatistics();
		synergyCalculator = new SynergySummator();
		statGetter = new StatisticsManager(".\\test\\synergyTest\\testStat.txt");
		heroStat = statGetter.getFullStat();
	}

	@Test
	void test() {
		HashSet<HeroStatistics> radiantTeam = new HashSet<HeroStatistics>();
		HashSet<HeroStatistics> direTeam = new HashSet<HeroStatistics>();
		radiantTeam.add(heroStatObject.findHeroStatisticsWithName("luna", heroStat));
		radiantTeam.add(heroStatObject.findHeroStatisticsWithName("axe", heroStat));
		radiantTeam.add(heroStatObject.findHeroStatisticsWithName("vengeful spirit", heroStat));
		radiantTeam.add(heroStatObject.findHeroStatisticsWithName("templar assassin", heroStat));
		direTeam.add(heroStatObject.findHeroStatisticsWithName("snapfire", heroStat));
		direTeam.add(heroStatObject.findHeroStatisticsWithName("earthshaker", heroStat));
		direTeam.add(heroStatObject.findHeroStatisticsWithName("morphling", heroStat));
		direTeam.add(heroStatObject.findHeroStatisticsWithName("storm spirit", heroStat));
		HeroStatistics bestHeroRadiant = synergyCalculator.findBestHero(heroStat, radiantTeam, direTeam, Side.Radiant);
		HeroStatistics bestHeroDire = synergyCalculator.findBestHero(heroStat, radiantTeam, direTeam, Side.Dire);
		assertEquals("drow ranger", bestHeroRadiant.getName());
		assertEquals("treant protector", bestHeroDire.getName());
	}

}
