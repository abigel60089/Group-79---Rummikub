import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComputerTest
{
    @Test
    public void detectsAndPlaysThirtyPointInitialMeld()
    {
        Computer computer = new Computer("Computer");
        computer.drawTile(new Tile(9, "red"));
        computer.drawTile(new Tile(10, "red"));
        computer.drawTile(new Tile(11, "red"));

        assertTrue(computer.canPlay());
        computer.takeTurn(new Game());

        assertTrue(computer.hasInitialMeld());
        assertTrue(computer.hasWon());
    }
}
