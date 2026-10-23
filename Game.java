
import java.util.Random;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Main class that creates Game object and runs Rummikub game.
 */
public class Game
{
    private LinkedChain<Tile> drawPile = new LinkedChain<Tile>();
    private LinkedChain<Set> table = new LinkedChain<Set>();
    private Player player1;
    private Player player2;
    private Scanner scanner;
    private boolean vsCom = false;

    // ----------------------------------------------------------
    /**
     * Create a new Game object.
     */
    public Game()
    {
        scanner = new Scanner(System.in);
        createTiles();
        shuffleDrawPile();
    }


    /**
     * Creates draw pile with random tiles.
     */
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


    // ----------------------------------------------------------
    /**
     * Shuffles order of tiles in draw pile to make random.
     */
    public void shuffleDrawPile()
    {
        Tile[] original = drawPile.toArray(new Tile[drawPile.size()]);
        drawPile.clear();
        Random r = new Random();
        for (int i = 0; i < original.length; i++)
        {
            int index = r.nextInt(original.length - i) + i;
            drawPile.addToEnd(original[index]);
            original[index] = original[i];
            original[i] = null;
        }
    }
    

    /**
     * Gets the number of sets currently on the table.
     *
     * @return the number of table sets
     */
    public int getTableSize()
    {
        return table.size();
    }


    /**
     * Gets a set from the table by its zero-based index.
     *
     * @param index 
     *          the index of the set
     * @return the set at that index, or null if out of range
     */
    public Set getTableSet(int index)
    {
        if (index < 0 || index >= table.size())
        {
            return null;
        }
    
        return table.get(index);
    }
    
    
    /**
     * Replaces a set on the table.
     *
     * @param index 
     *          the index of the set to replace
     * @param replacement 
     *          the new set
     * @return true if the replacement succeeds
     */
    public boolean replaceTableSet(int index, Set replacement)
    {
        if (index < 0 || index >= table.size()
            || replacement == null || !replacement.isValid())
        {
            return false;
        }

        LinkedChain<Set> newTable = new LinkedChain<Set>();

        for (int i = 0; i < table.size(); i++)
        {
            if (i == index)
            {
                newTable.addToEnd(replacement);
            }
            else
            {
                newTable.addToEnd(table.get(i));
            }
        }

        table = newTable;
        return true;
    }


    // ----------------------------------------------------------
    /**
     * After player plays set, set gets added to table.
     * 
     * @param set
     *            set of tiles played
     */
    public void addSetToTable(Set set)
    {
        if (set != null && set.isValid())
        {
            table.addToEnd(set);
        }
        else
        {
            System.out.println(
                "Set cannot be played. Play a different set or end your turn.");
        }
    }


    // ----------------------------------------------------------
    /**
     * Prints out all sets on table.
     */
    public void displayBoard()
    {
        System.out.println("Table:");
        if (table.isEmpty())
        {
            System.out.println("  (empty)");
            return;
        }

        for (int i = 0; i < table.size(); i++)
        {
            System.out.println("  [" + i + "] " + table.get(i));
        }
    }


    // ----------------------------------------------------------
    /**
     * Checks if either player has won and gotten rid of all tiles.
     * 
     * @return true if player 1 or player 2 won
     */
    public boolean checkWinner()
    {
        return player1 != null && player1.hasWon()
            || player2 != null && player2.hasWon();
    }


    // ----------------------------------------------------------
    /**
     * Gives each player 14 random tiles from the draw pile.
     */
    public void dealHands()
    {
        for (int i = 0; i < 14; i++)
        {
            player1.drawTile(drawFromPile());
            player2.drawTile(drawFromPile());
        }
    }


    // ----------------------------------------------------------
    /**
     * Removes tile from draw pile.
     * 
     * @return tile to remove
     */

    public Tile drawFromPile()
    {
        return drawPile.remove();
    }


    // ----------------------------------------------------------
    /**
     * Returns scanner for user input
     * 
     * @return scanner
     */

    public Scanner getInput()
    {
        return scanner;
    }


    // ----------------------------------------------------------
    /**
     * Overloaded method for play, uses false as the default parameter for
     * againstCom
     */
    public void play()
    {
        play(false);
    }


    // ----------------------------------------------------------
    /**
     * Main method handling overall gameplay, starting game and taking turns
     * until one player wins.
     */
    public void play(boolean againstCom)
    {
        vsCom = againstCom;
        System.out.println("Welcome to Rummikub!");
        System.out.print("Enter your name: ");
        player1 = new Player(getInput().nextLine());
        if (vsCom)
        {
            player2 = new Computer();
        }
        else
        {
            System.out.print("Enter Player 2's name: ");
            player2 = new Player(getInput().nextLine());
        }
        dealHands();

        while (true)
        {
            int player1Before = player1.getHand().size();
            int player2Before = player2.getHand().size();

            System.out.println();
            takePlayerTurn(player1);
            if (checkWinner())
            {
                break;
            }

            System.out.println();
            takePlayerTurn(player2);
            if (checkWinner())
            {
                break;
            }

            boolean noOneCanPlay =
                drawPile.isEmpty() && player1.getHand().size() == player1Before
                    && player2.getHand().size() == player2Before;
            if (noOneCanPlay)
            {
                System.out
                    .println("No one can play and the draw pile is empty.");
                break;
            }
        }
        announceWinner();
    }


    /**
     * Switches turn between players.
     */
    private void takePlayerTurn(Player currentPlayer)
    {
        if (vsCom)
        {
            if (currentPlayer == player1)
            {
                displayBoard();
            }
            currentPlayer.takeTurn(this);
        }
        else
        {
            displayBoard();
            System.out.println(
                "Pass the screen to " + currentPlayer.getName()
                    + ". Press Enter when ready.");
            getInput().nextLine();
            displayBoard();
            currentPlayer.takeTurn(this);
        }
    }


    /**
     * Prints winning message with winner's name unless no moves can be made.
     */
    private void announceWinner()
    {
        System.out.println();
        if (player1.hasWon())
        {
            System.out.println(
                player1.getName() + " played all their tiles and won!");
        }
        else if (player2.hasWon())
        {
            System.out.println(
                player2.getName() + " played all their tiles and won!");
        }
        else
        {
            int player1Points = player1.getHandValue();
            int player2Points = player2.getHandValue();
            System.out.println(
                "No more moves. " + player1.getName() + " has " + player1Points
                    + " points; " + player2.getName() + " has " + player2Points
                    + " points.");

            if (player1Points < player2Points)
            {
                System.out.println(player1.getName() + " wins!");
            }
            else if (player2Points < player1Points)
            {
                System.out.println(player2.getName() + " wins!");
            }
            else
            {
                System.out.println("It is a tie!");
            }
        }
    }


    /**
     * Main method that creates and runs Game class.
     * 
     * @param args
     *            arguments
     */
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play(true);
    }
}
