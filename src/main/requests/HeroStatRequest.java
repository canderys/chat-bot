package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroStatRequest implements Request {
	public String GetRequestResult(String splittedRequest, List<HeroStatistics> stat)
	{
		for (int i = 0; i < stat.size(); ++i)
		{
			if (stat.get(i).name.compareTo(splittedRequest) == 0)
			{
				return "Hero name: " + stat.get(i).name + "\nWin rate: " + 
						Math.round(stat.get(i).winRate * 100.0) / 100.0 +
						"%\nPick rate: " + Math.round(stat.get(i).pickRate * 100.0) / 100.0 + "%\n";
			}
		}
		return "This hero doesn't exist. Please, check his name and try again. Use help to get heroes list.\n";
	}
}
