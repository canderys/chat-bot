package main.statistics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StatisticsLoader {
	private String path;
	public StatisticsLoader(String path)
	{
		this.path = path;
	}
	
	public String downloadStatistics()
	{
		HttpURLConnection connection = null;
		StringBuilder s = new StringBuilder();
		
		try 
		{
			connection = (HttpURLConnection) new URL(path).openConnection();
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			
			connection.connect();
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				String line;
				while ((line = reader.readLine()) != null)
				{
					s.append(line);
					s.append("\n");
				}
			}	
			else
			{
				throw new Exception("Error: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
			}
		} 
		catch (Throwable ex)
		{
			ex.printStackTrace();
		}
		finally 
		{
			if (connection != null)
				connection.disconnect();
		}
		return s.toString();
	}
}
