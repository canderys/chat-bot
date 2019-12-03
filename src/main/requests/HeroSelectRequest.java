package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroSelectRequest implements Request {
	
	private String heroSide = "radiant";
	
	public HeroSelectRequest(String side)
	{
		heroSide = side;
	}
	
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{
		if (processor.addHeroToTeam(heroSide, splittedRequest))
			return new RequestResult("�� ������� ������� �����", RequestType.HEROSELECT);
		return new RequestResult("������ ����� �� ����������", RequestType.SELECTERROR);
	}
}