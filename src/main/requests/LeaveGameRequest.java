package main.requests;

import java.util.List;

import main.statistics.HeroStatistics;

public class LeaveGameRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, 
			RequestProcessor processor, long chatId)
	{
		if (processor.userInQueue(chatId))
			processor.clearGameQueue();
		if (processor.gameExists(chatId))
			processor.endGame(chatId);
		return new RequestResult("Вы покинули игру", RequestType.USERLEAVEQUEUE, chatId);
	}
}
