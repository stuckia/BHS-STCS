import java.util.ArrayList;
import java.util.HashMap;

public class Trie
{
	private TrieNode root;
    private HashMap<Character, Integer> map;

    private static class TrieNode
    {
    	public ArrayList<String> wordList = new ArrayList<String>();
    	public int value;
    	public TrieNode leftNode;
    	public TrieNode midNode;
    	public TrieNode rightNode;
    	

    	public TrieNode(int i)
    	{
    	    value = i;
    	    wordList = new ArrayList<String>();
    	}

    	public void addWord(String s)
    	{
    	    wordList.add(s);
    	}
    }
    
    public Trie() 
    {
    	map = new HashMap<Character, Integer>();
    	root = null;
    	
    	map.put('A',2);
		map.put('B',2);
		map.put('C',2);
		map.put('D',3);
		map.put('E',3);
		map.put('F',3);
		map.put('G',4);
		map.put('H',4);
		map.put('I',4);
		map.put('J',5);
		map.put('K',5);
		map.put('L',5);
		map.put('M',6);
		map.put('N',6);
		map.put('O',6);
		map.put('P',7);
		map.put('Q',7);
		map.put('R',7);
		map.put('S',7);
		map.put('T',8);
		map.put('U',8);
		map.put('V',8);
		map.put('W',9);
		map.put('X',9);
		map.put('Y',9);
		map.put('Z',9);
    }

    public int getNum(char i)
    {
        return map.get(i);
    }

    public void addWord(String word)
    {
    	throw new UnsupportedOperationException();
    }
    
    public ArrayList<String> getWordsSpelled(String sequence)
    {
    	throw new UnsupportedOperationException();
    }

    public ArrayList<String> getWordsPrefixed(String sequence)
    {
    	throw new UnsupportedOperationException();
    }
}
