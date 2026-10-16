import java.util.Random;
import java.util.Scanner;

public class Game
{
    private LinkedChain<Tile> drawPile = new LinkedChain<Tile>();
    private LinkedChain<Set> table = new LinkedChain<Set>();
    private Player player;
    private Computer computer;
    private Scanner scanner;
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
        Tile[] original = drawPile.toArray(new Tile[drawPile.size()]);
        drawPile.clear();
        Random r = new Random();
        for (int i = 0; i < drawPile.size(); i++)
        {
            int index = r.nextInt(drawPile.size() - i) + i;
            drawPile.add(original[index]);
            original[index] = original[i];
            original[i] = null;
        }
    }


    public void addSetToTable(Set set)
    {
        if (set.isValid())
        {
            table.add(set);
            player.playSet(set);
        }
        else
        {
            System.out.println(
                "Set cannot be played. Play a different set or end your turn.");
        }
    }
    
    public void displayBoard() {
        for (Set set : table.toArray(T[] tile.getData())) {
            System.out.print(set.toString());
            
        //not final;  needs a lot of fixing.
        }
    }
    
    public boolean checkWinner() {
        if (player.hasWon()) {
            return true;
        }
        
        return false;
    }
}
