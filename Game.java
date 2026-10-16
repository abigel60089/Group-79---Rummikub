import java.util.Random;
import java.util.Scanner;

public class Game
{
    private LinkedChain<Tile> drawPile = new LinkedChain<Tile>();
    private LinkedChain<Set> table = new LinkedChain<Set>();
    private Player player1;
    private Player player2;
    private Scanner scanner;

    public Game()
    {
        scanner = new Scanner(System.in);
        createTiles();
        shuffleDrawPile();
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
        for (int i = 0; i < original.length; i++)
        {
            int index = r.nextInt(original.length - i) + i;
            drawPile.addToEnd(original[index]);
            original[index] = original[i];
            original[i] = null;
        }
    }


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
            System.out.println("  " + table.get(i));
        }
    }

    public boolean checkWinner()
    {
        return player1 != null && player1.hasWon()
            || player2 != null && player2.hasWon();
    }

    public void dealHands()
    {
        for (int i = 0; i < 14; i++)
        {
            player1.drawTile(drawFromPile());
            player2.drawTile(drawFromPile());
        }
    }

    public Tile drawFromPile()
    {
        return drawPile.remove();
    }

    public Scanner getInput()
    {
        return scanner;
    }

    public void play()
    {
        System.out.println("Welcome to Rummikub!");
        System.out.print("Enter Player 1's name: ");
        player1 = new Player(getInput().nextLine());
        System.out.print("Enter Player 2's name: ");
        player2 = new Player(getInput().nextLine());
        dealHands();

        while (true)
        {
            int player1Before = player1.getHand().size();
            int player2Before = player2.getHand().size();

            System.out.println();
            displayBoard();
            takePlayerTurn(player1);
            if (checkWinner())
            {
                break;
            }

            System.out.println();
            displayBoard();
            takePlayerTurn(player2);
            if (checkWinner())
            {
                break;
            }

            boolean noOneCanPlay = drawPile.isEmpty() && player1.getHand().size() == player1Before && player2.getHand().size() == player2Before;
            if (noOneCanPlay)
            {
                System.out.println(
                    "No one can play and the draw pile is empty.");
                break;
            }
        }
        announceWinner();
    }

    private void takePlayerTurn(Player currentPlayer)
    {
        System.out.println("Pass the screen to " + currentPlayer.getName() + ". Press Enter when ready.");
        getInput().nextLine();
        displayBoard();
        currentPlayer.takeTurn(this);
    }

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
            System.out.println("No more moves. " + player1.getName() + " has " + player1Points + " points; " + player2.getName() + " has " + player2Points + " points.");

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

    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
}
