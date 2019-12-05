package main.requests;

import java.util.List;

import main.statistics.HeroStatistics;

public class DraftHeroRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, 
			RequestProcessor processor, long chatId)
	{
		if (processor.isRightTurn(chatId))
		{
			if (processor.setGameState(chatId, splittedRequest))
				return new RequestResult(processor.getGameState(chatId), RequestType.DRAFTHERO, chatId);
			return new RequestResult("Такого героя нет", RequestType.ERROR, chatId);
		}
		return new RequestResult("Не ваш ход", RequestType.ERROR, chatId);
	}
}
