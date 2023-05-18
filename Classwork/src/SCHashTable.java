public class SCHashTable<TKey, TValue> 
{
	/**
	 * A node containing a key and a value.
	 */
	private class Node
	{	 
		public Node(TKey key, TValue value, Node next)
		{
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		TKey key;
		TValue value;
		Node next;
	}

	/**
	 * The number of items in the hash table
	 */
	private int size;

	/**
	 * The buckets of the hash table.
	 */
	private Node[] buckets;

	/**
	 * Initializes an empty hash table.
	 */
	public SCHashTable(int initialSize) 
	{
		buckets = new SCHashTable.Node[initialSize];
		size = 0;
	}

	public int hashCode(TKey key)
	{
		int hash = 17;
		
		for(int i=0; i<key.toString().length(); i++)
		{
			hash = 31 * hash + key.toString().charAt(i);
		}
		
		return hash % size;
	}
	
	/**
	 * Returns the value associated with the specified key 
	 * in this symbol table.
	 */
	public TValue get(TKey key) 
	{
		int hash = hashCode(key);
		
		if(buckets[hash].key == key)
		{
			return buckets[hash].value;
		}
		else
		{
			int i = hash + 1;
			if(i == buckets.length)
			{
				i = 0;
			}
			
			while(i != hash)
			{
				if(buckets[i].key == key)
				{
					return buckets[i].value;
				}
				else if(i == buckets.length - 1)
				{
					i = 0;
				}
			}
			
			return null;
		}
	} 

	/**
	 * Inserts the specified key-value pair into the symbol table, 
	 * overwriting the old value with the new value if the symbol table 
	 * already contains the specified key.
	 */
	public void put(TKey key, TValue value) 
	{
		if(size >= buckets.length / 2)
		{
			resize(buckets.length * 2);
		}
		
		Node newNode = new Node(key, value, null);
		int hash = hashCode(key);
		
		if(buckets[hash] == null)
		{
			buckets[hash] = newNode;
		}
		else if(buckets[hash].key == key)
		{
			buckets[hash].value = value;
		}
		else
		{
			boolean emptyFound = false;
			int i = hash + 1;
			if(i == buckets.length)
			{
				i = 0;
			}
			
			while(!emptyFound)
			{
				if(buckets[i] == null)
				{
					buckets[i] = newNode;
					emptyFound = true;
				}
				else if(i == buckets.length - 1)
				{
					i = 0;
				}
				else if(i == hash)
				{
					return;
				}
			}
		}
	}

	/**
	 * Resize the hash table, with a new bucket count.
	 */
	private void resize(int newBucketCount)
	{
		Node[] originalBuckets = buckets;
		buckets = new SCHashTable.Node[newBucketCount];
		
		for(int i = 0; i< originalBuckets.length; i++)
		{
			put(originalBuckets[i].key, originalBuckets[i].value);
		}
	}
}
