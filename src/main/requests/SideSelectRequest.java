package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class SideSelectRequest implements Request {
	
	private String heroSide = "radiant";
	
	public SideSelectRequest(String side)
	{
		heroSide = side;
	}
	
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat,
			RequestProcessor processor, long chatId)
	{
		processor.setSide(chatId, heroSide);
		return new RequestResult("", RequestType.SIDESELECT, chatId);
	}
}