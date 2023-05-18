import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WordNet
{
	private SynsetNode root;
	private HashMap<Integer, SynsetNode> mapByID;
	private HashMap<Integer, ArrayList<SynsetNode>> mapByHash;
	
	//should run in time O(N^2) at worst
    public WordNet(String synsets, String hypernyms, String synsetCounts)
    {
    	//initialize instance fields
    	mapByID = new HashMap<Integer, SynsetNode>();
    	mapByHash = new HashMap<Integer, ArrayList<SynsetNode>>();
    	
        // *** Parse synsets ***
        int largestId = -1;				
        In inSynsets = new In(synsets);
        while (inSynsets.hasNextLine())
        {
            String line = inSynsets.readLine();
            String[] tokens = line.split(",");
            
            // Synset ID
            int id = Integer.parseInt(tokens[0]);
            if (id > largestId)
            {
                largestId = id;
            }
            
            if(!mapByID.containsKey(id))
            {
            	mapByID.put(id, new SynsetNode());
            	
            	if(id == 0)
                {
                	root = mapByID.get(0);
                }
            }
            
            // Nouns in synset
            String synset = tokens[1];
            String[] nouns = synset.split(" ");
            for (String noun : nouns)
            {
            	//for each noun in the synset, add the word to the SynsetNode
            	mapByID.get(id).addWord(noun);
            	
            	if(!mapByHash.containsKey(noun.hashCode()))
            	{
            		mapByHash.put(noun.hashCode(), new ArrayList<SynsetNode>());
            	}
            	
            	mapByHash.get(noun.hashCode()).add(mapByID.get(id));
            }
            
            // tokens[2] is gloss, but we're not using that
        }
        inSynsets.close();
        
        // *** Parse hypernyms ***
        In inHypernyms = new In(hypernyms);
        while (inHypernyms.hasNextLine())
        {
            String line = inHypernyms.readLine();
            String[] tokens = line.split(",");
            
            int v = Integer.parseInt(tokens[0]);
            
            for (int i=1; i < tokens.length; i++)
            {
            	//for each line, add the proper hypernyms to their associated SynsetNodes
            	mapByID.get(Integer.parseInt(tokens[i])).addChild(mapByID.get(v));
            	mapByID.get(v).addHyper(mapByID.get(Integer.parseInt(tokens[i])));
            }
        }
        inHypernyms.close();
        
        // *** Parse SynsetCounts ***
		In inCounts = new In(synsetCounts);
		while (inCounts.hasNextLine())
		{
			String line = inCounts.readLine();
			String[] tokens = line.split(",");
			int synsetID = Integer.parseInt(tokens[0]);
			int count = Integer.parseInt(tokens[1]);
			
			//set each SynsetNode synset count
            mapByID.get(synsetID).setCount(count);
		}
		inCounts.close();
    }

	// returns all WordNet nouns
    public Iterable<String> nouns()
    {
    	ArrayList<String> nouns = new ArrayList<String>();
    	
    	return nouns(root, nouns);
    }
    
    private Iterable<String> nouns(SynsetNode node, ArrayList<String> nounList)
    {
    	if(node.getChildren().iterator() == null)
    	{
    		Iterator<String> nodeNouns = node.getSynsets().iterator();
    		
    		while(nodeNouns.hasNext())
    		{
    			nounList.add(nodeNouns.next());
    		}
    		
    		return nounList;
    	}
    	
    	Iterator<SynsetNode> it = node.getChildren().iterator();
    	
    	while(it.hasNext())
    	{
    		SynsetNode next = it.next();
    		
    		if(next.getChildren() != null)
    		{
    			nounList = (ArrayList<String>) nouns(next, nounList);
    		}
    	}
    	
    	Iterator<String> nodeNouns = node.getSynsets().iterator();
		
		while(nodeNouns.hasNext())
		{
			nounList.add(nodeNouns.next());
		}
    	
    	return nounList;
    }

	// is the word a WordNet noun? MUST BE CONSTANT TIME
    public boolean isNoun(String word)
    {
    	return mapByHash.containsKey(word.hashCode());
    }
    
	// Returns the aggregated frequency of the synset 
    public int getAggregatedFrequency(int synsetID)
	{
    	int aggFreq = 0;
    	HashMap<Integer, SynsetNode> checked = new HashMap<Integer, SynsetNode>();
    	return getAggFreq(mapByID.get(synsetID), aggFreq, checked);
	}
    
    //private recursive helper method to get the aggregated frequency
    private int getAggFreq(SynsetNode focus, int aggFreq, HashMap<Integer, SynsetNode> checked)
    {
    	aggFreq += focus.getCount();
    	checked.put(focus.hashCode(), focus);
    	
    	if(focus.getChildren().iterator() != null)
    	{
    		Iterator<SynsetNode> it = focus.getChildren().iterator();
    	
    		while(it.hasNext())
    		{
    			SynsetNode next = it.next();
    			
    			if(!checked.containsKey(next.hashCode()))
    			{
    				aggFreq = getAggFreq(next, aggFreq, checked);
    			}
    		}
    	}
    	
    	return aggFreq;
    }
    
    //returns the similarity score between two nouns
    public double getSimilarity(String noun1, String noun2)
	{
		if(!isNoun(noun1) || !isNoun(noun2))
		{
			throw new IllegalArgumentException("Noun values invalid, try again");
		}
		
		ArrayList<SynsetNode> node1list = mapByHash.get(noun1.hashCode());
		ArrayList<SynsetNode> node2list = mapByHash.get(noun2.hashCode());
		
		int lowestFreq = getAggFreq(root, 0, new HashMap<Integer, SynsetNode>());
		int highestFreq = lowestFreq;
		
		HashMap<Integer, SynsetNode> node1map = new HashMap<Integer, SynsetNode>();		
		for(SynsetNode node : node1list)
		{
			if(node == root)
			{
				node1map.put(root.hashCode(), root);
			}
			else if(node.getHyper() != null)
			{
				node1map = simRecursion(node.getHyper(), node1map);
			}
		}
		
		HashMap<Integer, SynsetNode> node2map = new HashMap<Integer, SynsetNode>();
		for(SynsetNode node : node2list)
		{
			if(node == root)
			{
				node1map.put(root.hashCode(), root);
			}
			else if(node.getHyper() != null)
			{
				node2map = simRecursion(node.getHyper(), node2map);
			}
		}
		
		for(SynsetNode node : node1map.values())
		{
			if(node2map.containsValue(node))
			{
				int aggFreq = getAggFreq(node, 0, new HashMap<Integer, SynsetNode>());
    			
    			if(aggFreq <= lowestFreq)
    			{
    				lowestFreq = aggFreq;
    			}
			}
		}
		
		if(noun1.equalsIgnoreCase(noun2))
		{
			return - Math.log((lowestFreq * 1.0)/(highestFreq * 1.0));
		}
		if(lowestFreq == highestFreq)
		{
			return Math.log((highestFreq * 1.0)/(mapByID.size() - 1));
		}
		
		return - Math.log((lowestFreq * 1.0)/(highestFreq * 1.0));
	}
    
	private HashMap<Integer, SynsetNode> simRecursion(SynsetNode current, HashMap<Integer, SynsetNode> map)
	{
		
		if(current.getHyper() != null)
		{
			map = simRecursion(current.getHyper(), map);
		}
		
		if(!map.containsKey(current.hashCode()))
		{
			map.put(current.hashCode(), current);
		}
		return map;		
	}
	
    // for unit testing of this class
    public static void main(String[] args)
    {
		String synsetsFile = "testInput/synsets-SpecGraph.txt";
		String hypernymsFile = "testInput/hypernyms-SpecGraph.txt";
		String synsetCountsFile = "testInput/SynsetCounts-SpecGraph.txt";
		
		WordNet wordnet = new WordNet(synsetsFile, hypernymsFile, synsetCountsFile);

		// Add additional testing code here		
		System.out.println(wordnet.getSimilarity("A", "C"));
    }
}
