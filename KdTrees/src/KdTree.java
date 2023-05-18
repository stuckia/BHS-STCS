import java.awt.Color;
import java.util.ArrayList;

public class KdTree implements PointContainer
{
    private Node root;
	
	private static class Node
	{
		private Point2D p;		//this point
		private RectHV rect;	//the rectangle this point makes up
		private Node lb;		//left-bottom subtree
		private Node rt;		//right-top subtree
		
		private void drawPoint(Canvas canvas)
		{
			canvas.setPenRadius(.01);
			canvas.setPenColor(Color.BLACK);
			canvas.point(p.x(), p.y());
		}
		
		private void drawVertLine(Canvas canvas)
		{
			canvas.setPenRadius(.002);
    		canvas.setPenColor(Color.RED);
    		canvas.line(p.x(), rect.ymin(), p.x(), rect.ymax());
		}
		
		private void drawHorizLine(Canvas canvas)
		{
			canvas.setPenRadius(.002);
    		canvas.setPenColor(Color.BLUE);
    		canvas.line(rect.xmin(), p.y(), rect.xmax(), p.y());
		}
	}
    
    public boolean isEmpty()
    {
    	return (root == null);
    }
    
    public int size()
    {
    	return size(root);
    }
    
    private int size(Node node)
    {
    	if(node == null)
		{
			return 0;
		}
		
		int leftSize = size(node.lb);
		int rightSize = size(node.rt);
		
		return (1 + leftSize + rightSize);
    }
    
    public void insert(Point2D p)
    {
    	if(p == null)
    	{
    		throw new UnsupportedOperationException("Illegal p value given; Please try again :)");
    	}
    	else if(isEmpty())
    	{
    		root = new Node();
    		root.p = p;
    		root.rect = new RectHV(0,0,1,1);
    		return;
    	}
    	
    	Node newNode = new Node();
    	newNode.p = p;
    	insert(newNode, root, 0);
    }
    
    private void insert(Node newNode, Node current, int heightOfCurrent)
    {
    	if(current.p.compareTo(newNode.p) == 0)
    	{
    		return;
    	}
    	
    	if(heightOfCurrent % 2 == 0)	//comparing horizontally
    	{
    		if(newNode.p.x() <= current.p.x())	//newNode is to the left of (or same x of) current
    		{
    			if(current.lb == null)
    			{
    				newNode.rect = new RectHV(current.rect.xmin(), current.rect.ymin(), current.p.x(), current.rect.ymax());
    				current.lb = newNode;
    			}
    			else
    			{
    				insert(newNode, current.lb, heightOfCurrent + 1);
    			}
    		}
    		else	//newNode is to the right of current
    		{
    			if(current.rt == null)
    			{
    				newNode.rect = new RectHV(current.p.x(), current.rect.ymin(), current.rect.xmax(), current.rect.ymax());
    				current.rt = newNode;
    			}
    			else
    			{
    				insert(newNode, current.rt, heightOfCurrent + 1);
    			}
    		}
    	}
    	else	//comparing vertically
    	{
    		if(newNode.p.y() <= current.p.y())	//newNode is below (or same y of) current
    		{
    			if(current.lb == null)
    			{
    				newNode.rect = new RectHV(current.rect.xmin(), current.rect.ymin(), current.rect.xmax(), current.p.y());
    				current.lb = newNode;
    			}
    			else
    			{
    				insert(newNode, current.lb, heightOfCurrent + 1);
    			}
    		}
    		else	//newNode is to the top of current
    		{
    			if(current.rt == null)
    			{
    				newNode.rect = new RectHV(current.rect.xmin(), current.p.y(), current.rect.xmax(), current.rect.ymax());
    				current.rt = newNode;
    			}
    			else
    			{
    				insert(newNode, current.rt, heightOfCurrent + 1);
    			}
    		}
    	}
    }
    
    public boolean contains(Point2D p)
    {
    	if(p == null)
    	{
    		throw new UnsupportedOperationException("Illegal P value; Please try again :)");
    	}
    	
    	return contains(p, root, 0);
    }

    private boolean contains(Point2D p, Node current, int heightOfCurrent)
    {
    	if(current == null)
    	{
    		return false;
    	}
    	else if(p.compareTo(current.p) == 0)
    	{
    		return true;
    	}
    
    	if(heightOfCurrent % 2 == 0)	//comparing horizontally
    	{
    		if(p.x() <= current.p.x())	//p is to the left of or at current's x value
    		{
    			if(current.lb == null)
    			{
    				return false;
    			}
    			else
    			{
    				return contains(p, current.lb, heightOfCurrent + 1);
    			}
    		}
    		else	//p is to the right of current's x value
    		{
    			if(current.rt == null)
    			{
    				return false;
    			}
    			else
    			{
    				return contains(p, current.rt, heightOfCurrent + 1);
    			}
    		}
    	}
    	else	//comparing vertically
    	{
    		if(p.y() <= current.p.y())	//p is below or at current's y value
    		{
    			if(current.lb == null)
    			{
    				return false;
    			}
    			else
    			{
    				return contains(p, current.lb, heightOfCurrent + 1);
    			}
    		}
    		else	//p is above current's x value
    		{
    			if(current.rt == null)
    			{
    				return false;
    			}
    			else
    			{
    				return contains(p, current.rt, heightOfCurrent + 1);
    			}
    		}
    	}
    }

    public void draw(Canvas canvas)
    {
    	draw(canvas, root, 0);
    }
    
    private void draw(Canvas canvas, Node node, int currentHeight)
    {
    	if(node == null)
    	{
    		return;
    	}
    	
    	draw(canvas, node.lb, currentHeight + 1);
    	node.drawPoint(canvas);
    	
    	if(currentHeight % 2 == 0)	//even row in BST, vertical line
    	{
    		node.drawVertLine(canvas);
    	}
    	else	//odd row in BST, horizontal line
    	{
    		node.drawHorizLine(canvas);
    	}
    	
		draw(canvas, node.rt, currentHeight+1);
    }
    
        //return all points in the query rectangle :)
    public Iterable<Point2D> range(RectHV rect)
    {
    	if(rect == null)
    	{
    		throw new UnsupportedOperationException();
    	}
    	
    	ArrayList<Point2D> inRange = new ArrayList<Point2D>();
    	
    	return range(inRange, rect, root);
    }   
    
    private Iterable<Point2D> range(ArrayList<Point2D> inRange, RectHV rect, Node node)
    {
    	if(node == null)
    	{
    		return inRange;
    	}
    	
    	if(rect.intersects(node.rect))
    	{
    		if(inRange(rect, node) != null)
    		{
    			inRange.add(inRange(rect,node));
    			
    			inRange = (ArrayList<Point2D>) range(inRange, rect, node.lb);
    			inRange = (ArrayList<Point2D>) range(inRange, rect, node.rt);
    			
    			return inRange;
    		}
    		else
    		{
    			return inRange;
    		}
    	}
    	
    	return inRange;
    }
    
    private Point2D inRange(RectHV rect, Node node)
    {
    	if(rect.contains(node.p))
    	{
    		return node.p;
    	}
    	else
    	{
    		return null;
    	}
    }    
    
    public Point2D nearest(Point2D p)
    {
    	throw new UnsupportedOperationException();
    }     
}
