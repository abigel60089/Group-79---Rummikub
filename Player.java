import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Player represents the human Rummikub player. A Player owns a hand of tiles
 * (stored in a LinkedChain<Tile>) and knows how to: 1 draw tiles into the hand
 * 2 play valid sets (groups or runs) from the hand onto the table 3 sort and
 * display the hand 4 report the hand's point value and whether the player has
 * won Game rules enforced here: 1 A set can only be played if it is valid AND
 * every tile in it is in the hand. 2 The first play of the game (the "initial
 * meld") must be worth at least 30 points. 3 If a player ends their turn
 * without playing anything, they must draw a tile. Computer extends this class
 * and reuses everything except takeTurn(), which it overrides with automatic
 * decision-making instead of keyboard input. NOTE: LinkedChain positions are
 * 1-based (first tile is at position 1).
 */
public class Player
{

    /** Points needed for a player's first play (the initial meld). */
    public static final int INITIAL_MELD_POINTS = 30;

    /**
     * display name
     */
    protected String name;

    /**
     * tiles in player's hand
     */
    protected LinkedChain<Tile> hand;

    /** True once the player has made their first play worth 30+ points. */
    protected boolean hasInitialMeld;

    /**
     * <1 set during this turn
     */
    protected boolean playedThisTurn;

    // =====================================================================
    // Constructor
    // =====================================================================

    /**
     * Creates a player with an empty hand. Logic: validate the name so a blank
     * name can't enter the game, then start with an empty hand. Tiles are dealt
     * later by Game.dealHands().
     *
     * @param name
     *            the player's display name
     * @throws IllegalArgumentException
     *             if name is null or blank
     */
    public Player(String name)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Player name cannot be empty");
        }
        this.name = name.trim();
        this.hand = new LinkedChain<>();
        this.hasInitialMeld = false;
        this.playedThisTurn = false;
    }

    // =====================================================================
    // Getters
    // =====================================================================


    /** @return the player's name */
    public String getName()
    {
        return name;
    }


    /** @return the player's hand (used by Computer and Game) */
    protected LinkedChain<Tile> getHand()
    {
        return hand;
    }


    /**
     * @return true if the player has already made their initial 30-point meld
     */
    public boolean hasInitialMeld()
    {
        return hasInitialMeld;
    }


    /**
     * Checks whether the player has won. Logic: in Rummikub you win by playing
     * every tile, so an empty hand = a win.
     *
     * @return true if the hand is empty
     */
    public boolean hasWon()
    {
        return hand.isEmpty();
    }


    // turn logic
    /**
     * Description: Runs one turn for the human player using console input. show
     * the hand and a menu in a loop. The player can play as many sets as they
     * like, sort their hand, or end the turn. When the turn ends, endTurn()
     * makes them draw a tile if they didn't play anything.
     *
     * @param g
     *            the current game (gives access to input, draw pile, and table)
     * @throws IllegalArgumentException
     *             if g is null
     */
    public void takeTurn(Game g)
    {
        if (g == null)
        {
            throw new IllegalArgumentException("Game cannot be null");
        }
        Scanner in = g.getInput();
        boolean turnOver = false;

        while (!turnOver && !hasWon())
        {
            System.out.println();
            displayHand();
            if (hasInitialMeld)
            {
                System.out.println(
                    "1) Play a set   2) Add tile to existing set   "
                    + "3) Sort hand  4) End turn");
                System.out.print("Choice: ");
                String choice = in.nextLine().trim();
                
                switch (choice)
                {
                    case "1":
                        promptAndPlaySet(g, in);
                        break;
                    case "2":
                        promptAddTileToTable(g, in);
                    case "3":
                        sortHand();
                        break;
                    case "4":
                        turnOver = true;
                        break;
                    default:
                        System.out.println("Please enter 1, 2, 3, or 4.");
                }
            } else {
                System.out.println("1) Play a set   2) Sort hand   "
                    + "3) End turn");
                
                System.out.print("Choice: ");
                String choice = in.nextLine().trim();
                
                switch (choice)
                {
                    case "1":
                        promptAndPlaySet(g, in);
                        break;
                    case "2":
                        sortHand();
                        break;
                    case "3":
                        turnOver = true;
                        break;
                    default:
                        System.out.println("Please enter 1, 2, or 3.");
                }
            }
            
        }
        endTurn(g);
    }


    /**
     * Description: Asks the user which tiles to play and what kind of set, then
     * tries to play it. Logic: 1 Ask for the set type (group or run) and create
     * an empty Group or Run. 2 Read tile positions like "1 4 7" and copy those
     * tiles from the hand into the new set. Reject positions that are out of
     * range, repeated, or not numbers. 3 Call playSet(). If it succeeds, give
     * the set to Game to put on the table. If it fails, explain why.
     */
    private void promptAndPlaySet(Game g, Scanner in)
    {
        LinkedChain<Set> turnSets = new LinkedChain<>();
        boolean addAnother = true;

        // Collect all sets the player wants to play this turn.
        while (addAnother)
        {
            System.out.print("Group or Run? (g/r): ");
            String type = in.nextLine().trim().toLowerCase();

            Set s;

            if (type.equals("g"))
            {

                s = new Group();
            }
            else if (type.equals("r"))
            {
                s = new Run();
            }
            else
            {
                System.out.println("Invalid type.");
                return;
            }

            System.out.print(
                "Enter tile positions separated by spaces (e.g. 0 3 6): ");

            String line = in.nextLine().trim();

            if (line.isEmpty())
            {
                System.out.println("No tiles entered.");
                return;
            }

            String[] parts = line.split("\\s+");
            boolean[] chosen = new boolean[hand.size()];

            try
            {
                for (String p : parts)
                {
                    int pos = Integer.parseInt(p);

                    if (pos < 0 || pos >= hand.size())
                    {
                        System.out
                            .println("Position " + pos + " is out of range.");
                        return;
                    }

                    if (chosen[pos])
                    {
                        System.out
                            .println("Position " + pos + " was entered twice.");
                        return;
                    }

                    chosen[pos] = true;
                    s.addTile(hand.get(pos));
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter numbers only.");
                return;
            }

            // Reject invalid groups or runs before submitting the play.
            if (!s.isValid())
            {
                System.out.println(
                    "That is not a valid "
                        + (type.equals("g") ? "group." : "run."));
                return;
            }

            turnSets.add(s);

            System.out.println("Set: " + s);
            System.out.println("Set points: " + s.getPoints());

            System.out.print("Add another set? (y/n): ");
            String answer = in.nextLine().trim().toLowerCase();

            if (!answer.equals("y"))
            {
                addAnother = false;
            }
        }

        // Calculate the combined points for the sets submitted this turn.
        int totalPoints = 0;

        for (int i = 0; i < turnSets.size(); i++)
        {
            totalPoints += turnSets.get(i).getPoints();
        }

        System.out.println("Total points: " + totalPoints);

        // Submit all sets together. playSets handles validation,
        // the initial meld requirement, and removing tiles from the hand.
        if (playSets(turnSets))
        {
            for (int i = 0; i < turnSets.size(); i++)
            {
                g.addSetToTable(turnSets.get(i));
            }

        }
        else if (!hasInitialMeld && totalPoints < INITIAL_MELD_POINTS)
        {
            System.out.println(
                "Your first play must total at least " + INITIAL_MELD_POINTS
                    + " points.");
        }
        else
        {
            System.out.println(
                "Could not play these sets. Make sure all tiles "
                    + "are in your hand and the sets are valid.");
        }
    }


    /**
     * description: finishes the player's turn. logic: Rummikub requires you to
     * draw if you didn't play anything this turn If the draw pile is empty, the
     * turn just ends. The playedThisTurn flag is reset so the next turn starts
     * fresh.
     *
     * @param g
     *            the current game (used to draw from the pile)
     * @return true if this player has won (empty hand), false otherwise
     * @throws IllegalArgumentException
     *             if g is null
     */
    public boolean endTurn(Game g)
    {
        if (g == null)
        {
            throw new IllegalArgumentException("Game cannot be null");
        }
        if (!playedThisTurn)
        {
            Tile t = g.drawFromPile();
            if (t != null)
            {
                drawTile(t);
                System.out.println(name + " drew a tile.");
            }
            else
            {
                System.out.println("Draw pile is empty.");
            }
        }
        playedThisTurn = false;
        return hasWon();
    }

    // ===================================================================
    // hand management
    // ====================================================================


    /**
     * Adds a tile to the player's hand.
     *
     * @param t
     *            the tile to add
     * @throws IllegalArgumentException
     *             if t is null
     */
    public void drawTile(Tile t)
    {
        if (t == null)
        {
            throw new IllegalArgumentException("Tile cannot be null");
        }
        hand.add(t);
    }


    /**
     * Description: Plays one set from the hand. Logic: wraps the set in a
     * one-item chain and passes it to playSets(), so both methods follow
     * exactly the same rules. The caller (Game) is responsible for putting the
     * set on the table when this returns true.
     *
     * @param s
     *            the set to play
     * @return true if the set was played, false if it was rejected
     */
    public boolean playSet(Set s)
    {
        if (s == null)
        {
            return false;
        }
        LinkedChain<Set> single = new LinkedChain<>();
        single.add(s);
        return playSets(single);
    }


    /**
     * Description: Plays several sets from the hand at once (all or nothing).
     * This exists so the initial meld can combine sets: for example a group
     * worth 21 plus a run worth 15 = 36, which meets the 30-point rule even
     * though neither set does on its own. Logic: 1. Every set must be non-empty
     * and valid. 2. If this is the initial meld, the sets' combined points must
     * be >= 30. 3. Every tile in every set must be in the hand. The used[]
     * array marks which hand positions are already matched, so one hand tile
     * can't be counted twice (e.g. a set needing two R7s requires two R7s in
     * hand). 4. Only after everything checks out, remove the matched tiles.
     * Removing from the highest position down keeps lower positions from
     * shifting.
     *
     * @param sets
     *            the sets to play
     * @return true if all sets were played, false if any check failed (hand
     *             unchanged)
     */
    public boolean playSets(LinkedChain<Set> sets)
    {
        if (sets == null || sets.isEmpty())
        {
            return false;
        }

        // Step 1 and 2: every set valid, and total points for the initial meld
        int totalPoints = 0;
        for (int i = 0; i < sets.size(); i++)
        {
            Set s = sets.get(i);
            if (s == null || s.isEmpty() || !s.isValid())
            {
                return false;
            }
            totalPoints += s.getPoints();

            System.out.println("Set: " + s);
            System.out.println("Set points: " + s.getPoints());
        }

        System.out.println("Total initial meld points: " + totalPoints);

        if (!hasInitialMeld && totalPoints < INITIAL_MELD_POINTS)
        {
            return false;
        }

        // Step 3: match every tile in every set to an unused tile in the hand
        int handSize = hand.size();
        boolean[] used = new boolean[handSize];          // index
                                                         // 1..handSize
        for (int i = 0; i < sets.size(); i++)
        {
            Set s = sets.get(i);
            for (int k = 0; k < s.size(); k++)
            {
                Tile needed = s.get(k);
                boolean found = false;
                for (int j = 0; j < handSize && !found; j++)
                {
                    if (!used[j] && hand.get(j).equals(needed))
                    {
                        used[j] = true;
                        found = true;
                    }
                }
                if (!found)
                {
                    return false;                            // tile not in hand
                }
            }
        }

        // Step 4: remove matched tiles, highest position first
        for (int j = handSize - 1; j >= 0; j--)
        {
            if (used[j])
            {
                hand.remove(hand.get(j));
            }
        }

        hasInitialMeld = true;
        playedThisTurn = true;
        System.out.println("Has initial meld: " + hasInitialMeld);
        return true;
    }

    //kept in case someone wants to revert
//
//    private void removeTileAt(int index)
//    {
//        LinkedChain<Tile> newHand = new LinkedChain<Tile>();
//
//        for (int i = 0; i < hand.size(); i++)
//        {
//            if (i != index)
//            {
//                newHand.addToEnd(hand.get(i));
//            }
//        }
//
//        hand = newHand;
//    }


    public boolean addTileToTableSet(Game game, int handIndex, int tableIndex)
    {
        if (!hasInitialMeld)
        {
            System.out.println(
                "You must complete your initial turn first.");
            return false;
        }
        
        if (handIndex < 0 || handIndex >= hand.size())
        {
            return false;
        }
        
        if (tableIndex < 0 || tableIndex >= game.getTableSize())
        {
            return false;
        }
        
        Tile tile = hand.get(handIndex);
        Set original = game.getTableSet(tableIndex);

        if (original == null)
        {
            return false;
        }

        // Make a copy so an invalid move doesn't change
        // the original table set.
        Set updated = original.copy();

        updated.addTile(tile);

        if (!updated.isValid())
        {
            System.out.println("That tile cannot be added to this set.");
            return false;
        }

        if (!game.replaceTableSet(tableIndex, updated))
        {
            System.out.println("Table not able to be updated.");
            return false;
        }

        hand.remove(tile);
        playedThisTurn = true;
        return true;
    }
    
    
    private void promptAddTileToTable(Game g, Scanner in)
    {
        System.out.print("Enter hand tile index: ");

        int handIndex;
        try
        {
            handIndex = Integer.parseInt(in.nextLine().trim());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please enter a valid number.");
            return;
        }

        System.out.print("Enter table set index: ");

        int tableSetIndex;
        try
        {
            tableSetIndex = Integer.parseInt(in.nextLine().trim());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please enter a valid number.");
            return;
        }

        if (!addTileToTableSet(g, handIndex, tableSetIndex))
        {
            System.out.println("Could not add that tile.");
        }
        else
        {
            System.out.println("Tile added successfully!");
        }
    }


    /**
     * Sorts the hand by color, THEN by number (e.g. R1 R5 R9 B2 B7 ...). Logic:
     * a linked chain can't be sorted efficiently in place, so: 1. Move every
     * tile out of the chain into a regular array. 2. Sort the array (Java's
     * TimSort, O(n log n)). 3. Add the tiles back to the chain in sorted order.
     */
    public void sortHand()
    {
        Tile[] tiles = new Tile[hand.size()];
        for (int i = 0; i < tiles.length; i++)
        {
            tiles[i] = hand.remove();                       // always take the
                                                            // front tile
        }
        Arrays.sort(tiles, BY_COLOR_THEN_NUMBER);
        for (Tile t : tiles)
        {
            hand.add(t);
        }
    }


    /**
     * Adds up the numbers of all tiles left in the hand. Logic: used for
     * end-of-game scoring, where leftover tiles count against you.
     *
     * @return the total point value of the hand
     */
    public int getHandValue()
    {
        int total = 0;
        for (int i = 0; i < hand.size(); i++)
        {
            total += hand.get(i).getNumber();
        }
        return total;
    }


    /**
     * Prints the hand with position numbers, e.g. "1:R7 2:B3 3:K12". Logic: the
     * position numbers are what the user types to choose tiles in
     * promptAndPlaySet(), so they must match the chain's 1-based positions.
     */
    public void displayHand()
    {
        System.out.println(name + "'s hand (" + hand.size() + " tiles):");
        if (hand.isEmpty())
        {
            System.out.println("  (empty)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hand.size(); i++)
        {
            sb.append(i).append(":").append(hand.get(i)).append("  ");
        }
        System.out.println(sb.toString().trim());
    }

    // =====================================================================
    // Helpers shared with Computer
    // ====================================================================

    /** Orders tiles by color (enum order), then by number. */
    protected static final Comparator<Tile> BY_COLOR_THEN_NUMBER =
        Comparator.comparing(Tile::getColor).thenComparingInt(Tile::getNumber);

    /** Orders tiles by number, then by color. */
    protected static final Comparator<Tile> BY_NUMBER_THEN_COLOR =
        Comparator.comparingInt(Tile::getNumber).thenComparing(Tile::getColor);

    /**
     * Copies the hand into a Tile array without changing the hand. Logic: we
     * build the array ourselves instead of calling hand.toArray(), because a
     * generic toArray() often returns Object[], which throws a
     * ClassCastException when assigned to Tile[].
     *
     * @return a new array containing the hand's tiles in order
     */
    protected Tile[] handToArray()
    {
        return hand.toArray(new Tile[hand.size()]);
    }


    /** @return the player's name and how many tiles they hold */
    @Override
    public String toString()
    {
        return name + " (" + hand.size() + " tiles)";
    }
}
