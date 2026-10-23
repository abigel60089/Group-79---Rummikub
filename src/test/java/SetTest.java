import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SetTest
{
    @Test
    public void exposesCommonTileOperations()
    {
        Run set = new Run();
        Tile first = new Tile(5, "red");
        Tile second = new Tile(6, "red");
        Tile third = new Tile(7, "red");

        assertTrue(set.isEmpty());
        assertEquals("", set.toString());

        assertTrue(set.addTile(first));
        assertTrue(set.addTile(second));
        assertTrue(set.addTile(third));

        assertFalse(set.isEmpty());
        assertEquals(3, set.size());
        assertSame(first, set.get(0));
        assertSame(second, set.get(1));
        assertSame(third, set.get(2));
        assertEquals(
            "| " + Tile.red + "[5]" + Tile.def + " | "
                + Tile.red + "[6]" + Tile.def + " | "
                + Tile.red + "[7]" + Tile.def + " |",
            set.toString());
    }
}
