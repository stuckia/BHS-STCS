import java.awt.Color;

public class StdDrawCanvas implements Canvas
{
	@Override
	public void setXscale(double min, double max) 
	{
		StdDraw.setXscale(min, max);
	}

	@Override
	public void setYscale(double min, double max) 
	{
		StdDraw.setYscale(min, max);
	}

	@Override
	public void show(int t) 
	{
		StdDraw.show(0);
	}

	@Override
	public void setPenColor(Color color)
	{
		StdDraw.setPenColor(color);
	}
	
	@Override
    public void setPenRadius(double r)
    {
		StdDraw.setPenRadius(r);
    }
	
	@Override
	public void point(double x, double y) 
	{
		StdDraw.point(x, y);
	}

	@Override
	public void line(double x0, double y0, double x1, double y1) 
	{
		StdDraw.line(x0,  y0,  x1,  y1);
	}
}
