package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class AllStatRequest implements Request {
	public RequestResult GetRequestResult(String splittedRequest, List<HeroStatistics> stat)
	{
		String result = "";
		for (int i = 0; i < stat.size(); ++i)
			result += "¹" + (i + 1) + ".\nHero name: " + stat.get(i).getName() + "\nWin rate: " + 
					Math.round(stat.get(i).getWinRate() * 100.0) / 100.0 +
						"%\nPick rate: " + Math.round(stat.get(i).getPickRate() * 100.0) / 100.0 + "%\n\n";
		return new RequestResult(result, RequestType.GETHEROLIST);
	}
}