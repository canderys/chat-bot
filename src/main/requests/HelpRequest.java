package main.requests;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import main.statistics.HeroStatistics;

public class HelpRequest implements Request {
	public RequestResult GetRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{
		return new RequestResult("Бот умеет показывать различные статистики для героев игры Dota 2. Статистика основывается на данных сайта stratz.com \n"
				+ "Бот имеет следующие команды:\n"
				+ "1. help - показать справку\n"
				+ "2. getstat [hero_name] - показать статистику для героя [hero_name]\n"
				+ "3. getallstat - показывает список героев и статистику для них\n"
				+ "4. advicehero - находит наиболее подходящих героев для каждой команды, учитывая уже выбранных героев.\n"
				+ "После ввода команды бот переходит в режим выбора героев. Чтобы выбрать героя, нужно использовать команду:\n"
				+ "[dire/radiant] [hero_name] -\n"
				+ "4. exit - stop bot\n", RequestType.HELP);
	}
}
