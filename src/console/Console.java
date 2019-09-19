package console;
import java.util.Scanner;

public class Console {
	public static void Print(String s)
	{
		System.out.println(s);
	}
	
	public static String Read()
	{
		Scanner in = new Scanner(System.in);
		String a = in.nextLine();
		in.close();
		return a;
	}
}
