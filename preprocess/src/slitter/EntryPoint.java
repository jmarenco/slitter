package slitter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EntryPoint {

	public static void main(String[] args) throws IOException
	{
		if( args.length == 0 )
		{
			System.out.println("Use: java -jar slitterpreprocess.jar (file.dat)");
			return;
		}
		
		String input = args[0];
		String output1 = input + ".pos";
		String output2 = input + ".pol";
		System.out.println("Processing file " + input);

		Instance instance = new Instance(input);
		BufferedWriter writer1 = new BufferedWriter(new FileWriter(output1));
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(output2));
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<instance.getCombinations(); ++i)
		for(int j=i+1; j<instance.getCombinations(); ++j)
		for(int k=0; k<instance.getItems(i); ++k)
		for(int l=0; l<instance.getItems(j); ++l)
		{
			Equalizer equalizer = new Equalizer(instance, i, k, j, l);
			if( equalizer.solve() == true)
			{
				writer1.write(i + "\t" + j + "\t" + k + "\t" + l + "\r\n");
				writer1.write(j + "\t" + i + "\t" + l + "\t" + k + "\r\n");
			}

			IncompleteEqualizer incomplete = new IncompleteEqualizer(instance, i, k, j, l);
			if( incomplete.solve() == true)
			{
				writer2.write(i + "\t" + j + "\t" + k + "\t" + l + "\r\n");
				writer2.write(j + "\t" + i + "\t" + l + "\t" + k + "\r\n");
			}
		}
		
		writer1.close();
		writer2.close();
		
		System.out.println("Output written to " + output1);
		System.out.println("Output written to " + output2);
		System.out.printf("Total time: %.2f sec.", (System.currentTimeMillis() - start) / 1000.0);
		System.out.println();
	}
}
