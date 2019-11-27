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
				+ "1. <b>help</b> - показать справку\n"
				+ "2. <b>getstat</b> [<i>hero_name</i>] - показать статистику для героя [<i>hero_name</i>]\n"
				+ "3. <b>getallstat</b> - показывает список героев и статистику для них\n"
				+ "4. <b>advicehero</b> - находит наиболее подходящих героев для каждой команды, учитывая уже выбранных героев.\n"
				+ "После ввода команды бот переходит в режим выбора героев. Чтобы выбрать героя, нужно использовать команду:\n"
				+ "[<b>dire</b>/<b>radiant</b>] [<i>hero_name</i>] - добавить героя в соответствующую команду\n"
				+ "Для завершения выбора героев введите <b>end</b>\n"
				+ "5. <b>getherolist</b> - показывает список всех героев.", RequestType.HELP);
	}
}
