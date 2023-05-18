public class Board 
{
	public static final int PLAYER_NONE = 0;
	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 2;
	private static int rowNum;
	private static int colNum;
	
	private int[] array;
	
	private WeightedQuickUnionUFCloneable quickUnionP1;
	private WeightedQuickUnionUFCloneable quickUnionP2;
	
	// Constructs a new board with the specified number of rows and columns
	public Board(int rows, int columns)
	{
		rowNum = rows;
		colNum = columns;
		
		quickUnionP1 = new WeightedQuickUnionUFCloneable((rowNum*colNum) + 4);
		quickUnionP2 = new WeightedQuickUnionUFCloneable((rowNum*colNum) + 4);
		
		array = new int[(rowNum*colNum) + 4];
	}

	// Constructs a new Board that clones the state of the specified Board
	public Board(Board original)
	{
		rowNum = original.getRows();
		colNum = original.getColumns();
		
		quickUnionP1 = new WeightedQuickUnionUFCloneable((rowNum*colNum) + 4);
		quickUnionP2 = new WeightedQuickUnionUFCloneable((rowNum*colNum) + 4);
		
		array = new int[(rowNum*colNum) + 4];
		
		for(int i = 0; i<rowNum; i++)
		{
			for(int j = 0; j<colNum; j++)
			{
				Location location = new Location(i,j);
				setPlayer(location, original.getPlayer(location));	
			}
		}
	}

	// Returns the total number of rows in this Board
	public int getRows()
	{
		return rowNum;
	}

	// Returns the total number of columns in this Board
	public int getColumns()
	{
		return colNum;
	}

	// Returns one of the three "player" ints defined on this class
	// representing which player, if any, occupies the specified
	// location on the board
	public int getPlayer(Location location)
	{
		int returnValue = 0;
		if(location != null)
		{
			returnValue = array[(location.getRow()*colNum) + location.getColumn()];
		}
		
		return returnValue;
	}

	// Places a game piece from the specified player (represented by
	// one of the three "player" ints defined on this class) into the
	// specified location on the board
	public void setPlayer(Location location, int player) 
	{
		if(location != null)
		{
			array[(location.getRow()*colNum) + location.getColumn()] = player;
			int location1inUnion = (location.getRow()*colNum) + location.getColumn();
			WeightedQuickUnionUFCloneable quickUnion = null; 
			
			if(player == 1)
			{
				quickUnion = quickUnionP1;
			}
			else if(player == 2)
			{
				quickUnion = quickUnionP2;
			}
			
			if(location.getRow() != 0 && location.getColumn() != 0 && quickUnion != null)
			{
				Location location2 = new Location(location.getRow()-1, location.getColumn());
				int location2inUnion = (location2.getRow()*colNum) + location2.getColumn();
				if(player == getPlayer(location2))
				{
					quickUnion.union(location1inUnion, location2inUnion);
				}
			}
					
			if(location.getRow() != 0 && location.getColumn() != colNum - 1 && quickUnion != null)
			{
				Location location2 = new Location(location.getRow()-1, location.getColumn()+1);
				int location2inUnion = (location2.getRow()*colNum) + location2.getColumn();
				if(player == getPlayer(location2))
				{
					quickUnion.union(location1inUnion, location2inUnion);
				}
			}
			
			if(location.getColumn() != colNum - 1 && quickUnion != null)
			{
				Location location2 = new Location(location.getRow(), location.getColumn()+1);
				int location2inUnion = (location2.getRow()*colNum) + location2.getColumn();
				if(player == getPlayer(location2))
				{
					quickUnion.union(location1inUnion, location2inUnion);
				}
			}
			
			if(location.getRow() != rowNum - 1 && quickUnion != null)
			{
				Location location2 = new Location(location.getRow()+1, location.getColumn());
				int location2inUnion = (location2.getRow()*colNum) + location2.getColumn();
				if(player == getPlayer(location2))
				{
					quickUnion.union(location1inUnion, location2inUnion);
				}
			}				

			if(location.getRow() != rowNum - 1 && location.getColumn() != 0 && quickUnion != null)
			{
				Location location2 = new Location(location.getRow()+1, location.getColumn()-1);
				int location2inUnion = (location2.getRow()*colNum) + location2.getColumn();
				if(player == getPlayer(location2))
				{
					quickUnion.union(location1inUnion, location2inUnion);
				}
			}	

			if(location.getColumn() != 0 && quickUnion != null)
			{
				Location location2 = new Location(location.getRow(), location.getColumn()-1);
				int location2inUnion = (location2.getRow()*colNum) + location2.getColumn();
				if(player == getPlayer(location2))
				{
					quickUnion.union(location1inUnion, location2inUnion);
				}
			}
				
			connectWithSide(location, player);
		}
	}
		
	// Although the GameManager does not need to call this method, the
	// tests will call it to help verify that you're using the
	// union-find data structure correctly
	public boolean isConnected(Location location1, Location location2) 
	{
		int location1InQuickUnion = (location1.getRow()*colNum) + (location1.getColumn());
		int location2InQuickUnion = (location2.getRow()*colNum) + (location2.getColumn());
		
		boolean returnValue = false;
		
		if(getPlayer(location1) == 1 && getPlayer(location2) == 1)
		{
			returnValue = quickUnionP1.connected(location1InQuickUnion, location2InQuickUnion);
		}
		else if(getPlayer(location1) == 2 && getPlayer(location2) == 2)
		{
			returnValue = quickUnionP2.connected(location1InQuickUnion, location2InQuickUnion);
		}
		
		return returnValue;
	}
	
	//connects the player's point with the side
	public void connectWithSide(Location location, int player)
	{
		int locationInQuickUnion = (location.getRow()*colNum) + (location.getColumn());
		int secondLocationInQuickUnion = -1;
		
		if(player == 1)
		{
			if(location.getRow() == 0)
			{
				secondLocationInQuickUnion = rowNum*colNum;
			}
			else if(location.getRow() == rowNum-1)
			{
				secondLocationInQuickUnion = (rowNum*colNum) + 1;
			}
		}
		else if(player == 2)
		{
			if(location.getColumn() == 0)
			{
				secondLocationInQuickUnion = (rowNum*colNum) + 2;
			}
			else if(location.getColumn() == colNum-1)
			{
				secondLocationInQuickUnion = (rowNum*colNum) + 3;
			}
		}
		
		if(secondLocationInQuickUnion > -1)
		{
			array[secondLocationInQuickUnion] = player;
			if(player == 1)
			{
				quickUnionP1.union(locationInQuickUnion, secondLocationInQuickUnion);
			}
			else if(player == 2)
			{
				quickUnionP2.union(locationInQuickUnion, secondLocationInQuickUnion);
			}
		}
	}

	public String getQuickUnion()
	{
		String str1 = "P1: ";
		String str2 = "P2: ";
		for(int i = 0; i<(rowNum*colNum) + 4; i++)
		{
			str1 = str1 + ", " + quickUnionP1.find(i);
			str2 = str2 + ", " + quickUnionP2.find(i);
		}
		
		
		return str1 + "\n" + str2;
	}
	
	// Returns whether the specified location on the board contains
	// a game piece that is connected to one of the corresponding
	// player's sides.
	public int getSideConnection(Location location)
	{
		int locationInQuickUnion = (location.getRow()*colNum) + (location.getColumn());
		
		int returnValue = PLAYER_NONE;
		
		//checks to see if the player is connected to its corresponding sides
		if(quickUnionP1.connected(locationInQuickUnion, (colNum*rowNum) + 1) || quickUnionP1.connected(locationInQuickUnion, (colNum*rowNum)))
		{
			returnValue = PLAYER_1;
		}
		else if(quickUnionP2.connected(locationInQuickUnion, (colNum*rowNum) + 2) || quickUnionP2.connected(locationInQuickUnion, (colNum*rowNum) + 3))
		{
			returnValue = PLAYER_2;
		}
		
		return returnValue;
	}

	// Returns one of the three "player" ints indicating who is the winner
	// of the current Board.  PLAYER_NONE indicates no one has won yet.
	public int getCurrentWinner()
	{
		int returnValue = 0;
		
		if(quickUnionP1.connected(rowNum*colNum, (rowNum*colNum)+1))
		{
			returnValue = 1;
		}
		else if(quickUnionP2.connected((rowNum*colNum)+2, (rowNum*colNum)+3))
		{
			returnValue = 2;
		}
		
		return returnValue;
	}
}
