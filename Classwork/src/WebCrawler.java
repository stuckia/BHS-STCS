import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler
{
    private static final Pattern pattern = Pattern.compile("https://(\\w+\\.)*(\\w+)");

    public static Iterable<String> getReferencedUrls(String url)
    {
        ArrayList<String> ret = new ArrayList<String>();

        In in = new In(url);
        if (!in.exists())
        {
            return ret;
        }

        String input = in.readAll();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find())
        {
            ret.add(matcher.group());
        }
        return ret;
    }

	public static void main(String[] args)
    {
    	ArrayList<String> q = new ArrayList<String>();
    	
    	WebCrawler wc = new WebCrawler();
    	String root = "https://www.bsd405.org";
    	
    	q.add(root);
    	bfs(wc, q);
        // ********************************************************
        // TODO:  YOU WILL FILL IN THIS CODE.
        //
        // You will use Breadth First Search, with your starting vertex
        // being the URL in the root variable below.
        //
        // ALGORITHM:
        //
        // - Add root to a queue
        // - Repeat the following while the queue isn't empty:
        // -    Remove an URL from the queue
        // -    PRINT it
        // -    For all UNMARKED URLs that the dequeued URL points to:
        // -        Add them to the queue
        // -        Mark them
        //
        // (Use getReferencedUrls above to return an Iterable of all URLs
        // that the input URL points to.)
        //
        // BONUS: In addition to printing the URLs, also print their shortest
        // distance from the root.
        //
        // ********************************************************
        
        // NOTE!  Try other URLs for root if this one doesn't yield much
    }
    
    private static void bfs(WebCrawler wc, ArrayList<String> queue)
    {
    	while(queue.size() > 0)
    	{
    		String url = queue.get(0);
    		queue.remove(0);
    		
    		System.out.println(url);
    		
    		bfs(wc, (ArrayList<String>) WebCrawler.getReferencedUrls(url));
    	}
    }
}
