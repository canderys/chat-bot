package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroAdviceRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{	
		return new RequestResult("Введите героев, выбранных вашей командой, а затем героев, выбранных командой противника.\n"
				+ "Если команда укомплектована не полностью, то введите end после ввода последнего героя", RequestType.HEROADVICE);
	}
}