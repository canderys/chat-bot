package logic;

import console.Console;
import questions.Generator;

public class Logic {

	public static void main(String[] args)
	{
		Console.Print("hello");
		String path = "C:\\Users\\Саша\\eclipse-workspace\\char_bot\\src\\resources\\sosanya.txt";
		Generator generator  = new Generator(path);
		
		while(true)
		{
			String question = Console.Read();
			
		}
	}
}
