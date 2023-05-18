public class T9
{
	private Trie trie;
	private String sequence;
	// Initializes state of the object by reading the specified file
	// of words.
	public T9(String dictionary)
	{
        In in = new In("testinput/" + dictionary);
        String[] words = in.readAllStrings();
                
        trie = new Trie();
        sequence = null;
        
        for (String word : words)
        {
        	trie.addWord(word);
        }
	}
	
	// This simulates the pressing of a key on the phone.  Normally, this
	// updates the internal state by appending num to the end of the current
	// digit sequence, and then returns true.  However, if appending num
	// to the current digit sequence would produce a sequence
	// that does not spell or prefix any words in the dictionary, then
	// the internal state is left unmodified, and this returns false.
	// You may assume 2 <= num <= 9.
	public boolean press(int num)
	{
		if(num < 2 || num > 9)
		{
			throw new IllegalArgumentException("illegal num value inputted");
		}
		
		if(getCurrentDigitSequence().equals(""))
		{
			sequence = "" + num;
		}
		else
		{
			sequence += num;
		}
		
		if(trie.getWordsSpelled(sequence) == null && trie.getWordsPrefixed(sequence) == null)
		{
			return false;
		}
		
		return true;
	}
	
	// This simulates the pressing of the backspace key on the phone.  Normally,
	// this updates the internal state by removing the last digit from the
	// current digit sequence, and then returns true.  However, if the
	// internal state did not contain any digits on entry to this method, then
	// the internal state is left unmodified, and this returns false.
	public boolean back()
	{
		if(sequence.length()>1)
		{
			sequence = sequence.substring(0,sequence.length()-1);
			return true;
		}
		else if(sequence.length() == 0)
		{
			clear();
			return false;
		}
		
		clear();
		return true;
	}
	
	// Returns the sequence of digits pressed by the user.  If no digits
	// are currently stored, this returns the empty String "".
	public String getCurrentDigitSequence()
	{
		if(sequence == null)
		{
			return "";
		}
		
		return sequence;
	}
	
	// This simulates the user choosing to reset and start over with a new
	// word (typically in response to the user selecting a previously spelled
	// word to appear in the text message, ready to begin typing the next word).
	// This clears the state such that a subsequent call to
	// getCurrentDigitSequence would return "". 
	public void clear()
	{
		sequence = "";
	}
	
	// This returns all words spelled by the current digit sequence
	public Iterable<String> getWordsSpelled()
	{
		throw new UnsupportedOperationException();
	}
	
	// Normally, this returns all words *prefixed* by the current digit
	// sequence.  However, if the number of such words is greater than max,
	// this returns an empty Iterable.  This method should not return words
	// that are exactly spelled out by the sequence of digits pressed by the
	// user (i.e., words that would be returned by getWordsSpelled).
	public Iterable<String> getWordsPrefixed(int max)
	{
		throw new UnsupportedOperationException();
	}
}
