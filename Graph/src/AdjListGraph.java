import java.util.LinkedList;

public class AdjListGraph
{
	private LinkedList<Integer>[] list;
	private int vertices;
	private int edges;
	
	@SuppressWarnings("unchecked")
	public AdjListGraph(int V)
	{
		list = (LinkedList<Integer>[]) new LinkedList[V];
		vertices = V;
		edges = 0;		
	}
	
	public void addEdge(int v, int w)
	{
		if(!(v < list.length && w < list.length) || !(v > -1 && w > -1))
		{
			throw new UnsupportedOperationException("Illegal v or w values.");
		}
		
		list[v].add(w);
		list[w].add(v);
		edges++;
	}
	
	public Iterable<Integer> adj(int v)
	{
		if(!(v < list.length) || !(v > -1))
		{
			throw new UnsupportedOperationException("Illegal v value.");
		}
		
		return list[v];
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
