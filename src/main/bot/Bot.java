package main.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import main.statistics.StatisticsManager;
import main.statistics.HeroStatistics;
import main.console.Console;
import main.game.DraftGame;
import main.requests.RequestProcessor;
import main.requests.RequestResult;
import main.requests.RequestType;


public class Bot extends TelegramLongPollingBot {
	
	private List<HeroStatistics> fullStat;
	private RequestProcessor requestProcessor;
	
	private HashSet<Long> usersInSelectingModes = new HashSet<Long>();
	private HashSet<Long> usersInGame = new HashSet<Long>();
	private HashSet<Long> radiantUsers = new HashSet<Long>();
	private HashSet<Long> direUsers = new HashSet<Long>();
	
	private Map<Long, Integer> usersStatuses = new HashMap<Long, Integer>();
	//Статусы: 0 - обычное взаимодейтсвие с ботом, 1 - игра, 2 - подсказка по пику, 3 - показать статистику
	
	public Bot()
	{
		StatisticsManager statManager = new StatisticsManager();
		fullStat = statManager.getFullStat();
		requestProcessor = new RequestProcessor(fullStat, this);
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
			txt = data;
		}
		else
		if (e.hasMessage())
		{
			message = e.getMessage();
			txt = message.getText();
		}
		long id = message.getChatId();
		RequestResult result = requestProcessor.getRequest(txt, id, (usersStatuses.containsKey(id) ? usersStatuses.get(id) : 0));
		processRequest(message.getChatId(), result);
	}
	
	private void processRequest(long id, RequestResult request)
	{
		if (!usersStatuses.containsKey(id))
		{
			usersStatuses.put(id, 0);
			sendDefaultReplyKeyboard(id, "Главное меню");
		}
		if (request.getRequestType() == RequestType.HEROES)
		{
			sendTextMessage(id, request.getRequestText());
			return;
		}
		if (request.getRequestType() == RequestType.USERINQUEUE)
		{
			if (!requestProcessor.isGameFound())
				sendQueueReplyKeyboard(id, request.getRequestText());
			return;
		}
		if (usersInGame.contains(id))
		{
			long anotherId = requestProcessor.getAnotherPlayer(id);
			switch (request.getRequestType())
			{
				case ERROR:
					sendTextMessage(id, request.getRequestText());
					break;
				case DRAFTHERO:
					sendMessageWithId(id, requestProcessor.getGameState(id));
					sendMessageWithId(anotherId, requestProcessor.getGameState(anotherId));
					break;
				case USERLEAVEQUEUE:
					sendDefaultReplyKeyboard(id, "Один из игроков вышел, игра закончена");
					sendDefaultReplyKeyboard(anotherId, "Один из игроков вышел, игра закончена");
					usersInGame.remove(id);
					usersInGame.remove(anotherId);
					
					radiantUsers.remove(id);
					direUsers.remove(id);	
					radiantUsers.remove(requestProcessor.getAnotherPlayer(id));
					direUsers.remove(requestProcessor.getAnotherPlayer(id));
					break;
				default:
					sendMessageWithId(id, "Неверная команда");
					break;
			}
			if (requestProcessor.gameIsOver(id))
			{
				usersInGame.remove(id);
				usersInGame.remove(anotherId);
			}
			return;
		}
		if (!usersInSelectingModes.contains(id))
			switch (request.getRequestType())
			{
				case ERROR:
					sendTextMessage(id, request.getRequestText());
					break;
				case HELP:
					sendTextMessage(id, request.getRequestText());
					break;
				case GETHEROSTAT:
					sendStatistics(id, request.getRequestText());
					sendDefaultReplyKeyboard(id, "Главное меню");
					break;
				case GETHEROLIST:
					sendStatisticsChoice(id);
					break;
				case HEROADVICE:
					sendAdvice(id);
					break;
				case USERLEAVEQUEUE:
					sendDefaultReplyKeyboard(id, "Главное меню");
					break;
				default:
					sendTextMessage(id, "Неизвестная команда");
			}
		else
			switch (request.getRequestType())
			{
				case SIDESELECT:
					sendHeroSelectReplyKeyboard(id);
					break;
				case HEROSELECT:
					sendHeroSelect(id);
					break;
				case HEROADVICERESULT:
					sendHeroAdviceResult(id, request.getRequestText());
					sendDefaultReplyKeyboard(id, "Главное меню");
					break;
				case SELECTERROR:
					sendTextMessage(id, request.getRequestText());
					break;
				default:
					sendTextMessage(id, "Неизвестная команда.\nВы находитесь в режиме выбора героев. Пожалуйста, выберите героя или закончите выбор, нажав <b>результат</b>");
					break;
			}
	}
	
	private void sendHeroAdviceResult(long id, String text)
	{
		usersStatuses.put(id, 0);
		usersInSelectingModes.remove(id);
		sendTextMessage(id, text);
	}
	
	private void sendHeroSelect(long id)
	{
		sendTextMessage(id, "Герой успешно добавлен. Продолжайте добавлять героев или закончите выбор, нажав <b>результат</b>");
	}
	
	private void sendStatisticsChoice(long id)
	{
		usersStatuses.put(id, 3);
		sendHeroesListReplyKeyboard(id);
	}
	
	private SendMessage createMessage(long id, String text)
	{
		SendMessage message = new SendMessage();
		message.setChatId(id);
		message.setText("Выберите героя из списка");
		return message;
	}
	
	private ReplyKeyboardMarkup createBaseKeyboard()
	{
		ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
		keyboard.setSelective(true);
		keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);
        return keyboard;
	}
	
	private void send(SendMessage message, ReplyKeyboardMarkup keyboard, List< KeyboardRow > buttons)
	{
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
	
	private void sendHeroesReplyKeyboard(long id)
	{
		SendMessage message = createMessage(id, "Выберите героя из списка");
		ReplyKeyboardMarkup keyboard = createBaseKeyboard();
		List< KeyboardRow > buttons = new ArrayList< KeyboardRow >();
		buttons.add(new KeyboardRow());
		buttons.get(0).add(new KeyboardButton().setText("Выйти"));
		for (int i = 0; i < fullStat.size(); ++i)
		{
			if (i % 4 == 0)
				buttons.add(new KeyboardRow());
			buttons.get(i / 4 + 1).add(new KeyboardButton().setText(makeFinalName(fullStat.get(i).getName())));
		}
		send(message, keyboard, buttons);
	}
	
	private void sendHeroesListReplyKeyboard(long id)
	{
		SendMessage message = createMessage(id, "Выберите героя из списка");
		ReplyKeyboardMarkup keyboard = createBaseKeyboard();
		List< KeyboardRow > buttons = new ArrayList< KeyboardRow >();
		for (int i = 0; i < fullStat.size(); ++i)
		{
			if (i % 4 == 0)
				buttons.add(new KeyboardRow());
			buttons.get(i / 4).add(new KeyboardButton().setText(makeFinalName(fullStat.get(i).getName())));
		}
		send(message, keyboard, buttons);
	}
	
	private void sendDefaultReplyKeyboard(long id, String text)
	{
		SendMessage message = createMessage(id, "Главное меню");
		ReplyKeyboardMarkup keyboard = createBaseKeyboard();
		List< KeyboardRow > buttons = new ArrayList< KeyboardRow >();
		buttons.add(new KeyboardRow());
		buttons.add(new KeyboardRow());
		buttons.add(new KeyboardRow());
		buttons.get(0).add(new KeyboardButton().setText("Помощь"));
		buttons.get(0).add(new KeyboardButton().setText("Статистика"));
		buttons.get(1).add(new KeyboardButton().setText("Игра"));
		buttons.get(1).add(new KeyboardButton().setText("Совет"));
		buttons.get(2).add(new KeyboardButton().setText("Герои"));
		send(message, keyboard, buttons);
	}
	
	private void sendQueueReplyKeyboard(long id, String text)
	{
		SendMessage message = createMessage(id, "Выберите героя из списка");
		ReplyKeyboardMarkup keyboard = createBaseKeyboard();
		List< KeyboardRow > buttons = new ArrayList< KeyboardRow >();
		buttons.add(new KeyboardRow());
		buttons.get(0).add(new KeyboardButton().setText("Выйти"));
		send(message, keyboard, buttons);
	}
	
	private void sendAdviceReplyKeyboard(long id)
	{
		SendMessage message = createMessage(id, "Выберите сторону");
		ReplyKeyboardMarkup keyboard = createBaseKeyboard();
		List< KeyboardRow > buttons = new ArrayList< KeyboardRow >();
		buttons.add(new KeyboardRow());
		buttons.get(0).add(new KeyboardButton().setText("Свет"));
		buttons.get(0).add(new KeyboardButton().setText("Тьма"));
		send(message, keyboard, buttons);
	}
	
	private void sendHeroSelectReplyKeyboard(long id)
	{
		SendMessage message = createMessage(id, "Выберите героя из списка");
		ReplyKeyboardMarkup keyboard = createBaseKeyboard();
		List< KeyboardRow > buttons = new ArrayList< KeyboardRow >();
		buttons.add(new KeyboardRow());
		buttons.get(0).add(new KeyboardButton().setText("Результат"));
		buttons.add(new KeyboardRow());
		buttons.get(1).add(new KeyboardButton().setText("Свет"));
		buttons.get(1).add(new KeyboardButton().setText("Тьма"));
		for (int i = 0; i < fullStat.size(); ++i)
		{
			if (i % 4 == 0)
				buttons.add(new KeyboardRow());
			buttons.get(i / 4 + 2).add(new KeyboardButton().setText(makeFinalName(fullStat.get(i).getName())));
		}
		send(message, keyboard, buttons);
	}
	
	private void sendAdvice(long id)
	{
		usersStatuses.put(id, 2);
		sendAdviceReplyKeyboard(id);
		usersInSelectingModes.add(id);
		sendTextMessage(id, "Вы вошли в режим выбора героев. Выберите сторону и добавляйте героев для каждой стороны.");
	}
	
	private void sendStatistics(long id, String text)
	{
		usersStatuses.put(id, 0);
		SendPhoto photo = new SendPhoto();
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
			heroName = "magnataur";
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
		photo.setChatId(id);
		SendMessage s = new SendMessage();
		s.enableHtml(true);
		s.setChatId(id);
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
	
	private void sendTextMessage(long id, String text)
	{
		sendMessageWithId(id, text);
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
	
	public void startDraftGame(Pair<Long, Pair<Long, DraftGame>> game)
	{
		usersInGame.add(game.fst);
		radiantUsers.add(game.fst);
		usersInGame.add(game.snd.fst);
		direUsers.add(game.snd.fst);
		sendMessageWithId(game.fst, "Вы начали игру. Вам нужно собрать команду лучше, чем противник\n"+game.snd.snd.getState(0));
		sendMessageWithId(game.snd.fst, "Вы начали игру. Вам нужно собрать команду лучше, чем противник\n"+game.snd.snd.getState(1));
		
		sendHeroesReplyKeyboard(game.fst);
		sendHeroesReplyKeyboard(game.snd.fst);
		
		usersStatuses.put(game.fst, 1);
		usersStatuses.put(game.snd.fst, 1);
	}
	
	private void sendMessageWithId(long id, String text)
	{
		SendMessage message = new SendMessage();
		message.setChatId(id);
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
	
	public int getUserSide(long id)
	{
		if (radiantUsers.contains(id))
			return 0;
		return 1;
	}
	
	public void endGame(long id)
	{
		long anotherId = requestProcessor.getAnotherPlayer(id);
		usersStatuses.put(id, 0);
		usersStatuses.put(anotherId, 0);
		sendDefaultReplyKeyboard(id, "Главное меню");
		sendDefaultReplyKeyboard(anotherId, "Главное меню");
		usersInGame.remove(id);
		usersInGame.remove(anotherId);
		radiantUsers.remove(id);
		direUsers.remove(id);	
		radiantUsers.remove(anotherId);
		direUsers.remove(anotherId);
	}
}
