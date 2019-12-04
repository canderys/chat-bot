package main.console;
import java.util.Scanner;

public class Console {
	public static void print(String s)
	{
		System.out.println(s);
	}
	
	public static void print(int s)
	{
		System.out.println(Integer.toString(s));
	}
	
	public static void print(Boolean s)
	{
		System.out.println(s);
	}
	public static String read()
	{
		Scanner in = new Scanner(System.in);
		String a = in.next();
		in.close();
		return a;
	}

	public static String readLine()
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine())
			return in.nextLine();
		in.close();
		return "";
	}

	public static void print(double value) {
		System.out.println(Double.toString(value));
		
	}
}
