package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroAdviceRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat,
			RequestProcessor processor, long chatId)
	{	
		return new RequestResult("Выберите героев и найдите героя, которые лучше всего сочетается с ними.", RequestType.HEROADVICE, chatId);
	}
}