import java.awt.Color;
import java.util.Iterator;

public class PointSET implements PointContainer
{  
	private SET<Point2D> set;
	
	//constructs an empty set of points
	public PointSET()
	{
		set = new SET<Point2D>();
	}
	
	//is the set empty?
    public boolean isEmpty()
    {
    	return set.isEmpty();
    }
    
    //number of points in the set
    public int size()
    {
    	return set.size();
    }
    
    //add point to the set if it is not already in the set
    public void insert(Point2D p)
    {
    	if(p == null)
    	{
    		throw new UnsupportedOperationException();
    	}
    	
    	set.add(p);
    }
    
    //does the set contain point p
    public boolean contains(Point2D p)
    {
    	if(p == null)
    	{
    		throw new UnsupportedOperationException();
    	}
    	
    	return set.contains(p);
    }
    
    //draw all points to standard draw    
    public void draw(Canvas canvas)
    {
    	if(canvas == null)
    	{
    		throw new UnsupportedOperationException();
    	}
    	
        canvas.setPenColor(Color.BLACK);
        canvas.setPenRadius(.01);
        
        // TODO: Insert code here to call the point() method on canvas
        // for each point that has been inserted into your PointSET


    }
    
    //all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
    	if(rect == null)
    	{
    		throw new UnsupportedOperationException();
    	}
    	
    	SET<Point2D> inRange = new SET<Point2D>();
    	
    	Iterator<Point2D> it = set.iterator();
    	
    	while(it.hasNext())
    	{
    		Point2D next = it.next();
    		if(rect.contains(next))
    		{
    			inRange.add(next);
    		}
    	}
    	
    	return inRange;
    }
    
    //nearest neighbor to the set of p (return null if set is empty)
    public Point2D nearest(Point2D p)
    {
    	if(p == null)
    	{
    		throw new UnsupportedOperationException();
    	}
    	
    	if(isEmpty())
    	{
    		return null;
    	}
    	
    	Iterator<Point2D> it = set.iterator();
    	
    	Point2D champion = it.next();
    	
    	while(it.hasNext())
    	{
    		Point2D next = it.next();
    		
    		if(next.distanceTo(p) < champion.distanceTo(p))
    		{
    			champion = next;
    		}
    	}
    	
    	return champion;
    }
}
