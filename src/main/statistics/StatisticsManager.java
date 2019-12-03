package main.statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.util.Scanner;

import main.console.Console;


public class StatisticsManager implements StatisticsGetter
{
	private String statFileName;
	private String dateFileName;
	
	public String getStatFileName()
	{
		return statFileName;
	}
	public void setStatFileName(String name)
	{
		statFileName = name;
	}
	public String getDateFileName()
	{
		return dateFileName;
	}
	public void setDateFileName(String name)
	{
		dateFileName = name;
	}
	
	public StatisticsManager()
	{
		this.statFileName = "stat.txt";
		this.dateFileName = "data.txt";
	}
	public StatisticsManager(String statFile)
	{
		this.statFileName = statFile;
		this.dateFileName = "data.txt";
	}
	
	public List<HeroStatistics> createStatisticsFiles()
	{
		Calendar curDate = Calendar.getInstance();
		StatisticsParser parser = new GsonStatisticsParser();
		List<HeroStatistics> parsedStat = parser.getFullParsedStat(List.of(new HeroData(), new MetaData(), new MathupData()));
		calculateOtherFields(parsedStat);
		Gson g = new Gson();
		String serializedStatistics = g.toJson(parsedStat);
		String date = g.toJson(curDate);
		boolean correctWriteStat = writeFile(statFileName, serializedStatistics);
		boolean correctWriteDate = writeFile(dateFileName, date);
		if(!(correctWriteStat && correctWriteDate))
			Console.Print("cant create statistics files");
		return parsedStat;
	}
	public List<HeroStatistics> createStatisticsFiles(String statName, String dateName, List<Statistic> stats)
	{
		Calendar curDate = Calendar.getInstance();
		StatisticsParser parser = new GsonStatisticsParser();
		List<HeroStatistics> parsedStat = parser.getFullParsedStat(stats);
		calculateOtherFields(parsedStat);
		Gson g = new Gson();
		String serializedStatistics = g.toJson(parsedStat);
		String date = g.toJson(curDate);
		boolean correctWriteStat = writeFile(statName, serializedStatistics);
		boolean correctWriteDate = writeFile(dateName, date);
		if(!(correctWriteStat && correctWriteDate))
			Console.Print("cant create statistics files");
		return parsedStat;
	}
	
	public boolean writeFile(String name, String data)
	{
		try {
			PrintWriter writer = new PrintWriter(name);
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public String getFileString(String name)
	{
		String contents = null;
		try {
			contents = new String(Files.readAllBytes(Paths.get(name)));
		} catch (IOException e) {
			return null;
		}
		return contents;
	}
	
	public List<HeroStatistics> getFullStatInFile()
	{
		Gson g = new Gson();
		String allStat = getFileString(statFileName);
		Type type = new TypeToken<List<HeroStatistics>>(){}.getType();
		List<HeroStatistics> allStatList = g.fromJson(allStat, type);
		return allStatList;
	}
	public boolean isGetStatInFile()
	{
		String date = getFileString(this.dateFileName);
		if(date == null)
			return false;
		Gson g = new Gson();
		Calendar calendarDate = g.fromJson(date, Calendar.class);
		Calendar currentDate = Calendar.getInstance();
		long dateDiff = currentDate.getTimeInMillis() - calendarDate.getTimeInMillis();
		long diffDays = dateDiff / (24 * 60 * 60 * 1000);
		return diffDays < 7;
	}
	public List<HeroStatistics> getFullStat()
	{
		if(isGetStatInFile())
			return getFullStatInFile();
		else {
			return createStatisticsFiles();
		}
		
	}
	/*public static void main(String[] args)
	{
		StatisticsManager manager = new StatisticsManager();
		List<HeroStatistics> a =  manager.getFullStat();
		for(HeroStatistics hero : a)
		{
			Console.Print(hero.toString());
		}
	}*/
	
	public void calculateOtherFields(List<HeroStatistics> finishedStat)
	{
		int allGamesCounter = 0;
		for(HeroStatistics record : finishedStat)
		{
			allGamesCounter+= record.getMathesAmount();
		}
		for(HeroStatistics record : finishedStat)
		{	
			record.setName(record.getName().toLowerCase());
			record.setAllGames(allGamesCounter);
			record.setPickRate(((double)record.getMathesAmount() / allGamesCounter)*1000);
			record.setWinRate(((double)record.getWinsAmount() / record.getMathesAmount())*100);
		}
	}
	
	public String getStrStat(List<HeroStatistics> fullStat)
	{
		StringBuilder output = new StringBuilder();
		for(HeroStatistics record : fullStat)
		{
			output.append(record.toString());
		}
		return output.toString();
	}
}
