public class PlayerSmart implements Player
{
	private Board originalBoard;
	private int countWins;
	private int player;
	
	private int bestWins;
	private Location bestLocation;
	
	// Constructs a new instance of the PlayerSmart class
	public PlayerSmart()
	{
		countWins = 0;
		bestWins = 0;
	}

	// Returns the Location where this Player chooses to move
	@Override
	public Location getNextMove(Board board, int player)
	{		
		for(int i = 0; i < board.getRows(); i++)
		{
			for(int j = 0; j < board.getColumns(); j++)
			{	
				Location location = new Location(i,j);
				if(board.getPlayer(location) == 0)
				{
					originalBoard = new Board(board);
					this.player = player;
					testGameWins(location);
				}
			}
		}
		
		return bestLocation;
	}
	
	//runs the test game 100 times with the speculative next move, then if it is
	//better than the previous bestLocation, the speculative move replaces bestLocation
	public void testGameWins(Location speculativeNextMove)
	{
		countWins = 0;
		Board board = new Board(originalBoard);
		int rounds = 0;
		while(rounds < 100)
		{
			board.setPlayer(speculativeNextMove, player);
			testGame(board);
			board = new Board(originalBoard);
			rounds++;
		}
		
		if (countWins > bestWins)
		{
			bestLocation = speculativeNextMove;
		}
	}
		
	//runs a single test game and adds 1 to countWins if player wins
	public void testGame(Board board)
	{
		Board board1 = new Board(board);
		int play = player;
		while(board1.getCurrentWinner() == 0)
		{
			if(play == 1)
			{
				play = 2;
			}
			else
			{
				play = 1;
			}
			
			PlayerRandom randPlayer = new PlayerRandom();
			
			board1.setPlayer(randPlayer.getNextMove(board1, play), play);
			
			if(board1.getCurrentWinner() == player)
			{
				countWins++;
			}
		}
	}
}


// getNextMove should return the location that is most likely to get wins (highest scoring move)
