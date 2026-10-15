import java.util.Random;

public class Game
{
    private LinkedChain<Tile> drawPile = new LinkedChain<Tile>();
    private LinkedChain<Set> table = new LinkedChain<Set>();
    private Player player;
    private Computer computer;
    // private ? scanner;
    private boolean gameOver = false;

    public Game()
    {
        createTiles();
        shuffleDrawPile();
        // we need the player's name before creating the object
        player = null;
        computer = null;
    }


    private void createTiles()
    {
        String[] colors = { "black", "red", "yellow", "blue" };
        for (String color : colors)
        {
            for (int num = 1; num < 14; num++)
            {
                drawPile.add(new Tile(num, color));
                drawPile.add(new Tile(num, color));
            }
        }
        drawPile.add(new Tile(14, "black"));
        drawPile.add(new Tile(14, "black"));
    }


    public void shuffleDrawPile()
    {
        LinkedChain<Tile> randomPile = new LinkedChain<Tile>();
        Tile[] original = drawPile.toArray(new Tile[drawPile.size()]);
        Random r = new Random();
        for (int i = 0; i < drawPile.size(); i++)
        {
            int index = r.nextInt(drawPile.size() - i) + i;
            randomPile.add(original[index]);
            original[index] = original[i];
            original[i] = null;
        }
    }
}
