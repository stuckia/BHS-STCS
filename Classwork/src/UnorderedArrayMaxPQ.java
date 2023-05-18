import java.util.Arrays;

public class UnorderedArrayMaxPQ<Key extends Comparable<Key>>
{
    private Key[] elements;     // Store elements in an array
    private int N;              // number of elements currently stored

    public UnorderedArrayMaxPQ(int capacity)
    {    	
        // What we really want to do is say:
        //
        // pq = new Key[capacity]
        //
        // but Java won't let us do that.  (C# would, though.)
        elements = (Key[]) new Comparable[capacity];
    }

    public boolean isEmpty()
    {
        return N == 0;
    }

    public void insert(Key x)
    {
        // TODO: FILL THIS IN!
    	elements[N] = x;
    	N++;
    }

    public Key delMax()
    {
        // TODO: FILL THIS IN!  AND FIX THAT AWFUL RETURN STATEMENT!
    	Key maxKey = elements[0];
    	int maxIndex = 0;
    	for(int i = 1; i < elements.length; i++)
    	{
    		if(elements[i] != null)
    		{
    			if(!less(i, maxIndex))
    			{
    				maxKey = elements[i];
    				maxIndex = i;
    			}
    		}
    	}
    	
    	for(int i = maxIndex; i < elements.length; i++)
    	{
    		if(i != elements.length - 1)
    		{
    			elements[i] = elements[i + 1];
    		}
    		else
    		{
    			elements[i] = null;
    		}
    	}
    	
        return maxKey;
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
    
    public String toString()
    {
        return Arrays.toString(elements);
    }
    
    public static void main(String[] args)
    {
        int size = 10;
        UnorderedArrayMaxPQ<Integer> pq = new UnorderedArrayMaxPQ<Integer>(size);
        System.out.println("\n***\nINSERT CALLS\n***");
        for (int i=0; i < size; i++)
        {
            pq.insert((int)(Math.random() * size));
            System.out.println("Start");
            System.out.println(pq);
            System.out.println("End");
        }
        System.out.println("\n***\nDELMAX CALLS\n***");
        for (int i=0; i < size; i++)
        {
            System.out.print(pq.delMax() + " ");
        }
    }
}
