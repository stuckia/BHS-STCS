public class MaxPQ<Key extends Comparable<Key>>
{
	// [0] is unused, [1] is the root (max) of the tree.  The
	// array never grows; client must not fill up beyond capacity.
	//
	// [k] can find its parent at [k/2], and its children at
	// [2*k] and [2*k + 1]
	private Key[] elements;
	
	// How many elements have been added
	private int N;

	public MaxPQ(int capacity)
	{
		elements = (Key[]) new Comparable[capacity];
	}

	public boolean isEmpty()
	{
		return N == 0;
	}

	// Used by insert().  Takes the element at k and swims it
	// up the tree, exchanging with its parent each time,
	// until it's high enough
	private void swim(int k)
	{
        // *************************************************************************************
		// TODO: IMPLEMENT THIS!
        // *************************************************************************************
		
		boolean done = false;
		int indexOfMovedKey = k;
		Key key = elements[k];
		
		while(!done)
		{
			if(k == 1)
			{
				done = true;
			}
			else
			{
				if(!less(indexOfMovedKey, k/2))
				{
					elements[indexOfMovedKey] = elements[k/2];
					elements[k/2] = key;
				}
				else
				{
					done = true;
				}
			}
		}
		
	}

	public void insert(Key x)
	{
        // *************************************************************************************
		// TODO: IMPLEMENT THIS!
        // *************************************************************************************
		
		if(isEmpty())
		{
			elements[1] = x;
		}
		else
		{
			elements[N+1] = x;
			swim(N+1);
		}
		
		N++;
	}

	// Used by delMax().  Takes the element at k and sinks
	// it down the tree, exchanging with its child each time,
	// until it's low enough.
	private void sink(int k)
	{
		while (2*k <= N)
		{
			int j = 2*k;
			if (j < N && less(j, j+1))
			{
				j++;
			}

			if (!less(k, j))
			{
				break;
			}

			exch(k, j);
			k = j;
		}
	}

	public Key delMax()
	{
		Key max = elements[1];
		exch(1, N);
		N--;
		sink(1);
		return max;
	}

	private boolean less(int i, int j)
	{
		return elements[i].compareTo(elements[j]) < 0;
	}

	private void exch(int i, int j)
	{
		Key swap = elements[i];
		elements[i] = elements[j];
		elements[j] = swap;
	}

	private String getSpaces(int n)
	{
		String ret = "";
		for (int i=0; i < n; i++)
		{
			ret += " ";
		}
		return ret;
	}

	public String toString()
	{
		String ret = "";
		int height = (int) (Math.log10(N) / Math.log10(2)) + 1;
		int iCur = 1;

		for (int level = 1; level <= height && iCur <= N; level++)
		{
			int lengthOfLevel = (int) Math.pow(2, level - 1);
			int spacesBetweenNodes = (int) Math.pow(2, height - level + 1) - 1;
			int leftPadding = (int) Math.pow(2, height - level) - 1;

			// Add left padding
			ret += getSpaces(leftPadding);

			for (int i=0; i < lengthOfLevel; i++)
			{
				ret += elements[iCur++] + getSpaces(spacesBetweenNodes);
				if (iCur > N)
				{
					break;
				}
			}
			ret += '\n';
		}

		return ret;
	}

	public static void main(String[] args)
	{
		int size = 32;

		MaxPQ<String> pq = new MaxPQ<String>(size);
		
		// Test inserts
		for (int i=1; i < size; i++)
		{
			pq.insert("" + (char)('A' + ((int)(Math.random() * 26))));
			System.out.println(pq);
			System.out.println();
		}        
	
		// Test deletes
		for (int i=1; i < size; i++)
		{
			System.out.println("Deleting max '" + pq.delMax() + "':");
			System.out.println(pq);
			System.out.println();
		}        
	}
}
