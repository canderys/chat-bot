package main.requests;

import java.util.List;

import main.statistics.HeroStatistics;

public class DraftGameRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, 
			RequestProcessor processor, long chatId)
	{
		if (!processor.addUserToGameQueue(chatId))
			return new RequestResult("Èùåì ñîïåðíèêà...\nÄëÿ âûõîäà â ìåíþ íàæìèòå êíîïêó Âûéòè", RequestType.USERINQUEUE, chatId);
		return new RequestResult("Ñîïåðíèê íàéäåí", RequestType.USERINQUEUE, chatId);
	}
}
