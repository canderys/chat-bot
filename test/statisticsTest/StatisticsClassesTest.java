package statisticsTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.statistics.HeroData;
import main.statistics.HeroStatistics;
import main.statistics.MathupData;
import main.statistics.MetaData;
import main.statistics.StatisticsLoader;

class StatisticsClassesTest {
	@Test
	void heroDataFillTest() {
		HeroData heroData = new HeroData();
		List<HeroStatistics> heroStats = new ArrayList<HeroStatistics>();
		heroStats.add(new HeroStatistics());
		String testStat = "{\"1\":{\"id\":1,\"name\":\"npc_dota_hero_antimage\",\"displayName\":\"Anti-Mage\"},"
				+"\"2\":{\"id\":2,\"name\":\"puck\",\"displayName\":\"puck\"}}";
		heroData.getParsedStat(heroStats, testStat);
		assertEquals(heroStats.get(0).getId(), 1);
		assertEquals(heroStats.get(0).getName(), "Anti-Mage");
		assertEquals(heroStats.get(1).getName(), "puck");
		assertEquals(heroStats.get(1).getId(), 2);
		assertEquals(heroStats.size(), 2);
	}
	@Test
	void matchUpTest()
	{
		List<HeroStatistics> heroStats = new ArrayList<HeroStatistics>();
		heroStats.add(new HeroStatistics());
		MathupData mathupData = new MathupData();
		String firstStat = "{\"advantage\":[{\"with\":[{\"heroId1\":1,\"heroId2\":32,\"week\":2603,\"rank\":-1,\"m"
				+ "atchCount\":3902,\"kills\":32723,\"deaths\":21105,\""
				+ "assists\":32968,\"networth\":95212805,\"duration\":99"
				+ "27842,\"firstBloodTime\":404441,\"cs\":1574777,\"dn\":"
				+ "68241,\"goldEarned\""
				+ ":84883487,\"xp\":97080648,\"heroDamage\":"
				+ "89695466,\"towerDamage\":30006517,\"heroHealing\":4"
				+ "628918,\"level\":90892,\"synergy\":3.9732"
				+ "203011252576,\"wins\":0.58175294720656079}]}],";
		firstStat += "\"disadvantage\":[{\"with\":[{\"heroId1\":1,\"heroId2\":15,\"week\":2603,\"rank\":-1,\"m"
				+ "atchCount\":3902,\"kills\":32723,\"deaths\":21105,\""
				+ "assists\":32968,\"networth\":95212805,\"duration\":99"
				+ "27842,\"firstBloodTime\":404441,\"cs\":1574777,\"dn\":"
				+ "68241,\"goldEarned\""
				+ ":84883487,\"xp\":97080648,\"heroDamage\":"
				+ "89695466,\"towerDamage\":30006517,\"heroHealing\":4"
				+ "628918,\"level\":90892,\"synergy\":1.9732"
				+ "203011252576,\"wins\":0.58175294720656079}]}]}";
		String secondStat = "{\"advantage\":[{\"with\":[{\"heroId1\":2,\"heroId2\":56,\"week\":2603,\"rank\":-1,\"m"
				+ "atchCount\":3902,\"kills\":32723,\"deaths\":21105,\""
				+ "assists\":32968,\"networth\":95212805,\"duration\":99"
				+ "27842,\"firstBloodTime\":404441,\"cs\":1574777,\"dn\":"
				+ "68241,\"goldEarned\""
				+ ":84883487,\"xp\":97080648,\"heroDamage\":"
				+ "89695466,\"towerDamage\":30006517,\"heroHealing\":4"
				+ "628918,\"level\":90892,\"synergy\":8.9732"
				+ "203011252576,\"wins\":0.58175294720656079}]}],";
		secondStat += "\"disadvantage\":[{\"with\":[{\"heroId1\":2,\"heroId2\":21,\"week\":2603,\"rank\":-1,\"m"
				+ "atchCount\":3902,\"kills\":32723,\"deaths\":21105,\""
				+ "assists\":32968,\"networth\":95212805,\"duration\":99"
				+ "27842,\"firstBloodTime\":404441,\"cs\":1574777,\"dn\":"
				+ "68241,\"goldEarned\""
				+ ":84883487,\"xp\":97080648,\"heroDamage\":"
				+ "89695466,\"towerDamage\":30006517,\"heroHealing\":4"
				+ "628918,\"level\":90892,\"synergy\":1.9732"
				+ "203011252576,\"wins\":0.58175294720656079}]}]}";
		mathupData.getParsedStat(heroStats, List.of(firstStat, secondStat));
		assertEquals(heroStats.get(0).getSynergyByHeroWith(32),3.9732203011252576);
		assertEquals(heroStats.get(0).getSynergyByHeroWith(15),1.9732203011252576);
		assertEquals(heroStats.get(1).getSynergyByHeroWith(56),8.9732203011252576);
		assertEquals(heroStats.get(1).getSynergyByHeroWith(21),1.9732203011252576);
		
	}
	@Test
	void metaDataTest()
	{
		List<HeroStatistics> heroStats = new ArrayList<HeroStatistics>();
		heroStats.add(new HeroStatistics());
		MetaData metaData = new MetaData();
		String testStat = "[{\"heroId\":1,\"win\":[13469,13609,13623,14130,17270,17241,14051,12946,13427,13240,14062,15814,10691,173]},"
				+ "{\"heroId\":2,\"win\":[18230,17780,17920,19013,23066,22613,18614,17910,18041,18449,19158,21874,14097,271]}]";
		metaData.getParsedStat(heroStats, testStat);
		assertEquals(heroStats.get(0).getWinsAmount(), 183746);
		assertEquals(heroStats.get(0).getMathesAmount(), 0);
	}
}
