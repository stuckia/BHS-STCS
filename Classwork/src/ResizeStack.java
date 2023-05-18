public class ResizeStack
{
	private int arraySize;
	private int count;
	private String[] stack;
	
	public ResizeStack()
	{
		arraySize = 1;
		count = 0;
		stack = new String[arraySize];
	}
	
	public void push(String str)
	{
		if(count == arraySize)
		{
			String[] stack2 = new String[arraySize*2];
			System.arraycopy(stack, 0, stack2, 0, stack.length);
			stack = stack2;
			arraySize = arraySize*2;
		}
		
		stack[count] = str;
		count++;
	}
	
	public String pop()
	{
		if(count <= .75 * arraySize)
		{
			String[] stack2 = new String[(int) Math.round(arraySize * .75)];
			System.arraycopy(stack, 0, stack2, 0, stack2.length);
			stack = stack2;
			arraySize = (int) Math.round(arraySize * .75);
		}
		
		String returnStr = stack[count - 1];
		stack[count - 1] = null;
		count--;
		
		return returnStr;
	}
	
	public static void main(String[] args)
	{
		ResizeStack resize = new ResizeStack();
		
		resize.push("eggs");
		resize.push("bacon");
		resize.push("grits");
		resize.push("sausage");
		
		System.out.println(resize.pop());
		System.out.println(resize.pop());
		System.out.println(resize.pop());
		System.out.println(resize.pop());
	}
}
