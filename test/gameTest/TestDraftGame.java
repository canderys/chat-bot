package gameTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.console.Console;
import main.game.DraftGame;
import main.game.Game;
import main.statistics.HeroStatistics;
import main.statistics.StatisticsGetter;
import main.statistics.StatisticsManager;

class TestDraftGame {
	private Game<HeroStatistics> draftGame;
	private List<HeroStatistics> heroStat;
	@BeforeEach
	void setUp() throws Exception {
		StatisticsGetter statGetter = new StatisticsManager();
		heroStat =  statGetter.getFullStat();
		draftGame = new DraftGame(heroStat);
		StringBuilder expectedGreetings = new StringBuilder();
		expectedGreetings.append("В этой игре нужно выбирать и запрещать героев для команды сил тьмы и сил света" + "\n");
		expectedGreetings.append("Чтобы выбрать или запретить героя просто напишите его имя" + "\n");
		String greetings = draftGame.getState();
		assertEquals(greetings, expectedGreetings.toString());
	}

	@Test
	void test() {
		int i = 0;
		while(!draftGame.isEnd()) {
			String action = draftGame.getState();
			draftGame.setState(heroStat.get(i));
			i++;	
			draftGame.getTurn();
		}
		String action = draftGame.getState();
	}

}
