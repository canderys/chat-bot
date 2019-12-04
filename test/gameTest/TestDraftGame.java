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
	}

	@Test
	void test() {
		int i = 0;
		while(!draftGame.isEnd()) {
			String actionFirst = draftGame.getState(0);
			String actionSecond = draftGame.getState(1);
			Console.Print(i);
			draftGame.setState(heroStat.get(i));
			i++;
			Console.Print("first" + actionFirst + "\n");
			Console.Print("second" + actionSecond + "\n");
		}
		String end = draftGame.getState(0);
		Console.Print(end);
	}

}
