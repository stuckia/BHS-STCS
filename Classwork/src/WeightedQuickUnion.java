public class WeightedQuickUnion implements UnionFind
{
	private int[] id;
	private int[] size;
	
	public WeightedQuickUnion(int n)
	{
		id = new int[n];
		size = new int[n];
		
		for(int i=0; i<n; i++)
		{
			id[i] = i;
			size[i] = 1;
		}

	}
	
	public void union(int p, int q)
	{
		//must include at MOST 2 calls to find
		int baseValueOfP = find(p);
		int baseValueOfQ = find(q);
		
		if(baseValueOfP != baseValueOfQ)
		{
			if(size[baseValueOfP] < size[baseValueOfQ])
			{
				id[baseValueOfP]= baseValueOfQ;
				size[baseValueOfQ] = size[baseValueOfQ] + size[baseValueOfP];
			}
			else
			{
				id[baseValueOfQ] = baseValueOfP;
				size[baseValueOfP] = size[baseValueOfP] + size[baseValueOfQ];
			}
		}
		else
		{
			return;
		}
	}
	
	
	public int find(int p)
	{
		//finds the base value of p
		while(id[p] != p)
		{
			p = id[p];
		}
		return p;
	}
	
	public String valuesOfID()
	{
		System.out.println();
		for(int i=0; i<id.length; i++)
		{
			System.out.print(id[i] + " ");
		}
		return "";
	}
}
