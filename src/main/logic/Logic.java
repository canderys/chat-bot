package main.logic;

import java.util.List;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import main.requests.RequestProcessor;
import main.bot.Bot;
import main.console.Console;
import main.statistics.HeroStatistics;
import main.statistics.StatisticsLoader;
import main.statistics.StatisticsManager;

public class Logic {

	public static void main(String[] args)
	{
		ApiContextInitializer.init();
		TelegramBotsApi botapi = new TelegramBotsApi();
		try
		{
			botapi.registerBot(new Bot());
		} catch (TelegramApiException e)
		{
			e.printStackTrace();
		}	
		finally
		{
			botapi = null;
		}
	}
}