public interface UnionFind
{
	//connects object p to object q
	void union(int p, int q);
	
	//returns the connected component of object p
	//postcondition: if vertices p and q are connected, find(p)==find(q)
	int find(int p);
}
