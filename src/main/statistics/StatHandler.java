package main.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatHandler implements DataHandlerForParseStat
{
	public static Map<String, String> getInitialStatConformity()
	{
		Map<String, String> initialStatFieldsDict = new HashMap<String, String>();
		initialStatFieldsDict.put("id", "id");
		initialStatFieldsDict.put("displayName", "name");
		return initialStatFieldsDict;
	}
	
	
	public static Map<String, String> getmetaTrendFieldsConformity()
	{
		Map<String, String> initialStatFieldsDict = new HashMap<String, String>();
		initialStatFieldsDict.put("heroId", "id");
		initialStatFieldsDict.put("pick", "matchesAmount");
		initialStatFieldsDict.put("win", "winsAmount");
		return initialStatFieldsDict;
	}
	
	public List<String> getLinks()
	{
		List<String> links = List.of("https://api.stratz.com/api/v1/Hero",
									"https://api.stratz.com/api/v1/Hero/metaTrend");
		return links;
	}
	
	public List<List<String>> getSearchingListOfFields()
	{
		List<List<String>> searchingListOfFields = new ArrayList<List<String>>();
		List<String> initialStatFields = List.of("id","name", "displayName");
		List<String> metaTrendFields = List.of("heroId", "win", "pick");
		searchingListOfFields.add(initialStatFields);
		searchingListOfFields.add(metaTrendFields);
		return searchingListOfFields;
	}
	
	public List<Map<String, String>> getConformityList()
	{
		List<Map<String, String>> conformityList = new ArrayList<Map<String, String>>();
		conformityList.add(getInitialStatConformity());
		conformityList.add(getmetaTrendFieldsConformity());
		return conformityList;
	}
	
	
	public List<StatInfo> getStatisticsForParse()
	{
		List<StatInfo> statisticsForParse = new ArrayList<StatInfo>();
		List<String> links = getLinks();
		List<List<String>> searchingListOfFields = getSearchingListOfFields();
		List<Map<String, String>> conformityList = getConformityList();
		if(links.size() != searchingListOfFields.size() || links.size() != conformityList.size())
			throw new IllegalArgumentException("incorrect realization");
		for(int i = 0;i < links.size();i++)
		{
			statisticsForParse.add(new StatInfo(links.get(i), searchingListOfFields.get(i), conformityList.get(i)));
		}
		return statisticsForParse;
	}
	

}
