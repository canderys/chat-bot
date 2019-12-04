package main.logic;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import main.bot.Bot;

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