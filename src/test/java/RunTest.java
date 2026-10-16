import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RunTest
{
    @Test
    public void validatesConsecutiveSameColorTilesAndScoresAllPoints()
    {
        Run run = new Run();
        run.addTile(new Tile(5, "red"));
        run.addTile(new Tile(6, "red"));
        run.addTile(new Tile(7, "red"));

        assertTrue(run.isValid());
        assertEquals(18, run.getPoints());
    }


    @Test
    public void canExtendRunAtBothEnds()
    {
        Run run = new Run();
        run.addTile(new Tile(3, "blue"));
        run.addTile(new Tile(4, "blue"));
        run.addTile(new Tile(5, "blue"));

        assertTrue(run.addTile(new Tile(2, "blue")));
        assertTrue(run.addTile(new Tile(6, "blue")));
        assertTrue(run.isValid());
        assertEquals(20, run.getPoints());
    }


    @Test
    public void validatesAndScoresRunWithJokerAtFront()
    {
        Run run = new Run();
        run.addTile(new Tile(14, "black"));
        run.addTile(new Tile(5, "red"));
        run.addTile(new Tile(6, "red"));

        assertTrue(run.isValid());
        assertEquals(15, run.getPoints());
    }


    @Test
    public void rejectsGapAndDifferentColor()
    {
        Run gap = new Run();
        gap.addTile(new Tile(3, "red"));
        assertFalse(gap.addTile(new Tile(5, "red")));

        Run differentColor = new Run();
        differentColor.addTile(new Tile(3, "red"));
        differentColor.addTile(new Tile(4, "blue"));
        differentColor.addTile(new Tile(5, "red"));
        assertFalse(differentColor.isValid());
    }
}
