package heuristic;

public class LocalSearchThread extends Thread
{
	private Solution _initial;
	private Solution _final;
	
	public LocalSearchThread(Solution initial)
	{
		_initial = initial;
	}
	
	public void run()
	{
		_final = LocalSearch.run(_initial);
	}
	
	public Solution getFinal()
	{
		return _final;
	}
}
