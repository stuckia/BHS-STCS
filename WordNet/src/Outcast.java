import java.util.Arrays;
import java.util.Scanner;

public class Outcast
{
	public Outcast(WordNet wordnet)
	{
		throw new UnsupportedOperationException();
	}

    // given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns)
	{
		throw new UnsupportedOperationException();
	}

	// for unit testing of this class
	public static void main(String[] args)
	{
		// TODO: Set outcastFiles to an array of outcast files to feed them all through
		// your Outcast object, OR set it to null so you can enter nouns
		// directly in the Console window
		//
		//String[] outcastFiles = { "testInput/outcast3.txt", "testInput/outcast5.txt" };
		String[] outcastFiles = null;

		String synsetsFile = "testInput/synsets.txt";
		String hypernymsFile = "testInput/hypernyms.txt";
		String synsetCountsFile = "testInput/SynsetCounts.txt";

		WordNet wordnet = new WordNet(synsetsFile, hypernymsFile, synsetCountsFile);
		Outcast outcast = new Outcast(wordnet);

		if (outcastFiles == null)
		{
			// Get the outcast test list interactively from the user
			Scanner console = new Scanner(System.in);
			while (true)
			{
				System.out.print("Enter a space-separated list of nouns: ");
				String[] nouns = console.nextLine().split(" ");
				StdOut.println("Outcast is: " + outcast.outcast(nouns));
			}
		}
		else
		{
			// Get the outcast test list from array
			for (int t = 0; t < outcastFiles.length; t++) 
			{
				// NOTE: Although Eclipse crosses out readStrings, it's ok to use.
				String[] nouns = In.readStrings(outcastFiles[t]);
				StdOut.println(outcastFiles[t] + ": " + Arrays.toString(nouns) + " --> " + outcast.outcast(nouns));
			}
		}
	}
}
