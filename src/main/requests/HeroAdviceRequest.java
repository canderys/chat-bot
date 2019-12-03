package main.requests;

import java.util.List;
import main.statistics.HeroStatistics;

public class HeroAdviceRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{	
		return new RequestResult("������� ������, ��������� ����� ��������, � ����� ������, ��������� �������� ����������.\n"
				+ "���� ������� �������������� �� ���������, �� ������� end ����� ����� ���������� �����", RequestType.HEROADVICE);
	}
}