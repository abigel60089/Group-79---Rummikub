import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import student.TestCase;

/**
 * Tests for the Computer class.
 *
 * findMoves(), findGroups(), findRuns() and the currMoves field are private,
 * so these tests reach them with reflection (see the helpers at the bottom).
 * That way Computer.java doesn't need any changes to be tested.
 *
 * Tiles are built the same way Game.createTiles() builds them:
 * new Tile(number, color), with lowercase colors and 14 as the joker.
 *
 * Some tests are EXPECTED TO FAIL with the current Computer code. They
 * describe the correct behavior, so they pass once the bugs are fixed:
 *   - testFindGroupsDuplicateTiles   (new group gets get(0), not the new one)
 *   - testFindRunsUnsortedHand       (findRuns doesn't sort by number first)
 *   - testFindMovesGroupAndRun       (same sort bug: B9 comes before B4)
 *
 * @author Group 79
 * @version 2026.10.23
 */
public class ComputerTest
    extends TestCase
{
    // ----- colors and joker, matching Game.createTiles() -----
    private static final String BLACK = "black";
    private static final String RED = "red";
    private static final String YELLOW = "yellow";
    private static final String BLUE = "blue";
    private static final int JOKER = 14;

    private Computer cpu;


    /**
     * Creates a fresh Computer with an empty hand before each test.
     */
    public void setUp()
    {
        cpu = new Computer();
    }


    // ==================================================================
    // Constructor
    // ==================================================================

    /**
     * Normal: name is stored and the computer starts with nothing.
     *
     * @throws Exception if reflection fails
     */
    public void testConstructor()
        throws Exception
    {

        assertEquals(0, cpu.hand.size());
        assertFalse(cpu.hasInitialMeld);
        assertTrue(currMoves(cpu).isEmpty());
    }


    // ==================================================================
    // findGroups(int num)
    // ==================================================================

    /**
     * Normal: three 7s in different colors make one group; R2 is ignored.
     *
     * @throws Exception if reflection fails
     */
    public void testFindGroupsThreeColors()
        throws Exception
    {
        give(cpu, t(7, RED), t(7, BLUE), t(7, BLACK), t(2, RED));
        findGroups(cpu, 7);
        LinkedChain<Set> moves = currMoves(cpu);
        assertEquals(1, moves.size());
        assertEquals(3, moves.get(0).size());
        assertTrue(moves.get(0) instanceof Group);
    }


    /**
     * Normal: all four colors make one group of 4.
     *
     * @throws Exception if reflection fails
     */
    public void testFindGroupsFourColors()
        throws Exception
    {
        give(cpu, t(7, RED), t(7, BLUE), t(7, BLACK), t(7, YELLOW));
        findGroups(cpu, 7);
        LinkedChain<Set> moves = currMoves(cpu);
        assertEquals(1, moves.size());
        assertEquals(4, moves.get(0).size());
    }


    /**
     * Normal: a joker fills in for the missing third color.
     *
     * @throws Exception if reflection fails
     */
    public void testFindGroupsWithJoker()
        throws Exception
    {
        give(cpu, t(7, RED), t(7, BLUE), t(JOKER, BLACK));
        findGroups(cpu, 7);
        assertEquals(1, currMoves(cpu).size());
    }


    /**
     * Bad: only two tiles of that number is not a group.
     *
     * @throws Exception if reflection fails
     */
    public void testFindGroupsOnlyTwoTiles()
        throws Exception
    {
        give(cpu, t(7, RED), t(7, BLUE), t(3, BLACK));
        findGroups(cpu, 7);
        assertTrue(currMoves(cpu).isEmpty());
    }


    /**
     * Bad: duplicate colors only give 2 distinct colors, so no group.
     *
     * @throws Exception if reflection fails
     */
    public void testFindGroupsDuplicateColorsOnly()
        throws Exception
    {
        give(cpu, t(7, RED), t(7, RED), t(7, BLUE));
        findGroups(cpu, 7);
        assertTrue(currMoves(cpu).isEmpty());
    }


    /**
     * Edge: two of each color should make TWO groups.
     * EXPECTED TO FAIL until findGroups uses groups.get(groups.size() - 1)
     * instead of groups.get(0) when it creates a new group.
     * (findRuns has the same get(0) line and needs the same fix.)
     *
     * @throws Exception if reflection fails
     */
    public void testFindGroupsDuplicateTiles()
        throws Exception
    {
        give(cpu, t(7, RED), t(7, RED), t(7, BLUE), t(7, BLUE),
            t(7, BLACK), t(7, BLACK));
        findGroups(cpu, 7);
        assertEquals(2, currMoves(cpu).size());
    }


    /**
     * Edge: an empty hand doesn't crash and finds nothing.
     *
     * @throws Exception if reflection fails
     */
    public void testFindGroupsEmptyHand()
        throws Exception
    {
        findGroups(cpu, 7);
        assertTrue(currMoves(cpu).isEmpty());
    }


    // ==================================================================
    // findRuns(String col)
    // ==================================================================

    /**
     * Normal: three consecutive blues make one run; other colors ignored.
     *
     * @throws Exception if reflection fails
     */
    public void testFindRunsConsecutive()
        throws Exception
    {
        give(cpu, t(4, BLUE), t(5, BLUE), t(6, BLUE), t(1, RED));
        findRuns(cpu, BLUE);
        LinkedChain<Set> moves = currMoves(cpu);
        assertEquals(1, moves.size());
        assertEquals(3, moves.get(0).size());
        assertTrue(moves.get(0) instanceof Run);
    }


    /**
     * Normal: a joker fills a gap in the run (B4 J B6).
     *
     * @throws Exception if reflection fails
     */
    public void testFindRunsWithJoker()
        throws Exception
    {
        give(cpu, t(4, BLUE), t(JOKER, BLACK), t(6, BLUE));
        findRuns(cpu, BLUE);
        assertEquals(1, currMoves(cpu).size());
    }


    /**
     * Normal: the hand is out of order, but B4 B5 B6 is still a run.
     * EXPECTED TO FAIL until findRuns sorts the tiles by number first.
     *
     * @throws Exception if reflection fails
     */
    public void testFindRunsUnsortedHand()
        throws Exception
    {
        give(cpu, t(6, BLUE), t(4, BLUE), t(5, BLUE));
        findRuns(cpu, BLUE);
        assertEquals(1, currMoves(cpu).size());
    }


    /**
     * Bad: a gap (B4 B5 B7) is not a run.
     *
     * @throws Exception if reflection fails
     */
    public void testFindRunsGap()
        throws Exception
    {
        give(cpu, t(4, BLUE), t(5, BLUE), t(7, BLUE));
        findRuns(cpu, BLUE);
        assertTrue(currMoves(cpu).isEmpty());
    }


    /**
     * Bad: mixed colors (B4 R5 B6) is not a blue run.
     *
     * @throws Exception if reflection fails
     */
    public void testFindRunsMixedColors()
        throws Exception
    {
        give(cpu, t(4, BLUE), t(5, RED), t(6, BLUE));
        findRuns(cpu, BLUE);
        assertTrue(currMoves(cpu).isEmpty());
    }


    /**
     * Edge: a duplicate B5 must not break the run B4 B5 B6.
     *
     * @throws Exception if reflection fails
     */
    public void testFindRunsDuplicateTile()
        throws Exception
    {
        give(cpu, t(4, BLUE), t(5, BLUE), t(5, BLUE), t(6, BLUE));
        findRuns(cpu, BLUE);
        assertEquals(1, currMoves(cpu).size());
        assertEquals(3, currMoves(cpu).get(0).size());
    }


    /**
     * Edge: an empty hand doesn't crash and finds nothing.
     *
     * @throws Exception if reflection fails
     */
    public void testFindRunsEmptyHand()
        throws Exception
    {
        findRuns(cpu, BLUE);
        assertTrue(currMoves(cpu).isEmpty());
    }


    // ==================================================================
    // findMoves()
    // ==================================================================

    /**
     * Normal: finds both a group (9s) and a run (B4-B6) that don't overlap.
     * EXPECTED TO FAIL until findRuns sorts by number: B9 is reached
     * first, starts the run, and B4 B5 B6 are then rejected.
     *
     * @throws Exception if reflection fails
     */
    public void testFindMovesGroupAndRun()
        throws Exception
    {
        give(cpu, t(9, RED), t(9, BLUE), t(9, BLACK),
            t(4, BLUE), t(5, BLUE), t(6, BLUE));
        findMoves(cpu);
        LinkedChain<Set> moves = currMoves(cpu);
        assertEquals(2, moves.size());
        assertTrue(containsType(moves, Group.class));
        assertTrue(containsType(moves, Run.class));
    }


    /**
     * Bad: a hand with no possible sets finds nothing.
     *
     * @throws Exception if reflection fails
     */
    public void testFindMovesNoSets()
        throws Exception
    {
        give(cpu, t(1, RED), t(5, BLUE), t(9, BLACK), t(13, YELLOW));
        findMoves(cpu);
        assertTrue(currMoves(cpu).isEmpty());
    }


    /**
     * Edge: an empty hand doesn't crash and finds nothing.
     *
     * @throws Exception if reflection fails
     */
    public void testFindMovesEmptyHand()
        throws Exception
    {
        findMoves(cpu);
        assertTrue(currMoves(cpu).isEmpty());
    }


    /**
     * Edge: B7 fits both the run B5-B7 and the group of 7s, so both sets
     * are found. takeTurn() must not play both, or B7 would be used twice.
     *
     * @throws Exception if reflection fails
     */
    public void testFindMovesOverlappingTile()
        throws Exception
    {
        give(cpu, t(5, BLUE), t(6, BLUE), t(7, BLUE), t(7, RED), t(7, BLACK));
        findMoves(cpu);
        LinkedChain<Set> moves = currMoves(cpu);
        assertTrue(containsType(moves, Group.class));
        assertTrue(containsType(moves, Run.class));
    }


    /**
     * Edge: findMoves only looks at the hand, it must not change it.
     *
     * @throws Exception if reflection fails
     */
    public void testFindMovesDoesNotChangeHand()
        throws Exception
    {
        give(cpu, t(9, RED), t(9, BLUE), t(9, BLACK));
        findMoves(cpu);
        assertEquals(3, cpu.hand.size());
    }


    // ==================================================================
    // takeTurn(Game g)
    // ==================================================================

    /**
     * Bad: a null game is rejected with IllegalArgumentException.
     */
    public void testTakeTurnNullGame()
    {
        Exception thrown = null;
        try
        {
            cpu.takeTurn(null);
        }
        catch (IllegalArgumentException e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
    }


    /**
     * Edge: currMoves is cleared at the end of every turn, so moves from
     * one turn don't leak into the next.
     *
     * @throws Exception if reflection fails
     */
    public void testTakeTurnClearsCurrMoves()
        throws Exception
    {
        give(cpu, t(9, RED), t(9, BLUE), t(9, BLACK));
        cpu.takeTurn(new Game());
        assertTrue(currMoves(cpu).isEmpty());
    }


    // ==================================================================
    // Helpers
    // ==================================================================

    /**
     * Shorthand for making a tile.
     *
     * @param number the tile number (1-13, or 14 for a joker)
     * @param color the tile color
     * @return the new tile
     */
    private static Tile t(int number, String color)
    {
        return new Tile(number, color);
    }


    /**
     * Puts tiles straight into the computer's hand.
     *
     * @param c the computer
     * @param tiles the tiles to add
     */
    private static void give(Computer c, Tile... tiles)
    {
        for (Tile tile : tiles)
        {
            c.hand.add(tile);
        }
    }


    /**
     * Checks whether any move in the chain is a given kind of set.
     *
     * @param moves the moves to search
     * @param type Group.class or Run.class
     * @return true if at least one move is that type
     */
    private static boolean containsType(LinkedChain<Set> moves, Class<?> type)
    {
        for (int i = 0; i < moves.size(); i++)
        {
            if (type.isInstance(moves.get(i)))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * Reads the private currMoves field.
     *
     * @param c the computer
     * @return its currMoves chain
     * @throws Exception if reflection fails
     */
    @SuppressWarnings("unchecked")
    private static LinkedChain<Set> currMoves(Computer c)
        throws Exception
    {
        Field f = Computer.class.getDeclaredField("currMoves");
        f.setAccessible(true);
        return (LinkedChain<Set>)f.get(c);
    }


    /**
     * Calls the private findMoves().
     *
     * @param c the computer
     * @throws Exception if the method throws or reflection fails
     */
    private static void findMoves(Computer c)
        throws Exception
    {
        invoke(c, "findMoves", new Class<?>[0]);
    }


    /**
     * Calls the private findGroups(int).
     *
     * @param c the computer
     * @param num the number to look for
     * @throws Exception if the method throws or reflection fails
     */
    private static void findGroups(Computer c, int num)
        throws Exception
    {
        invoke(c, "findGroups", new Class<?>[] { int.class }, num);
    }


    /**
     * Calls the private findRuns(String).
     *
     * @param c the computer
     * @param col the color to look for
     * @throws Exception if the method throws or reflection fails
     */
    private static void findRuns(Computer c, String col)
        throws Exception
    {
        invoke(c, "findRuns", new Class<?>[] { String.class }, col);
    }


    /**
     * Calls a private method by name. If the method itself throws, that
     * exception is passed on so the test shows the real error.
     *
     * @param c the computer
     * @param name the method name
     * @param types the parameter types
     * @param args the arguments
     * @throws Exception if the method throws or reflection fails
     */
    private static void invoke(
        Computer c,
        String name,
        Class<?>[] types,
        Object... args)
        throws Exception
    {
        Method m = Computer.class.getDeclaredMethod(name, types);
        m.setAccessible(true);
        try
        {
            m.invoke(c, args);
        }
        catch (InvocationTargetException e)
        {
            Throwable cause = e.getCause();
            if (cause instanceof Exception)
            {
                throw (Exception)cause;
            }
            throw new RuntimeException(cause);
        }
    }
}