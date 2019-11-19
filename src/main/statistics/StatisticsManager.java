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
	public String createStatisticFile()
	{
		String name = "stat.txt";
		StatisticsParser parser = new GsonStatisticsParser();
		List<HeroStatistics> parsedStat = parser.getParsedStat();
		Gson g = new Gson();
		String serializedStatistics = g.toJson(parsedStat);
		try {
			PrintWriter writer = new PrintWriter(name);
			writer.write(serializedStatistics);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public List<HeroStatistics> getFullStat()
	{
		//String nameFile = createStatisticFile();
		StatisticsParser parser = new GsonStatisticsParser();
		List<HeroStatistics> allStat = parser.getParsedStat();
		calculateOtherFields(allStat);
		return allStat;
		
	}
	
	private void calculateOtherFields(List<HeroStatistics> finishedStat)
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
