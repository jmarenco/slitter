package slitter;

public class IncompleteEqualizer
{
	private Instance _instance;
	private Table _table;
	
	private int _combination1;
	private int _combination2;
	private int _item1;
	private int _item2;
	
	public IncompleteEqualizer(Instance instance, int combination1, int item1, int combination2, int item2)
	{
		_instance = instance;
		_combination1 = combination1;
		_combination2 = combination2;
		_item1 = item1;
		_item2 = item2;
	}
	
	public boolean solve()
	{
		if( _instance.getItem(_combination1, _item1) == _instance.getItem(_combination2, _item2) )
			return true;
		
		_table = new Table();
		
		int items = Math.max(_instance.getItems(_combination1)-1, _instance.getItems(_combination2)-1);

//		System.out.println(_instance.getItem(_combination1, _item1));
//		System.out.println(_instance.getItem(_combination2, _item2));
		
		return recursion(items, _instance.getItem(_combination1, _item1) - _instance.getItem(_combination2, _item2), true);
	}
	
	private boolean recursion(int item, int difference, boolean skip)
	{
//		System.out.println("recursion(" + item + ", " + difference + ", " + skip + ") called");

		if( item < 0 )
			return difference == 0 && skip == false;
		
		if( _table.contains(item, difference, skip) )
		{
//			System.out.println("recursion(" + item + ", " + difference + ", " + skip + ") = " + _table.get(item, difference, skip));
			return _table.get(item, difference, skip);
		}
		
		int width1 = item < _instance.getItems(_combination1) && item != _item1 ? _instance.getItem(_combination1, item) : 0;
		int width2 = item < _instance.getItems(_combination2) && item != _item2 ? _instance.getItem(_combination2, item) : 0;
		
//		System.out.println("  w1 = " + width1);
//		System.out.println("  w2 = " + width2);
		
		boolean ret = false;
		
		if( width1 > 0 || width2 > 0 )
			ret = recursion(item-1, difference, false);
		else
			ret = recursion(item-1, difference, skip);
		
		if( width1 > 0 && width2 > 0 )
		{
			ret = ret || recursion(item-1, difference + width1 - width2, skip)
					  || recursion(item-1, difference + width1, false)
					  || recursion(item-1, difference - width2, false);
		}
		
		if( width1 > 0 && width2 == 0 )
			ret = ret || recursion(item-1, difference + width1, skip);
		
		if( width2 > 0 && width1 == 0 )
			ret = ret || recursion(item-1, difference - width2, skip);
		
		_table.set(item, difference, skip, ret);
//		System.out.println("recursion(" + item + ", " + difference + ", " + skip + ") = " + ret);
		return ret;
	}
}
