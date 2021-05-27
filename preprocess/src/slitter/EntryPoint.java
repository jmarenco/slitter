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
		String output = changeExtension(input, ".pos");
		System.out.println("Processing file " + input);

		Instance instance = new Instance(input);
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<instance.getCombinations(); ++i)
		for(int j=i+1; j<instance.getCombinations(); ++j)
		for(int k=0; k<instance.getItems(i); ++k)
		for(int l=0; l<instance.getItems(j); ++l)
		{
			Algorithm algorithm = new Algorithm(instance, i, k, j, l);
			if( algorithm.solve() == true)
			{
				writer.write(i + "\t" + j + "\t" + k + "\t" + l + "\r\n");
				writer.write(j + "\t" + i + "\t" + l + "\t" + k + "\r\n");
			}
		}
		
		writer.close();
		
		System.out.println("Output written to " + output);
		System.out.printf("Total time: %.2f sec.", (System.currentTimeMillis() - start) / 1000.0);
		System.out.println();
	}
	
	private static String changeExtension(String fileName, String newExtension)
	{
		int i = fileName.lastIndexOf('.');
		return fileName.substring(0,i) + newExtension;
	}		
}
