package slitter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlgorithmTest
{
	private Instance _instance;
	
	@BeforeEach
	void initialize() throws FileNotFoundException
	{
		_instance = new Instance("../instances/test.dat");
		
		// Combination 1 = 200 200
		// Combination 2 = 100 200 300
		// Combination 3 = 150 250
		// Combination 4 = 250 250
	}
	
	@Test
	void testInstance()
	{
		assertTrue(new Algorithm(_instance, 0, 0, 1, 1).solve());
		assertTrue(new Algorithm(_instance, 1, 1, 3, 0).solve());
		assertTrue(new Algorithm(_instance, 1, 2, 3, 0).solve());
		assertFalse(new Algorithm(_instance, 1, 0, 3, 0).solve());
	}
}
