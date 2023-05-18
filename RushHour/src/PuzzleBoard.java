import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PuzzleBoard
{
	// Do not change the name or type of this field
	private Vehicle[] idToVehicle;
	
	// You may add additional private fields here
	private UpdateableMinPQ<Integer> pq;
	private static int WIDTH;
	private static int HEIGHT;
	private HashMap<Integer, Vehicle> indexToVehicle;
	
	public PuzzleBoard(Vehicle[] idToVehicleP)
	{
		idToVehicle = idToVehicleP;
		WIDTH = 6;
		HEIGHT = 6;
		indexToVehicle = new HashMap<Integer, Vehicle>(WIDTH * HEIGHT);
		pq = new UpdateableMinPQ<Integer>();
		
		for(int i=0; i<idToVehicle.length; i++)
		{
			if(idToVehicle[i] != null)
			{
				int x = idToVehicle[i].getLeftTopColumn();
				int y = idToVehicle[i].getLeftTopRow();
				
				//if vehicle is horizontal
				if(idToVehicle[i].getIsHorizontal())
				{
					for(int j=0; j<idToVehicle[i].getLength(); j++)
					{
						indexToVehicle.put(getIndex(x+1, y), idToVehicle[i]);
					}
				}
				else
				{
					for(int j=0; j<idToVehicle[i].getLength(); j++)
					{
						indexToVehicle.put(getIndex(x, y+1), idToVehicle[i]);
					}
				}
			}
		}
	}
	
	public Vehicle getVehicle(int id)
	{
		if(idToVehicle[id] != null)
		{
			return idToVehicle[id];
		}
		else
		{
			return null;
		}
	}

	public Vehicle getVehicle(int row, int column)
	{
		if(indexToVehicle.containsKey(getIndex(row, column)))
		{
			return indexToVehicle.get(getIndex(row, column));
		}
		else
		{
			return null;
		}
	}
	
	private int getIndex(int row, int column)
	{
		return column + (WIDTH * row);
	}
	
	public int heuristicCostToGoal()
	{
		throw new UnsupportedOperationException();
	}
	
	public boolean isGoal()
	{
		throw new UnsupportedOperationException();
	}
	
	public Iterable<PuzzleBoard> getNeighbors()
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString()
	{
		// You do not need to modify this code, but you can if you really
		// want to.  The automated tests will not use this method, but
		// you may find it useful when testing within Eclipse
		
		String ret = "";
		for (int row=0; row < PuzzleManager.NUM_ROWS; row++)
		{
			for (int col=0; col < PuzzleManager.NUM_COLUMNS; col++)
			{
				Vehicle vehicle = getVehicle(row, col);
				if (vehicle == null)
				{
					ret += " . ";
				}
				else
				{
					int id = vehicle.getId(); 
					ret += " " + id;
					if (id < 10)
					{
						ret += " ";
					}
				}
			}
			ret += "\n";
		}
		
		for (int id = 0; id < PuzzleManager.MAX_NUM_VEHICLES; id++)
		{
			Vehicle v = getVehicle(id);
			if (v != null)
			{
				ret += "id " + v.getId() + ": " + 
						(v.getIsHorizontal() ? "h (" : "v (") + 
						v.getLeftTopRow() + "," + v.getLeftTopColumn() + "), " + v.getLength() + "  \n";
			}
		}
		
		return ret;
	}
	
	@Override
	public int hashCode()
	{
		// DO NOT MODIFY THIS METHOD
		
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(idToVehicle);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		// DO NOT MODIFY THIS METHOD
		
		if (this == obj)
		{
			return true;
		}
		
		if (obj == null)
		{
			return false;
		}
		
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		PuzzleBoard other = (PuzzleBoard) obj;
		if (!Arrays.equals(idToVehicle, other.idToVehicle))
		{
			return false;
		}
		return true;
	}
}
