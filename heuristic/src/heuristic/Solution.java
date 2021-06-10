package heuristic;

public class Solution
{
	private Instance _instance;
	private Permutation _combinations;
	private Permutation[] _items;
	
	public static Solution shuffled(Instance instance)
	{
		Solution ret = new Solution(instance);
		ret._combinations = Permutation.shuffled(instance.getCombinations());
		
		for(int i=0; i<instance.getCombinations(); ++i)
			ret._items[i] = Permutation.shuffled(instance.getItems(i));
		
		return ret;
	}
	
	public static Solution identity(Instance instance)
	{
		Solution ret = new Solution(instance);
		ret._combinations = Permutation.identity(instance.getCombinations());
		
		for(int i=0; i<instance.getCombinations(); ++i)
			ret._items[i] = Permutation.identity(instance.getItems(i));
		
		return ret;
	}

	private Solution(Instance instance)
	{
		_instance = instance;
		_items = new Permutation[_instance.getCombinations()];
	}
	
	public int objective()
	{
		int ret = 0;
		for(int i=0; i<_instance.getCombinations()-1; ++i)
		{
			int combination = _combinations.get(i);
			int next = _combinations.get(i+1);
			
			for(int j=0; j<_instance.getItems(combination); ++j)
			for(int k=0; k<_instance.getItems(next); ++k)
			{
				if( position(combination, j) == position(next, k) )
					++ret;
			}
		}
		
		return ret;
	}
	
	public int position(int combination, int item)
	{
		int ret = 0;
		int i = 0;
		
		while( _items[combination].get(i) != item )
		{
			ret += _instance.getItem(combination, _items[combination].get(i));
			++i;
		}
		
		return ret + _items[combination].get(item);
	}
}
