public class RunTest
    extends student.TestCase
{
    private Run run;

    public void setUp()
    {
        run = new Run();
    }


    public void testValidRun()
    {
        run.addTile(new Tile(5, "red"));
        run.addTile(new Tile(6, "red"));
        run.addTile(new Tile(7, "red"));

        assertTrue(run.isValid());
        assertEquals(18, run.getPoints());
    }


    public void testExtendRunAtBothEnds()
    {
        run.addTile(new Tile(3, "blue"));
        run.addTile(new Tile(4, "blue"));
        run.addTile(new Tile(5, "blue"));

        assertTrue(run.addTile(new Tile(2, "blue")));
        assertTrue(run.addTile(new Tile(6, "blue")));
        assertTrue(run.isValid());
        assertEquals(20, run.getPoints());
    }


    public void testRunWithJokerAtFront()
    {
        run.addTile(new Tile(14, "black"));
        run.addTile(new Tile(5, "red"));
        run.addTile(new Tile(6, "red"));

        assertTrue(run.isValid());
        assertEquals(15, run.getPoints());
    }


    public void testGap()
    {
        run.addTile(new Tile(3, "red"));
        assertFalse(run.addTile(new Tile(5, "red")));
    }


    public void testDifferentColor()
    {
        run.addTile(new Tile(3, "red"));
        run.addTile(new Tile(4, "blue"));
        run.addTile(new Tile(5, "red"));
        assertFalse(run.isValid());
    }


    public void testEmpty()
    {
        assertEquals(0, run.getPoints());
    }


    public void testAllJokers()
    {
        run.addTile(new Tile(14, "red"));
        run.addTile(new Tile(14, "red"));
        run.addTile(new Tile(14, "red"));
        assertEquals(0, run.getPoints());
        assertFalse(run.isValid());
    }


    public void testMiddleJoker()
    {
        run.addTile(new Tile(1, "red"));
        run.addTile(new Tile(14, "black"));
        run.addTile(new Tile(3, "red"));
        assertEquals(6, run.getPoints());
        assertTrue(run.isValid());
    }


    public void testJokerOnFront()
    {
        run.addTile(new Tile(12, "red"));
        run.addTile(new Tile(13, "red"));
        run.addTile(new Tile(14, "black"));
        assertEquals(36, run.getPoints());
        assertTrue(run.isValid());
    }


    public void testFull()
    {
        for (int i = 1; i < 14; i++)
        {
            run.addTile(new Tile(i, "red"));
        }
        assertFalse(run.canAdd(new Tile(14, "red")));
    }
    
    public void testInvalidNumbers() {
        run.addTile(new Tile(-1, "red"));
        run.addTile(new Tile(0, "red"));
        run.addTile(new Tile(1, "red"));
        assertFalse(run.isValid());
    }
}
