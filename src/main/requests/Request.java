package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public interface Request {
	public RequestResult getRequestResult(String request, List<HeroStatistics> stat, 
			RequestProcessor processor, long chatId);
}
