package main.requests;

public class RequestResult {
	private String text;
	private RequestType type;
	
	public RequestResult(String s, RequestType t)
	{
		text = s;
		type = t;
	}
	
	public String getRequestText()
	{
		return text;
	}
	
	public RequestType getRequestType()
	{
		return type;
	}
}
