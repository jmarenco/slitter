package slitter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TableTest
{
	@Test
	void tableGet()
	{
		Table table = new Table();
		table.set(2, 200, false);
		table.set(2, 400, true);
		table.set(1, -50, true);
		
		assertFalse(table.get(2, 200));
		assertTrue(table.get(2, 400));
		assertTrue(table.get(1, -50));
	}

	@Test
	void tableNonexistentGet()
	{
		Table table = new Table();
		table.set(2, 200, false);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> { table.get(2, 100); });
	}
}
