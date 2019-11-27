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
				+ "1. <b>help</b> - �������� �������\n"
				+ "2. <b>getstat</b> [<i>hero_name</i>] - �������� ���������� ��� ����� [<i>hero_name</i>]\n"
				+ "3. <b>getallstat</b> - ���������� ������ ������ � ���������� ��� ���\n"
				+ "4. <b>advicehero</b> - ������� �������� ���������� ������ ��� ������ �������, �������� ��� ��������� ������.\n"
				+ "����� ����� ������� ��� ��������� � ����� ������ ������. ����� ������� �����, ����� ������������ �������:\n"
				+ "[<b>dire</b>/<b>radiant</b>] [<i>hero_name</i>] - �������� ����� � ��������������� �������\n"
				+ "��� ���������� ������ ������ ������� <b>end</b>\n"
				+ "5. <b>getherolist</b> - ���������� ������ ���� ������.", RequestType.HELP);
	}
}
