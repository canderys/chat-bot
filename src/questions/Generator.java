package questions;
import java.io.*;
import java.util.Scanner;

import console.Console;

public class Generator {
	public Generator(String fileName)
	{
		String path  = "C:\\Users\\Саша\\eclipse-workspace\\char_bot\\src\\resources\\sosanya.txt";
		FileReader fr = new FileReader(path);
		char[] text = new char[90];
		fr.read(text);
	}
}
