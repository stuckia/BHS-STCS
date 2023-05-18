import java.util.ArrayList;
import java.util.Iterator;

public class AcyclicSP
{
	private double[] distTo;
	private int[] prev;
	
	public AcyclicSP(EdgeWeightedDigraph digraph, int start)
	{
		distTo = new double[digraph.V()];
		prev = new int[digraph.V()];
		
		IndexMinPQ<Double> greenNodes = new IndexMinPQ<Double>(digraph.V());
		
		//FIRST PERFORM TOPOLOGICAL SORT ON ALL NODES
		Stack<Integer> topOrder = topOrderReversed(start, digraph);
		
		for(int i = 0; i < digraph.V();  i++)
		{
			prev[i] = -1;
			
			distTo[i] = Double.POSITIVE_INFINITY;
		}
		
		distTo[start] = 0.0;
		greenNodes.insert(start, 0.0);
		
		acyclicRecursion(digraph, start, greenNodes, topSort(new Stack<Integer>(), new boolean[digraph.V()], start, digraph));
	}
	
	public void acyclicRecursion(EdgeWeightedDigraph digraph, int start, IndexMinPQ<Double> greenNodes, Stack<Integer> topOrder)
	{
		int head = topOrder.pop();
		
		for(DirectedEdge edge : digraph.adj(head))
		{
			int to = edge.to();
			double newDist = distTo[head] + edge.weight();
			
			if(distTo[to] > newDist)
			{
				prev[to] = head;
				distTo[to] = newDist;
				
				if(!greenNodes.contains(to))
				{
					greenNodes.insert(to, distTo[to]);
				}
				else
				{
					greenNodes.decreaseKey(to, distTo[to]);
				}
			}
		}
	}
	
	public Stack<Integer> topOrderReversed(int start, EdgeWeightedDigraph digraph)
	{
		Stack<Integer> topOrderOrig = topSort(new Stack<Integer>(), new boolean[digraph.V()], start, digraph);
		Stack<Integer> topOrderRev = new Stack<Integer>();
		
		while(!topOrderOrig.isEmpty())
		{
			topOrderRev.push(topOrderOrig.pop());
		}
		
		return topOrderRev;
	}
	
	
	private Stack<Integer> topSort(Stack<Integer> topOrder, boolean[] visited, int vertex, EdgeWeightedDigraph digraph)
	{
		if(!visited[vertex])
		{
			visited[vertex] = true;
			
			Iterator<DirectedEdge> it = digraph.adj(vertex).iterator();
			
			while(it.hasNext())
			{
				int newVertex = vertex++;
				if(vertex++ == digraph.V())
				{
					newVertex = 0;
				}
				
				topOrder = topSort(topOrder, visited, newVertex, digraph);
			}
			
			topOrder.push(vertex);
		}
		
		return topOrder;
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
