package main.requests;

import java.util.List;

import main.statistics.HeroStatistics;

public class HelpRequest implements Request {
	public RequestResult getRequestResult(String splittedRequest, List<HeroStatistics> stat, 
			RequestProcessor processor, long chatId)
	{
		return new RequestResult("��� ����� ���������� ��������� ���������� ��� ������ ���� Dota 2. ���������� ������������ �� ������ ����� stratz.com \n"
				+ "��� ����� ��������� �������:\n"
				+ "1. <b>������</b> - �������� �������\n"
				+ "2. <b>����������</b> - �������� ���������� ��� �����\n"
				+ "3. <b>�����</b> - ������� �������� ���������� ������ ��� ������ �������, �������� ��� ��������� ������.\n"
				+ "4. <b>�����</b> - ���������� ������ ���� ������.\n"
				+ "6. <b>����</b> - ������ ���� DraftGame", RequestType.HELP, chatId);
	}
}
