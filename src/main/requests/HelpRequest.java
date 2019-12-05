package main.requests;

import java.util.List;

import main.statistics.HeroStatistics;

public class HelpRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, 
			RequestProcessor processor, long chatId)
	{
		return new RequestResult("Бот умеет показывать различные статистики для героев игры Dota 2. Статистика основывается на данных сайта stratz.com\n"
				+ "Бот имеет следующие команды:\n"
				+ "1. <b>Помощь</b> - показать справку\n"
				+ "2. <b>Статистика</b> - показать статистику для героя\n"
				+ "3. <b>Совет</b> - находит наиболее подходящих героев для каждой команды, учитывая уже выбранных героев.\n"
				+ "4. <b>Герои</b> - показывает список всех героев.\n"
				+ "6. <b>Игра</b> - начать игру DraftGame", RequestType.HELP, chatId);
	}
}
