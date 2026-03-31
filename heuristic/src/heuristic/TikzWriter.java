package heuristic;

public class TikzWriter
{
	public static void write(Solution solution, String description, String instance, double scale)
	{
		System.out.println("\\begin{figure}");
		System.out.println("\\begin{center}");
		System.out.println("\\begin{tikzpicture}[scale=0.03]");
		
		int height = 20;
		int margin = 4;
		double maxs = 0;
		
		for(int i=0; i<solution.getInstance().getCombinations(); ++i)
		{
			int top = (height + margin) * (solution.getInstance().getCombinations() - i - 1);
			int bottom = top + height;
			double start = 0;
			double anterior = 0;
			
			for(int j=0; j<solution.itemsPermutation(i); ++j)
			{
				double end = solution.slitterPosition(i,j) / scale;
				
				System.out.println("\\draw (" + start + "," + top +") -- (" + end + "," + top + ") -- (" + end + "," + bottom + ") -- (" + start + "," + bottom + ") -- cycle;");
				System.out.println("\\node[align=center] at (" + (start+end)/2 + "," + (top+bottom)/2 + ") {\\footnotesize " + (int)(solution.slitterPosition(i,j) - anterior) + "};");
				
				if( solution.coincidentSlitterPosition(i, j) )
					System.out.println("\\draw[arrows = {-Latex[width=4pt, length=4pt]}] (" + (end-10) + "," + (top+bottom)/2 + ") -- (" + (end-1) + "," + (top+bottom)/2 + ");");

				start = end;
				anterior = solution.slitterPosition(i, j);
			}
			
			maxs = Math.max(start, maxs);
		}

		int tops = (height + margin) * solution.getInstance().getCombinations();
		System.out.println("\\draw[dashed] (-2,-2) -- (-2," + tops + ");");
		System.out.println("\\draw[dashed] (" + (maxs+2) + ",-2) -- (" + (maxs+2) + "," + tops + ");");
		System.out.println("\\end{tikzpicture}");
		System.out.println("\\end{center}");
		System.out.println("\\caption{" + description + " solution for the instance " + instance.replaceAll(".dat", "").toUpperCase() + ".} % Objective = " + solution.objective());
		System.out.println("\\end{figure}");
		System.out.println();
	}
}
