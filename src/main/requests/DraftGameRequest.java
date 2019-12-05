package main.requests;

import java.util.List;

import main.statistics.HeroStatistics;

public class DraftGameRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, 
			RequestProcessor processor, long chatId)
	{
		if (!processor.addUserToGameQueue(chatId))
			return new RequestResult("Поиск соперника...\nДля выхода в меню нажмите кнопку выйти", RequestType.USERINQUEUE, chatId);
		return new RequestResult("Вы нашли игру", RequestType.USERINQUEUE, chatId);
	}
}
