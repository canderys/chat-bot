package GameTest;

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
	@BeforeEach
	void setUp() throws Exception {
		StatisticsGetter statGetter = new StatisticsManager();
		List<HeroStatistics> heroStat =  statGetter.getFullStat();
		draftGame = new DraftGame(heroStat);
		for(int i = 0;i < 22;i++) {
			draftGame.setState(heroStat.get(i));
		}
	}

	@Test
	void test() {
		assertEquals(draftGame.getState(), "kek");
	}

}
