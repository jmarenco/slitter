package heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Heuristic
{
	private Instance _instance;
	private ArrayList<Solution> _candidates;
	private Solution _best;
	private Random _random;
	
	private static int _candidateSize = 25;
	private static int _iterations = 200;
	private static int _shake = 10;
	private static boolean _verbose = false;
	
	public Heuristic(Instance instance)
	{
		_instance = instance;
		_random = new Random();
	}
	
	public Solution run()
	{
		initializeCandidates();
		
		for(int i=0, stagnation=0, bestObj=0; i<_iterations; ++i)
		{
			ArrayList<Solution> path = SolutionRelinker.relink(randomSolution(), randomSolution());
			ArrayList<Solution> locals = localSearch(path);

			_candidates.addAll(locals);

			sortCandidates();
			updateBest();
			
			while( _candidates.size() > _candidateSize )
				_candidates.remove(_candidates.size()-1);
			
			if( bestObj < _best.objective() )
			{
				bestObj = _best.objective();
				stagnation = 0;
			}
			else if( (++stagnation) >= _shake )
			{
				initializeCandidates();
				stagnation = 0;
			}
			
			if( _verbose == true )
				System.out.println("It " + i + ": " + _candidates.get(0).objective() + " - " + _candidates.get(_candidates.size()-1).objective() + " -> " + _best.objective());
		}
		
		return _best;
	}
	
	private void initializeCandidates()
	{
		ArrayList<Solution> initial = new ArrayList<Solution>();

		for(int i=0; i<_candidateSize; ++i)
			initial.add(Solution.shuffled(_instance));
		
		_candidates = localSearch(initial);

		sortCandidates();
		updateBest();
	}
	
	private void sortCandidates()
	{
		Collections.sort(_candidates, (j,k) -> k.objective() - j.objective());
	}
	
	private void updateBest()
	{
		if (_best == null)
			_best = Solution.cloned(_candidates.get(0));
		else if( _candidates.get(0).objective() > _best.objective() )
			_best = Solution.cloned(_candidates.get(0));
	}
	
	private void shakeCandidates()
	{
		while( _candidates.size() > _candidateSize / 2 )
			_candidates.remove(0);
		
		while( _candidates.size() < _candidateSize )
			_candidates.add(Solution.shuffled(_instance));
		
		sortCandidates();
		updateBest();
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
