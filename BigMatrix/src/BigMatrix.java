import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//REMEMBER: [ROW][COL]
public class BigMatrix 
{
	private HashMap<Integer, HashMap<Integer, Integer>>[] nzElements;
	private int rows, cols;
	
	@SuppressWarnings("unchecked")
	public BigMatrix()
	{
		rows = 2000000000;
		cols = rows;
		
		nzElements = (HashMap<Integer, HashMap<Integer, Integer>>[]) new HashMap[2];
		nzElements[0] = new HashMap<Integer, HashMap<Integer, Integer>>();
		nzElements[1] = new HashMap<Integer, HashMap<Integer, Integer>>();
	}
	
	//set a value at a given row/column position in the matrix, runs in constant time, will not store anything unnecessary
	public void setValue(int row, int col, int value)
	{	
		if(!nzElements[0].containsKey(row))
		{
			nzElements[0].put(row, new HashMap<Integer, Integer>());
		}
		
		nzElements[0].get(row).put(col, value);
		
		if(!nzElements[1].containsKey(col))
		{
			nzElements[1].put(col, new HashMap<Integer, Integer>());
		}
		
		nzElements[1].get(col).put(row, value);
		
		if(value == 0)
		{
			nzElements[0].get(row).remove(col);
			if(nzElements[0].get(row).size() == 0)
			{
				nzElements[0].remove(row);
			}
			
			nzElements[1].get(col).remove(row);
			if(nzElements[1].get(col).size() == 0)
			{
				nzElements[1].remove(col);
			}
		}
	}
	
	//returns the value at the given row/column position, returns 0 if no value, runs in constant time
	public int getValue(int row, int col)
	{		
		if(nzElements[0].get(row) == null || nzElements[0].get(col) == null)
		{
			return 0;
		}
		if(nzElements[0].get(row).get(col) == null || nzElements[1].get(col).get(row) == null)
		{
			return 0;
		}
		
		return nzElements[0].get(row).get(col).intValue();
	}
	
	//returns a list of the rows that have at least one column with a nonzero value
	public List<Integer> getNonEmptyRows()
	{
		if(nzElements[0].size() == 0)
		{
			return new ArrayList<Integer>();
		}
		
		List<Integer> nonEmptyRows = new ArrayList<Integer>(nzElements[0].keySet());
		
		return nonEmptyRows;
	}
	
	//returns a list of rows that have at least one nonzero value in the given column
	public List<Integer> getNonEmptyRowsInColumn(int col)
	{
		if(col<0 || !nzElements[1].containsKey(col))
		{
			return new ArrayList<Integer>();
		}
		
		List<Integer> nonEmptyRows = new ArrayList<Integer>(nzElements[1].get(col).keySet());
		
		return nonEmptyRows;
	}
	
	//returns a list of the columns that have at least one row with a nonzero value
	public List<Integer> getNonEmptyCols()
	{
		if(nzElements[1].size() == 0)
		{
			return new ArrayList<Integer>();
		}
		
		List<Integer> nonEmptyCols = new ArrayList<Integer>(nzElements[1].keySet());
		
		return nonEmptyCols;
	}
	
	//returns a list of columns that have at least one nonzero value in the given row
	public List<Integer> getNonEmptyColsInRow(int row)
	{
		if(row<0 || !nzElements[0].containsKey(row))
		{
			return new ArrayList<Integer>();
		}
		
		List<Integer> nonEmptyRows = new ArrayList<Integer>(nzElements[0].get(row).keySet());
		
		return nonEmptyRows;
	}
	
	//returns the sum of all entries in the given row
	public int getRowSum(int row)
	{
		if(row < 0 || row >= rows)
		{
			throw new UnsupportedOperationException();
		}
		
		if(!nzElements[0].containsKey(row))
		{
			return 0;
		}
		
		int rowSum = 0;
		
		List<Integer> nzElemsInRow = new ArrayList<Integer>(nzElements[0].get(row).values());
		
		Iterator<Integer> it = nzElemsInRow.iterator();
		
		while(it.hasNext())
		{
			rowSum += it.next();
		}
		
		return rowSum;
	}
	
	//returns the sum of all entries in the given column
	public int getColSum(int col)
	{
		if(col < 0 || col >= cols)
		{
			throw new UnsupportedOperationException();
		}
		
		if(!nzElements[1].containsKey(col))
		{
			return 0;
		}
		
		int colSum = 0;
		
		List<Integer> nzElemsInCol = new ArrayList<Integer>(nzElements[1].get(col).values());
		
		Iterator<Integer> it = nzElemsInCol.iterator();
		
		while(it.hasNext())
		{
			colSum += it.next();
		}
		
		return colSum;
	}
	
	//returns the sum of all entries in the matrix
	public int getTotalSum()
	{
		if(nzElements[0].size() == 0 || nzElements[1].size() == 0)
		{
			return 0;
		}
		
		int totalSum = 0;
		
		Iterator<Integer> nzRows = getNonEmptyRows().iterator();
		
		while(nzRows.hasNext())
		{
			int row = nzRows.next();
			Iterator<Integer> nzColsInRow = getNonEmptyColsInRow(row).iterator();
			
			while(nzColsInRow.hasNext())
			{
				int col = nzColsInRow.next();
				totalSum += getValue(row, col);
			}
		}
		
		return totalSum;
	}
	
	//returns a new matrix, with the values of the current matrix multiplied by the given constant
	public BigMatrix multiplyByConstant(int constant)
	{
		BigMatrix newMatrix = new BigMatrix();
		
		if(constant == 0)
		{
			return newMatrix;
		}
		
		Iterator<Integer> nzRows = getNonEmptyRows().iterator();
		
		while(nzRows.hasNext())
		{
			int row = nzRows.next();
			
			Iterator<Integer> nzColsInRow = getNonEmptyColsInRow(row).iterator();
			
			while(nzColsInRow.hasNext())
			{
				int col = nzColsInRow.next();
				newMatrix.setValue(row, col, constant * getValue(row, col));
			}
		}
		
		return newMatrix;
	}
	
	//returns a new matrix, consisting of the sum of the current matrix and the passed-in matrix
	public BigMatrix addMatrix(BigMatrix other)
	{
		BigMatrix newMatrix = new BigMatrix();
		
		Iterator<Integer> nzRowsOriginal = getNonEmptyRows().iterator();
		
		while(nzRowsOriginal.hasNext())
		{
			int row = nzRowsOriginal.next();
			
			Iterator<Integer> nzColsInRow = getNonEmptyColsInRow(row).iterator();
			
			while(nzColsInRow.hasNext())
			{
				int col = nzColsInRow.next();
				
				newMatrix.setValue(row, col, getValue(row, col) + other.getValue(row, col));
			}
		}
		
		Iterator<Integer> nzRowsOther = other.getNonEmptyRows().iterator();
		while(nzRowsOther.hasNext())
		{
			int rowOther = nzRowsOther.next();
			
			Iterator<Integer> nzColsInRowOther = other.getNonEmptyColsInRow(rowOther).iterator();
			
			while(nzColsInRowOther.hasNext())
			{
				int colOther = nzColsInRowOther.next();
				
				newMatrix.setValue(rowOther, colOther, getValue(rowOther, colOther) + other.getValue(rowOther, colOther));
			}
		}
		
		return newMatrix;
	}
}
