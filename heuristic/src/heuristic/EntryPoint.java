package heuristic;

import java.io.FileNotFoundException;

public class EntryPoint
{
	public static void main(String[] args) throws FileNotFoundException
	{
		if( args.length == 0 )
		{
			System.out.println("Use: java -jar heuristic.jar (file.dat)");
			return;
		}

		long start = System.currentTimeMillis();

		Instance instance = new Instance(args[0]);
		Heuristic heuristic = new Heuristic(instance);
		Solution solution = heuristic.run();
		
		System.out.println("Objective value: " + solution.objective());
		System.out.printf("Total time: %.2f sec.", (System.currentTimeMillis() - start) / 1000.0);
	}
}
