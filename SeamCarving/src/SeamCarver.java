import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SeamCarver
{
	private SmC_Picture originalPic;
	private SmC_Picture editedPic;
	
	public SeamCarver(SmC_Picture pictureP)
	{
		if(pictureP == null)
		{
			throw new NullPointerException("SmC_Picture provided is invalid, try again");
		}
		originalPic = pictureP;
		editedPic = pictureP;
	}

	public SmC_Picture picture()
	{
		return editedPic;
	}

	public int width()
	{
		return editedPic.width();
	}

	public int height()
	{
		return editedPic.height();
	}

	public double energy(int x, int y)
	{
		if(x<0 || y<0 || x>= width() || y>= height())
		{
			throw new IndexOutOfBoundsException("provided indices x and y are out of bounds, try again");
		}
		else if(x==0 || y==0 || x==width()-1 || y==height()-1)
		{
			return 1000.0;
		}
		
		//CALCULATE R
		int rx = editedPic.get(x+1, y).getRed() - editedPic.get(x-1, y).getRed();
		int ry = editedPic.get(x, y+1).getRed() - editedPic.get(x, y-1).getRed();
		
		//CALCULATE G
		int gx = editedPic.get(x+1, y).getGreen() - editedPic.get(x-1, y).getGreen();
		int gy = editedPic.get(x, y+1).getGreen() - editedPic.get(x, y-1).getGreen();
		
		//CALCULATE B
		int bx = editedPic.get(x+1, y).getBlue() - editedPic.get(x-1, y).getBlue();
		int by = editedPic.get(x, y+1).getBlue() - editedPic.get(x, y-1).getBlue();
		
		//COMBINE AND RETURN
		double calcX = Math.pow(rx*1.0, 2) + Math.pow(gx*1.0, 2) + Math.pow(bx*1.0, 2);
		double calcY = Math.pow(ry*1.0, 2) + Math.pow(gy*1.0, 2) + Math.pow(by*1.0, 2);
		
		return Math.sqrt(calcX + calcY);
	}

	public int[] findHorizontalSeam()
	{
		SmC_Picture rotatedPic = new SmC_Picture(height(), width());
		
		for(int i=0; i<width(); i++)
		{
			for(int j=0; j<height(); j++)
			{
				rotatedPic.set(j, i, editedPic.get(i, j));
			}
		}
		
		SeamCarver SC = new SeamCarver(rotatedPic);
		
		return SC.findVerticalSeam();
	}


	public int[] findVerticalSeam()
	{
		HashMap<Double, int[]> track = new HashMap<Double, int[]>();
		
		int[] path;
		
		for(int i=1; i<width(); i++)
		{
			path = new int[height()];
			double minE = 0.0;
			
			for(int j=1; j<height(); j++)
			{
				if(j==1)
				{
					path[0] = i;
					path[1] = i;
				}
				else if(j==height()-1)
				{
					path[j] = path[j-1];
				}
				else
				{
					int prevV = path[j-1];
					if(prevV == 0)
					{
						if(energy(prevV,j) < energy(prevV+1,j))
						{
							path[j] = prevV;
						}
						else
						{
							path[j] = prevV+1;
						}
					}
					else if(prevV == width()-1)
					{
						if(energy(prevV,j) < energy(prevV-1,j))
						{
							path[j] = prevV;
						}
						else
						{
							path[j] = prevV-1;
						}
					}
					else
					{
						if(Math.min(Math.min(energy(prevV,j), energy(prevV-1,j)), energy(prevV+1,j)) == energy(prevV,j))
						{
							path[j] = prevV;
						}
						else if(Math.min(Math.min(energy(prevV,j), energy(prevV-1,j)), energy(prevV+1,j)) == energy(prevV+1,j))
						{
							path[j] = prevV+1;
						}
						else
						{
							path[j] = prevV-1;
						}
					}
				}
			}
			
			for(int x=0; x<path.length; x++)
			{
				minE = minE + energy(path[x], x);
			}
			
			track.put(minE, path);		
		}
		
		Iterator<Double> list = track.keySet().iterator();
		double smallestE = list.next();
		
		while(list.hasNext())
		{
			double next = list.next();
			if(smallestE > next)
			{
				smallestE = next;
			}
		}
		
		return track.get(smallestE);
	}

	public void removeHorizontalSeam(int[] a)
	{
		if(a == null)
		{
			throw new NullPointerException("SmC_Picture provided is invalid, try again");
		}
		else if(a.length != width())
		{
			throw new IllegalArgumentException("a[] is invalid");
		}
		else if(width() <= 1)
		{
			throw new IllegalArgumentException("width invalid, try again");
		}
		
		SmC_Picture newPic = new SmC_Picture(width(), height()-1);
		
		for(int i=0; i<width(); i++)
		{	
			for(int k=0; k<height()-1; k++)
			{
				if(a[i] > height()-1 || a[i] < 0)
				{
					throw new IllegalArgumentException("illegal int[] a inputted");
				}
				else if(k < a[i])
				{
					newPic.set(i, k, editedPic.get(i, k));
				}
				else
				{
					newPic.set(i, k, editedPic.get(i, k+1));
				}
			}
		}
		
		editedPic = newPic;
	}

	public void removeVerticalSeam(int[] a)
	{
		if(a == null)
		{
			throw new NullPointerException("SmC_Picture provided is invalid, try again");
		}
		else if(a.length != height())
		{
			throw new IllegalArgumentException("a[] is invalid");
		}
		else if(height() <= 1)
		{
			throw new IllegalArgumentException("height invalid, try again");
		}
		
		SmC_Picture newPic = new SmC_Picture(width()-1, height());
		
		for(int i=0; i<height(); i++)
		{	
			for(int k=0; k<width()-1; k++)
			{
				if(a[i] > width()-1 || a[i] < 0)
				{
					throw new IllegalArgumentException("illegal int[] a inputted");
				}
				else if(k < a[i])
				{
					newPic.set(k, i, editedPic.get(k, i));
				}
				else
				{
					newPic.set(k, i, editedPic.get(k+1, i));
				}
			}
		}
		
		editedPic = newPic;
	}	
}
