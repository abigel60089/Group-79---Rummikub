public class GroupTest
    extends student.TestCase
{
    private Group group;

    public void setUp()
    {
        group = new Group();
    }


    public void testThreeDifferentColorsWithSameNumber()
    {
        assertTrue(group.addTile(new Tile(8, "red")));
        assertTrue(group.addTile(new Tile(8, "blue")));
        assertTrue(group.addTile(new Tile(8, "black")));

        assertTrue(group.isValid());
        assertEquals(24, group.getPoints());
    }


    public void testDuplicateColorOrDifferentNumber()
    {
        assertTrue(group.addTile(new Tile(8, "red")));
        assertFalse(group.addTile(new Tile(8, "red")));
        assertFalse(group.addTile(new Tile(9, "blue")));
        assertEquals(1, group.size());
        assertFalse(group.isValid());
    }


    public void testFourTileGroupIncludingJoker()
    {
        group.addTile(new Tile(10, "red"));
        group.addTile(new Tile(10, "blue"));
        group.addTile(new Tile(10, "yellow"));
        group.addTile(new Tile(14, "black"));

        assertTrue(group.isValid());
        assertEquals(40, group.getPoints());
    }


    public void testEmpty()
    {
        assertEquals(0, group.getPoints());
    }


    public void testAllJokers()
    {
        group.addTile(new Tile(14, "red"));
        group.addTile(new Tile(14, "red"));
        group.addTile(new Tile(14, "red"));
        assertFalse(group.isValid());
        assertEquals(0, group.getPoints());
    }


    public void testMiddleJoker()
    {
        group.addTile(new Tile(1, "black"));
        group.addTile(new Tile(14, "red"));
        group.addTile(new Tile(1, "red"));
        assertTrue(group.canAdd(new Tile(14, "red")));
        assertTrue(group.isValid());
    }
}
