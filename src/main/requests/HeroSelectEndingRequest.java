package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroSelectEndingRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{
		RequestResult result = new RequestResult(processor.findBestHeroes(), RequestType.HEROADVICERESULT);
		processor.clearTeams();
		return result;
	}
}
