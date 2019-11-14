package main.requests;

import java.util.Map;
import java.util.HashMap;
import main.statistics.HeroStatistics;
import java.util.List;

public class RequestProcessor {
	
	private Map<String, Request> availableRequests;
	private List<HeroStatistics> heroStats;
	
	public RequestProcessor(List<HeroStatistics> stat)
	{
		heroStats = stat;
		availableRequests = new HashMap<String, Request>();
		availableRequests.put("getstat", new HeroStatRequest());
		availableRequests.put("help", new HelpRequest());
		availableRequests.put("getallstat", new AllStatRequest());
	}
	
	public RequestResult GetRequest(String request)
	{
		String currentRequest = request.toLowerCase();
		currentRequest = currentRequest.trim();
		while (currentRequest.indexOf("  ") != -1)
			currentRequest = currentRequest.replaceAll("  ", " ");
		if (currentRequest.indexOf(' ') != -1)
		{
			String requestName = currentRequest.substring(0, currentRequest.indexOf(' '));
			if (availableRequests.containsKey(requestName))
				return availableRequests.get(requestName).GetRequestResult(currentRequest.substring(currentRequest.indexOf(' ') + 1), heroStats);
			return new RequestResult("Incorrect request. Type help to show available requests.\n", RequestType.ERROR);
		}
		else
		{
			if (availableRequests.containsKey(currentRequest))
				return availableRequests.get(currentRequest).GetRequestResult("", heroStats);
			return new RequestResult("Incorrect request. Type help to show available requests.\n", RequestType.ERROR);
		}
	}
}
