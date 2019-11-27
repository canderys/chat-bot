package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class AllStatRequest implements Request {
	public RequestResult GetRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{
		String result = "";
		for (int i = 0; i < stat.size(); ++i)
			result += "№" + (i + 1) + ".\nИмя героя: " + stat.get(i).getName() + "\nВинрейт: " + 
					Math.round(stat.get(i).getWinRate() * 100.0) / 100.0 +
						"%\nПроцент выбора: " + Math.round(stat.get(i).getPickRate() * 100.0) / 100.0 + "%\n\n";
		return new RequestResult(result, RequestType.GETHEROLIST);
	}
}