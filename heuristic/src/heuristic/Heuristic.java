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
			ArrayList<Solution> path = SolutionRelinker.relink(randomSolution(), randomSolution());

			_candidates.addAll(localSearch(path));
			Collections.sort(_candidates, (j,k) -> k.objective() - j.objective());
			
			while( _candidates.size() > _candidateSize )
				_candidates.remove(_candidates.size()-1);
		}
		
		return _candidates.get(0);
	}
	
	private void initializeCandidates()
	{
		ArrayList<Solution> initial = new ArrayList<Solution>();

		for(int i=0; i<_candidateSize; ++i)
			initial.add(Solution.shuffled(_instance));
		
		_candidates = localSearch(initial);
	}
	
	private Solution randomSolution()
	{
		return _candidates.get(_random.nextInt(_candidates.size()));
	}
	
	private ArrayList<Solution> localSearch(ArrayList<Solution> initialSolutions)
	{
		ArrayList<LocalSearchThread> threads = new ArrayList<LocalSearchThread>();
		ArrayList<Solution> ret = new ArrayList<Solution>(); 
		
		for(Solution initial: initialSolutions)
		{
			LocalSearchThread thread = new LocalSearchThread(initial);
			threads.add(thread);
			thread.start();
		}
		
		for(LocalSearchThread thread: threads)
		{
			try
			{
				thread.join();
				ret.add(thread.getFinal());
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
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
