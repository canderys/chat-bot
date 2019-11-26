package main.requests;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import main.statistics.HeroStatistics;

public class HelpRequest implements Request {
	public RequestResult GetRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{
		/*List<String> heroesNames = new ArrayList<String>();
		for (int i = 0; i < stat.size(); ++i)
			heroesNames.add(stat.get(i).name);
		Collections.sort(heroesNames);
		String heroes = "";
		for (int i = 0; i < heroesNames.size(); ++i)
			heroes += (i + 1) + ". " + heroesNames.get(i) + "\n";*/
		return new RequestResult("Bot can show statistics for heroes from game Dota 2. Statistics bases on stratz.com data\n"
				+ "Bot has some commands:\n"
				+ "1. help - show help and heroes list\n"
				+ "2. getstat [hero_name] - show statistics for [hero_name]\n"
				+ "3. getallstat - show statistics for all heroes\n"
				+ "4. exit - stop bot\n"
				/*+ "Heroes list:\n"
				+ heroes*/, RequestType.HELP);
	}
}
