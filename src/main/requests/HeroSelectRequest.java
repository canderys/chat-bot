package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroSelectRequest implements Request {
	
	private String heroSide = "radiant";
	
	public HeroSelectRequest(String side)
	{
		heroSide = side;
	}
	
	public RequestResult GetRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{
		processor.addHeroToTeam(heroSide, splittedRequest);
		return new RequestResult("Вы успешно выбрали героя", RequestType.HEROSELECT);
	}
}