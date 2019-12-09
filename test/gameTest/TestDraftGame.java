package gameTest;

import org.junit.Test;
import org.junit.Assert;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import main.game.DraftGame;
import main.game.Game;
import main.statistics.HeroStatistics;
import main.statistics.StatisticsManager;

class TestDraftGame {
	private Game<HeroStatistics> draftGame;
	private List<HeroStatistics> heroStat;
	StatisticsManager statGetter;
	@BeforeEach
	void setUp() throws Exception {
		statGetter = new StatisticsManager();
		heroStat =  statGetter.getFullStat();
		draftGame = new DraftGame(heroStat);
	}

	@Test
	void test() {
		int i = 0;
		StringBuilder message = new StringBuilder();
		while(!draftGame.isEnd()) {
			message.append(draftGame.getState(0));
			message.append(draftGame.getState(1));
			draftGame.setState(heroStat.get(i));
			i++;
		}
		message.append(draftGame.getState(0));
		String stringMessage = message.toString();
		//statGetter.writeFile(".\\test\\gameTest\\dataTestDraftGame.txt", stringMessage);
		String expected = statGetter.getFileString(".\\test\\gameTest\\dataTestDraftGame.txt");
		for(int y = 0; y < stringMessage.length();y++)
		{
			Assert.assertEquals(expected.charAt(y), stringMessage.charAt(y));
		}
			
	}
}
