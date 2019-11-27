package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroStatRequest implements Request {
	public RequestResult GetRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{
		for (int i = 0; i < stat.size(); ++i)
		{
			if (stat.get(i).getName().compareTo(splittedRequest) == 0)
			{
				return new RequestResult(MakeFinalName(stat.get(i).getName()) + "\n<b>Win rate:</b> <i>" +
						Math.round(stat.get(i).getWinRate() * 100.0) / 100.0 +
						"%</i>\n<b>Pick rate:</b> <i>" + Math.round(stat.get(i).getPickRate() * 100.0) / 100.0 + "%</i>\n" +
						"Если вы хотите посмотреть полную статистику, вы можете посмотреть эту страницу: https://stratz.com/ru-ru/heroes/" + stat.get(i).getId(), RequestType.GETHEROSTAT);
			}
		}
		return new RequestResult("Такого героя нет, проверьте имя и попробуйте снова. Используйте <b>help</b> для справки.\n", RequestType.ERROR);
	}
	
	private String MakeFinalName(String name)
	{
		String[] splitted = name.split(" ");
		String result = "";
		for (int i = 0; i < splitted.length; ++i)
			result += splitted[i].substring(0, 1).toUpperCase() + splitted[i].substring(1) + " ";
		return result;
	}
}
