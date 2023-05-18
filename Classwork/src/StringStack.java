public class StringStack
{
	private int maxSize;
	private int count;
	private String[] stack;
	
	public StringStack(int n)
	{
		maxSize = n;
		count = 0;
		stack = new String[maxSize];
	}
	
	public void push(String str)
	{
		stack[count] = str;
		count++;
	}
	
	public String pop()
	{
		String str = stack[count-1];
		count--;
		return str;
	}
	
	public static void main(String[] args)
	{
		StringStack string = new StringStack(4);
		
		string.push("eggs");
		string.push("bacon");
		string.push("grits");
		string.push("sausage");
		
		System.out.println(string.pop());
		System.out.println(string.pop());
		System.out.println(string.pop());
		System.out.println(string.pop());
	}
}
