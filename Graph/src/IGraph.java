public interface IGraph extends com.touchgraph.graphlayout.DrawableGraph
{
	/**
	 * Returns the number of vertices in the graph.
	 */
	int V();
	
	/**
	 * Returns the number of edges in the graph.
	 * @return
	 */
	int E();
	
	/**
	 * Adds an edge to the graph.
	 * @param v The first endpoint of the edge.
	 * @param w The second endpoint of the edge.
	 */
	void addEdge(int v, int w);
	
	/**
	 * Returns an iterable, containing all vertices
	 * adjacent to the given vertex.
	 * @param v The vertex whose adjacent vertices to return.
	 */
	Iterable<Integer> adj(int v);
}