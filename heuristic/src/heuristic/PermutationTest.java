package heuristic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PermutationTest
{
	@Test
	void identityTest()
	{
		Permutation permutation = Permutation.identity(5);
		assertEquals(5, permutation.size());

		for(int i=0; i<5; ++i)
			assertEquals(i, permutation.get(i));
	}
	
	@Test
	void swapTest()
	{
		Permutation permutation = Permutation.identity(5);
		permutation.swap(1, 3);

		assertEquals(0, permutation.get(0));
		assertEquals(3, permutation.get(1));
		assertEquals(2, permutation.get(2));
		assertEquals(1, permutation.get(3));
		assertEquals(4, permutation.get(4));
	}
	
	@Test
	void shuffleTest()
	{
		Permutation permutation = Permutation.shuffled(3);

		assertTrue(0 == permutation.get(0) || 0 == permutation.get(1) || 0 == permutation.get(2));
		assertTrue(1 == permutation.get(0) || 1 == permutation.get(1) || 1 == permutation.get(2));
		assertTrue(2 == permutation.get(0) || 2 == permutation.get(1) || 2 == permutation.get(2));
	}
	
	@Test
	void insertBeforeTest()
	{
		Permutation permutation = Permutation.identity(5);
		permutation.insert(3, 1);

		assertEquals(0, permutation.get(0));
		assertEquals(3, permutation.get(1));
		assertEquals(1, permutation.get(2));
		assertEquals(2, permutation.get(3));
		assertEquals(4, permutation.get(4));
	}
	
	@Test
	void insertAfterTest()
	{
		Permutation permutation = Permutation.identity(5);
		permutation.insert(2, 4);

		assertEquals(0, permutation.get(0));
		assertEquals(1, permutation.get(1));
		assertEquals(3, permutation.get(2));
		assertEquals(4, permutation.get(3));
		assertEquals(2, permutation.get(4));
	}
}
