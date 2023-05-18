public class QuickFind implements UnionFind
{
	private int[] id;
	
	public QuickFind(int n)
	{
		id = new int[n];
		for(int i=0; i<n; i++)
		{
			id[i] = i;
		}
	}
	
	public void union(int p, int q)
	{
		int idAtP = id[p];
		
		if(id[p]-id[q] != 0)
		{
			for(int i=0; i<id.length; i++)
			{
				if(id[i] == idAtP)
				{
					id[i] = id[q];
				}
			}
		}
	}
	
	public int find(int p)
	{
		return id[p];
	}
}
