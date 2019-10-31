package main.statistics;

import java.util.Map;
import java.util.HashMap;
import main.statistics.HeroStatistics;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;

public class StatisticsParser implements IStatisticsParser
{
	
	public List<Map<String, String>> ParseStatistic(String stats, List<String> fields)
	{
		List<Map<String, String>> processedFields = new ArrayList<Map<String, String>>();
		List<String> searchingFields = new ArrayList<String>();
		for(String field  : fields)
		{
			searchingFields.add('"'+ field + '"');
		}
		int entryIndex = 0;
		int dataIndex = 0;
		while(entryIndex != -1)
		{
			boolean addFlag = true;
			Map<String, String> record = new HashMap<String, String>();
			for(int i = 0; i < searchingFields.size();++i)
			{
				String curField = searchingFields.get(i);
				entryIndex = stats.indexOf(curField, entryIndex);
				if(entryIndex == -1 || (i > 0 && dataIndex + 1 != entryIndex)) {
					addFlag = false;
					break;
				}
				dataIndex = entryIndex + curField.length() + 1;
				char endSymbol;
				if(stats.charAt(dataIndex) == '"')
				{
					dataIndex++;
					endSymbol = '"';
				}
				else if(stats.charAt(dataIndex) == '[')
				{
					dataIndex++;
					endSymbol = ']';
				}
				else
				{
					endSymbol = ',';
				}
				String value = "";
				while(stats.charAt(dataIndex) != endSymbol)
				{
					value += stats.charAt(dataIndex);
					dataIndex++;
				}
				if(endSymbol == '"' || endSymbol == ']')
				{
					dataIndex++;
				}
				record.put(curField.substring(1, curField.length() - 1), value);
			}
			if(addFlag) {
				processedFields.add(new HashMap<String, String>(record));
			}
		}
		return processedFields;
	}
	
	public List<HeroStatistics> FillStat(List<Map<String, String>> parsedStat, 
												Map<String, String> heroStatFieldsDict,
												List<HeroStatistics> completeStat)
	{
		Class<HeroStatistics> heroStatClass = HeroStatistics.class;
		List<String> heroStatFields = new ArrayList<String>();
		List<String> completeFields = new ArrayList<String>();
		List<HeroStatistics> fillStat = new ArrayList<HeroStatistics>();
		for(Field field : heroStatClass.getFields())
		{
			heroStatFields.add(field.getName());
		}
		for(Map<String, String> record : parsedStat)
		{
			HeroStatistics curRecord = new HeroStatistics();
			for(String key : record.keySet())
			{
				if(heroStatFields.contains(heroStatFieldsDict.get(key)))
				{
					try {
						Field curField = heroStatClass.getField(heroStatFieldsDict.get(key));
						completeFields.add(curField.getName());
						if(curField.getType().equals(int.class))
						{
							String[] recordData = record.get(key).split(",");
							int fullData = 0;
							for(String curValueStr : recordData)
							{
								fullData+=Integer.parseInt(curValueStr);
							}
							curField.set(curRecord, fullData);
						}
						else if(curField.getType().equals(double.class))
							curField.set(curRecord, Double.parseDouble((record.get(key))));
						else
							curField.set(curRecord, record.get(key));
					} catch (NoSuchFieldException e) {
						e.printStackTrace();	
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			fillStat.add(curRecord);
		}
		if(completeStat != null)
		{
			combineStatistics(completeStat, fillStat, completeFields);
			return completeStat;
		}
		return fillStat;
	}
	 
	public void combineStatistics(List<HeroStatistics> first, List<HeroStatistics> second, List<String> fields)
	{
		Class<HeroStatistics> heroStatClass = HeroStatistics.class;
		if(first.size() != second.size())
			throw new IllegalArgumentException("first size != second size");
		for(int i = 0; i < second.size();++i)
		{
			for(String field : fields)
			{
				try {
					Field curField = heroStatClass.getField(field);
					curField.set(first.get(i),curField.get(second.get(i)));
				} catch (NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
