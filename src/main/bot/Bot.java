package main.bot;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import main.statistics.StatisticsManager;
import main.statistics.HeroStatistics;
import main.console.Console;
import main.requests.RequestProcessor;
import main.requests.RequestResult;
import main.requests.RequestType;


public class Bot extends TelegramLongPollingBot {
	
	private List<HeroStatistics> fullStat;
	private RequestProcessor requestProcessor;
	private boolean heroSelectingMode;
	
	public Bot()
	{
		heroSelectingMode = false;
		StatisticsManager statManager = new StatisticsManager();
		fullStat = statManager.getFullStat();
		requestProcessor = new RequestProcessor(fullStat);
		Console.print("Bot is ready");
	}
	
	@Override
	public String getBotUsername() {
		return "StatBot";
	}

	@Override
	public void onUpdateReceived(Update e) {
		Message message = new Message();
		String txt = "";
		if (e.hasCallbackQuery())
		{
			String data = e.getCallbackQuery().getData();
			message = e.getCallbackQuery().getMessage();
			txt = e.getCallbackQuery().getMessage().getText() + " " +  data;
		}
		else
		if (e.hasMessage())
		{
			message = e.getMessage();
			txt = message.getText();
		}
		RequestResult result = requestProcessor.getRequest(txt);
		processRequest(message, result);
	}
	
	private void processRequest(Message msg, RequestResult request)
	{
		if (request.getRequestType() == RequestType.HEROES)
		{
			sendTextMessage(msg, request.getRequestText());
			return;
		}
		if (!heroSelectingMode)
		{
		if (request.getRequestType() == RequestType.ERROR || request.getRequestType() == RequestType.HELP)
			sendTextMessage(msg, request.getRequestText());
		else 
			if (request.getRequestType() == RequestType.GETHEROSTAT)
				sendStatistics(msg, request.getRequestText());
			else
				if (request.getRequestType() == RequestType.GETHEROLIST)
					sendStatisticsChoice(msg);
				else
					if (request.getRequestType() == RequestType.HEROADVICE)
						sendAdvice(msg);
					else
						sendTextMessage(msg, "Unknown command");
		}
		else
		{
			if (request.getRequestType() == RequestType.HEROSELECT)
				sendHeroSelect(msg);
			else
				if (request.getRequestType() == RequestType.HEROADVICERESULT)
					sendHeroAdviceResult(msg, request.getRequestText());
				else
					if (request.getRequestType() == RequestType.SELECTERROR)
						sendTextMessage(msg, request.getRequestText());
					else
					sendTextMessage(msg, "����������� �������.\n�� ���������� � ������ ������ ������. ����������, �������� ����� ��� ��������� �����, ������� <b>end</b>");
		}
	}
	
	private void sendHeroAdviceResult(Message msg, String text)
	{
		heroSelectingMode = false;
		sendTextMessage(msg, text);
	}
	
	private void sendHeroSelect(Message msg)
	{
		sendTextMessage(msg, "����� ������� ��������. ����������� ��������� ������ ��� ��������� �����, ������� <b>end</b>");
	}
	
	private void sendStatisticsChoice(Message msg)
	{
		SendMessage message = new SendMessage();
		message.setChatId(msg.getChatId());
		message.setText("getstat");
		InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
		List< List<InlineKeyboardButton> > buttons = new ArrayList< List<InlineKeyboardButton> >();
		for (int i = 0; i < fullStat.size(); ++i)
		{
			if (i % 4 == 0)
			{
				buttons.add(new ArrayList<InlineKeyboardButton>());
			}
			buttons.get(i / 4).add(new InlineKeyboardButton().setText(makeFinalName(fullStat.get(i).getName())).setCallbackData(fullStat.get(i).getName()));
		}
		keyboard.setKeyboard(buttons);
		message.setReplyMarkup(keyboard);
		try
		{
			execute(message);
		} catch (TelegramApiException e)
		{
			e.printStackTrace();
		}
	}
	
	private void sendAdvice(Message msg)
	{
		heroSelectingMode = true;
		sendTextMessage(msg, "�� ����� � ����� ������ ������. �������� ����� � ��������������� �������, ��������� [<b>radiant</b>/<b>dire</b>] [<i>heroname</i>].\n"
				+ "��� ��������� ������ ���� ������ ����������� <b>getherolist</b>");
	}
	
	private void sendStatistics(Message msg, String text)
	{
		SendPhoto photo = new SendPhoto();
		Console.print(text);
		String heroName = text.substring(0, text.indexOf(" \n")).replace("-", "").replace(' ', '_').toLowerCase();
		switch (heroName)
		{
		case "io":	
			heroName = "wisp";
			break;
		case "windranger": 
			heroName = "windrunner";
			break;
		case "necrolyte":
			heroName = "necrophos";
			break;
		case "magnus":
			heroName = "mangataur";
			break;
		case "outworld_devourer":
			heroName = "obsidian_destroyer";
			break;
		case "queen_of_pain":
			heroName = "queenofpain";
			break;
		case "nature's_prophet":
			heroName = "furion";
			break;
		case "lifestealer":
			heroName = "life_stealer";
			break;
		case "zeus":
			heroName = "zuus";
			break;
		case "centaur_warrunner":
			heroName = "centaur";
			break;
		}
		photo.setPhoto("http://cdn.dota2.com/apps/dota2/images/heroes/" + heroName + "_full.png");
		photo.setChatId(msg.getChatId());
		SendMessage s = new SendMessage();
		s.enableHtml(true);
		s.setChatId(msg.getChatId());
		s.setText(text);
		try
		{
			execute(photo);
			execute(s);
		} catch (TelegramApiException e)
		{
			e.printStackTrace();
		}
	}
	
	private void sendTextMessage(Message msg, String text)
	{
		SendMessage message = new SendMessage();
		message.setChatId(msg.getChatId());
		message.enableHtml(true);
		message.setText(text);
		try
		{
			execute(message);
		} catch (TelegramApiException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getBotToken() {
		return "822763128:AAEdyCT3EQVZxJbJTrAsnC19Ap2PMd2t-Tw";
	}
	
	private String makeFinalName(String name)
	{
		String[] splitted = name.split(" ");
		String result = "";
		for (int i = 0; i < splitted.length; ++i)
			result += splitted[i].substring(0, 1).toUpperCase() + splitted[i].substring(1) + " ";
		return result;
	}

}
