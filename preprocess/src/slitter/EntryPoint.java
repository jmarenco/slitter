package slitter;

import java.io.FileNotFoundException;

public class EntryPoint {

	public static void main(String[] args) throws FileNotFoundException
	{
		if( args.length == 0 )
		{
			System.out.println("Use: java -jar slitterpreprocess.jar (file.dat)");
			return;
		}
		
		Instance instance = new Instance(args[0]);
		
		for(int i=0; i<instance.getCombinations(); ++i)
		for(int j=i+1; j<instance.getCombinations(); ++j)
		for(int k=0; k<instance.getItems(i); ++k)
		for(int l=0; l<instance.getItems(j); ++l)
		{
			Algorithm algorithm = new Algorithm(instance, i, k, j, l);
			if( algorithm.solve() == true)
			{
				System.out.println(i + "\t" + j + "\t" + k + "\t" + l);
				System.out.println(j + "\t" + i + "\t" + l + "\t" + k);
			}
		}
	}
}
