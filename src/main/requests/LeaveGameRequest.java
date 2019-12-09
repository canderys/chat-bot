package main.requests;

import java.util.List;

import main.statistics.HeroStatistics;

public class LeaveGameRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, 
			RequestProcessor processor, long chatId)
	{
		processor.leaveGame(chatId);
		return new RequestResult("Вы покинули игру", RequestType.USERLEAVEQUEUE, chatId);
	}
}
