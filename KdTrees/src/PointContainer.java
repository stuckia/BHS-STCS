
interface PointContainer 
{
    boolean isEmpty();
    
    int size();
    
    void insert(Point2D p);
    
    boolean contains(Point2D p);

    void draw(Canvas canvas);
    
    Iterable<Point2D> range(RectHV rect);
    
    Point2D nearest(Point2D p);
}
