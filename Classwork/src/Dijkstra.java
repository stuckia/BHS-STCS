import java.util.ArrayList;

public class Dijkstra
{
	private double[] distTo;
	private int[] prev;
	
	public Dijkstra(EdgeWeightedDigraph digraph, int start)
	{
		distTo = new double[digraph.V()];
		prev = new int[digraph.V()];
		
		IndexMinPQ<Double> yellowNodes = new IndexMinPQ<Double>(digraph.V());
		
		for(int i = 0; i < digraph.V();  i++)
		{
			prev[i] = -1;
			
			distTo[i] = Double.POSITIVE_INFINITY;
		}
		
		distTo[start] = 0.0;
		yellowNodes.insert(start, 0.0);
	
		while(!yellowNodes.isEmpty())
		{
			int head = yellowNodes.delMin();
			
			for(DirectedEdge edge : digraph.adj(head))
			{
				int to = edge.to();
				double newDist = distTo[head] + edge.weight();
				
				if(distTo[to] > newDist)
				{
					prev[to] = head;
					distTo[to] = newDist;
					
					if(!yellowNodes.contains(to))
					{
						yellowNodes.insert(to, distTo[to]);
					}
					else
					{
						yellowNodes.decreaseKey(to, distTo[to]);
					}
				}
			}
		}
	}
	
	public double getDistTo(int vertex)
	{
		return distTo[vertex];
	}
	
	public Iterable<Integer> getPathTo(int vertex)
	{
		boolean reachedEnd = (prev[vertex] != -1);
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		while(!reachedEnd)
		{
			list = (ArrayList<Integer>) getPathTo(prev[vertex]);
		}
		
		list.add(vertex);
		
		return list;
	}
}
