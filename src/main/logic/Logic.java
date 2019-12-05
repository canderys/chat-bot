package main.logic;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import main.bot.Bot;

public class Logic {

	public static void main(String[] args)
	{
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("socksProxyHost", "127.0.0.1");
		System.getProperties().put("socksProxyPort", "9150");
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