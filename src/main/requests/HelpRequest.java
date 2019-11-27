package main.requests;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import main.statistics.HeroStatistics;

public class HelpRequest implements Request {
	public RequestResult GetRequestResult(String splittedRequest, List<HeroStatistics> stat, RequestProcessor processor)
	{
		return new RequestResult("��� ����� ���������� ��������� ���������� ��� ������ ���� Dota 2. ���������� ������������ �� ������ ����� stratz.com \n"
				+ "��� ����� ��������� �������:\n"
				+ "1. help - �������� �������\n"
				+ "2. getstat [hero_name] - �������� ���������� ��� ����� [hero_name]\n"
				+ "3. getallstat - ���������� ������ ������ � ���������� ��� ���\n"
				+ "4. advicehero - ������� �������� ���������� ������ ��� ������ �������, �������� ��� ��������� ������.\n"
				+ "����� ����� ������� ��� ��������� � ����� ������ ������. ����� ������� �����, ����� ������������ �������:\n"
				+ "[dire/radiant] [hero_name] -\n"
				+ "4. exit - stop bot\n", RequestType.HELP);
	}
}
