import java.util.Scanner;

public class Triangles
{
	public static void main(String[] args)
	{
		Triangles t = new Triangles();
		t.go();
	}
	
	public Triangles()
	{
		quit = false;
	}
	
	public void go()
	{
		while (quit == false)
		{
			printTriangle();
			askToQuit();
		}
	}
	
	private void printTriangle()
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < i; j++)
			{
				System.out.print(" . ");
			}
			System.out.println();
		}
	}
	
	private void askToQuit()
	{
		System.out.println("Should we quit [y/n]?");
		String input = console.next();
		if (input.equalsIgnoreCase("y"))
		{
			quit = true;
		}		
	}
	
	private boolean quit;
	Scanner console = new Scanner(System.in);
}
