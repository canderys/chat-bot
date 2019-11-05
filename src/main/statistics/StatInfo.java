package main.statistics;

import java.util.List;
import java.util.Map;

public class StatInfo {
	public String link;
	public List<String> searchFields;
	public Map<String, String> fieldsConformity;
	
	public StatInfo(String link, List<String> searchFields,  Map<String, String> fieldsConformity)
	{
		this.link = link;
		this.searchFields = searchFields;
		this.fieldsConformity = fieldsConformity;
	}
}
