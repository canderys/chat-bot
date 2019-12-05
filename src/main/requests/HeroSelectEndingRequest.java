package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroSelectEndingRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, 
			RequestProcessor processor, long chatId)
	{
		RequestResult result = new RequestResult(processor.findBestHeroes(chatId), RequestType.HEROADVICERESULT, chatId);
		processor.clearTeams(chatId);
		return result;
	}
}
