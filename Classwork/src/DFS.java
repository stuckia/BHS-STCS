import java.util.HashSet;
import java.util.Iterator;

public class DFS 
{
    private boolean[] marked;  // marked[v] = true if v is reachable
    private Stack<Integer> topOrder;
    private HashSet<Integer> cycleSet;
    private boolean containsCycle;

    public DFS(Digraph G, int s) 
    {
        marked = new boolean[G.V()];
        topOrder = new Stack<Integer>();
        cycleSet = new HashSet<Integer>(s);
        containsCycle = false;
        dfs(G, s);
    }

    public DFS(Digraph G)
    {
    	marked = new boolean[G.V()];
    	topOrder = new Stack<Integer>();
    	cycleSet = new HashSet<Integer>(G.V());
    	containsCycle = false;
    	dfs(G, 0);
    }
    
    private void dfs(Digraph G, int v) 
    { 
    	if(!visited(v))
    	{
    		marked[v] = true;
    		cycleSet.add(v);
    		Iterator<Integer> it = G.adj(v).iterator();
    		
    		while(it.hasNext())
    		{
    			int w = it.next();
    			if(!visited(w))
    			{
    				dfs(G, w);
    			}
    		}
    		
    		topOrder.push(v);
    	}
    	if(cycleSet.contains(v))
    	{
    		containsCycle = true;
    	}
    	
    	marked[v] = false; 
    }

    public boolean visited(int v) 
    {
        if(marked[v] == true)
        {
        	return true;
        }
        
        return false;
    }
    
    public Iterable<Integer> topologicalOrder()
    {
    	//return stack
    	Stack<Integer> topOrderReversed = new Stack<Integer>();
    	
    	while(!topOrder.isEmpty())
    	{
    		topOrderReversed.push(topOrder.pop());
    	}
    	
    	return topOrderReversed;
    }

    public boolean hasCycles()
    {
    	return containsCycle;
    }
    
    public static void main(String[] args)
    {
    	Digraph graph = new Digraph(5);
    	
    	graph.addEdge(0, 1);
    	graph.addEdge(1, 3);
 
    	graph.addEdge(3, 4);
    	graph.addEdge(4, 0);
    	
    	DFS depthfs = new DFS(graph, 0);
    	
    	System.out.println("0 = " + depthfs.visited(0));
    	System.out.println("1 = " + depthfs.visited(1));
    	System.out.println("2 = " + depthfs.visited(2));
    	System.out.println("3 = " + depthfs.visited(3));
    	System.out.println("4 = " + depthfs.visited(4));
    	
    	System.out.println("Top Order: " + depthfs.topologicalOrder());
    	
    	DFS depthfs2 = new DFS(graph);
    	
    	System.out.println("0 = " + depthfs2.visited(0));
    	System.out.println("1 = " + depthfs2.visited(1));
    	System.out.println("2 = " + depthfs2.visited(2));
    	System.out.println("3 = " + depthfs2.visited(3));
    	System.out.println("4 = " + depthfs2.visited(4));
    	
    	System.out.println("Top Order2: " + depthfs2.topologicalOrder());
    }
}
