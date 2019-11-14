package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public interface Request {
	public RequestResult GetRequestResult(String request, List<HeroStatistics> stat);
}
