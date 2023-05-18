import java.util.ArrayList;

//VERTEX = NODE itself
//EDGE = CONNECTION BETWEEN TWO NODES

public class AdjMatGraph
{
	private int[][] array;
	private int vertices;
	private int edges;
	
	@SuppressWarnings("unchecked")
	public AdjMatGraph(int V)
	{
		array = new int[V][V];
		vertices = V;
		edges = 0;
		
		for(int i=0; i<vertices; i++)
		{
			for(int j=0; j<vertices; j++)
			{
				array[i][j] = 0;
			}
		}
	}
	
	//v is first endpoint of an edge, w is second endpoint of an edge; RUN TIME O(1)
	public void addEdge(int v, int w)
	{
		if(!(v < array.length && w < array.length) || !(v > -1 && w > -1))
		{
			throw new UnsupportedOperationException("Illegal v or w values.");
		}
		
		array[v][w] = 1;
		array[w][v] = 1;
		edges++;
	}
	
	//return a list of all neighbors connected to v by edges; RUN TIME O(V)
	public Iterable<Integer> adj(int v)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i=0; i<vertices; i++)
		{
			if(array[v][i] != 0)
			{
				list.add(i);
			}
		}
		
		return list;
	}
	
	public int V()
	{
		return vertices;
	}
	
	public int E()
	{
		return edges;
	}
}
