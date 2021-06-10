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
			
			best = bestWithCombinationInserts(current);
			
			if( best == current )
				best = bestWithItemInserts(current);
			
			if( best == current )
				best = bestWithCombinationSwaps(current);
			
			if( best == current )
				best = bestWithItemSwaps(current);
		}
		
		return current;
	}
	
	private static Solution bestWithCombinationSwaps(Solution solution)
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
	
	private static Solution bestWithItemSwaps(Solution solution)
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
	
	private static Solution bestWithCombinationInserts(Solution solution)
	{
		Solution ret = solution;
		Instance instance = solution.getInstance();
		
		for(int i=0; i<instance.getCombinations(); ++i)
		for(int j=0; j<instance.getCombinations(); ++j)
		{
			Solution neighbor = Solution.cloned(solution);
			neighbor.insert(i, j);
			
			if( solution.objective() < neighbor.objective())
				ret = neighbor;
		}
		
		return ret;
	}
	
	private static Solution bestWithItemInserts(Solution solution)
	{
		Solution ret = solution;
		Instance instance = solution.getInstance();
		
		for(int i=0; i<instance.getCombinations(); ++i)
		{
			int combination = solution.getCombination(i);
			
			for(int j=0; j<instance.getItems(combination); ++j)
			for(int k=0; k<instance.getItems(combination); ++k)
			{
				Solution neighbor = Solution.cloned(solution);
				neighbor.insert(combination, j, k);
			
				if( solution.objective() < neighbor.objective())
					ret = neighbor;
			}
		}
		
		return ret;
	}
}
