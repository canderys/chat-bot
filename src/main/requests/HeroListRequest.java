package main.requests;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import main.statistics.HeroStatistics;

public class HeroListRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{
		String result = "";
		List<String> heroes = new ArrayList<String>();
		for (int i = 0; i < stat.size(); ++i)
			heroes.add(stat.get(i).getName());
		Collections.sort(heroes);
		for (int i = 0; i < heroes.size(); ++i)
			result = result + makeFinalName(heroes.get(i)) + "\n";
		return new RequestResult(result, RequestType.HEROES);
	}
	
	private String makeFinalName(String name)
	{
		String[] splitted = name.split(" ");
		String result = "";
		for (int i = 0; i < splitted.length; ++i)
			result += splitted[i].substring(0, 1).toUpperCase() + splitted[i].substring(1) + " ";
		return result;
	}
}