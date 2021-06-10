package heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Heuristic
{
	private Instance _instance;
	private ArrayList<Solution> _candidates;
	private Random _random;
	
	private static int _candidateSize = 10;
	
	public Heuristic(Instance instance)
	{
		_instance = instance;
		_random = new Random();
	}
	
	public Solution run()
	{
		initializeCandidates();
		
		for(int i=0; i<100; ++i)
		{
			for(Solution solution: SolutionRelinker.relink(randomSolution(), randomSolution()))
				_candidates.add(LocalSearch.run(solution));
			
			Collections.sort(_candidates, (j,k) -> k.objective() - j.objective());
			
			while( _candidates.size() > _candidateSize )
				_candidates.remove(_candidates.size()-1);
		}
		
		return _candidates.get(0);
	}
	
	private void initializeCandidates()
	{
		_candidates = new ArrayList<Solution>();
		
		for(int i=0; i<_candidateSize; ++i)
			_candidates.add(LocalSearch.run(Solution.shuffled(_instance)));
	}
	
	private Solution randomSolution()
	{
		return _candidates.get(_random.nextInt(_candidates.size()));
	}
	
	public Solution grasp()
	{
		Solution ret = Solution.shuffled(_instance);
		
		for(int i=0; i<100; ++i)
		{
			Solution local = LocalSearch.run(Solution.shuffled(_instance));
			
			if( local.objective() > ret.objective() )
				ret = local;
		}
		
		return ret;
	}
}
