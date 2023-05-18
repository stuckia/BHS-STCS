import java.awt.Font;

// START *LEARNING* HERE!  If you want to learn about StdDraw, start here and
// trace the code as it executes from the main method below.

/**
 * This class demonstrates how to do some very simple animation.  It creates
 * two instances of SquareWithText, and moves them vertically and then
 * horizontally.  IMPORTANT: YOUR program must start in the StudentMain class, NOT
 * in this class.  We will NOT run your Demo class.
 *
 */
public class Demo 
{
	public static void main(String[] args) 
	{
		// You always start by choosing whatever coordinate resolution
		// you want with StdDraw.  For the demo, we'll let both x and y
		// range between 0-1000, but you can choose anything that's convenient for you
		StdDraw.setXscale(0, 1000);
		StdDraw.setYscale(0, 1000);
		
		// This is how you choose the font.
		StdDraw.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		// Create two instances of our own SquareWithText class.  We made
		// a draw method on SquareWithText, which demonstrates more StdDraw methods.
		SquareWithText box1 = new SquareWithText(100, 100, 100, "1");
		SquareWithText box2 = new SquareWithText(800, 800, 100, "2");
		
		// This loop animates both instances moving vertically
		for (int step = 0; step <= 50; step++)
		{
			// When you animate, you start by clearing the "off-screen"
			// buffer.  This is a hidden place where all of your drawings will
			// go.  Later, when you call StdDraw.show(n), everything drawn to the
			// "off-screen" buffer is switched into the on-screen window at once.
			// That's how you render one frame of your animation
			StdDraw.clear();
			
			// This calls methods on the SquareWithText class to change
			// its center y-coordinate by 10 pixels
			box1.setY(box1.getY() + 10);		// Go UP
			box2.setY(box2.getY() - 10);		// Go DOWN
			
			// Have the boxes draw themselves in their new locations
			// Go into SquareWithText.java to see how the draw method works
			box1.draw();
			box2.draw();
			
			// Display everything we drew in the off-screen buffer into the
			// window at once.  The 20 means to pause 20 milliseconds before
			// continuing.  Try changing this number to see what happens. 
			StdDraw.show(20);
		}
		
		// This loop animates both instances moving horizontally
		for (int step = 0; step <= 50; step++)
		{
			StdDraw.clear();
			
			box1.setX(box1.getX() + 10);	// Go RIGHT
			box2.setX(box2.getX() - 10);	// Go LEFT
			
			box1.draw();
			box2.draw();
			
			StdDraw.show(20);
		}
	}
}
