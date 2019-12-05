package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroSelectRequest implements Request {
	
	private String heroSide = "radiant";
	
	public HeroSelectRequest(String side)
	{
		heroSide = side;
	}
	
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat,
			RequestProcessor processor, long chatId)
	{
		if (processor.addHeroToTeam(heroSide, splittedRequest, chatId))
			return new RequestResult("Вы успешно выбрали героя", RequestType.HEROSELECT, chatId);
		return new RequestResult("Такого героя не существует", RequestType.SELECTERROR, chatId);
	}
}