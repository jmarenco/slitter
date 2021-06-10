package heuristic;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionTest
{
	private Instance _test;
	
	@BeforeEach
	void initialize() throws FileNotFoundException
	{
		_test = new Instance("../instances/test.dat");
		
		// Test:
		// Combination 1 = 200 200
		// Combination 2 = 100 200 300
		// Combination 3 = 150 250
		// Combination 4 = 250 250
	}
	
	@Test
	void positionTest()
	{
		Solution solution = Solution.identity(_test);
		
		assertEquals(400, solution.position(0, 1));
		assertEquals(600, solution.position(1, 2));
		assertEquals(150, solution.position(2, 0));
		assertEquals(400, solution.position(2, 1));
		assertEquals(250, solution.position(3, 0));
	}
	
	@Test
	void objectiveTest()
	{
		Solution solution = Solution.identity(_test);
		assertEquals(0, solution.objective());
		
		solution.swap(1, 2);
		assertEquals(1, solution.objective());

		solution.swap(2, 3);
		assertEquals(1, solution.objective());

		solution.swap(2, 0, 1);
		assertEquals(2, solution.objective());
	}
}
