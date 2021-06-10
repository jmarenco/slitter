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
	
	public static Solution cloned(Solution solution)
	{
		Solution ret = new Solution(solution.getInstance());
		ret._combinations = solution._combinations.clone();
		
		for(int i=0; i<solution.getInstance().getCombinations(); ++i)
			ret._items[i] = solution._items[i].clone();
		
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
		
		return ret + _instance.getItem(combination, item);
	}
	
	public void swap(int combination1, int combination2)
	{
		_combinations.swap(combination1, combination2);
	}

	public void swap(int combination, int item1, int item2)
	{
		_items[combination].swap(item1, item2);
	}
	
	public void insert(int combination1, int position)
	{
		_combinations.insert(combination1, position);
	}

	public void insert(int combination, int item1, int position)
	{
		_items[combination].insert(item1, position);
	}
	
	public Instance getInstance()
	{
		return _instance;
	}
	
	public int getCombination(int i)
	{
		return _combinations.get(i);
	}
	
	public boolean equals(Solution that)
	{
		if( that == null )
			return false;
		
		if( this.getInstance() != that.getInstance() )
			return false;
		
		if( this._combinations.equals(that._combinations) == false )
			return false;
		
		for(int i=0; i<_instance.getCombinations(); ++i)
		{
			if( this._items[i].equals(that._items[i]) == false )
				return false;
		}
		
		return true;
	}
}
