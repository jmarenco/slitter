package heuristic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PermutationRelinkerTest
{
	@Test
	void noRelink()
	{
		Permutation initial = Permutation.identity(5);
		Permutation target = Permutation.identity(5);
		
		ArrayList<Permutation> ret = new PermutationRelinker(initial, target).run();
		assertEquals(0, ret.size());
	}
	
	@Test
	void twoStepRelink()
	{
		Permutation initial = Permutation.identity(5);
		Permutation target = Permutation.identity(5);
		
		target.swap(1, 3);
		
		ArrayList<Permutation> ret = new PermutationRelinker(initial, target).run();
		assertEquals(2, ret.size());
	}

	@Test
	void threeStepRelink()
	{
		Permutation initial = Permutation.identity(5);
		Permutation target = Permutation.identity(5);
		
		target.swap(1, 3);
		target.swap(2, 3);
		
		ArrayList<Permutation> ret = new PermutationRelinker(initial, target).run();
		assertEquals(3, ret.size());
	}

	@Test
	void startSwapRelink()
	{
		Permutation initial = Permutation.identity(5);
		Permutation target = Permutation.identity(5);
		
		target.swap(0, 3);
		target.swap(2, 3);
		
		ArrayList<Permutation> ret = new PermutationRelinker(initial, target).run();
		assertEquals(3, ret.size());
	}
}
