package slitter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InstanceTest
{
	@Test
	void readInstance() throws FileNotFoundException
	{
		Instance instance = new Instance("../instances/test.dat");
		
		assertEquals(4, instance.getCombinations());
		assertEquals(3, instance.getMaxItems());
		assertEquals(2, instance.getItems(0));
		assertEquals(3, instance.getItems(1));
		assertEquals(300, instance.getItem(1, 2));
		assertEquals(150, instance.getItem(2, 0));
	}

	@Test
	void accessNonexistingItem() throws FileNotFoundException
	{
		Instance instance = new Instance("../instances/test.dat");
		Assertions.assertThrows(IllegalArgumentException.class, () -> { instance.getItem(0, 2); });
	}
}
