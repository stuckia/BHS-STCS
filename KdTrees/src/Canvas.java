import java.awt.Color;

/**
 * A canvas to draw on.
 */
public interface Canvas 
{
	/**
	 * Set the x-scale (a 10% border is added to the values)
	 * @param min the minimum value of the x-scale
	 * @param max the maximum value of the x-scale
	 */
	void setXscale(double min, double max);

	/**
	 * Set the y-scale (a 10% border is added to the values).
	 * @param min the minimum value of the y-scale
	 * @param max the maximum value of the y-scale
	 */
	void setYscale(double min, double max);

	/**
	 * Display on screen, pause for t milliseconds, and turn on
	 * <em>animation mode</em>: subsequent calls to
	 * drawing methods such as <tt>line()</tt>, <tt>circle()</tt>, and <tt>square()</tt>
	 * will not be displayed on screen until the next call to <tt>show()</tt>.
	 * This is useful for producing animations (clear the screen, draw a bunch of shapes,
	 * display on screen for a fixed amount of time, and repeat). It also speeds up
	 * drawing a huge number of shapes (call <tt>show(0)</tt> to defer drawing
	 * on screen, draw the shapes, and call <tt>show(0)</tt> to display them all
	 * on screen at once).
	 * @param t number of milliseconds
	 */
	void show(int t);
	
    /**
     * Set the pen color to the given color. The available pen colors are
     * BLACK, BLUE, CYAN, DARK_GRAY, GRAY, GREEN, LIGHT_GRAY, MAGENTA,
     * ORANGE, PINK, RED, WHITE, and YELLOW.
     * @param color the Color to make the pen
     */
	void setPenColor(Color color);
	
    /**
     * Set the radius of the pen to the given size.
     * @param r the radius of the pen
     * @throws IllegalArgumentException if r is negative
     */
    void setPenRadius(double r);

	/**
	 * Draw a point at (x, y).
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 */
	void point(double x, double y);

	/**
	 * Draw a line from (x0, y0) to (x1, y1).
	 * @param x0 the x-coordinate of the starting point
	 * @param y0 the y-coordinate of the starting point
	 * @param x1 the x-coordinate of the destination point
	 * @param y1 the y-coordinate of the destination point
	 */
	void line(double x0, double y0, double x1, double y1);
}
