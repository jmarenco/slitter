package heuristic;

import java.util.Random;

public class Generator
{
	public static Instance create(int combinations, int maxItems, int seed)
	{
		Random random = new Random(seed);
		Instance ret = new Instance(combinations, maxItems);
		
		for(int i=0; i<combinations; ++i)
		{
			int max = maxItems - random.nextInt(1);
			for(int j=0; j<max; ++j)
				ret.setItem(i, j, 50 * random.nextInt(3) + 10 * random.nextInt(2) + 100);
		}
		
		return ret;
	}
}
