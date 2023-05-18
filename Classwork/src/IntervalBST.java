import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class IntervalBST
{
    private Node root;             // root of BST

    private class Node 
    {
        private Interval1D interval;         // associated data
        private Node left, right;  // left and right subtrees
        private double maxEndpoint; // max of all right endpoints rooted here
        private int N;             // number of nodes in subtree

        public Node(Interval1D val, int N) 
        {
            this.interval = val;
            this.maxEndpoint = 0;
            this.N = N;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() 
    {
        return size() == 0;
    }

    // return number of key-value pairs in BST
    public int size() 
    {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) 
    {
        if (x == null)
        {
            return 0;
        }
        return x.N;
    }


    // return an Iterable of ALL Interval1D's found in the tree that
    // intersect with an interval whose endpoints are lo and hi
    public Iterable<Interval1D> intersects(Interval1D interval) 
    {
    	ArrayList<Interval1D> intersect = new ArrayList<Interval1D>();
    	return intersects(intersect, interval, root);
    }
    
    private ArrayList<Interval1D> intersects(ArrayList<Interval1D> intersect, Interval1D interval, Node x)
    {
    	if(x == null || interval == null)
    	{
    		throw new UnsupportedOperationException();
    	}
    	
    	if(x.interval.intersects(interval))
    	{
    		intersect.add(x.interval);
    	}
    	
    	if(interval.left() <= x.interval.right())
    	{
    		return intersects(intersect, interval, x.left);
    	}
    	else if(x.interval.left() <= interval.right())
    	{
    		return intersects(intersect, interval, x.right);
    	}
    	else
    	{
    		return intersect;
    	}
    }

    public Interval1D find(double leftEndpoint)
    {
        return find(root, leftEndpoint);
    }

    private Interval1D find(Node current, double leftEndpoint)
    {
        if (current == null)
        {
            return null;
        }

        if (leftEndpoint == current.interval.left())
        {
            return current.interval;
        }

        if (leftEndpoint < current.interval.left())
        {
            return find(current.left, leftEndpoint);
        }

        return find(current.right, leftEndpoint);
    }    

    /***********************************************************************
     *  Insert key-value pair into BST
     *  If key already exists, update with new value
     ***********************************************************************/
    public void insert(Interval1D val) 
    {
        root = insert(root, val);
    }

    private Node insert(Node x, Interval1D val)
    {
        if (x == null) 
        {
            return new Node(val, 1);
        }

        double key = val.left();

        x.maxEndpoint = Math.max(x.maxEndpoint, val.right());
        if (key < x.interval.left())
        {
            x.left  = insert(x.left,  val);
        }
        else if (key > x.interval.left())
        {
            x.right = insert(x.right, val);
        }
        else
        {
            x.interval   = val;
        }
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    /*****************************************************************************
     *  Test client
     *****************************************************************************/
    public static void main(String[] args)
    {
    	// Change these constants to change the characteristics
    	// of the test cases
        final int NUM_LINES = 75;
        final double SCALE = 200;
        final double MAX_SEGMENT_LENGTH = 75;
        final int CANVAS_SIZE = 500;
        final int SLEEP_MS = 5;

        StdDraw.setCanvasSize(CANVAS_SIZE, CANVAS_SIZE);
        StdDraw.setXscale(0, SCALE);
        StdDraw.setYscale(0, SCALE);
        StdDraw.clear();
        StdDraw.setPenRadius(0.0062);
        StdDraw.setPenColor(StdDraw.BLACK);

        IntervalBST tree = new IntervalBST();
        HashMap<Double, Double> leftToY = new HashMap<Double, Double>(); 

        // Generate random segments, add them to the
        // IntervalBST
        int y = 0;
        for (int i=0; i < NUM_LINES; i++)
        {
            int x = (int) (Math.random() * (SCALE - MAX_SEGMENT_LENGTH));
            if (leftToY.containsKey((double) x))
            {
                // Already have an interval starting at x.  Try again
                i--;
                continue;
            }
            
            int length = (int) (Math.random() * MAX_SEGMENT_LENGTH);
            
            Interval1D interval = new Interval1D(x, x + length);
            drawInterval(interval, y);
            tree.insert(interval);
            leftToY.put((double) x, (double) y);
            
            y += 2;
        }
        
        // Slide a test interval back and forth while
        // marking intersecting intervals
        final double TEST_INTERVAL_LENGTH = MAX_SEGMENT_LENGTH / 4;
        final double TEST_INTERVAL_Y = 200;
        Interval1D testInterval = null;
        Iterable<Interval1D> intersectors = null;
        boolean fMovingRight = true;
        double testIntervalLeft = 0;
        while (true)
        {
        	if (fMovingRight && testIntervalLeft >= SCALE - TEST_INTERVAL_LENGTH)
        	{
        		fMovingRight = false;
        		testIntervalLeft = SCALE - TEST_INTERVAL_LENGTH - 1;
        	}
        	else if (!fMovingRight && testIntervalLeft < 0)
        	{
        		fMovingRight = true;
        		testIntervalLeft = 0;
        	}
        	
            // Erase prior iteration from StdDraw
            if (testInterval != null)
            {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setPenRadius(0.0100);
                drawInterval(testInterval, TEST_INTERVAL_Y);
                StdDraw.setPenRadius(0.0062);
            }
            
            // Erase highlighting of prior intersectors
            if (intersectors != null)
            {
                StdDraw.setPenColor(StdDraw.BLACK);
                drawIntersectors(intersectors, leftToY);
            }
            
            // Draw test interval
            testInterval = new Interval1D(testIntervalLeft, testIntervalLeft + TEST_INTERVAL_LENGTH);
            StdDraw.setPenColor(StdDraw.BLUE);
            drawInterval(testInterval, TEST_INTERVAL_Y);
            
            // Highlight intersectors
            intersectors = tree.intersects(testInterval);
            StdDraw.setPenColor(StdDraw.RED);
            drawIntersectors(intersectors, leftToY);
            
            if (fMovingRight)
            {
            	testIntervalLeft++;
            }
            else
            {
            	testIntervalLeft--;
            }
            
            StdDraw.show(SLEEP_MS);
        }
    }
    
    private static void drawInterval(Interval1D interval, double y)
    {
        StdDraw.line(interval.left(), y, interval.right(), y);
    }
    
    private static void drawIntersectors(Iterable<Interval1D> intersectors, HashMap<Double, Double> leftToY)
    {
        for (Interval1D interval : intersectors)
        {
            drawInterval(interval, leftToY.get(interval.left()));
        }
    }
}
