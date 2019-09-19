package main.questions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Generator {
	public ArrayList<Question> quetions = new ArrayList<Question>();
	public Generator(String fileName)
	{
		try
		{
			File file = new File("resources//" + fileName);			
			Scanner in = new Scanner(file);
			while (in.hasNextLine())
				quetions.add(new Question(in.nextLine()));
			in.close();
		}
		catch (FileNotFoundException ex)
		{
			System.err.println(ex);
		}
	}
}