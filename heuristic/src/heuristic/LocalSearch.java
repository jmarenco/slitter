package heuristic;

public class LocalSearch
{
	public static Solution run(Solution initial)
	{
		Solution current = initial;
		Solution best = null;
		
		while( current != best )
		{
			if( best != null )
				current = best;
			
			best = bestNeighborCombinations(current);
			
			if( best == current )
				best = bestNeighborItems(current);
		}
		
		return current;
	}
	
	private static Solution bestNeighborCombinations(Solution solution)
	{
		Solution ret = solution;
		Instance instance = solution.getInstance();
		
		for(int i=0; i<instance.getCombinations(); ++i)
		for(int j=i+1; j<instance.getCombinations(); ++j)
		{
			Solution neighbor = Solution.cloned(solution);
			neighbor.swap(i, j);
			
			if( solution.objective() < neighbor.objective())
				ret = neighbor;
		}
		
		return ret;
	}
	
	private static Solution bestNeighborItems(Solution solution)
	{
		Solution ret = solution;
		Instance instance = solution.getInstance();
		
		for(int i=0; i<instance.getCombinations(); ++i)
		{
			int combination = solution.getCombination(i);
			
			for(int j=0; j<instance.getItems(combination); ++j)
			for(int k=j+1; k<instance.getItems(combination); ++k)
			{
				Solution neighbor = Solution.cloned(solution);
				neighbor.swap(combination, j, k);
			
				if( solution.objective() < neighbor.objective())
					ret = neighbor;
			}
		}
		
		return ret;
	}
}
