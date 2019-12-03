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
		expectedGreetings.append("� ���� ���� ����� �������� � ��������� ������ ��� ������� ��� ���� � ��� �����" + "\n");
		expectedGreetings.append("����� ������� ��� ��������� ����� ������ �������� ��� ���" + "\n");
		String greetings = draftGame.getState();
		assertEquals(greetings, expectedGreetings.toString());
	}

	@Test
	void test() {
		String firstAction = draftGame.getState();
		assertEquals("������ �� ������ ��������� �����" + "\n" , firstAction);
		int i = 0;
		while(!draftGame.isEnd()) {
			draftGame.setState(heroStat.get(i));
			i++;
			String action = draftGame.getState();
		}
		String action = draftGame.getState();
	}

}
