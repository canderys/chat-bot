package main.requests;

public class RequestResult {
	private String text;
	private RequestType type;
	private long chatId;
	
	public RequestResult(String s, RequestType t, long id)
	{
		text = s;
		type = t;
		chatId = id;
	}
	
	public String getRequestText()
	{
		return text;
	}
	
	public RequestType getRequestType()
	{
		return type;
	}
	
	public Long getChatId()
	{
		return chatId;
	}
}
