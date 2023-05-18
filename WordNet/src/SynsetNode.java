import java.util.ArrayList;

//my very own, self-implemented node system for managing the WordNet synsets
public class SynsetNode
{
	private ArrayList<String> synsets;
	private ArrayList<SynsetNode> children;
	private SynsetNode hypernyms;
	private int synCount;
	
  //constructor
	public SynsetNode()
	{
		synsets = new ArrayList<String>();
		hypernyms = null;
		children = new ArrayList<SynsetNode>();
		synCount = 1;
	}
	
	public void addWord(String word)
	{
		synsets.add(word);
		synCount++;
	}
	
	public Iterable<String> getSynsets()
	{
		return synsets;
	}
	
	public void addHyper(SynsetNode hyper)
	{
		hypernyms = hyper;
	}
	
	public SynsetNode getHyper()
	{
		return hypernyms;
	}
	
	public void addChild(SynsetNode child)
	{
		children.add(child);
	}
	
	public Iterable<SynsetNode> getChildren()
	{
		return children;
	}
	
	public void setCount(int count)
	{
		synCount = count;
	}
	
	public int getCount()
	{
		return synCount;
	}
}
