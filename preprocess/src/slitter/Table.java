package slitter;

import java.util.HashMap;
import java.util.Map;

public class Table
{
	private Map<Key, Boolean> _map;
	
	public static class Key
	{
		public int items;
		public int width;
		
		public Key(int i, int w)
		{
			items = i;
			width = w;
		}
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + items;
			result = prime * result + width;
			return result;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (items != other.items)
				return false;
			if (width != other.width)
				return false;
			return true;
		}
	}
	
	public Table()
	{
		_map = new HashMap<Key, Boolean>();
	}
	
	public void set(int items, int width, boolean value)
	{
		Key key = new Key(items, width);
		_map.put(key, value);
	}
	
	public boolean get(int items, int width)
	{
		Key key = new Key(items, width);
		
		if (_map.containsKey(key) == false)
			throw new IllegalArgumentException("No such key!");
		
		return _map.get(key);
	}
}
