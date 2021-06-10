package heuristic;

public class Heuristic
{
	private Instance _instance;
	
	public Heuristic(Instance instance)
	{
		_instance = instance;
	}
	
	public Solution run()
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
