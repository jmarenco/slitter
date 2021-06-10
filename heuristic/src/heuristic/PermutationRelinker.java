package heuristic;

import java.util.ArrayList;

public class PermutationRelinker
{
	private Permutation _origin;
	private Permutation _target;
	
	public static ArrayList<Permutation> relink(Permutation origin, Permutation target)
	{
		PermutationRelinker relinker = new PermutationRelinker(origin, target);
		return relinker.run();
	}
	
	public PermutationRelinker(Permutation origin, Permutation target)
	{
		_origin = origin;
		_target = target;
		
		if( _origin.size() != _target.size() )
			throw new IllegalArgumentException();
	}
	
	public ArrayList<Permutation> run()
	{
		int n = _target.size();
		
		Permutation current = _origin.clone();
		ArrayList<Permutation> ret = new ArrayList<Permutation>();
		
		while( current.equals(_target) == false )
		{
			for(int i=0; i<n; ++i)
			{
				int element = current.get(i);
				int indexTarget = _target.indexOf(element);
				
				if( i != indexTarget)
				{
					current.insert(i, indexTarget);
					
					ret.add(current);
					current = current.clone();
				}
			}
		}
		
		return ret;
	}
}
