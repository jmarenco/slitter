package heuristic;

import java.util.ArrayList;

public class SolutionRelinker
{
	private Solution _origin;
	private Solution _target;
	
	public static ArrayList<Solution> relink(Solution origin, Solution target)
	{
		SolutionRelinker relinker = new SolutionRelinker(origin, target);
		return relinker.run();
	}
	
	public SolutionRelinker(Solution origin, Solution target)
	{
		_origin = origin;
		_target = target;
		
		if( _origin.getInstance() != _target.getInstance() )
			throw new IllegalArgumentException();
	}
	
	public ArrayList<Solution> run()
	{
		Solution current = Solution.cloned(_origin);
		ArrayList<Solution> ret = new ArrayList<Solution>();
		
		for(Permutation permutation: PermutationRelinker.relink(_origin.getPermutation(), _target.getPermutation()))
		{
			current.setPermutation(permutation);
			ret.add(current);
			current = Solution.cloned(current);
		}
		
		for(int i=0; i<_origin.getInstance().getCombinations(); ++i)
		{
			for(Permutation permutation: PermutationRelinker.relink(_origin.getPermutation(i), _target.getPermutation(i)))
			{
				current.setPermutation(i, permutation);
				ret.add(current);
				current = Solution.cloned(current);
			}
		}
		
		return ret;
	}
}
