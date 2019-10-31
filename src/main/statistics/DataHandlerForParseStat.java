package main.statistics;

import java.util.List;
import java.util.Map;

public interface DataHandlerForParseStat {
	public List<List<String>> getSearchingListOfFields();
	public List<Map<String, String>> getConformityList();
	public List<String> getLinks();
	public List<StatInfo> getStatisticsForParse();
}
