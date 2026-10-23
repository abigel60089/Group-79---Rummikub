import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TileTest
{
    @Test
    public void equalTilesHaveSameNumberAndColor()
    {
        Tile tile = new Tile(7, "blue");

        assertEquals(tile, new Tile(7, "blue"));
        assertNotEquals(tile, new Tile(8, "blue"));
        assertNotEquals(tile, new Tile(7, "red"));
        assertNotEquals(tile, null);
        assertTrue(tile.equals(tile));
    }


    @Test
    public void exposesNumberColorAndText()
    {
        Tile tile = new Tile(12, "yellow");

        assertEquals(12, tile.getNumber());
        assertEquals("yellow", tile.getColor());
        assertEquals(Tile.yellow + "[12]" + Tile.def, tile.toString());
    }
}
