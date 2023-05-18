import java.util.HashSet;
import java.util.Iterator;

// IF VERTICES ARE IN A PATH TOGETHER, THEY COUNT AS A CONNECTED COMPONENT


public class ConnectedComponents
{
    private boolean[] marked;  // marked[v] = true if v is reachable
    private HashSet[] connectedComps;
    private HashSet<Integer> hasBeenAdded;
    private int components;

    public ConnectedComponents(Graph G, int s) 
    {
        marked = new boolean[G.V()];
        connectedComps = new HashSet[s];
        hasBeenAdded = new HashSet<Integer>();
    	for(int i=0; i<s; i++)
    	{
    		connectedComps[i] = new HashSet<Integer>();
    	}
    	
        for(int i=0; i<s; i++)
        {
        	if(!hasBeenAdded.contains(i))
        	{
        		components++;
        	}
      
        	dfs(G, i);
        }
    }

    public ConnectedComponents(Graph G)
    {
    	marked = new boolean[G.V()];
    	connectedComps = new HashSet[G.V()];
    	hasBeenAdded = new HashSet<Integer>();
    	for(int i=0; i<G.V(); i++)
    	{
    		connectedComps[i] = new HashSet<Integer>();
    	}
    	
        for(int i=0; i<G.V(); i++)
        {
        	if(!hasBeenAdded.contains(i))
        	{
        		components++;
        	}
      
        	dfs(G, i);
        }
    }
    
    private void dfs(Graph G, int v) 
    { 
    	if(!visited(v))
    	{
    		if(!hasBeenAdded.contains(v))
    		{
    			hasBeenAdded.add(v);
    		}
    		
    		marked[v] = true;
    		Iterator<Integer> it = G.adj(v).iterator();
    		
    		while(it.hasNext())
    		{
    			int w = it.next();
    			
    			((HashSet<Integer>)connectedComps[v]).add(w);
    			
    			if(!visited(w))
    			{
    				dfs(G, w);
    			}
    		}
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
    
    //returns the component of the given vertex (indexed at 0)
    public Iterable<Integer> getComponent(int vertex)
    {
    	return ((HashSet<Integer>)connectedComps[vertex]);
    }
    
    //returns number of components in the graph
    public int getComponentCount()
    {
    	return components;
    }
    
    public static void main(String[] args)
    {
    	Graph graph = new Graph(6);
    	
    	graph.addEdge(0, 1);
    	graph.addEdge(1, 3);
    	graph.addEdge(3, 4);
    	graph.addEdge(2, 5);
    	
    	ConnectedComponents comp = new ConnectedComponents(graph);
    	
    	HashSet<Integer> connectZero = (HashSet<Integer>) comp.getComponent(0);
    	
    	for (Integer vertex : connectZero)
		{
			System.out.println("Connected to 0: " + vertex);
		}
    	
    	HashSet<Integer> connectTwo = (HashSet<Integer>) comp.getComponent(2);
    	
    	for(Integer vertex : connectTwo)
    	{
    		System.out.println("Connected to 2: " + vertex);
    	}
    	
    	System.out.println("Total connected components : " + comp.getComponentCount());
    	
    }
}
