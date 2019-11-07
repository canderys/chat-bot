package statisticsTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import main.statistics.HeroStatistics;
import main.statistics.StatisticsLoader;
import main.statistics.StatisticsParser;

class StatisticsParserTest {
	@Test
	void HeroStatTest() {
		StatisticsParser parser = new StatisticsParser();	
		String simpleStat = StatisticsLoader.GetStatisticsByLink("https://api.stratz.com/api/v1/Hero");
		List<Map<String, String>> parsedStat = parser.ParseStatistic(simpleStat, List.of("id","name", "displayName"));
		Assert.assertEquals(parsedStat.size(), 117);
	}
	
	@Test
	void SimpleTest() {
		StatisticsParser parser = new StatisticsParser();	
		String simpleStat = "\"id\":1,\"name\":\"npc_dota_hero_antimage\",\"displayName\":\"Anti-Mage\"";
		List<Map<String, String>> parsedStat = parser.ParseStatistic(simpleStat, List.of("id","name", "displayName"));
		Assert.assertEquals(parsedStat.size(), 1);
		Assert.assertEquals(parsedStat.get(0).get("id"), "1");
	}
	
	@Test
	void FillStatTest()
	{
		StatisticsParser parser = new StatisticsParser();	
		String simpleStat = "\"id\":1,\"name\":\"npc_dota_hero_antimage\",\"displayName\":\"Anti-Mage\"";
		List<Map<String, String>> parsedStat = parser.ParseStatistic(simpleStat, List.of("id","name", "displayName"));
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("id", "id");
		fields.put("displayName", "name");
		List<HeroStatistics> filledStat = parser.FillStat(parsedStat, fields, null);
		Assert.assertEquals(filledStat.size(), 1);
		HeroStatistics expected = new HeroStatistics();
		expected.id = 1;
		expected.name = "Anti-Mage";
		Assert.assertEquals(filledStat.get(0).id,expected.id);
		Assert.assertEquals(filledStat.get(0).name,expected.name);
	}
}
