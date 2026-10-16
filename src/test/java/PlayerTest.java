import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PlayerTest
{
    @Test
    public void rejectsInitialMeldBelowThirtyWithoutRemovingTiles()
    {
        Player player = new Player("Alex");
        Group group = groupOf(7, "red", "blue", "black");
        addTilesToPlayer(player, group);

        assertFalse(player.playSet(group));
        assertEquals(3, player.getHand().size());
        assertFalse(player.hasInitialMeld());
    }


    @Test
    public void acceptsMultipleSetsAsInitialMeldWhenTotalIsThirty()
    {
        Player player = new Player("Alex");
        Group group = groupOf(8, "red", "blue", "black");
        Run run = runOf(1, 2, 3, "yellow");
        addTilesToPlayer(player, group);
        addTilesToPlayer(player, run);
        LinkedChain<Set> meld = new LinkedChain<Set>();
        meld.addToEnd(group);
        meld.addToEnd(run);

        assertEquals(30, group.getPoints() + run.getPoints());
        assertTrue(player.playSets(meld));
        assertTrue(player.hasInitialMeld());
        assertTrue(player.hasWon());
    }


    @Test
    public void rejectsSetContainingTileNotInHandWithoutChangingHand()
    {
        Player player = new Player("Alex");
        Group group = groupOf(10, "red", "blue", "black");
        player.drawTile(new Tile(10, "red"));
        player.drawTile(new Tile(10, "blue"));

        assertFalse(player.playSet(group));
        assertEquals(2, player.getHand().size());
        assertFalse(player.hasInitialMeld());
    }


    @Test
    public void permitsSmallSetAfterInitialMeld()
    {
        Player player = new Player("Alex");
        Group initialMeld = groupOf(10, "red", "blue", "black");
        addTilesToPlayer(player, initialMeld);
        assertTrue(player.playSet(initialMeld));

        Group laterSet = groupOf(1, "red", "blue", "black");
        addTilesToPlayer(player, laterSet);
        assertTrue(player.playSet(laterSet));
        assertEquals(0, player.getHand().size());
        assertEquals(0, player.getHandValue());
    }


    private static Group groupOf(int number, String... colors)
    {
        Group group = new Group();
        for (String color : colors)
        {
            group.addTile(new Tile(number, color));
        }
        return group;
    }


    private static Run runOf(int first, int second, int third, String color)
    {
        Run run = new Run();
        run.addTile(new Tile(first, color));
        run.addTile(new Tile(second, color));
        run.addTile(new Tile(third, color));
        return run;
    }


    private static void addTilesToPlayer(Player player, Set set)
    {
        for (int i = 0; i < set.size(); i++)
        {
            player.drawTile(set.get(i));
        }
    }
}
