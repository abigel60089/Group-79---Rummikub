import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GroupTest
{
    @Test
    public void acceptsThreeDifferentColorsWithSameNumber()
    {
        Group group = new Group();
        assertTrue(group.addTile(new Tile(8, "red")));
        assertTrue(group.addTile(new Tile(8, "blue")));
        assertTrue(group.addTile(new Tile(8, "black")));

        assertTrue(group.isValid());
        assertEquals(24, group.getPoints());
    }


    @Test
    public void rejectsDuplicateColorOrDifferentNumber()
    {
        Group group = new Group();
        assertTrue(group.addTile(new Tile(8, "red")));

        assertFalse(group.addTile(new Tile(8, "red")));
        assertFalse(group.addTile(new Tile(9, "blue")));
        assertEquals(1, group.size());
        assertFalse(group.isValid());
    }


    @Test
    public void acceptsFourTileGroupIncludingJoker()
    {
        Group group = new Group();
        group.addTile(new Tile(10, "red"));
        group.addTile(new Tile(10, "blue"));
        group.addTile(new Tile(10, "yellow"));
        group.addTile(new Tile(14, "black"));

        assertTrue(group.isValid());
        assertEquals(40, group.getPoints());
    }
}
