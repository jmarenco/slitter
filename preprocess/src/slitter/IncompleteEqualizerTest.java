package slitter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncompleteEqualizerTest
{
	private Instance _test;
//	private Instance _test2;
//	private Instance _test3;
//	private Instance _test4;
	
	@BeforeEach
	void initialize() throws FileNotFoundException
	{
		_test = new Instance("../instances/test.dat");
//		_test2 = new Instance("../instances/test2.dat");
//		_test3 = new Instance("../instances/test3.dat");
//		_test4 = new Instance("../instances/test4.dat");
		
		// Test:
		// Combination 1 = 200 200
		// Combination 2 = 100 200 300
		// Combination 3 = 150 250
		// Combination 4 = 250 250

		// Test 2:
		// Combination 1 = 8 10 5 6 3 4
		// Combination 2 = 6 9 5 4 7 5

		// Test 3:
		// Combination 1 = 8 10 5 6 4
		// Combination 2 = 6 9 5 4 7 5
		
		// Test 4:
		// Combination 1 = 6 4 8
		// Combination 2 = 3 6 4
	}
	
	@Test
	void testInstance()
	{
		assertTrue(new Equalizer(_test, 0, 0, 2, 1).solve());
		assertFalse(new IncompleteEqualizer(_test, 0, 0, 2, 1).solve());
		assertFalse(new IncompleteEqualizer(_test, 2, 1, 0, 0).solve());
		assertFalse(new IncompleteEqualizer(_test, 2, 0, 0, 0).solve());
	}
}
