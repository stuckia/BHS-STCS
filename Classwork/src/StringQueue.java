public class StringQueue
{
	private Node head;
	private Node tail;
	
	public StringQueue()
	{
		head = null;
		tail = null;
	}
	
	//add to the tail in the list
	public void enqueue(String str)
	{
		Node newNode = new Node(str, head);
		
		//if there is nothing in the list yet, initialize head and tail to newNode :)
		if(head == null)
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			tail.setNext(newNode);
			tail = newNode;
		}
	}
	
	//remove head from the linked list
	public String dequeue()
	{
		String str = head.getValue();
		
		Node headNode = head;
		Node nextNode = headNode.getNext();
		
		//set the node after head as the new head
		head = nextNode;
		headNode.setNext(null);

		//return the first node
		return str;
	}
	
	private class Node
	{
		private Node next;
		private String value;
		
		//if this is not the first Node, thus adding on to the linked list
		public Node(String value, Node nextNode)
		{
			this.value = value;
			next = nextNode;
		}
		
		//returns the string value
		public String getValue()
		{
			return value;
		}
		
		//sets the next value
		public void setNext(Node nextNode)
		{
			next = nextNode;
		}
		
		//returns the next value
		public Node getNext()
		{
			return next;
		}
	}
}
