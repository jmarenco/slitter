package slitter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Instance
{
	private int[][] _items;
	private int[] _cardinality;
	
	public Instance(String file) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new FileInputStream(file));
		
		int combinations = scanner.nextInt();
		int maxItems = scanner.nextInt();
		
		_items = new int[combinations][maxItems];
		_cardinality = new int[combinations];
		
		for(int i=0; i<combinations; ++i)
		for(int j=0; j<maxItems; ++j)
		{
			_items[i][j] = scanner.nextInt();
			
			if (_items[i][j] > 0)
				_cardinality[i]++;
		}
		
		scanner.close();
	}
	
	public int getCombinations()
	{
		return _items.length;
	}
	
	public int getMaxItems()
	{
		return _items[0].length;
	}
	
	public int getItems(int i)
	{
		return _cardinality[i];
	}
	
	public int getItem(int i, int j)
	{
		if (j < 0 || j >= getItems(i) )
			throw new IllegalArgumentException("No such item!");
		
		return _items[i][j];
	}
}
