package heuristic;

import java.util.ArrayList;
import java.util.Collections;

public class Permutation
{
	private int[] _index;
	
	public static Permutation shuffled(int n)
	{
		Permutation ret = new Permutation(n);
		ret.shuffle();
		return ret;
	}
	
	public static Permutation identity(int n)
	{
		return new Permutation(n);
	}
	
	private Permutation(int n)
	{
		_index = new int[n];
		
		for(int i=0; i<n; ++i)
			_index[i] = i;
	}
	
	private Permutation(int[] index)
	{
		_index = index.clone();
	}
	
	public int size()
	{
		return _index.length;
	}
	
	public int get(int index)
	{
		return _index[index];
	}
	
	public void shuffle()
	{
		ArrayList<Integer> aux = new ArrayList<Integer>();

		for(int i=0; i<size(); ++i)
			aux.add(_index[i]);
		
		Collections.shuffle(aux);
		
		for(int i=0; i<size(); ++i)
			_index[i] = aux.get(i);
	}

	public void swap(int index1, int index2)
	{
		int aux = _index[index1];
		_index[index1] = _index[index2];
		_index[index2] = aux;
	}
	
	public void insert(int index, int position)
	{
		if( position < index )
		{
			int aux = _index[index];

			for(int i=index; i>position; --i)
				_index[i] = _index[i-1];
			
			_index[position] = aux;
		}
		else if( position > index )
		{
			int aux = _index[index];

			for(int i=index; i<position; ++i)
				_index[i] = _index[i+1];
			
			_index[position] = aux;
		}
	}
	
	public Permutation clone()
	{
		return new Permutation(_index);
	}
	
	public boolean equals(Permutation that)
	{
		if( this.size() != that.size() )
			return false;
		
		for(int i=0; i<size(); ++i) if( this.get(i) != that.get(i) )
			return false;
		
		return true;
	}
}
