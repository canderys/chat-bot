package main.statistics;

import java.lang.reflect.Field;


public class HeroStatistics {
	public int id;
	public double pickRate;
	public double winRate;
	public String name;
	public int matchesAmount;
	public int winsAmount;
	public int allGames;
	
	public HeroStatistics()
	{
		this.name = "";
	}
	
	public String ToString()
	{
		String output = "";
		Class<HeroStatistics> heroStatClass = HeroStatistics.class;
		Field[] fields = heroStatClass.getFields();
		for(Field field : fields)
		{
			try {
				output += field.getName() + " : " + field.get(this).toString() + 
													System.lineSeparator();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
}
