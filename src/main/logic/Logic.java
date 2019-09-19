package main.logic;

import main.console.Console;
import main.questions.Generator;
import main.questions.Question;

public class Logic {

	public static void main(String[] args)
	{
		Console.Print("hello");
		Generator generator  = new Generator("questions.txt");
		for(Question question : generator.quetions)
		{
			Console.Print(question.Text);
			question.Answer =  Console.Read();
		}
	}
}
